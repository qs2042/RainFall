import axios, {AxiosError, AxiosRequestConfig, AxiosResponse, CreateAxiosDefaults} from "axios";

import qs from 'qs'

import {apiUtil} from "@/utils/projectUtil";

// 配置
const config = {
    baseURL: apiUtil.getApiUrl(),
    timeout: 1000*12,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json', // application/x-www-form-urlencoded
        'X-Requested-With': 'XMLHttpRequest',
        // 'token': getToken()
        // 'real-ip': getIp(),
    },
} as CreateAxiosDefaults

// 创建axios实例
const instance = axios.create(config);

// 拦截器(请求)
instance.interceptors.request.use(
    // 请求前
    (config) => {
        // [token]
        // 方法1: config.headers.Authroization = token
        // 方法2: axios.defaults.headers.common["token"] = token
        // 方法3(常用): config.headers["token"] = token
        // config.headers["token"] = getToken()

        // [content-type]
        // axios的post请求, 默认是使用json格式的(Content-Type: application/json)
        // 这样子Java后端就需要用@RequestBody去接收, 很麻烦
        // 这里就将格式改为: application/x-www-form-urlencoded
        // 注意要单独变更array的格式化方式, 默认是: array[0]=x&array[1]=x
        // 修改过后就是array=x&array=x
        if (config.method === "post") {
            config.data = qs.stringify(config.data, { arrayFormat: 'repeat' })
            config.headers["Content-Type"] = "application/x-www-form-urlencoded"
        }
        return config
    },

    // 报错后
    (error: AxiosError) => {
        return Promise.reject(error)
    }
)

// 拦截器(响应)
instance.interceptors.response.use(
    // 响应前
    (response) => {
        return response
    },

    // 报错后
    (error) => {
        return Promise.reject(error)
    }
)


export default instance
