<template>
  <div id="app">
    <UploadFile />
  </div>
</template>

<script>
import UploadFile from './components/UploadFile.vue'

export default {
  name: 'App',
  components: {
    UploadFile
  }
}
</script>

<style>
/* Глобальные стили для body и html */
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background: rgb(63, 94, 251);
  background: radial-gradient(
    circle,
    rgba(63, 94, 251, 1) 47%,
    rgba(79, 100, 240, 1) 57%,
    rgba(63, 94, 251, 1) 58%,
    rgba(99, 108, 226, 1) 62%,
    rgba(70, 70, 134, 1) 66%,
    rgba(198, 147, 158, 1) 87%
  );
}

#app {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
  background: rgba(255, 255, 255, 0.9); /* Полупрозрачный белый фон для контента */
  border-radius: 15px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  margin-top: 20px;
}
</style>