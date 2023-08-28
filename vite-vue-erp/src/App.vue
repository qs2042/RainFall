<template>
  <template v-if="user !== null">
    <index-view/>
  </template>
  <template v-else>
    <login-view />
  </template>

  <ws-view />
</template>

<script setup lang="ts">
// vue
import {ref, watch} from "vue";

// view
import IndexView from "./views/IndexView.vue";

// store
import settingsStore from "./plugins/pinia/settingsStore";
import LoginView from "@/views/user/LoginView.vue";
import WsView from "@/views/WsView.vue";
import {message} from "ant-design-vue";
const storeSettings = settingsStore()
document.title = storeSettings.title

// 标题
watch(() => storeSettings.title, (newValue, oldValue) => {
  document.title = storeSettings.title
})

// 字体
watch(() => storeSettings.font, (newValue, oldValue) => {
  let e = document.querySelector("#app")
  e.style.fontFamily = storeSettings.font
})
watch(() => storeSettings.fontSize, (newValue, oldValue) => {
  let e = document.querySelector("#app")
  e.style.fontSize = storeSettings.fontSize + "px"
})

// 是否登录
const user = localStorage.getItem("user")

/*
TODO
  自动生成的增删改查页面, 筛选数据(id, 排序, 布尔值, 时间)
  .
  内容管理设置     文章、页面、分类、标签、评论、回收站、垃圾邮件
  SEO 设置        网站地图、404 页面
  数据备份与恢复   数据备份、还原、导出
  缓存设置        页面缓存、数据库缓存、对象缓存
  安全设置        防火墙、安全认证、SSL 证书
  邮件设置        邮件服务器、邮件模板、邮件通知
 */

</script>



