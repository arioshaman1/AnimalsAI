import { createApp } from 'vue'; // Импортируем createApp из Vue 3
import App from './App.vue';
import router from './router'; // Импортируем роутер

createApp(App)
  .use(router) // Используем роутер
  .mount('#app');