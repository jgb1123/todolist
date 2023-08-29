import axios from "axios";
import {useCookies} from "vue3-cookies";
const { cookies } = useCookies();
const accessToken = cookies.get('accessToken')
const refreshToken = cookies.get('refreshToken')

const instance = axios.create({
    baseURL: 'http://localhost:5173/api/v1',
    timeout: 1000,
    headers: {
        'Authorization': `Bearer ${accessToken}`,
        'RefreshToken': `${refreshToken}`
    }
})

instance.interceptors.request.use(
    (config) => {
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
        const originalConfig = error.config;
        if(error.response.status === 401) {
            getRefresh()
            return instance(originalConfig)
        }
        return Promise.reject(error)
    })

const getRefresh = (config) => {
    instance.get('/auth/refresh')
        .then((res) => {
            if(res.status === 200) {
                cookies.set('accessToken', res.data.accessToken)
                cookies.set('refreshToken', res.data.refreshToken)
                console.log('refresh')
            }
        })
}

export default instance;