import { createRouter, createWebHistory } from 'vue-router'; // Импортируем функции из Vue Router 4
import UserLogin from '@/components/UserLogin.vue';
import UserRegister from '@/components/UserRegister.vue';
import PhotoUpload from '@/components/PhotoUploader.vue';
import Home from '@/components/Home.vue'; // Импортируем главную страницу
import YoloModel from '@/components/YoloModel.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home, 
  },
  {
    path: '/login',
    name: 'UserLogin',
    component: UserLogin,
  },
  {
    path: '/register',
    name: 'UserRegister',
    component: UserRegister,
  },
  {
    path: '/upload',
    name: 'PhotoUpload',
    component: PhotoUpload,
  },
  {
    path: '/yolomodel',
    name: 'yoloModel',
    component: YoloModel,
  },
  {
    path: '/:pathMatch(.*)*', // Должен быть в конце, чтобы не перехватывать другие маршруты
    redirect: '/', // Перенаправляем на главную страницу
  },
];

const router = createRouter({
  history: createWebHistory(), // Используем историю HTML5
  routes,
});

export default router;