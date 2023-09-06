import axios from "axios";
import {useCookies} from "vue3-cookies";
import router from "../router/index.js";

const { cookies } = useCookies();
const instance = axios.create({
    baseURL: 'http://localhost:5173/api/v1',
    timeout: 1000,
})

instance.interceptors.request.use(
    (config) => {
        const accessToken = cookies.get('accessToken')
        const refreshToken = cookies.get('refreshToken')
        config.headers['Authorization'] = 'Bearer ' + accessToken
        config.headers['RefreshToken'] = refreshToken
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
);

instance.interceptors.response.use(
    (res) => {
        return res
    },
    async (error) => {
        const config = error.config
        if(error.response.status === 401) {
            // RefreshToken으로 토큰 재발급
            await getNewTokensAndAgainRequest(config)
            router.go(0)
        }
        return Promise.reject(error)
    })

const getNewTokensAndAgainRequest = async (config) => {
    try {
        const res = await instance.get('/auth/refresh');
        if (res.status === 200) {
            // 쿠키에 새로 발급받은 토큰 저장
            const accessToken = res.data.accessToken
            const refreshToken = res.data.refreshToken
            cookies.set('accessToken', accessToken)
            cookies.set('refreshToken', refreshToken)
            console.log('refresh')
            // return await instance(config)
        }
    } catch(err) {
        await router.push({name: 'login'})
    }
}

export default instance;