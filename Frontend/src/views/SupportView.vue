<template>
    <div>
    <HomeNav/>
    <div class="mt-16">
    <h1 style="color:#a31212">CONTACT US</h1>

    <v-row>
                <v-col>

                    <v-card outlined class="my-4 card" >
                    <v-img src="../assets/undraw_order_confirmed_re_g0if.svg"
                    :width="200"></v-img>
                   </v-card> 
            </v-col>
            <v-col>
                     <v-card outlined class="my-4 card" >
                    <v-img src="../assets/takeout_boxes_ap54.svg"
                    :width="300"></v-img>
                    </v-card> 
            </v-col>
       
               
            <v-col>
                    <v-card outlined class="my-4 card" >
                    <v-img src="../assets/deliveries_2r4y.svg"
                    :width="200"></v-img>
                    </v-card>
            </v-col>
        </v-row>
    <v-row>

    <v-card-text>Get in touch with the right people at Us Express and submit your enquiries below:</v-card-text>
</v-row>
    
        <v-row>
            <v-col>
                <p>Customer support hotline</p>
            </v-col>
            <v-col>
            <v-icon>mdi-phone-in-talk</v-icon>
                +95978667755
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <p>If you have comment sent mail</p>
            </v-col>
            <v-col>
            <v-icon>mdi-email-outline</v-icon>
                usexpress.deli@gmail.com
            </v-col>
        </v-row>
        <div>
          
        </div>
    </div>
    <FooterView/>
    </div>
</template>
<script>
import HomeNav from '../components/HomeNav.vue';
import FooterView from "../components/Footer.vue";

export default {
    name:'SupportView',
    components: {
        HomeNav,FooterView
},
}
</script>