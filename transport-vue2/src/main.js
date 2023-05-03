import Vue from 'vue'
import App from './App.vue'
import router from './router'
import 'materialize-css/dist/js/materialize.min.js'

import VueGoodTablePlugin from 'vue-good-table';
// import the styles
import 'vue-good-table/dist/vue-good-table.css'
import http from "@/plugins/http";

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
Vue.use(VueGoodTablePlugin)

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(http, {
    baseUrl: "http://localhost:8080/api/v1"
})
Vue.prototype.$baseUrl = "http://localhost:8080/api/v1"
export const url = Vue.prototype.$baseUrl

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

