import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Login from '@/views/login/Login.vue';

import Layout from '@/layout/index.vue';
import Home from '@/views/Home.vue';
import Profile from '@/views/profile/Profile.vue';

export const constantRoutes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/',
    redirect: '/dashboard',
    component: Home
  },
  {
    path: '/',
    component: Layout,
    name: 'ParentRoute',
    children: [
      {
        path: '/users/profile',
        component: Profile,
        name: '个人中心',
      }
    ]
  },

];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: constantRoutes,
})

export default router
