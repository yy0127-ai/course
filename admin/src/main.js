import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from "axios";

Vue.config.productionTip = false;
Vue.prototype.$ajax = axios; //Vue.prototype.xxx可以理解为Vue组件的全局变量。可以在任意Vue组件中，使用this.xxx来获取这个值，$是代表Vue全局属性的一个约定

new Vue({ //创建一个新的Vue实例 这个实例会管理整个应用的状态和行为
  router,  //传递了一个路由实例给Vue实例
  render: h => h(App), //Vue实例的渲染函数，h是createElement函数的别名，用于创建虚拟DOM，这里意思为渲染APP组件    index.html --->    App--->app.vue 拿app.vue去渲染index.html中的app id
}).$mount('#app');//将Vue实例挂载到指定的HTML元素上
