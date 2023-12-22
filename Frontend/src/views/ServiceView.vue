<template>
    <div>
      
      <HomeNav/>

   
   
        <v-container color="#c4c3d0" class="mt-15">

  
    <div class="background"></div>
          
          <v-row class="row">
            <v-col >
              <v-card outlined class="my-4 card" >
               <v-row class="innercard" align="center" no-gutters>
                </v-row> 
                <v-img src="../assets/showing_support_re_5f2v.svg"
        ></v-img>
              </v-card>
              <v-card-title>customer always first support</v-card-title>
            </v-col>
    
            <v-col >
              <v-card outlined class="my-4 card " >
               <v-row class="innercard" align="center" no-gutters>
                </v-row> 
                 <v-img src="../assets/takeout_boxes_ap54.svg"
        ></v-img> 
              </v-card>
              <v-card-title>Flexible package pickups</v-card-title>
            </v-col>
          
      
              <v-col>
              <v-card outlined class="my-4 card" >
                <v-row class="innercard" align="center" no-gutters>
                </v-row> 
               <v-img src="../assets/delivery_address_re_cjca.svg" 
      ></v-img>
              </v-card>
              <v-card-title>Tracking</v-card-title>
          </v-col>
      </v-row>
      <v-row>
          <v-col>
              <!-- <v-avatar size="85"><v-img src="../assets/a4.png"></v-img></v-avatar> -->
              <v-card-title>Delivery option for every budget</v-card-title>
              <p>Choose from a variety of delivery option- we provide same day,next day,rider and stansard option!</p>
          </v-col>
          <v-col>
              <!-- <v-avatar size="85"><v-img src="../assets/cash.webp"></v-img></v-avatar> -->
              <v-card-title>Cash-on delivery service</v-card-title>
              <p>Need to collect payment from customer?We offer Cash-on service in all area that we cover.</p>
          </v-col>
      </v-row>
      <v-row>
          <v-col>
              <!-- <v-avatar size="85"><v-img src="../assets/pick.jpg"></v-img></v-avatar> -->
              <v-card-title>Flexible package pickups</v-card-title>
              <p>We offer pickups optin-whatever you need us to pick up your package from you,or prefer to drop them all yourself at our extensive network ofdrop all point.</p>
          </v-col>
          <v-col>
              <!-- <v-avatar size="85"><v-img src="../assets/serviceregion.jpg"></v-img></v-avatar> -->
              <v-card-title>Fast service in the region</v-card-title>
              <p>Us Express is the only last mile courier with almost 100% service in Yangon Region.</p>
          </v-col>
      </v-row>
  
        </v-container>
        <FooterView/>
      </div>
    </template>
      
    <script>
    
    import HomeNav from '../components/HomeNav.vue'
    import FooterView from "../components/Footer.vue";
    export default {
      name:'ServiceView',
      components:{
        HomeNav,FooterView
      },
  
      props: [
          'customerId',
      ],
      data() {
          return {
              showNotifications: false,
              userNotifications: [],
              deliveryId: '',
              showTrack: false,
          }
      },
      computed: {
          isCustomer() {
              // Check if the user is a customer (based on your role-checking logic)
              const userRole = this.$store.state.userRole;
              return userRole === 'customer';
          },
  
          hasCustomerNotifications() {
              return this.userNotifications.length > 0;
          }
      },
      created() {
          // Retrieve user notifications from session storage
  
  
      },
  
      mounted() {
          setInterval(() => {
              this.getNotification();
          }, 100);
      },
      methods: {
          toggleSearch(){
  
              if(this.showTrack){
                  this.$router.push({name: "TrackRider", params: {id: this.deliveryId}})
              }
              this.showTrack = !this.showTrack;
          },
          getNotification() {
              const notifications = JSON.parse(sessionStorage.getItem('notifications'));
              if (notifications) {
                  this.userNotifications = notifications;
              }
          },
          logout() {
              // Perform logout logic, clear user data from store and session storage
              this.$store.commit('setUserRole', null); // Clear the user role in Vuex
              sessionStorage.removeItem('userRole'); // Remove the user role from session storage
              // ... Additional logout actions if needed
              this.$router.push('/'); // Redirect to the home page or login page
          },
      },
        
    
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
      border-radius: 40px;
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
      