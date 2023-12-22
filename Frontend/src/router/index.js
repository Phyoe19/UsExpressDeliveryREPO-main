import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

import HomeView from '../views/HomeView.vue'
import ServiceView from '../views/ServiceView.vue'
import SupportView from '../views/SupportView.vue'
import AboutUsView from '../views/AboutUsView.vue'
import TermsOfUseView from '../views/TermsOfUseView.vue'
import PrivacypolicyView from '../views/PrivacypolicyView.vue'

import CustomerRegistration from '../views/customer/CustomerRegistration.vue'
import CustomerLogin from '../views/customer/CustomerLogin.vue'
import CustomerHome from '../views/customer/CustomerHome.vue'
import PreDelivery from '../views/delivery/PreDelivery.vue'
import GetNearRiders from '../views/delivery/GetNearRiders.vue'
import TrackRider from '../views/delivery/TrackRider.vue'
import CustomerProfile from '../views/customer/CustomerProfile.vue'
import CustomerDeliveryHistory from '../views/customer/CustomerDeliveryHistory.vue'

import RiderLogin from '../views/rider/RiderLogin.vue'
import RiderRegistration from '../views/rider/RiderRegistration.vue'
import RiderHome from '../views/rider/RiderHome.vue'
import RiderProfile from '../views/rider/RiderProfile.vue'
import RiderDeliveryNavigate from '../views/rider/RiderDeliveryNavigate.vue'
import RiderReport from '../views/rider/RiderReport.vue'
import RiderCompleteDelivery from '../views/rider/RiderCompleteDelivery.vue'

import AdminHome from '../views/admin/AdminHome.vue'
import AdminLogin from '../views/admin/AdminLogin.vue'
import AdminMaps from '../views/admin/AdminMaps.vue'
import AdminRiders from '../views/admin/AdminRiders.vue'
import AdminCustomers from '../views/admin/AdminCustomers.vue'



Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },

  //customer

  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },

  {
    path: '/service',
    name: 'ServiceView',
    component: ServiceView
  },
  {
    path: '/terms',
    name: 'TermsOfUseView',
    component: TermsOfUseView
  },

  {
    path: '/privacy',
    name: 'PrivacypolicyView',
    component: PrivacypolicyView
  },

  {
    path: '/support',
    name: 'SupportView',
    component: SupportView
  },

  {
    path: '/aboutus',
    name: 'AboutUsView',
    component: AboutUsView
  },


  {
    path: '/customer-registration',
    name: 'CustomerRegistration',
    component: CustomerRegistration
  },

  {
    path: '/customer-login',
    name: 'CustomerLogin',
    component: CustomerLogin,

  },

  {
    path: '/customer-home/:id',
    name: 'CustomerHome',
    component: CustomerHome,
    meta: {
      requiredRole: 'CUSTOMER',
    },
  },

  {
    path: '/customer/profile/:id',
    name: 'CustomerProfile',
    component: CustomerProfile,
    meta: {
      requiredRole: 'CUSTOMER',
    },
  },

  {
    path: '/customer/delivery-history/:id',
    name: 'CustomerDeliveryHistory',
    component: CustomerDeliveryHistory,
    meta: {
      requiredRole: 'CUSTOMER',
    },
  },

  {
    path: '/delivery/pre-delivery/:id',
    name: 'PreDelivery',
    component: PreDelivery,
    meta: {
      requiredRole: 'CUSTOMER',
    },
  },
  {
    path: '/delivery/get-near-riders/:id',
    name: 'GetNearRiders',
    component: GetNearRiders,
    meta: {
      requiredRole: 'CUSTOMER',
    },
  },
  {
    path: '/delivery/track-rider/:id',
    name: 'TrackRider',
    component: TrackRider,
    meta: {
      requiredRole: 'CUSTOMER',
    },

  },


  //rider

  {
    path: '/rider-registration',
    name: 'RiderRegistration',
    component: RiderRegistration
  },

  {
    path: '/rider-login',
    name: 'RiderLogin',
    component: RiderLogin
  },

  {
    path: '/rider-home/:id',
    name: 'RiderHome',
    component: RiderHome,
    meta: {
      requiredRole: 'RIDER',
    },
  },

  
  {
    path: '/rider-complete-delivery/:id',
    name: 'RiderCompleteDelivery',
    component: RiderCompleteDelivery,
    meta: {
      requiredRole: 'RIDER',
    },
  },

  {
    path: '/rider/profile/:id',
    name: 'RiderProfile',
    component: RiderProfile,
    meta: {
      requiredRole: 'RIDER',
    },
  },

  {
    path: '/rider/delivery-navigate/:id',
    name: 'RiderDeliveryNavigate',
    component: RiderDeliveryNavigate,
    meta: {
      requiredRole: 'RIDER',
    },
  },

  {
    path: '/rider/report/:id',
    name: 'RiderReport',
    component: RiderReport,
    meta: {
      requiredRole: 'RIDER',
    },
  },


  //admin
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: AdminLogin
  },
  
  {
    path: '/admin/home/:id',
    name: 'AdminHome',
    component: AdminHome,
    meta: {
       requiredRole: 'admin',
    },
  },

  {
    path: '/admin/maps/:id',
    name: 'AdminMaps',
    component: AdminMaps,
    meta: {
       requiredRole: 'admin',
    },
  },

  {
    path: '/admin/riders/:id',
    name: 'AdminRiders',
    component: AdminRiders,
    meta: {
       requiredRole: 'admin',
    },
  },

  {
    path: '/admin/customers/:id',
    name: 'AdminCustomers',
    component: AdminCustomers,
    meta: {
       requiredRole: 'admin',
    },
  },




]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {

  const requiredRole = to.meta.requiredRole;
  // Check if the user role matches the required role for the route
  if (requiredRole && store.state.userRole !== requiredRole) {
    // Redirect to an appropriate route based on the user's role
    switch (store.state.userRole) {
      case 'CUSTOMER':
        next({ name: 'CustomerLogin' });
        break;
      case 'RIDER':
        next({ name: 'RiderLogin' });
        break;
      case 'ADMIN':
        next({ name: 'AdminLogin' });
        break;
      default:
        next({ name: 'home' });
        break;
    }
  } else {
    // User has the required role, allow navigation to the target route
    next();
  }
});

export default router
