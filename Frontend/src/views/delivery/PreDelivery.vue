<template>
  <div>
    <CustomerNavBar :customerId="preDelivery.customerId"/>
    <v-row style="margin-top: 5rem;">

      <v-col cols="12" lg="6" md="12">
        <v-card class="pa-10 ml-9" style="
  border-radius: 50px;
  box-shadow: 4px 4px 4px 2px rgba(0, 0, 0, 0.25) inset,
    0px 4px 4px 0px rgba(0, 0, 0, 0.25);
    height: 90vh;
">
          <h3>Delivery Request</h3>

          <v-row>
            <v-card-text>
              <v-form @submit.prevent="submitRequestDelivery">
                <v-row>
                  <v-col cols="12" lg="6" md="6" class="inputCol">
                    <v-text-field v-model="preDelivery.receiverName" label="Reciever Name" required
                      outlined></v-text-field>
                  </v-col>
                  <v-col cols="12" lg="6" md="6" class="inputCol">
                    <v-text-field v-model="preDelivery.receiverPhone" label="Receiver Phone Number" required
                      outlined></v-text-field>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12" lg="6" md="6" class="inputCol">
                    <v-text-field v-model="preDelivery.itemWeight" label="Item Weight (KG)" required
                      outlined></v-text-field>
                  </v-col>
                  <v-col cols="12" lg="6" md="6" class="inputCol">
                    <v-text-field v-model="detailsAddress" label="Reciever Building No,Street Name" required
                      outlined>Reciever Building No,Street Name</v-text-field>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12" lg="6" md="6" class="inputCol">
                    <v-text-field v-model="preDelivery.pickupAddressText" label="Pick Up Address" required outlined
                      readonly></v-text-field>
                  </v-col>
                  <v-col cols="12" lg="6" md="6" class="inputCol">
                    <v-text-field v-model="preDelivery.destinationAddressText" label="Reciever Address" required outlined
                      readonly></v-text-field>
                  </v-col>
                </v-row>
                <v-row>

                  <v-col cols="12" lg="6" md="6" class="inputCol" v-if="preDelivery.distance">
                    <v-text-field :value="calCost()" label="Cost" required outlined readonly></v-text-field>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col cols="12" lg="6" md="6" class="inputCol">
                    <v-text-field v-model="preDelivery.distance" label="Distance (Km)" required outlined
                      readonly></v-text-field>
                  </v-col>
                  <v-col cols="12" lg="6" md="6" class="inputCol">
                    <!-- <p>{{ radios || 'null' }}</p> -->
                    <v-radio-group v-model="radios" mandatory style="padding: 0; margin-top: 0;">
                      <v-radio label="Cash on Delivery" value="radio-1"></v-radio>
                    </v-radio-group>


                  </v-col>

                </v-row>

                <v-row>
                  <v-col cols="12" lg="6" md="6" class="inputCol">
                    <v-btn type="confirm" color="grey">Confirm</v-btn>
                  </v-col>
                </v-row>

              </v-form>
            </v-card-text>
          </v-row>


        </v-card>
      </v-col>

      <v-col cols="12" lg="6" md="12">

        <div>

        </div>

        <div>
          <div class="map" v-if="true">
            <l-map style="height: 90vh; border-radius: 30px;" :zoom="mapProp.zoom"
              :center="[preDelivery.pickupLat, preDelivery.pickupLng]" @click="handleMapClick">
              <l-tile-layer :url="mapProp.url" :attribution="mapProp.attribution"></l-tile-layer>

              <!-- <l-circle :lat-lng="[preDelivery.pickupLat, preDelivery.pickupLng]" :radius="mapProp.circle.radius"
            :color="mapProp.circle.color" /> -->
              <l-marker :lat-lng="[preDelivery.pickupLat, preDelivery.pickupLng]"
                :icon="mapProp.pickupMarkerIcon"></l-marker>
              <l-marker :lat-lng="[preDelivery.destinationLat, preDelivery.destinationLng]"
                :icon="mapProp.destinationMarker" :draggable="true" @dragend="handleMarkerDragEnd($event)">
              </l-marker>
            </l-map>
          </div>
        </div>


      </v-col>

    </v-row>

  </div>
</template>



