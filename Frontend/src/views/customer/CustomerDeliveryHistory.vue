<template>
  <div>
    <UserNavBar />
    <div style="margin-top: 100px;">

    </div>
    <v-data-table :items="formattedConfirmDeliveries" :headers="confirmHeaders" :items-per-page="10" class="elevation-1"/>
  </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'
import UserNavBar from '@/components/UserNavBar.vue'
export default {

  components:{
    UserNavBar
  },

  data(){
    return{
      historyDeliveries: [],
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
    }
  },

  computed:{
    ...mapGetters(['userData']),

    formattedConfirmDeliveries(){
      return this.historyDeliveries.map((delivery) => ({
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

  mounted(){
    this.getDeliveryHistory();
  },

  methods:{
    async getDeliveryHistory(){
      await axios.get('http://localhost:7071/customer/getHistory/' + this.userData.customerId)
      .then(response => {
        const respData = response.data;
        this.historyDeliveries = respData;
      })
    }
  }

}
</script>

<style>

</style>