import * as Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import VPagination from "@hennge/vue3-pagination";
import "@hennge/vue3-pagination/dist/vue3-pagination.css";
import {RotateSquare2} from 'vue-loading-spinner';
import Markdown from 'vue3-markdown-it';
import 'highlight.js/styles/monokai.css';
import 'github-markdown-css/github-markdown-dark.css';
import 'tocbot/src/scss/tocbot.scss';
import MyProfile from '@/components/MyProfile.vue';
import VueSidebarMenu from 'vue-sidebar-menu';
import 'vue-sidebar-menu/dist/vue-sidebar-menu.css';

router.beforeEach((to, from) => {
    if(to.meta.title) {
        document.title = to.meta.title as string;
    }
    return;
});

const app = Vue.createApp(App);

app.use(store);
app.use(router);
app.component('markdown',Markdown);
app.component('v-pagination',VPagination);
app.component('v-loading',RotateSquare2);
app.component('my-profile',MyProfile);
app.use(VueSidebarMenu);
app.mount('#app');