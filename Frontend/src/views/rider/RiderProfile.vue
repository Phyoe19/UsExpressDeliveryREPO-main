<template>
  <div>

    <UserNavBar />

    <div style="margin-top: 100px;">

    </div>
    <v-card class="mx-auto" color="white" brighten max-width="550">

      <v-toolbar flat color="#404040" dark>
        <v-avatar size="35"><v-icon>mdi-account</v-icon></v-avatar>
        <v-spacer></v-spacer>
        <v-toolbar-title class="font-weight-light">
          User Profile
        </v-toolbar-title>

        <v-spacer></v-spacer>

        <v-btn icon @click="isEditing = !isEditing">
          <v-fade-transition leave-absolute>
            <v-icon v-if="isEditing">mdi-close</v-icon>

            <v-icon v-else>mdi-pencil</v-icon>
          </v-fade-transition>
        </v-btn>
      </v-toolbar>
      <div class="d-flex justify-center w-100 mt-5"><v-avatar size="80"><v-img
            src="../../assets/profileAvatar.jpg"></v-img></v-avatar></div>

      <v-card-text>

        <v-text-field :disabled="!isEditing" color="#000" v-model="rider.name" label="Name" required outlined>
        </v-text-field>

        <v-text-field :disabled="!isEditing" color="#000" v-model="rider.email" label="Email" requirerd outlined>
        </v-text-field>

        <v-text-field :disabled="!isEditing" color="#000" v-model="rider.password" label="Password" required outlined>
        </v-text-field>

        <v-text-field :disabled="!isEditing" color="#000" v-model="rider.address" label="Address" required outlined>
        </v-text-field>

        <v-text-field :disabled="!isEditing" color="#000" v-model="rider.phoneNumber" label="Phone-Number" required
          outlined>
        </v-text-field>


        <!-- <v-autocomplete
          :disabled="!isEditing"
          :items="states"
          :custom-filter="customFilter"
          color="white"
          item-title="name"
          item-value="abbr"
          label="State"
        ></v-autocomplete> -->
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn :disabled="!isEditing" @click="save">
          Save
        </v-btn>
      </v-card-actions>

      <v-snackbar v-model="hasSaved" :timeout="2000" attach position="absolute" location="bottom left">
        Your profile has been updated
      </v-snackbar>
    </v-card>

  </div>
</template>
<script>
import UserNavBar from '@/components/UserNavBar.vue';
import axios from 'axios'
import { mapGetters } from 'vuex'
export default {
  name: 'ProfileView',

  components: {
    UserNavBar,
  },

  computed: {

    ...mapGetters(['userData']),

  },

  mounted() {
    this.setRider();
  },

  data: () => ({
    hasSaved: false,
    isEditing: null,
    rider: {},

  }),

  methods: {


    setRider() {
      this.rider = this.userData;
      console.log(this.rider)
    },

    async save() {

      console.log(this.rider);

      await axios.put("http://localhost:7071/rider/update", this.rider)
        .then(response => {
          const respData = response.data;
          this.$store.commit('setUserData', respData);
        }).catch(error => console.error(error))
      this.isEditing = !this.isEditing
      this.hasSaved = true
    },
  },
}
</script>