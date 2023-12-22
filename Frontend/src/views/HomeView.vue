<template>
  <div >
    <HomeNav />
    <div class="mt-7 pa-12">
      <h2>Welcome to our delivery service !</h2>

      <h4>
        At Our Delivery Service, we take pride in providing exceptional
        delivery services exclusively in Yangon. We understand the importance of
        reliable and efficient delivery, and we are dedicated to serving our
        customers with utmost care and professionalism.
      </h4>
    </div>
    <!-- First Row -->
    <v-row align="center" >
      <!-- First Column -->
      <v-col cols="12" md="6">
        <div class="button-container">
          <div class="text-container">
            <h3 class="ml-4 mb-1 mb-5">Swift, Secure, UsExpress Delivers with Care</h3>
          </div>
          <!-- <v-btn style="border-radius: 10px; background: #b62626; color: white">
            Get Started
          </v-btn> -->
        </div>
      </v-col>

      <!-- Second Column PIC -->
      <v-col cols="12" mt="5" md="6">
        <div class="RiderPic">
          <img src="../assets/delivery_truck_vt6p.svg" alt="Rider" style="width: 55%; height: auto;" />
        </div>
      </v-col>
    </v-row>

    <!--2nd Row Stepper Section -->
    <v-row align="center" justify="center" mb-5>
      <v-col cols="12" md="8">
        <div class="stepper-container">
          <v-stepper v-model="e1">
            <v-stepper-header>
              <v-stepper-step :complete="e1 > 1" step="1" color="#b62626">
                Register and Login
              </v-stepper-step>

              <v-stepper-step :complete="e1 > 2" step="2" color="#b62626">
                Delivery Address
              </v-stepper-step>

              <v-stepper-step step="3" color="#B62626">
                Order Details
              </v-stepper-step>
            </v-stepper-header>

            <v-stepper-items>
              <v-stepper-content step="1">
                <v-card class="mb-12" color="#b62626;" height="200px">
                  <h2>Register and Login</h2>
                  <p>
                    Welcome to our delivery service platform! To get started,
                    you'll need to register and create an account. Click on the
                    "Register" button to begin the registration process. You'll
                    be asked to provide some basic information, such as your
                    name, email address, and contact number. Rest assured that
                    we value your privacy, and your information will be kept
                    secure and confidential.
                  </p>
                </v-card>

                <v-btn color="#b62626" dark @click="e1 = 2">
                  Next
                </v-btn>
              </v-stepper-content>

              <v-stepper-content step="2">
                <v-card color=" #B36464;" height="200px">
                  <h2>Delivery Address</h2>
                  <p>
                    Please enter the delivery address and recipient's details
                    carefully. Make sure to include the full address with
                    landmarks, if applicable. Providing accurate address
                    information will ensure smooth and timely delivery of your
                    order. We value your privacy and assure you that your
                    personal information will be kept confidential.
                  </p>
                </v-card>

                <v-btn color="#b62626" dark @click="e1 = 3">
                  Next
                </v-btn>
              </v-stepper-content>

              <v-stepper-content step="3">
                <v-card class="mb-12" color="#B62626;" height="200px">
                  <h2>Order Details</h2>
                  <p>
                    In this step, you'll provide the order details for your
                    delivery. Please specify the items you want to send, their
                    quantity, and any additional notes or packaging
                    instructions you may have. We strive to deliver your items
                    safely and securely, so it's essential to accurately
                    describe the contents of your order.
                  </p>
                </v-card>

                <v-btn color="#B62626" dark @click="e1 = 1">Done!</v-btn>
              </v-stepper-content>
            </v-stepper-items>
          </v-stepper>
        </div>
      </v-col>
    </v-row>

    <FooterView />
  </div>
</template>

<script>
import HomeNav from "../components/HomeNav.vue";
import FooterView from "../components/Footer.vue";

export default {
  name: "HomeView",
  components: {
    HomeNav,
    FooterView,
  },

  data() {
    return {
      userRole: null,
      e1: 1,
    };
  },
};
</script>

<style scoped>
.button-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
}

.RiderPic {
  display: flex;
  justify-content: center;
  align-items: center;
}

.RiderPic img {
  width: 80%;
  height: auto;
}

@media (min-width: 600px) {
  

  .button-container {
    align-items: flex-start;
    margin-left: 150px; /* Adjust the margin as needed */
  }
}


</style>

