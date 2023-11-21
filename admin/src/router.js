import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'

Vue.use(Router);
export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [{
    path: '*',
    redirect: "/login",
  },{
    path: '/login',  //如果子路由是/login 则加载的是Login组件
    component: Login
  }]
})