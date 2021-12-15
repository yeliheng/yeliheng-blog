import * as Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import VPagination from "@hennge/vue3-pagination";
import "@hennge/vue3-pagination/dist/vue3-pagination.css";
import VueScrollUp from 'vue-scroll-up';
import {RotateSquare2} from 'vue-loading-spinner';
import Markdown from 'vue3-markdown-it';
import 'highlight.js/styles/monokai.css';
import 'github-markdown-css/github-markdown-dark.css';


const app = Vue.createApp(App);

app.use(store);
app.use(router);
app.component('markdown',Markdown);
app.component('v-pagination',VPagination);
app.component('v-scroll-up',VueScrollUp);
app.component('v-loading',RotateSquare2);
app.mount('#app');