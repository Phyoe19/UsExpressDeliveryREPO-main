<template>
  <div>

    <UserNavBar />

    <v-container class="mt-15">
      <v-row>
        <v-col>
          <div>
            <v-card class="mx-auto" max-width="344" outlined height="auto">
              <v-list-item three-line>
                <v-list-item-content>
                  <div class="text-overline mb-4">
                    Customer Delivery
                  </div>
                  <v-list-item-title class="text-h5 mb-5">
                    Delivery ID: {{ deliveryData.deliveryId }}
                  </v-list-item-title>
                  <v-list-item-subtitle class="text-subtitle-1 mb-3">
                    To: {{ deliveryData.destinationAddressText }}
                  </v-list-item-subtitle>
                  <v-list-item-subtitle class="text-subtitle-1 mb-3">
                    Recipient Name: {{ deliveryData.receiverName }}
                  </v-list-item-subtitle>
                  <v-list-item-subtitle class="text-subtitle-1 mb-3">
                    Phone no: {{ deliveryData.receiverPhone }}
                  </v-list-item-subtitle>
                  <v-list-item-subtitle class="text-subtitle-1 mb-3">
                    Payment: Cash On Delivery
                  </v-list-item-subtitle>
                </v-list-item-content>

                <v-list-item-avatar tile size="80" color="grey">
                  <v-avatar rounded="0" size="80"><v-img src="../../assets/profileAvatar.jpg"></v-img></v-avatar>
                </v-list-item-avatar>
              </v-list-item>

              <v-card-actions>
                <v-btn outlined rounded text @click="navigateToRiderDeliveryCompleted" color="#000">
                  Done
                </v-btn>
                <v-btn outlined rounded text @click="navigateToRiderReport" color="#000">
                  Fail
                </v-btn>
              </v-card-actions>
            </v-card>
          </div>
        </v-col>
        <div>
        </div>
        <v-col>
          <div v-if="trackRiderData.latitude != null && trackRiderData.longitude != null">
            <LRoutingMachine :sCor="[trackRiderData.latitude, trackRiderData.longitude]"
              :eCor="[deliveryData.destinationLat, deliveryData.destinationLng]" />
          </div>

          <div v-if="trackRiderData.latitude == null || trackRiderData.longitude == null">
            <LoadingComp />
          </div>
        </v-col>
      </v-row>
    </v-container>
    <v-snackbar v-model="hasCompleted" :timeout="2000" attach position="absolute" location="bottom left">
        Delivery Completed
      </v-snackbar>
      
  </div>
</template>

<script>
import axios from 'axios';
import LRoutingMachine from '../leafletMap/LRoutingMachine.vue';
import { mapGetters } from 'vuex';
// import { connectWebSocket, sendMessage } from '@/utils/websocketconfig';
// import { API_KEY } from '@/utils/apiKey';
import UserNavBar from '@/components/UserNavBar.vue';
import LoadingComp from '@/components/LoadingComp.vue';
import { disconnectWebSocket } from '@/utils/websocketconfig';




export default {
  components: {
    LRoutingMachine,
    UserNavBar,
    LoadingComp,
  },

  data() {
    return {
      hasCompleted: false,
      riderData: {},
      deliveryData: {},
      trackRiderData: {
        riderId: this.$route.params.id,
        latitude: null,
        longitude: null,
      },
    };
  },

  computed: {
    ...mapGetters(['delivery', 'userData']),
  },

  created() {
    this.deliveryData = this.delivery;
    this.riderData = this.userData;
    console.log('Delivery Data: ', this.deliveryData);
  },

  mounted(){
    this.getRiderLocation();
  },

  methods: {

    getRiderLocation() {
      navigator.geolocation.watchPosition(pos => {
        this.trackRiderData.latitude = pos.coords.latitude;
        this.trackRiderData.longitude = pos.coords.longitude;

        console.log(this.trackRiderData.latitude, ", ", this.trackRiderData.longitude, ", ", this.deliveryData.destinationLat, ", ", this.deliveryData.destinationLng)
      },
        (error) => {
          console.error(error);
        }
      )
    },

    async navigateToRiderDeliveryCompleted() {
      this.deliveryData.cdStatus = "COMPLETED";
      await axios.put("http://localhost:7071/delivery/update", this.deliveryData)
      .then(response => {
        const DeliData = response.data;
        this.$store.commit('setDelivery', null)
        console.log(DeliData);
      })
    
      this.riderData.deliStatus = "FREE"
      await axios.put("http://localhost:7071/rider/update", this.riderData)
      .then(response =>{
        const riderUpdatedData =  response.data;
        console.log("Rider updated Data: ", riderUpdatedData);
        this.$store.commit('setUserData', riderUpdatedData)
      })

      this.hasCompleted = true;

      disconnectWebSocket(this.navigateToCompleteDelivery());
      
      
      
    },

    navigateToCompleteDelivery(){
      this.$router.push({name: 'RiderCompleteDelivery', params:{id: this.trackRiderData.riderId}})
    },

    navigateToReport(){
      this.$router.push({name: 'RiderReport', params:{id: this.trackRiderData.riderId}})
    },

    async navigateToRiderReport() {

      this.deliveryData.cdStatus = "FAILED";
      await axios.put("http://localhost:7071/delivery/update", this.deliveryData)
      .then(response => {
        const DeliData = response.data;
        this.$store.commit('setDelivery', null);
        console.log(DeliData);
      })
    
      this.riderData.deliStatus = "FREE"
      await axios.put("http://localhost:7071/rider/update", this.riderData)
      .then(response =>{
        const riderUpdatedData =  response.data;
        console.log("Rider updated Data: ", riderUpdatedData);
        this.$store.commit('setUserData', riderUpdatedData)
      })

    this.navigateToReport();
      
    },

    

  }
};
</script>

<style></style>
