<template>
      <div id="loading-container">
      <div class="loader"></div>
      <p>Loading...</p>
    </div>
</template>

<script>
export default {

}
</script>

<style scoped>

#loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100vw;
  height: 100vh;
}

.loader {
background-image: url("../assets/cat.gif");
width: 500px;
height: 500px;
background-size: cover;
background-position: center;
}

</style>