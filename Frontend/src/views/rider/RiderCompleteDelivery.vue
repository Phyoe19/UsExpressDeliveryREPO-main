<template>
    <div style="margin-top: 100px;">
       <h1>Congratulation!
        You Completed Delivery.</h1> 

        <v-btn @click="navigateToHome">
            Back to Home
        </v-btn>

    </div>
</template>

<script>
export default {

    methods:{
        navigateToHome(){
      this.$router.push({name: 'RiderHome', params:{id: this.$route.params.id}})
    },
    }

}
</script>

<style>

</style>