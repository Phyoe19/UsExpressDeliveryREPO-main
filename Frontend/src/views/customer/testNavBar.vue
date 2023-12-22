<template>
  <div class="navbar">
    <!-- Your other navbar content -->
    <div class="notification-icon" @click="toggleNotifications">
      <i class="fa fa-bell"></i>
      <span v-if="notificationCount > 0" class="notification-count">{{ notificationCount }}</span>
    </div>
    <!-- Notification popup component -->
    <NotificationPopup v-if="showNotifications" :notifications="notifications" @close="toggleNotifications" />
  </div>
</template>

<script>
import axios from 'axios';
import NotificationPopup from './NotificationPopup.vue';

export default {
  components: {
    NotificationPopup,
  },
  data() {
    return {
      showNotifications: false,
      notifications: [], // Fetch notifications from the backend and populate this array
    };
  },

  computed: {
    notificationCount() {
      return this.notifications.filter(notification => !notification.readed).length;
    },
  },
  methods: {
    toggleNotifications() {
      this.showNotifications = !this.showNotifications;
      if (this.showNotifications) {
        axios.get("http://localhost:7071/notification/updateNoti")
      }
    },
  },
};
</script>
