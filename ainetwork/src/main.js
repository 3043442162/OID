import { createApp } from 'vue'
import App from './App.vue'
import vue3TreeOrg from 'vue3-tree-org';
import "vue3-tree-org/lib/vue3-tree-org.css";
import './assets/main.css'

// import router from "./router";
// import store from "./store";
// import ElementUI from 'element-plus'
// import 'element-plus/dist/index.css'

const  app = createApp(App)

    app.use(vue3TreeOrg)
        // app.user(ElementUI)
    .mount('#app');
