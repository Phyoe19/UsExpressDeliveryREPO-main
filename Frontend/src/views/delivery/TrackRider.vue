<template>
    <div>
        <UserNavBar />
        <div class="pa-10 mt-15" v-if="!isCompletedDelivery">
            <div v-if="rider.riderId > 0 && pickupLocation.length > 1" style="margin-top: 100px;">
               


                <v-card max-width="500px" class="mx-auto">

                    <v-card-text class="py-0 mx-auto">
                        <div class="map-timeline">
                            <div class="map-timeline-content">
                                <v-timeline align-top dense>
                                    <v-card class="mx-auto" max-width="400">
                                        <v-card dark flat>

                                            <v-card-title class="pa-2 #404040">
                                                <h3 class="text-h6 font-weight-light text-center grow">
                                                    Tracking
                                                </h3>
                                            </v-card-title>
                                        </v-card>
                                        <v-card-text class="py-0">
                                            <v-timeline align-top dense>

                                                <v-timeline-item color="teal lighten-3" small>
                                                    <v-row class="pt-1">

                                                        <v-col>
                                                            <strong>Delivery</strong>
                                                            <div class="text-caption">Package Delivery</div>

                                                        </v-col>
                                                    </v-row>
                                                </v-timeline-item>


                                                <v-timeline-item color="pink" small>
                                                    <v-row class="pt-1">

                                                        <v-col>
                                                            <strong>Rider Location</strong>
                                                            <div class="text-caption">Rider name: {{ rider.name }}</div>
                                                            <div class="text-caption">Rider phone no: {{ rider.phoneNumber
                                                            }}</div>
                                                            <v-avatar>
                                                                <v-img src="../../assets/profileAvatar.jpg"></v-img>
                                                            </v-avatar>
                                                        </v-col>
                                                    </v-row>
                                                </v-timeline-item>

                                                <v-timeline-item color="pink" small>
                                                    <v-row class="pt-1">

                                                        <v-col>
                                                            <strong>Rider Location: {{ riderLocationText }}</strong>
                                                        </v-col>
                                                    </v-row>
                                                </v-timeline-item>

                                                <v-timeline-item color="teal lighten-3" small>
                                                    <v-row class="pt-1">

                                                        <v-col>
                                                            <strong>Destination Address: {{ destinationAddressText
                                                            }}</strong>
                                                        </v-col>
                                                    </v-row>
                                                </v-timeline-item>
                                            </v-timeline>
                                        </v-card-text>
                                    </v-card>
                                </v-timeline>
                            </div>
                        </div>
                    </v-card-text>
                </v-card>

                <div style="max-width: 500px; margin: auto;">
                    <LRoutingMachine :sCor="[pickupLocation[0], pickupLocation[1]]"
                        :eCor="[rider.latitude, rider.longitude]" />
                </div>


            </div>
            <div v-else>
                <div v-if="hasDelivery">
                    {{ displayText }}
                </div>
                <div v-if="showLoading">
                    <LoadingComp />
                </div>



            </div>

            <div v-if="notAvailable">
                <h1>Delivery ID is not available</h1>
            </div>


        </div>

        <div v-if="isCompletedDelivery">
            <div v-if="isDeliverySuccessful" style="margin-top: 100px;">
                <h1>Your Delivery has been delivered successfully. Thanks for choosing us</h1>
            </div>
            <div v-if="isDeliveryFail" style="margin-top: 100px;">
                <h1>Your Delivery has not been delivered. Sorry for being unsuccessful. We will inform the re deliver date
                    later</h1>
            </div>

        </div>
    </div>
</template>

<script>
import { connectWebSocket, subscribeToDestination } from '@/utils/websocketconfig';
import LRoutingMachine from '../leafletMap/LRoutingMachine.vue';
import axios from 'axios';
import UserNavBar from '@/components/UserNavBar.vue';
import LoadingComp from '@/components/LoadingComp.vue';
import { API_KEY } from '@/utils/apiKey';

export default {

    components: {
        LRoutingMachine,
        UserNavBar,
        LoadingComp,
    },

    data() {
        return {

            riderLocationText: '',

            isCompletedDelivery: false,

            isDeliverySuccessful: false,

            isDeliveryFail: false,

            delivery: {},

            showLoading: false,

            displayText: '',

            hasDelivery: false,
            rider: {},
            deliveryId: this.$route.params.id,
            destinationAddressText: '',

            notAvailable: false,

            pickupLocation: [],
            riderLocation: [],

        }
    },

    mounted() {
        this.getDelivery();
        connectWebSocket(this.subscribeWebSocketMessage);

        setInterval(()=>{
            this.getCompleteDelivery()
        },10000);
    },

    methods: {

         getRiderLocation() {
            const language = 'my';
            return new Promise((resolve, reject) => {
                 axios
                    .get(
                        `https://api.opencagedata.com/geocode/v1/json?key=${API_KEY}&q=${this.rider.latitude}+${this.rider.longitude}&language=${language}`
                    )
                    .then((response) => {
                        const result = response.data.results[0].components;
                        if (result) {
                            const { road, suburb } = result;
                            this.riderLocationText = road + ' ' + suburb;
                            resolve(this.riderLocationText);
                        } else {
                            reject(new Error('Unable to get postal code'));
                        }
                    })
                    .catch((error) => {
                        reject(error);
                    });
            });
        },

        getDelivery() {
            axios.get(`http://localhost:7071/delivery/trackDelivery/${this.deliveryId}`)
                .then(response => {

                    if (response != null) {

                        const data = response.data;
                        if (data.delivery != null) {
                            this.pickupLocation[0] = data.delivery.pickupLat;
                            this.pickupLocation[1] = data.delivery.pickupLng;
                            this.destinationAddressText = data.delivery.destinationAddressText;

                            this.displayText = data.text;
                            this.showLoading = true;
                            console.log("Pickup Cors: ", this.pickupLocation);
                        }
                        else if (data.text != null) {
                            this.displayText = data.text;


                        }
                        else {
                            this.displayText = "SOMETHING WRONG"
                        }

                        this.hasDelivery = true;

                        console.log("Response for track ", data)

                    } else {
                        console.log("delivery id is not available")
                    }

                }).catch(error => {
                    console.error(error)
                    this.notAvailable = true;
                });
        },

        subscribeWebSocketMessage() {
            subscribeToDestination(`/package-delivery/rider/track-rider/${this.deliveryId}`, this.handleRiderMessage);
        },

        handleRiderMessage(message) {
            const riderData = JSON.parse(message.body);
            this.rider = riderData;

            this.getRiderLocation();
            console.log("Rider: ", this.rider);
        },

        async getCompleteDelivery() {
            await axios.get("http://localhost:7071/delivery/getById/" + this.deliveryId)
                .then(response => {
                    const respData = response.data;
                    if (respData.cdStatus != "ONGOING") {
                        this.isCompletedDelivery = true;
                    
                        if (respData.cdStatus == "COMPLETED") {
                            this.isDeliverySuccessful = true;
                        } else {
                            this.isDeliveryFail = true;
                        }
                    }
                })
        },



    },




}
</script>

<style></style>