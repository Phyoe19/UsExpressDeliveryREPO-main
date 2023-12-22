<template>
    <div v-if="currentLocation || riderLocation">

        <l-map style="height: 500px ; border-radius: 30px;" :zoom="zoom" :center="currentLocation">
            <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>

            <div v-if="riderLocation">
                <l-marker :lat-lng="riderLocation" :icon="riderMarkerIcon"></l-marker>
            </div>

            <div v-if="currentLocation">
                <l-circle :lat-lng="currentLocation" :radius="circle.radius" :color="circle.color" />
                <l-marker :lat-lng="currentLocation" :icon="pickupMarkerIcon"></l-marker>
                <div v-if="riders">
                    <div v-if="riders.length > 0">
                        <l-marker v-for="rider in riders" :key="rider.id" :lat-lng="getLatLng(rider)"
                            :icon="riderMarkerIcon">
                        </l-marker>

                    </div>
                </div>
            </div>
        </l-map>

    </div>
</template>

<script>
import { LMap, LTileLayer, LCircle, LMarker } from 'vue2-leaflet';
import L from 'leaflet';
export default {

    props: [
        'riders',
        'currentLocation',
        'riderLocation',
    ],

    components: {
        LMap,
        LTileLayer,
        LCircle,
        LMarker,
    },
    data() {
        return {

            url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',// https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png
            attribution:
                '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            zoom: 15,

            circle: {
                radius: 3000,
                color: 'grey',//
            },

            riderMarkerIcon: L.icon({
                iconUrl: require('@/assets/riderbikeicon.png'),
                iconSize: [50, 50],
            }),
            pickupMarkerIcon: L.icon({
                iconUrl: require('@/assets/pickupMarker.png'),
                iconSize: [50, 50],
            }),

        };
    },

    methods: {
        getLatLng(rider) {
            return [rider.latitude, rider.longitude];
        },
    },


}
</script>

<style></style>