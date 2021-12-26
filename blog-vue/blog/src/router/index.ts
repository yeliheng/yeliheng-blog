import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import NotFound from '../views/error/404.vue';
import Layout from '../layout/index.vue';

export const constantRoutes: Array<RouteRecordRaw> = [
  {
    path: '/',
    component: Layout,
    meta: {
      title: 'Yeliheng的技术小站 - 首页',
      keepAlive: true
    },
    children:[
      {
        path: '/',
        component: import('@/views/home/Home.vue'),
        meta: {
          title: 'Yeliheng的技术小站 - 首页',
          keepAlive: true
        },
    },
    {
      path: '/categories',
      component: (resolve) => import('@/views/category/Category.vue'),
      meta: {
        title: '分类 - Yeliheng的技术小站',
        keepAlive: true
      }
    },
    {
      path: '/tags',
      component: (resolve) => import('@/views/tag/Tag.vue'),
      meta: {
        title: '标签 - Yeliheng的技术小站',
        keepAlive: true
      }
    }
  ]
  },
  {
    path: '/articles/:id',
    component: (resolve) => import('@/views/article/Article.vue'),
    meta: {
      keepAlive: false
    }
  },
  {
    path: '/categories',
    component: (resolve) => import('@/views/category/Category.vue'),
    meta: {
      title: '分类 - Yeliheng的技术小站',
      keepAlive: false
    }
  }
  
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: constantRoutes,
})

export default router
