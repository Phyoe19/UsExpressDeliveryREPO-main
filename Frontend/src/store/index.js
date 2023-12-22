import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userRole: null,
    userData: null,
    delivery: null,
    notifications: [],

  },
  getters: {

    delivery(state){
      return state.delivery;
    },

    userData(state){
      return state.userData;
    },

    userRole(state) {
      return state.userRole;
    },

    userId(state){
    if (state.userRole === 'CUSTOMER') {
      return state.userData.customerId;
    } else if (state.userRole === 'RIDER') {
      return state.userData.riderId;
    } else {
      return null;
    }
  },

  notificationCount(state) {
    return state.notifications.filter(notification => !notification.readed).length;
  },

  },
  mutations: {

    setDelivery(state, deli){
      state.delivery =deli;
    },

    setNotifications(state, noti){
      state.notifications = noti;
    },

    setUserRole(state, role) {
      state.userRole = role;
    },

    setUserData(state, data){
      state.userData = data;
    }

  },
  actions: {

  },
});
