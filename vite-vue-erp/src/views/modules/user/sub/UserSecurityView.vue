<template>
  <a-form layout="horizontal" v-bind="{ labelCol: { span: 4 }, wrapperCol: { span: 14 } }"
          :model="data" class="overflow-auto" style="max-height: 64vh">
    <a-form-item label="ID">
      <a-input-number class="mb-1 w-100" v-model:value="data.id" placeholder="请输入: ID"></a-input-number>
    </a-form-item>
    <a-form-item label="用户ID">
      <a-input-number class="mb-1 w-100" v-model:value="data.userId" placeholder="请输入: 用户ID"></a-input-number>
    </a-form-item>
    <a-form-item label="邮箱">
      <a-input class="mb-1" v-model:value="data.email" placeholder="请输入: 邮箱"></a-input>
    </a-form-item>
    <a-form-item label="手机">
      <a-input class="mb-1" v-model:value="data.phoneNumber" placeholder="请输入: 手机"></a-input>
    </a-form-item>
    <a-form-item label="qq">
      <a-input class="mb-1" v-model:value="data.qq" placeholder="请输入: qq"></a-input>
    </a-form-item>
    <a-form-item label="密保">
      <a-input class="mb-1" v-model:value="data.securityQuestions" placeholder="请输入: 密保"></a-input>
    </a-form-item>
    <a-form-item label="更新时间">
      <a-input class="mb-1" v-model:value="data.updatedAt" placeholder="请输入: 更新时间"></a-input>
    </a-form-item>
  </a-form>
</template>

<script lang="ts" setup>
// vue
import {ref, reactive, onMounted, defineProps, toRefs} from "vue";
import {cloneDeep} from 'lodash-es';

// antd
import {SearchOutlined} from '@ant-design/icons-vue';
import {message} from "ant-design-vue";

// api
import {IUserSecurity, userSecurityApi} from "@/api/erp_member/UserSecurity.ts";
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
