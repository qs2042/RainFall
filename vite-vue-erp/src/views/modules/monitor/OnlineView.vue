<template>
  <main class="pt-2 pb-2">

    <a-row :gutter="16">
      <a-col :span="12" class="mb-3">
        <a-statistic class="p-3 text-center shadow-sm border border-1 border-opacity-10" title="当前用户" :value="store.onlineCount"/>
      </a-col>
      <a-col :span="12" class="mb-3">
        <a-statistic class="p-3 text-center shadow-sm border border-1 border-opacity-10" title="用户数量峰值" :precision="2" :value="store.peakOnlineCount"/>
      </a-col>
      <a-col :span="24" class="mb-3">
        <div class="mb-2 d-flex flex-wrap gap-2 justify-content-between">
          <card size="small" style="flex: 1" type="default" label="在线时长第一名" />
          <card size="small" style="flex: 1" type="dark" label="总在线时长第一名" />
          <card size="small" style="flex: 1" type="success" label="平均在线时长" />
          <card size="small" style="flex: 1" type="warning" label="平局总在线时长" />
        </div>
      </a-col>
      <a-col :span="24" class="mb-3">
        <a-table class="shadow-sm" size="middle" :dataSource="table.dataSource" :columns="table.columns" bordered/>
      </a-col>
    </a-row>
  </main>
</template>

<script lang="ts" setup>
// vue
import {reactive} from "vue";

// store
import commonStore from "@/plugins/pinia/commonStore";
const store = commonStore()

import {wsApi} from "@/api/Other";
import Card from "@/components/Card.vue";

// TODO: 表格
const table = reactive({
  dataSource: [
    /*{
      id: '1',
      username: 'Mike',
      age: 32,
      onlineTime: '2023年6月14日20:03:21',
      onlineDuration: "50分钟"
    },
    {
      id: '2',
      username: 'John',
      age: 42,
      onlineTime: '2023年6月14日20:03:21',
      onlineDuration: "50分钟"
    },*/
  ],

  columns: [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
    },{
      title: '账户',
      dataIndex: 'account',
      key: 'account',
    },{
      title: '登录时间',
      dataIndex: 'createdAt',
      key: 'createdAt',
    },{
      title: '在线时长',
      dataIndex: 'onlineDuration',
      key: 'onlineDuration',
    },
  ]
})

let ws = store.ws.bean
// ws.send("111")
// ws.send(JSON.stringify({ type: 102, msg: "11111111111111", data: {} }))
// ws.send(JSON.stringify({ type: store.WS_MESSAGE_TYPES.GET_USER_ID_LIST, msg: "2222", data: {} }))

wsApi.list().then(({data}) => {
  console.log(data)
  table.dataSource = data.data.list
})

</script>

<style scoped>

</style>
