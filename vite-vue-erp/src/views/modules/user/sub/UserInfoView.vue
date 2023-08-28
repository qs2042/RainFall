<template>
  <a-form layout="horizontal" v-bind="{ labelCol: { span: 4 }, wrapperCol: { span: 14 } }"
          :model="data" class="overflow-auto" style="max-height: 64vh">
    <a-form-item label="ID">
      <a-input-number class="mb-1 w-100" v-model:value="data.id" placeholder="请输入: ID"></a-input-number>
    </a-form-item>
    <a-form-item label="用户ID">
      <a-input-number class="mb-1 w-100" v-model:value="data.userId" placeholder="请输入: 用户ID"></a-input-number>
    </a-form-item>
    <a-form-item label="昵称">
      <a-input class="mb-1" v-model:value="data.nick" placeholder="请输入: 昵称"></a-input>
    </a-form-item>
    <a-form-item label="头像">
      <a-input class="mb-1" v-model:value="data.face" placeholder="请输入: 头像"></a-input>
    </a-form-item>
    <a-form-item label="年龄">
      <a-input-number class="mb-1 w-100" v-model:value="data.age" placeholder="请输入: 年龄"></a-input-number>
    </a-form-item>
    <a-form-item label="性别">
      <a-input-number class="mb-1 w-100" v-model:value="data.sex" placeholder="请输入: 性别"></a-input-number>
    </a-form-item>
    <a-form-item label="个人介绍">
      <a-input class="mb-1" v-model:value="data.introduce" placeholder="请输入: 个人介绍"></a-input>
    </a-form-item>
    <a-form-item label="注册IP">
      <a-input class="mb-1" v-model:value="data.registerIp" placeholder="请输入: 注册IP"></a-input>
    </a-form-item>
    <a-form-item label="注册时间">
      <a-input class="mb-1" v-model:value="data.createdAt" placeholder="请输入: 注册时间"></a-input>
    </a-form-item>
    <a-form-item label="更新时间">
      <a-input class="mb-1" v-model:value="data.updatedAt" placeholder="请输入: 更新时间"></a-input>
    </a-form-item>
  </a-form>
</template>

<script lang="ts" setup>
// vue
import {reactive, defineProps, toRefs} from "vue";

// antd
import {message} from "ant-design-vue";

// api
import {IUserInfo, userInfoApi} from "@/api/erp_member/UserInfo.ts";
import {iconUtil, antdUtil} from "@/utils/projectUtil";

const props = defineProps({
  // type: {} as () => IUserInfo
  data: {
    type: Object, default: () => {
    }
  }
})
const {data} = toRefs(props)

</script>

<style>

</style>
