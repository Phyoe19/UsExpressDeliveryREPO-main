<template>
    <div>
      <HomeNav/>
      <v-container color="#c4c3d0" class="mt-15">
      <div class="background"></div>
      <v-card-title>Privacy Policy</v-card-title>
      <p>Us-Express Delivery,we take your privacy seriously.We are committed to complying with all data protection laws as are applicable to us.This policy sets out how manages, collects, uses and discloses Personal Data belonging to you.</p>
      <v-spacer></v-spacer>
      <p>If you provide personal information of any third party us,we assume that you have obtained the required consent from the revelent third party to share and transfer his or her personal information to us.</p>
    <v-spacer></v-spacer>
    <v-card-title>What Personal Data does this Policy cover?</v-card-title>
    <p>When you create account,or otherwise provide us with your pesonal information through the Platform,the personal information  we collect may include:
        <ul>
            <li>UserName</li>
            <li>Phone Number</li>
            <li>Email</li>
            <li>Address(including residential and current-location)</li>
            
            <li>Any other information that may be reseonably required to facilitate the delivery of products to you and other individual Recipients.</li>
        </ul>
    </p>
      
   <!-- <v-img src="../assets/usexpressLogo.jpg"></v-img> -->
  
  </v-container>
  <FooterView/>
  </div>
  </template>
  <script>
  import HomeNav from '../components/HomeNav.vue';
  import FooterView from "../components/Footer.vue";
  export default {
      name:'PrivacypolicyView',
      components: {
          HomeNav,FooterView
  },
  methods:{
        navigateTo(routeName) {
  
  this.$router.push({ name: routeName });
  },
      },
  }
  </script>