import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";
import VueAxios from "vue-axios";
import './plugins/element.js'
import 'element-ui/lib/theme-chalk/index.css';
import 'default-passive-events'
import { RecycleScroller } from 'vue-virtual-scroller'
Vue.config.productionTip = false;
//可以在vue组件中通过this . axios 使用axios库
Vue.use(VueAxios, axios);

const ip="http://127.0.0.1:8006"
// const ip="http://127.0.0.1:8000"
 axios.defaults.baseURL = ip
//实际生产环境需要外网地址

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')

import global_ from './components/Global'//引用文件
Vue.prototype.GLOBAL = global_//挂载到Vue实例上面





axios.interceptors.request.use(config => {
    // 为请求头添加Authorization字段为服务端返回的token
    config.headers.Authorization = localStorage.getItem('token')
    // return config是固定用法 必须有返回值
    return config
})


