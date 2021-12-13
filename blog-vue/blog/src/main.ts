import * as Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import VPagination from "@hennge/vue3-pagination";
import "@hennge/vue3-pagination/dist/vue3-pagination.css";
import VueScrollUp from 'vue-scroll-up';


const app = Vue.createApp(App);

app.use(store);
app.use(router);
app.component('v-pagination',VPagination);
app.component('v-scroll-up',VueScrollUp);
app.mount('#app');