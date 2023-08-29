import axios from "axios";
import {useCookies} from "vue3-cookies";
const { cookies } = useCookies();
const accessToken = cookies.get('accessToken')

const instance = axios.create({
    baseURL: 'http://localhost:5173/api/v1',
    timeout: 1000,
    headers: {
        'Authorization': `Bearer ${accessToken}`
    }
})

instance.interceptors.request.use(
    (config) => {
        console.log('request fulfilled')
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
);

instance.interceptors.response.use(
    (res) => {
        console.log('axios response', res);
        return res
    },
    (error) => {
        return Promise.reject(error)
    })

export default instance;