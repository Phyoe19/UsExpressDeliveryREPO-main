<template>
    <div class="adminNavbar">
        <v-navigation-drawer v-if="login" v-model="drawer" app>
            <v-list>
                <v-list-item v-for="link in links" :key="link.text" :to="link.route" router>
                    <v-list-item-action>
                        <v-icon>{{ link.icon }}</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>{{ link.text }}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>

        <v-app-bar v-if="login" app>
            <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
            <v-toolbar-title>Admin Dashboard</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn @click="logout">
                <span>Sign Out</span>
                <v-icon right>mdi mdi-logout</v-icon>
            </v-btn>
        </v-app-bar>
    </div>
</template>
  
<script>
export default {

    props: [
        'adminId'
    ],


    data() {
        return {

            drawer: false,
            login: true,
            links: [
                {
                    icon: "mdi mdi-view-dashboard",
                    text: "Dashboard",
                    route: { name: "AdminHome", params: { id: this.adminId } }, // Use route name instead of route path
                },
                {
                    icon: "mdi mdi-account",
                    text: "Riders",
                    route: { name: "AdminRiders", params: { id: this.adminId } }, 
                },
                {
                    icon: "mdi mdi-account-group",
                    text: "Customers",
                    route: { name: "AdminCustomers", params: { id: this.adminId } }, 
                },
                {
                    icon: "mdi mdi-map",
                    text: "Maps",
                    route: { name: "AdminMaps", params: {id: this.adminId} }, 
                },
            ],
        };
    },

    methods: {
        logout() {
            
            this.$store.commit('setUserRole', null);
            this.$store.commit('setUserData', null); // Clear the user role in Vuex

            this.$router.push({name: 'home'}); // Redirect to the home page or login page
        },
    }
};
</script>
  
<style></style>
  