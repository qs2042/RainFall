<template>
  <div class="d-flex flex-column justify-content-center align-items-center p-2">
    <div class="fw-bold" style="font-size: 4em">{{ d.getRawMsg(code) }}</div>
    <div class="fs-1 opacity-75">{{ d.getMsg(code) }}</div>
    <a-space>
      <a-button>回到首页</a-button>
      <a-button>保存错误</a-button>
    </a-space>
  </div>
</template>

<script lang="ts" setup>
import {defineProps, reactive, toRefs, defineEmits} from "vue"

const emits = defineEmits(["update"])

const d = reactive({
  raw: {
    "200": { raw: "200 OK", zh: "请求成功" },
    "201": { raw: "201 Created", zh: "创建成功" },
    "204": { raw: "204 No Content", zh: "请求成功,但响应体为空" },
    "400": { raw: "400 Bad Request", zh: "请求错误,服务器无法处理" },
    "401": { raw: "401 Unauthorized", zh: "未授权,需要进行身份验证" },
    "403": { raw: "403 Forbidden", zh: "禁止访问,服务器拒绝请求" },
    "404": { raw: "404 Not Found", zh: "未找到页面,您的页面迷路了" },
    "500": { raw: "500 Internal Server Error", zh: "服务器内部错误" },
    "502": { raw: "502 Bad Gateway", zh: "网关错误" },
    "503": { raw: "503 Service Unavailable", zh: "服务不可用" },
    "504": { raw: "504 Gateway Timeout", zh: "网关超时" },
  },

  getMsg(code) {
    const msg = this.raw[code];
    return msg ? msg.zh : "未知错误";
  },

  getRawMsg(code) {
    const msg = this.raw[code];
    return msg ? msg.raw : "Unknown Error";
  },
})

const props = defineProps({
  code: { type: String, default: () => "404", validator(value: unknown): boolean {

    return ['404'].includes(<string>value)
    }
  }
})

const { code } = toRefs(props)
</script>
