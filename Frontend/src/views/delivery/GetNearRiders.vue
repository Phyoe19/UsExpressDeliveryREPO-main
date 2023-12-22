<template>
  <div>
    <UserNavBar />
    <div class="near-riders-container mt-10">
      <v-container>
        <v-row v-if="showRiders">
          <v-col cols="12">
            <h2 class="near-riders-title">Near Riders</h2>
            <div v-if="nearRiders.length > 0">
              <LeafletMap :currentLocation="pickupLocation" :riders="nearRiders" />
            </div>
            
          </v-col>
        </v-row>

        <v-row v-else>
          <v-col cols="12">
            <p class="no-riders-message">There are no near riders</p>
          </v-col>
        </v-row>

        <div v-if="trackRider">
          <v-btn @click="navigateToTrackRider">
            TrackRider
          </v-btn>
        </div>
      </v-container>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import LeafletMap from '../leafletMap/LeafletMap.vue';
import { connectWebSocket, subscribeToDestination } from '@/utils/websocketconfig';
import UserNavBar from '@/components/UserNavBar.vue';
import { mapGetters } from 'vuex';

export default {

  //

  components: {
    LeafletMap,
    UserNavBar,
  },

  data() {
    return {

      showRiders : false,

      trackRider: false,

      customer: {},

      delivery: {},
      preDeliId: this.$route.params.id,
      nearRiders: [],
      pickupLocation: [],
    }
  },

  computed: {
    ...mapGetters(['userId', 'userData']),
  },

  created() {
    this.customer = this.userData;
  },


  mounted() {
    connectWebSocket(this.subscribeWebSocketMessage);
    this.getNearRiders();

  },
  methods: {

    navigateToTrackRider(){
      this.$router.push({name: 'TrackRider', params:{id: this.delivery.deliveryId}})
    },


    async getNearRiders() {
      const url = 'http://localhost:7071/delivery/getNearRiders/' + this.preDeliId;

      await axios.get(url)
        .then(response => {
          const respData = response.data;

          this.nearRiders = respData.nearRiders;

          this.pickupLocation[0] = respData.pickupLat;
          this.pickupLocation[1] = respData.pickupLng;

          console.log("Pickup Cors: ", this.pickupLocation);
          console.log("near riders: ", this.nearRiders);

          this.showRiders = true;
        })
        .catch(error => console.error(error))
    },


    subscribeWebSocketMessage() {
      subscribeToDestination('/package-delivery/customer/get-delivery/', this.handleDeliveryIdMessage);

    },
    handleDeliveryIdMessage(message) {
      const deliveryData = JSON.parse(message.body);
      this.delivery = deliveryData;
      console.log("Delivery : ", this.delivery);
      this.trackRider = true;
    },


  },



}
</script>

<style scoped>
.near-riders-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.near-riders-title {
  font-size: 24px;
  margin-bottom: 20px;
}

.no-riders-message {
  font-size: 18px;
  color: red;
}
</style>
