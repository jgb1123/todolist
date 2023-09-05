import { createApp } from 'vue'
import {Notify, Quasar, SessionStorage, Dialog} from 'quasar'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import VueCookies from 'vue3-cookies';
import './style.css'
import 'quasar/dist/quasar.css'
import '@quasar/extras/material-icons/material-icons.css'
import '@quasar/extras/fontawesome-v5/fontawesome-v5.css'
import '@quasar/extras/mdi-v4/mdi-v4.css'

const app = createApp(App)
const pinia = createPinia();
app.use(Quasar, {
    plugins: {
        SessionStorage,
        Notify,
        Dialog
    }
})
app.use(pinia)
app.use(router)
app.use(VueCookies)
app.config.globalProperties.axios = axios;
app.mount('#app')
