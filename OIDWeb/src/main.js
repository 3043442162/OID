import { createApp } from 'vue'
import App from './App.vue'
import vue3TreeOrg from 'vue3-tree-org';
import "vue3-tree-org/lib/vue3-tree-org.css";
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';

import { createMemoryHistory, createRouter } from 'vue-router'

import TreeView from './components/Tree.vue'
import AboutView from './components/table.vue'
// import Login from './components/login.vue'

const routes = [
    { path: '/tree', component: TreeView },
    { path: '/xml', component: AboutView }
    // {path: '/login', component: Login}
]

const router = createRouter({
    history: createMemoryHistory(),
    routes,
})
createApp(App)
    .use(vue3TreeOrg)
    .use(router)
    .use(ElementPlus,  { size: 'small', zIndex: 3000 })
    .mount('#app')
