import { createApp } from 'vue'
import {Notify, Quasar, SessionStorage} from 'quasar'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import 'quasar/dist/quasar.css'

const app = createApp(App)
app.use(Quasar, {
    plugins: {
        SessionStorage,
        Notify
    }
})
app.use(router)
app.config.globalProperties.axios = axios;
app.mount('#app')
