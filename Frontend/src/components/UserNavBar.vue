<template>
    <v-app-bar app color="#b62626" height="70" dark>
        <div> 
      <v-avatar size="65">    
    <v-img
      src="../assets/us_express.png"
    ></v-img>
  </v-avatar>
  </div>

        <v-toolbar-title>UsEXPRESS</v-toolbar-title>

        <v-spacer></v-spacer>

        <v-menu v-if="!userData">

            <template v-slot:activator="{ on, attrs }">
                <v-btn icon v-bind="attrs" v-on="on" class="mx-10">
                    Login
                </v-btn>

            </template>

            <v-list style="font-size: 1rem">
                <v-list-item @click="navigateTo('RiderLogin')">Rider</v-list-item>
                <v-list-item @click="navigateTo('CustomerLogin')">Customer</v-list-item>
            </v-list>

        </v-menu>

        <v-menu v-if="!userData">

            <template v-slot:activator="{ on, attrs }">
                <v-btn icon v-bind="attrs" v-on="on" class="mx-10">
                    Register
                </v-btn>

            </template>

            <v-list style="font-size: 1rem">
                <v-list-item @click="navigateTo('RiderRegistration')">Rider</v-list-item>
                <v-list-item @click="navigateTo('CustomerRegistration')">Customer</v-list-item>
            </v-list>

        </v-menu>


        <!--Customer-->
        <div v-if="userRole === 'CUSTOMER'">


            <v-btn text v-if="userData" exact>
                <router-link class="link"
                    :to="{ name: 'CustomerProfile', params: { id: this.userId } }">Profile</router-link>
            </v-btn>

            <v-btn text v-if="userData" exact>
                <router-link class="link" :to="{ name: 'CustomerHome', params: { id: this.userId } }">Home</router-link>
            </v-btn>


            <v-btn text v-if="userData" exact>
                <router-link class="link"
                    :to="{ name: 'CustomerDeliveryHistory', params: { id: this.userId } }">History</router-link>
            </v-btn>
        </div>
        <!--Customer-->


        <!--Rider-->

        <div v-if="userRole === 'RIDER'">

            <v-btn text v-if="userData" exact>
                <router-link class="link" :to="{ name: 'RiderHome', params: { id: this.userId } }">Home</router-link>
            </v-btn>

            <v-btn text v-if="userData" exact>
                <router-link class="link" :to="{ name: 'RiderProfile', params: { id: this.userId } }">Profile</router-link>
            </v-btn>

        </div>

        <!--Rider-->

        <div class="notification-icon" @click="toggleNotifications">
            <i class="fa fa-bell"></i>
            <span v-if="notificationCount > 0" class="notification-count">{{ notificationCount }}</span>
        </div>
        <NotificationPopup v-if="showNotifications" :notifications="notifications" @close="toggleNotifications" />


        <v-btn text v-if="userData" exact @click="logout">
            Logout
        </v-btn>
    </v-app-bar>
</template>
  
<script>
import axios from 'axios';
import { mapGetters } from 'vuex';
export default {

    name: 'CustomerNavBar',
    data() {
        return {

            showLoginDialog: false,
            showRegisterDialog: false,
            showNotifications: false,
            showTrack: false,
            deliveryId: '',
        }
    },
    computed: {
        ...mapGetters(['userId', 'userData', 'userRole', 'notificationCount']),
    },
    created() {
        this.showDialog();

    },

    mounted() {
    },
    methods: {
        showDialog() {
            if (!this.userData && !this.userId && !this.userRole) {
                this.showLoginDialog = true;
                this.showRegisterDialog = true;
            }
        },

        navigateTo(routeName) {

            this.$router.push({ name: routeName });
        },

        toggleSearch() {

            if (this.showTrack) {
                this.$router.push({ name: "TrackRider", params: { id: this.deliveryId } })
            }
            this.showTrack = !this.showTrack;
        },

        toggleNotifications() {
            this.showNotifications = !this.showNotifications;
            if (this.showNotifications) {
                // Fetch notifications for the current user from the backend
                // and update the 'notifications' array.

                if (this.userRole === "RIDER") {
                    axios.get(`http://localhost:7071/notification/get-rider-noti-by-id/${this.userId}`)
                        .then(reponse => {
                            const respData = reponse.data;
                            this.$store.commit('setNotifications', respData);
                        }).catch(error => console.error(error))
                } else if (this.userRole === "CUSTOMER") {
                    axios.get(`http://localhost:7071/notification/get-cus-noti-by-id/${this.userId}`)
                        .then(reponse => {
                            const respData = reponse.data;
                            this.$store.commit('setNotifications', respData);
                        }).catch(error => console.error(error))
                } else {
                    this.notifications = [];
                }

            }
        },

        logout() {
            
            this.$store.commit('setUserRole', null);
            this.$store.commit('setUserData', null); // Clear the user role in Vuex

            this.$router.push({name: 'home'}); // Redirect to the home page or login page
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
</style>
  