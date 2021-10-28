import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Login from '../views/login/Login.vue';
import Home from '../views/Home.vue';
import Layout from '@/layout/index.vue';

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
  {
    path: '/dashboard',
    component: Home
  }
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: constantRoutes
})

export default router
