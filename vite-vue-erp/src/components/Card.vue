<template>
  <div v-if="size === 'middle'" :style="styles[type]">
    <div class="pt-2 pb-2 ps-3 pe-3 d-flex">
      <div class="w-100">
        <div class="h1 text-white fw-bold">{{ value }}+</div>
        <div class="text-white">{{ label }}</div>
      </div>
      <div>
        <component class="text-white align-self-end opacity-50" style="font-size: 6em" :is="iconUtil.getRandomIcon()" />
      </div>
    </div>
    <div class="p-1 text-center text-white" style="background-color: rgba(0,0,0,.1)">
      More info
      <right-circle-outlined/>
    </div>
  </div>

  <div v-else class="d-flex bg-white border shadow-sm p-1">
    <div class="p-3" :style="styles[type]">
      <component class="fs-2 text-white" :is="iconUtil.getRandomIcon()" />
    </div>
    <div class="ms-2">
      <div>{{ label }}</div>
      <div>{{ value }}+</div>
    </div>
  </div>

</template>

<script lang="ts" setup>
import {defineProps, toRefs} from "vue";

import {RightCircleOutlined} from "@ant-design/icons-vue";

import {iconUtil} from "@/utils/projectUtil";

const prop = defineProps({
  type: {
    type: String,
    default: 'default',
    required: false,
    validator: (value) => {
      // 自定义验证逻辑
      return ["default", "success", "primary", "warning", "dark", "danger"].includes(<string>value);
    }
  },
  size: {
    type: String,
    default: 'middle',
    required: false,
    validator: (value) => {
      return ["small", "middle", "large"].includes(<string>value);
    }
  },
  label: {
    type: String,
    default: 'null',
    required: false
  },
  value: {
    type: Number,
    default: 0, // 显示模式: 1k5+, 15,000+
    required: false,
  }
})
const { type } = toRefs(prop)

const styles = {
  default: {
    backgroundColor: "rgb(116,119,116)"
  },
  success: {
    backgroundColor: "rgb(25, 135, 84)"
  },
  primary: {
    backgroundColor: "rgb(13, 110, 253)"
  },
  warning: {
    backgroundColor: "rgb(254, 193, 7)"
  },
  dark: {
    backgroundColor: "rgb(33, 37, 41)"
  },
  danger: {
    backgroundColor: "rgb(220, 53, 69)"
  }
}

</script>

<style scoped>

</style>
