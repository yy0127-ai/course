import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login.vue"
import Admin from "./views/admin.vue"
import Welcome from "./views/admin/welcome.vue"
import Chapter from "./views/admin/chapter.vue"
import Section from "./views/admin/section.vue"

Vue.use(Router); //Vue实例中使用Router插件
export default new Router({
  mode: "history", //路由模式 历史模式 不会带#
  base: process.env.BASE_URL, //基础路径
  routes: [{
    path: "*",
    redirect: "/login",
  },{
    path: "/login",  //如果子路由是/login 则加载的是Login组件
    component: Login,
  },{
    path: "/",
    name: "admin",
    component: Admin,
    children: [{
      path: "welcome",
      name: "welcome",
      component: Welcome,
    },{
      path: "business/chapter",
      name: "business/chapter",
      component: Chapter,
    },{
      path: "business/section",
      name: "business/section",
      component: Section,
    }]
  }]
})