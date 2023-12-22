<template>
    <div>
      <HomeNav/>
      <v-container color="#c4c3d0" class="mt-15">
      <div class="background"></div>
      <v-card-title>About Us</v-card-title>
      <p>E-commerce growth made easy with our dedicated delivery platform. Offer fast and convenient delivery options to increase customer satisfaction and loyalty.</p>
      <v-spacer></v-spacer>
      <v-row>
        <v-col>
      <v-avatar size="150">
        <v-img src="../assets/e-commerencedeli.png"></v-img>
      </v-avatar>
    </v-col>
      <v-col>
        <v-card-title>E-commerence Delivery</v-card-title>
        <p>Streamline your delivery process and offer your customers fast and convenient delivery options. Our platform makes sure that your items are delivered safely and on time, every time.</p>
      </v-col>
    </v-row>
    <v-row>
        <v-col>
            <v-card-title>Real-time Tracking</v-card-title> 
            <p>Keep your customers informed every step of the way with real-time tracking updates. Our platform provides you with the tools you need to provide a top-notch delivery experience.</p>
        </v-col>
            <v-col>
            <v-avatar size="150">
            <v-img src="../assets/real-timetrack.png"></v-img>
        </v-avatar>
        </v-col>
    </v-row>
    <v-row>
        <v-col>
      <v-avatar size="150">
        <v-img src="../assets/scalability.png"></v-img>
      </v-avatar>
    </v-col>
      <v-col>
        <v-card-title>Scalability</v-card-title>
        <p>As your business grows, so does our platform. Our delivery network is designed to scale with your business, ensuring that you can handle any increase in delivery volume.</p>
      </v-col>
    </v-row>
  
  </v-container>
  <FooterView/>
  </div>
  </template>
  <script>
  import HomeNav from '../components/HomeNav.vue';
  import FooterView from "../components/Footer.vue";
  export default {
      name:'AboutUsView',
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