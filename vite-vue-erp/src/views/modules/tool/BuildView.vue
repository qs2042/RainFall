<template>
  <main>
    <a-row :gutter="20" class="min-vh-100">
      <a-col :span="5">
        <div class="d-flex justify-content-around flex-wrap gap-2">
          <template v-for="(item, index) in buttonList" :key="index">
            <a-button @click="item.onClick">
              <template #icon>
                <component :is="iconUtil.getIcon(item.icon)"></component>
              </template>
              {{ item.label }}</a-button>
          </template>
        </div>
      </a-col>

      <a-col class="border-2 border-start border-end" :span="14">
        <dynamic-validate-form :fields="fields"/>
      </a-col>

      <a-col :span="5">
        123
      </a-col>
    </a-row>
  </main>
</template>

<script lang="ts" setup>
// vue
import {ref} from "vue";

//
import DynamicValidateForm from "@/components/DynamicValidateForm.vue";
import {iconUtil} from "@/utils/projectUtil";

const addComponent = () => {
  fields.value.push({label: "账号名称", key: "username", value: ""})
}
const buttonList = ref([
  {label: "单行文本", icon: "edit-outlined", onClick: () => addComponent()},
  {label: "多行文本", icon: "form-outlined", onClick: () => addComponent()},
  {label: "按钮", icon: "appstore-outlined", onClick: () => addComponent()},
  {label: "开关", icon: "bulb-outlined", onClick: () => addComponent()},
  {label: "评分", icon: "star-outlined", onClick: () => addComponent()},
  {label: "上传", icon: "cloud-upload-outlined", onClick: () => addComponent()},
  {label: "图片", icon: "file-image-outlined", onClick: () => addComponent()},
  {label: "多选", icon: "check-square-outlined", onClick: () => addComponent()},
])


const fields = ref([
  {label: "账号名称", key: "username", value: ""},
])

</script>

<style scoped>
</style>