<script>
import CustomerNavBar from '../../components/UserNavBar.vue'
import L from 'leaflet';
import { LMap, LTileLayer, LMarker } from 'vue2-leaflet';
import axios from 'axios';
import { API_KEY } from '../../utils/apiKey.js'//src\utils\apiKey.js
const language = 'my';
export default {
  components: {
    LMap,
    LTileLayer,
    LMarker,
    CustomerNavBar,
  },
  data() {
    return {
      radios: null,

      detailsAddress: '',
      preDeliId: '',
      preDelivery: {
        receiverName: '',
        receiverPhone: '',
        itemWeight: '',
        distance: '',
        cost: '',
        pickupLat: '',
        pickupLng: '',
        destinationLat: '',
        destinationLng: '',
        pickupAddressText: null,
        destinationAddressText: '',
        postalCode: '',
        customerId: this.$route.params.id,

      },
      mapProp: {
        url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',//'https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png'
        attribution:
          '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
        zoom: 15,
        // circle: {
        //   radius: 1000, //metre
        //   color: 'blue',
        // },
        destinationMarker: L.icon({
          iconUrl: require('@/assets/destinationMarker.png'),
          iconSize: [50, 50],
        }),
        pickupMarkerIcon: L.icon({
          iconUrl: require('@/assets/pickupMarker.png'),
          iconSize: [50, 50],
        }),
      },

    }
  },
  created() {
    navigator.geolocation.getCurrentPosition(pos => {
      const { latitude, longitude } = pos.coords;
      //assign requestDelivery object pickupaddress
      this.preDelivery.pickupLat = latitude;
      this.preDelivery.pickupLng = longitude;

      this.getPickUpAddress(latitude, longitude);

    });
  },

  methods: {

    calCost(){
      let kg = this.preDelivery.itemWeight;
      let km = this.preDelivery.distance;

      if(kg < 10.00){
        kg = 1.00;
      }else{
        kg = kg/10;
      }

      if(km < 2.00){
        km = 1;
      }else{
        km = km/2;
      }

      const cost = (kg * km) * 1000;

      this.preDelivery.cost = cost;
      return cost.toFixed(2);

    },

    async getPickUpAddress(latitude, longitude) {
      try {
        const address = await this.getAddressResult(latitude, longitude);
        this.preDelivery.pickupAddressText = address[0] + " " + address[1] + " " + address[2];
        this.preDelivery.postalCode = address[3]
      } catch (error) {
        console.error(error);
        // Handle error
      }

    },

    async getDestinationAddress(latitude, longitude) {
      try {
        const address = await this.getAddressResult(latitude, longitude);
        this.preDelivery.destinationAddressText = address[0] + " " + address[1] + " " + address[2];
      } catch (error) {
        console.error(error);
        // Handle error
      }
    },

    handleMapClick(e) {
      const { lat, lng } = e.latlng;

      //assign secondmarker which is an array to mark on the map
      console.log(e.latlng)

      this.preDelivery.destinationLat = lat;
      this.preDelivery.destinationLng = lng;

      this.findDistance(this.preDelivery.pickupLat, this.preDelivery.pickupLng, this.preDelivery.destinationLat, this.preDelivery.destinationLng);
      this.getDestinationAddress(lat, lng);
    },

    getAddressResult(lat, lng) {
      return new Promise((resolve, reject) => {
        axios.get(`https://api.opencagedata.com/geocode/v1/json?key=${API_KEY}&q=${lat}+${lng}&language=${language}`)
          .then(response => {
            const result = response.data.results[0].components;
            if (result) {
              const { road, suburb, state, postcode } = result;
              const address = [road, suburb, state, postcode];
              resolve(address);
            } else {
              reject(new Error('Unable to retrieve address'));
            }
          })
          .catch(error => {
            reject(error);
          });
      });
    },


    handleMarkerDragEnd(e) {
      const { lat, lng } = e.target.getLatLng();

      this.preDelivery.destinationLat = lat;
      this.preDelivery.destinationLng = lng;


      this.findDistance(this.preDelivery.pickupLat, this.preDelivery.pickupLng, this.preDelivery.destinationLat, this.preDelivery.destinationLng);
      this.getDestinationAddress(lat, lng);
    },

    findDistance(lat1, lng1, lat2, lng2) {

      // Haversine formula to calculate distance between two points on a sphere (Earth)
      const R = 6371; // Earth's radius in meters
      const φ1 = (lat1 * Math.PI) / 180;
      const φ2 = (lat2 * Math.PI) / 180;
      const Δφ = ((lat2 - lat1) * Math.PI) / 180;
      const Δλ = ((lng2 - lng1) * Math.PI) / 180;

      const a =
        Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
        Math.cos(φ1) * Math.cos(φ2) * Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
      const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

      const distance = R * c;
      this.preDelivery.distance = distance.toFixed(2);
    },

    submitRequestDelivery() {
      console.log(this.preDelivery)
      axios.post('http://localhost:7071/delivery/pre/save', this.preDelivery)
        .then(response => {
          const respData = response.data;
          this.preDeliId = respData.preDeliId;
          console.log(respData);
          this.$router.push({name: 'GetNearRiders', params: {id: this.preDeliId}})
        }).catch(error => console.error(error))

      
    }
  }

}
</script>

<style></style>

<!-- rider (send location data)-> always sending location to admin while online

                              if currently assigned delivery or not
                              this rider id in deliveries list(in process) ? send rider location to customer() 

make delivery (delivery || Id ) -> 
                                    backend{ 
                                      delivery object = getByID // object

                                      //riderService
                                      riders = getRiderByPostalCode() (**Online)//<- have to define get all online riders or all riders orr offline

                                      loop(){ 
                                        findDistance()
                                       }

                                       send to all riders()
                                      
                                    }

rider (subscribe deliveries) <- delivery 
if rider confirms -> {
  rider id is assigned to that delivery object and
  showing rider is coming to customer (rider real time location)
} -->