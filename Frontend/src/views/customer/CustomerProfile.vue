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

        <v-text-field :disabled="!isEditing" color="#000" v-model="customer.name" label="Name" required outlined>
        </v-text-field>

        <v-text-field :disabled="!isEditing" color="#000" v-model="customer.email" label="Email" requirerd outlined>
        </v-text-field>

        <v-text-field :disabled="!isEditing" color="#000" v-model="customer.password" label="Password" required outlined>
        </v-text-field>

        <v-text-field :disabled="!isEditing" color="#000" v-model="customer.address" label="Address" required outlined>
        </v-text-field>

        <v-text-field :disabled="!isEditing" color="#000" v-model="customer.phoneNumber" label="Phone-Number" required
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
    this.setCustomer();
  },

  data: () => ({
    hasSaved: false,
    isEditing: null,
    customer: {},
    // states: [
    //   { name: 'Florida', abbr: 'FL', id: 1 },
    //   { name: 'Georgia', abbr: 'GA', id: 2 },
    //   { name: 'Nebraska', abbr: 'NE', id: 3 },
    //   { name: 'California', abbr: 'CA', id: 4 },
    //   { name: 'New York', abbr: 'NY', id: 5 },
    // ],
  }),

  methods: {
    // customFilter (itemTitle, queryText, item) {
    //   const textOne = item.raw.name.toLowerCase()
    //   const textTwo = item.raw.abbr.toLowerCase()
    //   const searchText = queryText.toLowerCase()

    //   return textOne.indexOf(searchText) > -1 ||
    //     textTwo.indexOf(searchText) > -1
    // },

    setCustomer() {
      this.customer = this.userData;
      console.log(this.customer)
    },

    async save() {

      console.log(this.customer);

      await axios.put("http://localhost:7071/customer/update", this.customer)
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