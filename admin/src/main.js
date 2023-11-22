import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from "axios";

Vue.config.productionTip = false;
Vue.prototype.$ajax = axios; //Vue.prototype.xxx可以理解为Vue组件的全局变量。可以在任意Vue组件中，使用this.xxx来获取这个值，$是代表Vue全局属性的一个约定

new Vue({
  router,
  render: h => h(App), //index.html --->    App--->app.vue 拿app.vue去渲染index.html中的app id
}).$mount('#app');
