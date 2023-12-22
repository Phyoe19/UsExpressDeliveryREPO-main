<template>
    <v-container fluid fill-height>
        <div class="background"></div>


        <v-row>
            <v-col cols="7" lg="6" md="6">
                <v-card class="pa-12 ml-12" style="border-radius: 30px;
        box-shadow: 2px 2px 2px 3px rgba(0, 0, 0, 0.25) inset, 0px 2px 2px 0px rgba(0, 0, 0, 0.25);">

                    <h3 class="mb-7"> Customer Register</h3>

                    <div v-if="usernameError">
                        <h5 style="color: red;" class="my-5">{{ usernameError }}</h5>
                    </div>


                    <v-row>
                        <v-card-text>
                            <v-form ref="form" @submit.prevent="registerCustomer">
                                <v-row>
                                    <v-col cols="12" lg="6" md="6">
                                        <v-text-field v-model="customer.name" label="Name" required outlined></v-text-field>
                                    </v-col>
                                    <v-col cols="12" lg="6" md="6">
                                        <v-text-field v-model="customer.phoneNumber" label="Phone Number" required outlined
                                            type="Phone Number"></v-text-field>
                                    </v-col>
                                </v-row>

                                <v-row>
                                    <v-col cols="12" lg="6" md="6">
                                        <v-text-field v-model="customer.email" label="Email" required
                                            outlined></v-text-field>
                                    </v-col>
                                    <v-col cols="12" lg="6" md="6">
                                        <v-text-field v-model="customer.address" label="Address" required
                                            outlined></v-text-field>
                                    </v-col>
                                </v-row>

                                <v-row>
                                    <v-col cols="12" lg="6" md="6">
                                        <v-text-field v-model="customer.password" label="Password" required outlined
                                            type="password"></v-text-field>
                                    </v-col>

                                </v-row>

                                <v-btn type="submit" color="primary">Sign Up</v-btn>
                            </v-form>
                        </v-card-text>

                    </v-row>

                </v-card>
            </v-col>
            <v-col cols="12" lg="6" md="6">
                <div class="backgroundImg">

                </div>
            </v-col>
        </v-row>
    </v-container>
</template>
  
<script>
import axios from 'axios';
export default {
    data() {
        return {
            usernameError: '',
            customer: {
                name: '',
                email: '',
                password: '',
                address: '',
                phoneNumber: '',
                role: "CUSTOMER",
            }
        };
    },

    methods: {
        registerCustomer() {
            if (this.$refs.form.validate()) {
                // Submit the customer registration form
                console.log('Customer:', this.customer);
                // You can perform further actions like making an API call to register the customer
                axios.post('http://localhost:7071/customer/save', this.customer).then(response => {
                    if (response.status == 202) {
                        console.log('Reponse: ', response.data)
                        this.$router.push('/customer-login');
                    }
                }).catch(error => {
                    if(error.response && error.response.status == 400){
                        this.usernameError = 'Username was taken. Please try another one';

                        this.customer.name = '';
                        this.customer.email = '';
                        this.customer.password = '';
                        this.customer.address = '';
                        this.customer.phoneNumber = '';
                    }
                })
            }
        }
    }
};
</script>
  
<style scoped>
.backgroundImg {
    width: 100%;
    height: 100%;
    background-image: url("../../assets/customerRegister.svg");
    background-size: 70% auto;
    background-position: center;
}
</style>


  
  