<template>
  <div>
    <div id="mapid">
      <img src="../../assets/navigation.png" class="map-toggle" alt="Navigate" style="width: 50px; height: 50px; cursor: pointer;"
        @click="toggleRoutingContainer">
    </div>
    <!-- <div class="map-toggle">
      
    </div> -->
  </div>
</template>

<script>

import L from 'leaflet';
import 'leaflet-routing-machine';
import "leaflet/dist/leaflet.css";


export default {
  props: [
    'sCor',
    'eCor',
  ],
  data() {
    return {
      map: null,
      leaflet: L,
      tileLayer: null,
      routingControl: null,
    };
  },
  mounted() {
    this.initializeMap();
  },
  methods: {
    initializeMap() {
      console.log("sCor: ", this.sCor);
      console.log("eCor: ", this.eCor);
      this.map = this.leaflet.map('mapid').setView(this.sCor, 15);
      this.tileLayer = L.tileLayer(
        'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
        {
          attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
        }
      );

      this.tileLayer.addTo(this.map);

      // using leaflet-routing-machine to add routing line
      this.routingControl = L.Routing.control({
        waypoints: [
          L.latLng(this.sCor[0], this.eCor[1]),
          L.latLng(this.eCor[0], this.eCor[1])
        ],
        router: L.Routing.osrmv1({
          //serviceUrl: 'http://127.0.0.1:5000/route/v1' //
          serviceUrl: 'https://router.project-osrm.org/route/v1'
        }),
        /* createMarker: function (i, wp, n) {
          var marker_icon = new L.Icon.Default();
          var marker = L.marker(wp.latLng, {
            draggable: false,
            icon: marker_icon,

          });
          console.log(i, n)
          return marker;

        } */

        createMarker: function (i, wp, n) {
          // Define the icons for the start and end waypoints
          var startIcon = L.icon({
            iconUrl: require('@/assets/pickupMarker.png'),
            iconSize: [32, 32], // Set the icon size for the start marker
          });

          var endIcon = L.icon({
            iconUrl: require('@/assets/destinationMarker.png'),
            iconSize: [32, 32], // Set the icon size for the end marker
          });

          // Check the index (i) to determine which marker icon to use
          var marker;
          if (i === 0) {
            // This is the start waypoint
            marker = L.marker(wp.latLng, {
              draggable: false,
              icon: startIcon,
            });
          } else if (i === n - 1) {
            // This is the end waypoint
            marker = L.marker(wp.latLng, {
              draggable: false,
              icon: endIcon,
            });
          } else {
            // For other waypoints (if any), use the default marker icon
            marker = L.marker(wp.latLng);
          }

          return marker;
        },


      }).addTo(this.map);

    },
    toggleRoutingContainer() {
      this.showRoutingContainer = !this.showRoutingContainer;
      if (this.showRoutingContainer) {
        // Show the routing container
        document.querySelector('.leaflet-routing-container').style.display = 'block';
      } else {
        // Hide the routing container
        document.querySelector('.leaflet-routing-container').style.display = 'none';
      }
    },
  },
}
</script>

<style >
@import '~leaflet-routing-machine/dist/leaflet-routing-machine.css';

#mapid {
  height: 500px;
  position: relative;
}


.leaflet-routing-container {
  position: absolute;
  top: 0;
  right: 0;
  z-index: 1000;
  background: #fff;
  opacity: 0.7;
  padding: 20px;
  width: 240px;
  border-radius: 0;
  display: none;
}

.map-toggle {
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 1000;
}
</style>

my LRoutingMachine is like that