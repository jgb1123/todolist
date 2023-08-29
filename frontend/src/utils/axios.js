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
    async (error) => {
        const config = error.config
        if(error.response.status === 401) {
            // RefreshToken으로 토큰 재발급
            await getNewTokens()
            // 새로운 토큰 headers에 다시 저장 후 재요청
            const newAccessToken = cookies.get('accessToken')
            config.headers['Authorization'] = 'Bearer ' + newAccessToken
            return instance(config)
        }
        return Promise.reject(error)
    })

const getNewTokens = async () => {
    // instance.get('/auth/refresh')
    //     .then((res) => {
    //         if(res.status === 200) {
    //             const accessToken = res.data.accessToken
    //             const refreshToken = res.data.refreshToken
    //             cookies.set('accessToken', accessToken)
    //             cookies.set('refreshToken', refreshToken)
    //             console.log('refresh')
    //         }
    //     })
    // 동기문제

    const res = await instance.get('/auth/refresh');
    if(res.status === 200) {
        const accessToken = res.data.accessToken
        const refreshToken = res.data.refreshToken
        cookies.set('accessToken', accessToken)
        cookies.set('refreshToken', refreshToken)
        console.log('refresh')
    }
}

export default instance;