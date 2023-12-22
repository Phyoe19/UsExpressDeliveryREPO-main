<template>
  <div>
    <UserNavBar/>

    <div style="margin-top: 100px;">

    </div>
    <v-container>
      <v-row>
        <v-col>
          <div class="profile">
            <img src="../../assets/profileAvatar.jpg" alt="customerAvatar" class="avatar-img" />
            <span class="hello">Hello <span class="cusName">{{ customer.name }}</span></span>
          </div>
        </v-col>

        <v-col class="slogan">
          Hello there! Thank you for joining US EXPRESS delivery service.
        </v-col>
      </v-row>
    </v-container>


    <v-container fluid>
      <v-row class="row">
        <v-col >
          <v-card outlined class="my-6 card" color="#c4c3d0">
            <v-row class="innercard" align="center" no-gutters>
              <v-col cols="auto">
                <v-icon size="50" color="#708090">mdi-truck-delivery</v-icon>
              </v-col>
              <v-col>
                <v-card-title class="headline">Delivery Service</v-card-title>
                <v-card-text>
                  <p>Send your package and get real-time updates on the delivery status.</p>
                </v-card-text>
              </v-col>
            </v-row>
            <v-card-actions>
              <v-btn color="#666699" text @click="navigateToRequestDelivery">Send a package</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>

        <v-col >
          <v-card outlined class="my-6 card" color="#c4c3d0">
            <v-row class="innercard" align="center" no-gutters>
              <v-col>
                <v-card-title class="headline">Track your delivery</v-card-title>
                <v-card-text>
                  <p>Track your package and get real-time updates on the delivery status.</p>
                </v-card-text>
                <v-text-field class="custom-search-box" v-model="deliveryId" label="Enter delivery id to track"
                  prepend-icon="mdi-magnify" color="primary" outlined></v-text-field>
              </v-col>
            </v-row>
            <v-card-actions>
              <v-btn color="#666699" text @click="navigateToTrackDelivery">Track</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>

        <v-col >
          <v-card outlined class="my-6 card" color="#c4c3d0">
            <v-row class="innercard" align="center" no-gutters>

              <v-col cols="auto">
                <v-icon size="50" color="dark">mdi-magnify</v-icon>
              </v-col>

  
              <v-col>
                <v-card-title class="headline">Delivery History</v-card-title>
                <v-card-text>
                  <!-- Insert delivery history content here -->
                  Browse Delivery History
                </v-card-text>
              </v-col>

            </v-row>
            <v-card-actions >
          <v-btn color="#666699" text @click="navigateToBrowseDelivery">Browse</v-btn>
        </v-card-actions>
          </v-card>

        </v-col>


      </v-row>
    </v-container>




  </div>
</template>
  
<script>
import UserNavBar from '@/components/UserNavBar.vue'
import axios from 'axios'

export default {

  data() {
    return {
      deliveryId: '',
      customer: {},
    }
  },

  components: {
    UserNavBar
  },

  mounted() {
    this.getCustomerData();
  },

  methods: {
    navigateToRequestDelivery() {
      this.$router.push({ name: 'PreDelivery', params: { id: this.customer.customerId } });
    },

    navigateToTrackDelivery() {
      this.$router.push({ name: 'TrackRider', params: { id: this.deliveryId } });
    },

    navigateToBrowseDelivery(){
      this.$router.push({ name: 'CustomerDeliveryHistory', params: { id: this.customer.customerId  } });
    },

    getCustomerData() {
      axios.get('http://localhost:7071/customer/getById/' + this.$route.params.id)
        .then(response => {
          const respData = response.data;
          this.customer = respData;
          console.log("customer home: customer ", this.customer)
        }).catch(error => console.error(error))
    }

  }
}
</script>
  
<style scoped>
/* Add custom styles for the card if needed */




.slogan{
padding: 15px auto 10px auto;
}

.profile {
  display: flex;
  align-items: center;
}

.avatar-img {
  width: 100px;
  height: 100px;
  margin-right: 10px;
  border-radius: 50%;
}

.hello {
  display: flex;
  flex-direction: column;
  margin-top: 10px;
  margin-bottom: 10px;
}

.cusName{
  color: #333333;
  font-size: 30px;
}

.custom-search-box {
  width: 270px;
  color: #333;
  border-radius: 30px;
}

.card {
  width: 300px;
  border-radius: 30px;
}

.innercard {
  width: 100%;
  height: 280px;
}

.headline {
  font-size: 20px;
  font-weight: bold;
}
</style>
  