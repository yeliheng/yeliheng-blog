import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
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
        component: () => import('@/views/home/Home.vue'),
        meta: {
          title: 'Yeliheng的技术小站 - 首页',
          keepAlive: true
        },
      },
      {
        path: '/categories',
        component: () => import('@/views/category/Category.vue'),
        meta: {
          title: '分类 - Yeliheng的技术小站',
          keepAlive: true
        }
      },
      {
        path: '/categories/:id',
        component: () => import('@/views/category/CategoryArticle.vue'),
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/tags',
        component: () => import('@/views/tag/Tag.vue'),
        meta: {
          title: '标签 - Yeliheng的技术小站',
          keepAlive: true
        }
      },
      {
        path: '/tags/:id',
        component: () => import('@/views/tag/TagArticle.vue'),
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/about',
        component: () => import('@/views/about/About.vue'),
        meta: {
          title: '关于我 - Yeliheng的技术小站',
          keepAlive: true
        }
      },

    ]
  },
  {
    path: '/p/:url',
    component: () => import('@/views/article/Article.vue'),
    meta: {
      keepAlive: false
    }
  },
  {
    path: '/categories',
    component: () => import('@/views/category/Category.vue'),
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
