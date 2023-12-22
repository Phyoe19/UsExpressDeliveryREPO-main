<template>
    <div class="notification-popup">
      <div class="notification-item" v-for="notification in notifications" :key="notification.notiId">
        <div>{{ notification.notiData }}</div>
        <div v-if="!notification.readed" class="mark-as-read" @click="markAsRead(notification.notiId)">Mark as Read</div>
      </div>
    </div>
  </template>
  
  <script>
import axios from 'axios';
  export default {
    props: ['notifications'],
    methods: {
      markAsRead(notificationId) {
        // Send an API request to mark the notification as read on the backend.
        // Update the 'read' property of the notification and save it to the backend.

        axios.put('http://localhost:7071/notification/updateNoti', notificationId)
        .then(response => {
          console.log(response);
        })
        .catch(error => console.error(error))

      },
    },
  };
  </script>
  


