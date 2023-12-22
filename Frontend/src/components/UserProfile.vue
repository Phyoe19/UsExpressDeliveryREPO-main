<template>
    <div class="user-profile">
      <div class="avatar">
        <img :src="currentUser.avatar" alt="User Avatar" />
      </div>
      <div class="profile-info">
        <h3>{{ currentUser.name }}</h3>
        <p>{{ currentUser.email }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "UserProfile",
    data(){
        return{
            currentUser: {
        name: "John Doe",
        email: "john@example.com",
        avatar: "src/assets/profileAvatar.jpg",
      },
        }
    }
  };
  </script>
  
  <style scoped>
  .user-profile {
    display: flex;
    align-items: center;
  }
  
  .avatar {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    overflow: hidden;
    margin-right: 1rem;
  }
  
  .avatar img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .profile-info {
    flex: 1;
  }
  
  .profile-info h3 {
    margin: 0;
    font-size: 1.5rem;
  }
  
  .profile-info p {
    margin: 0;
  }
  </style>
  