<template>
    <div>
      <HomeNav/>
      <v-container color="#c4c3d0" class="mt-15">
      <div class="background"></div>
      <v-card-title>Terms Of Use</v-card-title>
      <p>We,Us-Express,a services incorporated in Yangon and hereinafter referred to as"(Us-Express)","we","us","our",would like to thank you hereafter referred to as ("you","user" or "buyer")for visting our website.Your terms of use of our site and the provision of serevices by us through the site shall be governed by the terms and conditiions as set out herein("User Aggrement"),our privacy poplicy("Privacy Policy")and all other polices, which shall form a legally binding and enforceable contract between you and us.You should immediately cease all usage of our site and notify us to discountinue any updates which you have signed up for,
        failin which you shall be deemed to be bound by all the provisions contained in the User Aggrement, Privacy Policy and all other policies.
      </p>
      
    <v-card-title>Customer Account</v-card-title>
    <p>
        <ul>
            <li>User must be registered on sites to access or user some services(a registered User is also referred to as a "Customer below").Except with Us-Express's approval, one User may only register one customer account on the sites.</li>
            <li>Upon registration on the sites,shall assign an account and issue a customer ID and password(the latter shalls be choosen by a regisered user during registratiion) to each register User,An account may have web-based email account with limited storage space for customer to send or receive emails.</li>
            <li>As a customer ID and password is unique to a single account.Each customer shall be solely responsible for maintaining the confidentiality and security of your customer ID and password and for all activies that occurs under your account.</li>
            <li>Customer agrees that all activities that occur under your account will be deemed to have been authorized by the customer.</li>
            <li>Customer acknoledges that sharing of your account with other person, or allowing multiple user outsite of your business entity to use your account(collectively, "multiple use"), may cause irreparable harm to our delivery or other user of the site.Our Delivery shall not be liable for any loss or damages arisin from such a breach and shall have the right to suspend or terminate Customere's account without liability to Customer.</li>
        </ul>
        
    </p>
      
  
  </v-container>
  <FooterView/>
  </div>
  </template>
  <script>
  import HomeNav from '../components/HomeNav.vue';
  import FooterView from "../components/Footer.vue";
  export default {
      name:'TermsofUseView',
      components: {
          HomeNav,FooterView
  },
  methods:{
        navigateTo(routeName) {
  
  this.$router.push({ name: routeName });
  },
      },
  }
  </script>