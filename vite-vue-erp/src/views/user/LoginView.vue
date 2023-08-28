<template>
  <main class="d-flex flex-column align-items-center justify-content-center min-vh-100" style="background-color: rgb(232, 236, 239)">
    <div class="fw-bold fs-1 mb-4">后台管理系统</div>
    <div class="bg-white rounded-2 mb-1 shadow-sm border border-1" style="min-width: 30%">
      <div class="border-bottom p-2 ps-5 pe-5 text-center"> 欢迎来到后台管理系统 </div>
      <div class="text-center fs-3 mt-3 mb-3">Login</div>
      <div class="p-4 pt-0 pb-3">
        <a-form name="basic">
          <a-form-item class="d-flex">
            <a-input-group class="d-flex" compact>
              <a-input v-model:value="data.account" placeholder="账号">
                <template #prefix>
                  <user-outlined />
                </template>
              </a-input>
              <a-tooltip title="清除当前信息和历史记录">
                <a-button @click="clear">清空记录</a-button>
              </a-tooltip>
            </a-input-group>
          </a-form-item>
          <a-form-item>
            <a-input type="password" v-model:value="data.password" placeholder="密码">
              <template #prefix>
                <form-outlined />
              </template>
            </a-input>
          </a-form-item>
          <a-form-item>
            <div class="d-flex mb-2">
              <a-checkbox  v-model:checked="data.rememberAccount">记住账号</a-checkbox>
              <a-checkbox v-model:checked="data.agreeTerms">同意条款</a-checkbox>
            </div>
            <a-button class="w-100" @click="login">登录</a-button>
            <div class="text-end text-success fw-bold" @click="onReadme">
              README.md
            </div>
          </a-form-item>
        </a-form>
      </div>
    </div>
    <div class="d-flex justify-content-between gap-2">
      <div>Copyright © <b>2022-2023</b> halfRain, All rights reserved.</div>
      <div>Version: <b>1.0.0</b></div>
    </div>
  </main>
</template>

<script lang="ts" setup>
// vue
import {onMounted, reactive} from "vue";

// antd
import {UserOutlined, FormOutlined} from "@ant-design/icons-vue";
import {message} from "ant-design-vue";

//
import {baseApi} from "@/api/erp_oss/oss";
import {userApi} from "@/api/erp_member/User";
import {localStorageUtil} from "@/utils/util";

const data = reactive({
  account: "",
  password: "",
  rememberAccount: false,
  agreeTerms: false
})

onMounted(() => {
  let account = localStorageUtil.getItem("account")
  if (account === undefined || account=== null) {
    return
  }
  data.account = account
  data.rememberAccount = true
})

const login = () => {
  if (!data.agreeTerms) {
    message.error("需要同意条款才能使用")
    return;
  }

  if (data.account.length === 0 || data.password.length === 0) {
    message.error("账号密码不可为空")
    return;
  }

  userApi.login(data.account, data.password).then(({data}) => {
    let success = data.data.success
    if (success) {
      // 保存信息
      localStorageUtil.setItem("user", data.data.map)

      // 如果需要保存名称
      if(data.rememberAccount) {
        localStorageUtil.setItem("account", data.account)
      } else {
        localStorageUtil.removeItem("account")
      }

      // 刷新
      location.reload();
    } else {
      message.error("登录失败")
    }
  })
}

const clear = () => {
  data.account = ""
  localStorageUtil.removeItem("account")
}

const onReadme = () => {
  baseApi.getObject("public", "readme.md").then(({data}) => {
    message.success(data)
  })
}
</script>

<style scoped>

</style>
