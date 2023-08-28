<template>

</template>

<script lang="ts" setup>
// antd
import {message} from "ant-design-vue";

// store
import commonStore from "@/plugins/pinia/commonStore";
import settingsStore from "@/plugins/pinia/settingsStore";
const store = commonStore()
const storeSettings = settingsStore()

// util
import {localStorageUtil} from "@/utils/util";

const MESSAGE_TYPES = store.WS_MESSAGE_TYPES

let ws

const user = localStorageUtil.getItem("user", "object")
if (user !== undefined) {
  ws = new WebSocket(`ws://127.0.0.1:88/api/system/onLine/${user.account}/${user.id}?a=1&a=2&b=1`);
  ws.onopen = () => {
    store.setWs(ws)
  }
  ws.onerror = (event) => {
    console.log("ws出现错误")
  }
  ws.onclose = (event) => {
    store.setWs(undefined)
    console.log("ws已关闭 " + JSON.stringify(event))
  }
  ws.onmessage = function(event) {
    // 解析json
    let data = JSON.parse(event.data);

    // 根据消息类型处理数据
    switch (data.type) {
        // 广播消息
      case MESSAGE_TYPES.BROADCAST_MESSAGE:
        store.onlineCount = data.data.onlineCount;
        store.peakOnlineCount = data.data.peakOnlineCount;
        console.log("在线人数: " + store.onlineCount);
        console.log("在线人数(巅峰): " + store.peakOnlineCount);
        break;
        // 群聊消息
      case MESSAGE_TYPES.GROUP_CHAT:
        break;
        // 私聊
      case MESSAGE_TYPES.PRIVATE_CHAT:
        message.success("私聊: " + data.msg)
        break;
        // 错误消息
      case MESSAGE_TYPES.ERROR_MESSAGE:
        message.error("ws报错: " + data.msg)
        break;
    }
  }
} else {
  store.setWs(undefined)
}



</script>

<style scoped>

</style>
