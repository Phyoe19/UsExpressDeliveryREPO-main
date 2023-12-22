<template>

    <v-footer color="#b62626"  dense dark style="min-height: 20vh;">
      <v-row align="center" justify="center">
        <v-col>
          <v-row class="a" dark align="center" justify="center">
            <v-col>
              <router-link to="/aboutus" class="custom-link">About Us</router-link>
            </v-col>
            <v-col>
              <router-link to="/service" class="custom-link">Our Service</router-link>
            </v-col>
            <v-col>
              <router-link to="/privacy" class="custom-link">Privacy Policy</router-link>
            </v-col>
            <v-col>
              <router-link to="/terms" class="custom-link">Terms of Use</router-link>
            </v-col>
         
            <v-col>
              <router-link to="/Support" class="custom-link">Contact Us</router-link>
            </v-col>
          </v-row>
  
          <v-row align="center" justify="center">
            <v-col>
              <p>&copy; 2023 Us Express. All rights reserved.</p>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-footer>
  
  </template>
  
  <script>
  export default {
    name: 'FooterView',
  };
  </script>
  <style scoped>
  
  v-footer {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
  }
  
  .custom-link {
    color: white;
    outline: none;
    text-decoration-color: aliceblue;
    text-decoration: none;
  }
  
  .custom-link:hover {
    color: black;
  }
  </style>