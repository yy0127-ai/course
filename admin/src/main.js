import Vue from 'vue'
import App from './app.vue'
import router from './router'

Vue.config.productionTip = false;

new Vue({
  router,
  render: h => h(App), //index.html --->    App--->app.vue 拿app.vue去渲染index.html中的app id
}).$mount('#app')
