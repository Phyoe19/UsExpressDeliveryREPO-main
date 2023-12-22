<template>
    <v-app-bar app color="#b62626" dense dark height="70">
       <div> 
        <v-avatar size="65">    
      <v-img
        src="../assets/us_express.png"
      ></v-img>
    </v-avatar>
    </div>
    
        <v-toolbar-title style="margin-left: 15px;" >Us Express</v-toolbar-title>
        <v-spacer></v-spacer>
  
        <v-menu>
        <template v-slot:activator="{ on }">
          <div class="icon-wrapper" v-on="on"> 
    <v-icon >mdi-home</v-icon>
    <span @click="navigateTo('home')">Home</span>
  
  
  </div>
  
  </template>
    </v-menu> 
  
        <div class="icon-container">
            <v-menu>
          
        <template v-slot:activator="{ on }">
          <div class="icon-wrapper" v-on="on">
            <v-icon>mdi-account</v-icon>
            <span>Customer</span>
          </div>
        </template>
  
        <v-list style="font-size: 15px">
          <v-list-item   @click="navigateTo('CustomerLogin')">Login</v-list-item>
          <v-list-item  @click="navigateTo('CustomerRegistration')">Register</v-list-item>
        </v-list>
      </v-menu>
      <div class="icon-separator"></div>
      <v-menu>
        <template v-slot:activator="{ on }">
          <div class="icon-wrapper" v-on="on">
            <v-icon  >mdi-motorbike</v-icon>
            <span>Rider</span>
          </div>
        </template>
  
        <v-list style="font-size: 15px">
          <v-list-item  @click="navigateTo('RiderLogin')">Login</v-list-item>
          <v-list-item  @click="navigateTo('RiderRegistration')">Register</v-list-item>
        </v-list>
      </v-menu>
    </div>
  
        <div class="icon-separator"></div>
        <v-menu>
        <template v-slot:activator="{ on }">
          <div class="icon-wrapper" v-on="on">    
    <v-icon >mdi-wrench</v-icon>
    <span @click="navigateTo('ServiceView')">Service</span>
  </div>
        </template>
        </v-menu>
        <div class="icon-separator"></div>
        <v-menu>
        <template v-slot:activator="{ on }">
          <div class="icon-wrapper" v-on="on"> 
    <v-icon >mdi-lifebuoy</v-icon>
    <span @click="navigateTo('SupportView')">Support</span>
  
  </div>
  
  </template>
    </v-menu>          
    </v-app-bar>
  </template>
  
  <script>
  
  export default {
  
    name: 'HomeNav',
  
    methods: {
  
  
        navigateTo(routeName) {
  
            this.$router.push({ name: routeName });
        },
  
  
      
    
    },
  };
  </script>
  
  <style scoped>
  .link {
    color: white;
    text-decoration: none;
    cursor: pointer;
  }
  .icon-container {
  display: flex;
  align-items: center;
  }
  
  .icon-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  
  padding: 10px; 
  cursor: pointer;
  }
  
  .icon-separator {
  width: 30px; /* Add some space between Customer and Rider buttons */
  }
  
  
  
  
  </style>