import axios from 'axios'
const http = axios.create(
    {
        baseURL: 'http://localhost:80',
        timeout: 10000
    }
)

// axios请求拦截器
http.interceptors.request.use(config => {
    return config
}, e => Promise.reject(e))

// axios响应式拦截器
http.interceptors.response.use(res => res.data, e => {
    return Promise.reject(e)
})


export default http