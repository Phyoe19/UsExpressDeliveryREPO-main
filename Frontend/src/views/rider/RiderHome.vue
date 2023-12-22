<template>
  <div>
    <div v-if="trackRiderData.latitude != null && trackRiderData.longitude != null">

      <UserNavBar />

      <div style="100px">

      </div>
      <h3 class="mt-15">Rider id: {{ trackRiderData.riderId }}</h3>

      <div v-if="show" class="mt-1">
        <div v-if="trackRiderData.latitude != null">
          <LeafletMap :currentLocation="[trackRiderData.latitude, trackRiderData.longitude]" />
        </div>
        
        <!--History-->
        <v-data-table :items="formattedConfirmDeliveries" :headers="confirmHeaders" :items-per-page="5" class="elevation-1"/>
        

      </div>

      <div v-if="requestDeliveryArray.length > 0">

        <div v-for="requestDelivery in requestDeliveryArray" :key="requestDelivery.preDeliId">
          <div class="delivery-card" v-if="requestDelivery">
            <v-card class="mx-auto" max-width="344">

              <div v-if="requestDelivery.pickupLat != null && requestDelivery.pickupLng != null">
                <LRoutingMachine :sCor="[requestDelivery.pickupLat, requestDelivery.pickupLng]"
                  :eCor="[requestDelivery.destinationLat, requestDelivery.destinationLng]" />
              </div>

              <v-card-title>
                Request Delivery ID: {{ requestDelivery.preDeliId }}
              </v-card-title>

              <v-card-subtitle class="text-h5">
                Pickup address: {{ requestDelivery.pickupAddressText }}
              </v-card-subtitle>

              <v-card-actions>
                <v-btn color="success lighten-2" text @click="handleConfirmDelivery(requestDelivery)">
                  Confirm
                </v-btn>
                <v-btn color="warning lighten-2" text @click="cancelRequestDelivery(requestDelivery)">
                  Cancel
                </v-btn>

                <v-spacer></v-spacer>

                <v-btn icon @click="showExtend = !showExtend">
                  <v-icon>{{ showExtend ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
                </v-btn>
              </v-card-actions>

              <v-expand-transition>
                <div v-show="showExtend">
                  <v-divider></v-divider>

                  <v-card-text>
                    Customer name: {{ customerName }}<br>
                    Receiver name: {{ requestDelivery.receiverName }}<br>
                    Receiver Phone Number: {{ requestDelivery.receiverPhone }}<br>
                  </v-card-text>
                </div>
              </v-expand-transition>
            </v-card>
          </div>
        </div>
      </div>


      <div v-if="ongoingDelivery != null" class="my-1">
        <v-card class="mx-auto" max-width="344" outlined>
          <v-list-item three-line>
            <v-list-item-content>
              <div class="text-overline mb-4">
                Ongoing Delivery
              </div>
              <v-list-item-title class="text-h5 mb-1">
                Delivery ID: {{ ongoingDelivery.deliveryId }}
              </v-list-item-title>
              <v-list-item-subtitle>{{ ongoingDelivery.pickupAddressText }}</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-avatar tile size="80" color="grey">
              <v-avatar rounded="0" size="80"><v-img src="../../assets/profileAvatar.jpg"></v-img></v-avatar>
            </v-list-item-avatar>
          </v-list-item>

          <v-card-actions>
            <v-btn outlined rounded text @click="navigateToRiderDeliveryNavigate" color="#000">
              Button
            </v-btn>
          </v-card-actions>
        </v-card>
      </div>


    </div>

    <div v-else>
      <LoadingComp />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { API_KEY } from '@/utils/apiKey';
import { connectWebSocket, sendMessage, subscribeToDestination } from '@/utils/websocketconfig';
import LeafletMap from '../leafletMap/LeafletMap.vue';
import LRoutingMachine from '../leafletMap/LRoutingMachine.vue';
import L from 'leaflet';
import LoadingComp from '@/components/LoadingComp.vue';
import { mapGetters } from 'vuex';
import UserNavBar from '@/components/UserNavBar.vue';

export default {
  components: {
    LeafletMap,
    LRoutingMachine,
    LoadingComp,
    UserNavBar,
  },

  data() {
    return {

      historyDeliveries: [],

      confirmHeaders:[
      { text: "Delivery ID", value: "deliveryId" },
        { text: "Receiver Name", value: "receiverName" },
        { text: "Receiver Phone", value: "receiverPhone" },
        { text: "Sender Name", value: "senderName" },
        { text: "From", value: "from" },
        { text: "To", value: "to" },
        { text: "Cost", value: "cost" },
      ],

      riderData: {},

      ongoingDelivery: null,

      requestDeliveryArray: [],

      customerName: '',
      seCors: [],

      showExtend: false,
      show: true,

      confirmDelivery: {
        preDeliId: '',
        riderId: this.$route.params.id,
      },

      trackRiderData: {
        deliveryId: 0,
        preDelivery: null,
        riderId: this.$route.params.id,
        latitude: null,
        longitude: null,
        deliStatus: '',
        postalCode: '',
        status: 'OFFLINE',
      },
      riderMarkerIcon: L.icon({
        iconUrl: require('@/assets/riderbikeicon.png'),
        iconSize: [50, 50],
      }),
    };
  },

  created() {

    console.log("User data: ", this.userData)

    this.trackRiderData.deliStatus = this.userData.deliStatus

    

       

  },

  computed: {
    ...mapGetters(['userData', 'delivery']),
    formattedConfirmDeliveries(){
      return this.historyDeliveries.map((delivery) => ({
        deliveryId: delivery.deliveryId,
        senderName: delivery.customer.name, 
        receiverName: delivery.receiverName,
        receiverPhone: delivery.receiverPhone,
        from: delivery.pickupAddressText,
        to: delivery.destinationAddressText,
        cost: delivery.cost,
      }));
    }
  },

  mounted() {
    connectWebSocket(this.subscribeWebSocketMessage);
    this.trackRiderLocation();
    window.addEventListener('beforeunload', this.handleBeforeUnload);

    this.getDelivery();
    this.getDeliveryHistory();
  },

  methods: {

    async cancelRequestDelivery(requestDelivery){
      this.requestDeliveryArray.pop(requestDelivery);
      this.show = true;
      this.trackRiderData.deliStatus = 'FREE';
      this.trackRiderData.preDelivery = null;

      const riderData = this.userData;

      
      
      await axios.put("http://localhost:7071/rider/update", riderData)
      .then(response => {
        const riderRespData = response.data;
        
        this.trackRiderData.preDelivery = null;
        this.trackRiderData.deliStatus = riderRespData.deliStatus;
      })

      riderData.deliStatus = 'FREE';
      this.sendWebSocketMessage(this.trackRiderData)

    },

    navigateToRiderDeliveryNavigate() {
      this.$router.push({ name: 'RiderDeliveryNavigate', params: { id: this.trackRiderData.riderId } })
    },

    async getDelivery() {
      await axios.get(`http://localhost:7071/rider/delivery-ongoing/${this.trackRiderData.riderId}`)
        .then(response => {
          const respData = response.data;
          this.ongoingDelivery = respData;
          this.$store.commit('setDelivery', this.ongoingDelivery);
        }).catch(error => console.log("No current delivery",error));
    },

    getPostalCode(lat, lng) {
      const language = 'my';
      return new Promise((resolve, reject) => {
        axios
          .get(
            `https://api.opencagedata.com/geocode/v1/json?key=${API_KEY}&q=${lat}+${lng}&language=${language}`
          )
          .then((response) => {
            const result = response.data.results[0].components;
            if (result) {
              const { postcode } = result;
              console.log(postcode);
              this.trackRiderData.postalCode = postcode;
              resolve(postcode);
            } else {
              reject(new Error('Unable to get postal code'));
            }
          })
          .catch((error) => {
            reject(error);
          });
      });
    },

    trackRiderLocation() {
      //testing getCurrentPosition //Production watchPosition
      navigator.geolocation.watchPosition(
        (position) => {
          this.trackRiderData.latitude = position.coords.latitude;
          this.trackRiderData.longitude = position.coords.longitude;
          this.getPostalCode(
            this.trackRiderData.latitude,
            this.trackRiderData.longitude
          );

          console.log(this.trackRiderData);
          this.sendWebSocketMessage(this.trackRiderData);
        },
        (error) => {
          console.error(error);
        }
      );
    },

    setRiderDeliStatus(deliStatus) {
      this.trackRiderData.deliStatus = deliStatus;
    },

    setRiderStatus(status) {
      this.trackRiderData.status = status;
    },

    async handleConfirmDelivery(requestDelivery) {

      await axios.post('http://localhost:7071/delivery/confirm', 
      {
        preDeliId: requestDelivery.preDeliId,
        riderId: this.$route.params.id,
      })
        .then(response => {

          const respData = response.data;

          this.trackRiderData.deliveryId = respData.deliveryId;

          console.log("Rider track data: ", this.trackRiderData);

          this.trackRiderData.deliStatus = 'DELIVERING';
          this.sendWebSocketMessage(this.trackRiderData)

          this.$store.commit('setDelivery', respData);

        }).catch(error => console.error(error));

        this.trackRiderData.preDelivery = null;
        this.trackRiderData.deliveryId = this.delivery.deliveryId;
        this.$router.push({name: 'RiderDeliveryNavigate', params: {id: this.trackRiderData.riderId }})

    },

    sendWebSocketMessage(rider) {
      this.setRiderStatus('ONLINE');

      console.log('Track Rider data:', rider);
      sendMessage('/app/track-rider', JSON.stringify(rider));
    },

    subscribeWebSocketMessage() {
      subscribeToDestination('/package-delivery/rider/get-pre-delivery/' + this.trackRiderData.riderId, this.handleRequestDelivery);
    },

    handleRequestDelivery(message) {
      const preDeliveryData = JSON.parse(message.body);

        // If the rider is currently free, update the preDeliveryData and deliStatus
        this.trackRiderData.preDelivery = preDeliveryData;
        this.confirmDelivery.preDeliId = preDeliveryData.preDeliId;
        this.requestDeliveryArray.push(preDeliveryData);
        this.customerName = this.getCustomerName(preDeliveryData.customerId);
        

        if(this.delivery != null){
          this.setRiderDeliStatus("DELIVERING")
        }else{
          this.setRiderDeliStatus("PENDING")
        }
          
        
        this.sendWebSocketMessage(this.trackRiderData);
        console.log("whyyy: ", this.trackRiderData)
        this.show = false;
        
        
     
    },

    // updateRiderDeliStatus(){
    //   axios.get
    // },

    async getDeliveryHistory(){
      await axios.get('http://localhost:7071/rider/getHistory/' + this.userData.riderId)
      .then(response => {
        const respData = response.data;
        this.historyDeliveries = respData;
      })
    },
  


    async getCustomerName(id) {
      await axios.get("http://localhost:7071/customer/getById/" + id)
        .then(response => {
          const responseData = response.data;
          const customerName = responseData.name;
          return customerName;
        }).catch(error => console.error(error));
    },

    handleBeforeUnload(event) {
      event.preventDefault();
      this.setRiderStatus('OFFLINE');
      sendMessage('/app/track-rider', JSON.stringify(this.trackRiderData));
    },
  },

  beforeDestroy() {

    this.setRiderStatus('OFFLINE');
    sendMessage('/app/track-rider', JSON.stringify(this.trackRiderData));
    window.removeEventListener('beforeunload', this.handleBeforeUnload);
  },
};
</script>

<style scoped>
.riderNavigate {
  width: 500px;
  height: 500px;
}
</style>
