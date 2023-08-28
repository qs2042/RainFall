import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
    //
    plugins: [vue()],

    // 是否自动在浏览器打开
    open: true,

    // 是否开启 https
    https: false,

    // 服务端渲染
    // ssr: false,

    // 引入第三方的配置 moment,mockjs
    optimizeDeps: {
        include: [
            // '@vue/runtime-core', '@vue/compiler-sfc',
            "echarts", "axios"
        ]
    },

    // 基础路径, 默认是/, 如果要部署到nginx, 直接/访问网站就不用考虑, 如果说想/erp访问网站, 那就这样子写
    base: "/erp",

    //
    resolve: {
        alias: {
            // path.resolve(__dirname, 'src')
            '@': "/src"
        }
    }
})
