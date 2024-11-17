import { createApp } from 'vue'
import App from './App.vue'
import vue3TreeOrg from 'vue3-tree-org';
import "vue3-tree-org/lib/vue3-tree-org.css";
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';

import { createMemoryHistory, createRouter } from 'vue-router'

import TreeView from './components/Tree.vue'
import XmlView from './components/XML.vue'
import Login from './components/login.vue'
import Menu from './components/Menu.vue'
const routes = [
    {path:'/',redirect:'/login'},
    { path: '/tree', component: TreeView },
    { path: '/xml', component: XmlView },
    {path: '/login', component: Login},
    {path:"/menu", component:Menu }
]

const router = createRouter({
    history: createMemoryHistory(),
    routes,
})
router.beforeEach((to, from, next) => {
    // 判断有没有登录
    if (!localStorage.getItem('satoken')) {
        console.log("success log")
        if (to.name === "/login") {
            next();
        } else {
            router.push('/login')
        }
    } else {
        next();
    }
});

createApp(App)
    .use(vue3TreeOrg)
    .use(router)
    .use(ElementPlus,  { size: 'small', zIndex: 3000 })
    .mount('#app')
