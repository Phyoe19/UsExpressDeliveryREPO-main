<template>
    <div>
      <div>
        <AdminNavBar class="mb-10" :adminId="adminId"/>
      </div>
  
      <div>
        <UsExpressSlogan />
      </div>
  
    </div>
  </template>
    
  <script>
  import AdminNavBar from '@/components/AdminNavBar.vue';
  
  import UsExpressSlogan from '../../components/UsExpressSlogan.vue';
  export default {
    name: 'AdminCustomers',


    data(){
      return{
        adminId: this.$route.params.id,
      }
    },

    components: {
      AdminNavBar,
      UsExpressSlogan
    },
    // Component options...
  }
  </script>
    
  <style>
  /* Component styles... */
  </style>
    