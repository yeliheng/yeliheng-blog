import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Login from '@/views/login/Login.vue';

import Layout from '@/layout/index.vue';
import NotFound from '../views/error/404.vue';

export const constantRoutes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/home',
    component: Layout
  },
  {
    path: '',
    redirect: '/home'
  },
  
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: constantRoutes,
})

export default router
