<template>
  <div class="home">
    <AdminNavBar class="mb-10" v-if="admin" :adminId="admin.adminId" />

    <UsExpressSlogan />

    <!-- Set Price and Promotion -->
    <h3 class="d-flex pa-5 mt-6 ml-2">Set Price and Promotion</h3>
    <div class="ml-11">
      <v-row>
        <v-col cols="6" sm="9">
          <v-card
            class="justify-left pa-7"
            style="border-radius: 20px; box-shadow: 2px 2px 2px 3px #00000040 inset; width: 65%;"
          >
            <v-card-text>
              <!-- Form for setting price and promotion -->
              <v-form @submit.prevent="submitPriceData">
                <v-row>
                  <v-col cols="6" lg="4" md="4">
                    <v-text-field
                      v-model="setPriceData.KG"
                      label="Cost per Weight (KG)"
                      required
                      outlined
                      :rules="[rules.required, rules.number]"
                    ></v-text-field>
                  </v-col>
                  <!-- Percentage -->
                  <v-col cols="6" lg="4" md="4">
                    <v-text-field
                      v-model="setPriceData.percent"
                      label="Discount Percentage"
                      required
                      outlined
                      :rules="[rules.required, rules.number]"
                    ></v-text-field>
            </v-col>
        </v-row>

<!-- CostKM -->
                <v-row>
                  <v-col cols="6" lg="4" md="4">
                    <v-text-field
                      v-model="setPriceData.KM"
                      label="Cost per Distance (KM)"
                      required
                      outlined
                      :rules="[rules.required, rules.number]"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-btn type="submit" color="grey">Submit</v-btn>
              </v-form>

              <!-- Response message -->
              <!-- <v-card v-if="responseMessage" outlined>
                <v-card-title>Response</v-card-title>
                <v-card-text>{{ responseMessage }}</v-card-text>
          </v-card> -->


          </v-card-text>
          </v-card>
          </v-col>
          </v-row>
     
          </div>
          <div class="tables pa-6" style="width: 50%">
          <h3 class="d-flex mt-6 ml-2">Riders Table</h3>
  
        <v-data-table :items="formattedRiders" :headers="riderHeaders" :items-per-page="5" class="elevation-1"/>
    
        <!-- </div> -->
          </div>

          <div class="tables pa-6" style="width: 50%">
          <h3 class="d-flex mt-6 ml-2">Confirmed Deliveries Table</h3>
  
        <v-data-table :items="formattedConfirmDeliveries" :headers="confirmHeaders" :items-per-page="5" class="elevation-1"/>
    
        <!-- </div> -->
          </div>

         
        <div v-if="showRiders">
          Hello
          <div >
            <div v-if="riderLiveArray.length > 0">
              <LeafletMap :currentLocation="currentLocation" :riders="riderLiveArray" />
            </div>
            
          </div>
        </div>
   </div>


</template>

<script>
import axios from "axios";
import AdminNavBar from "@/components/AdminNavBar.vue";
import UsExpressSlogan from "@/components/UsExpressSlogan.vue";
import { connectWebSocket, subscribeToDestination, disconnectWebSocket } from '@/utils/websocketconfig';


export default {
  name: "AdminHome",
  components: {
    AdminNavBar,
    UsExpressSlogan,
  },

  data() {
    return {
      showRiders: false,
      riderLiveArray: [],
      riderArray: [],
      confirmDeliveryArray: [],
      responseMessage: "",
      admin: JSON.parse(sessionStorage.getItem("admin")),
      riderHeaders: [
        { text: "Rider ID", value: "riderId" },
        { text: "Name", value: "name" },
        { text: "Email", value: "email" },
        { text: "Phone Number", value: "phoneNumber" },
        { text: "Address", value: "address" },
        // Add more columns as needed
      ],

      confirmHeaders:[
      { text: "Delivery ID", value: "deliveryId" },
        { text: "Receiver Name", value: "receiverName" },
        { text: "Receiver Phone", value: "receiverPhone" },
        { text: "Sender Name", value: "senderName" },
        { text: "From", value: "from" },
        { text: "To", value: "to" },
        { text: "Cost", value: "cost" },
        { text: "Rider ID", value: "riderId" },
        { text: "Rider Name", value: "riderName" },
      ],

      
      currentLocation: [],


      setPriceData: {
        KG: "",
        percent: "",
        KM: "",
      },
      rules: {
        required: (value) => !!value || "Field is required.",
        number: (value) => !isNaN(parseFloat(value)) || "Must be a number.",
      },
    };
  },

  created(){
    navigator.geolocation.watchPosition(pos =>{
      const { latitude, longitude } = pos.coords;
      this.currentLocation = [ latitude, longitude ]
    })
  },

  mounted(){
    this.getRiders();
    this.getConfirmDeliveries();

    connectWebSocket()
    
  },

  computed: {
    formattedRiders() {
      // Map the riderArray to the format expected by Vuetify data table
      return this.riderArray.map((rider) => ({
        riderId: rider.riderId,
        name: rider.name,
        email: rider.email,
        phoneNumber: rider.phoneNumber,
        address: rider.address,
        // Add more properties as needed for other columns
      }));
    },

    formattedConfirmDeliveries(){
      return this.confirmDeliveryArray.map((delivery) => ({
        deliveryId: delivery.deliveryId,
        senderName: delivery.customer.name, 
        receiverName: delivery.receiverName,
        receiverPhone: delivery.receiverPhone,
        from: delivery.pickupAddressText,
        to: delivery.destinationAddressText,
        cost: delivery.cost,
        riderId: delivery.rider.riderId,
        riderName: delivery.rider.name
      }));
    }
  },

  methods: {

    subscribeRiders(){
      subscribeToDestination('/package-delivery/admin/track-rider', this.handleRidersMessage)
    },
    handleRidersMessage(message){
      const data = JSON.parse(message.body);
      this.riderLiveArray = data;
      this.showRiders = true;
    },
    async submitPriceData() {
      try {
        const response = await axios.post(
          "http://localhost:7071/admin/calculate",
          this.setPriceData
        );
        this.responseMessage = "API response: " + response.data;
      } catch (error) {
        this.responseMessage = "API call error: " + error.message;
      }
    },

    async getRiders(){
      await axios.get("http://localhost:7071/rider/")
      .then(response => {
        const responseData = response.data;
        this.riderArray = responseData;
      })
    },

    async getConfirmDeliveries(){
      await axios.get("http://localhost:7071/delivery/")
      .then(response => {
        const responseData = response.data;
        this.confirmDeliveryArray = responseData;
      })
    }
  },

  beforeDestroy(){
    disconnectWebSocket();
  },
  destroyed(){
    disconnectWebSocket();
  }
};
</script>
