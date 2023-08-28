import {createApp, defineAsyncComponent} from 'vue'
import './style.css'
import 'ant-design-vue/dist/antd.css' // or 'ant-design-vue/dist/antd.less'
// import 'ant-design-vue/dist/antd.min.css'

import App from './App.vue'

const app = createApp(App)

//
const modules = import.meta.glob('./views/modules/*.vue');
const modulesTwo = import.meta.glob('./views/modules/*/*.vue');
for (const path in modules) {
    const result = path.match(/.*\/(.+).vue$/);
    if (result) {
       const name = result[1];
       const component = modules[path];
       app.component(path, defineAsyncComponent(component));
    }
}
for (const path in modulesTwo) {
    const result = path.match(/.*\/(.+).vue$/);
    if (result) {
        const name = result[1];
        const component = modulesTwo[path];
        app.component(path, defineAsyncComponent(component));
    }
}



import Antd from 'ant-design-vue';
// import {DatePicker} from 'ant-design-vue';
app.use(Antd)

import { createPinia } from 'pinia'
const pinia = createPinia()
app.use(pinia)

app.mount('#app')

import './utils/util.css'
import {all} from "axios";
