<template>
  <error-view code="500" v-if="!d.isLoad" />
  <main v-else>
    <a-table :dataSource="table.dataSource" :columns="table.columns">
      <template #bodyCell="{ column, text, record }">
        <template v-if="column.dataIndex === 'operation'">
          <a-space class="mb-2">
            <a-button size="small" @click="table.onGetValue(record)">获取缓存值</a-button>
            <a-button size="small" type="danger" @click="table.onDelete(record)">删除</a-button>
          </a-space>
        </template>
      </template>
    </a-table>
  </main>
</template>

<script lang="ts" setup>
// vue
import {onMounted, reactive} from "vue";

// api
import {monitorApi} from "@/api/Other";

// view
import ErrorView from "@/views/meta/ErrorView.vue";

// 数据
const d = reactive({
  isLoad: false,
  raw: {},
})

// 表格
const table = reactive({
  dataSource: [],
  columns: [
    {
      title: '缓存名称',
      dataIndex: '',
      key: 'name',
    },
    {
      title: '缓存值',
      dataIndex: 'value',
      key: 'age',
    },
    {
      title: 'operation',
      dataIndex: 'operation',
    },
  ],
  onDelete: (item) => {
    console.log("正在删除: ", item)
  },
  onGetValue: (item) => {
    console.log("正在获取: ", item)
  }
})

onMounted(() => {
  monitorApi.cacheList().then(({data}) => {
    d.isLoad = data.code === 200

    d.raw = data.data
    table.dataSource = d.raw?.Keys
  })
})



</script>

<style scoped>

</style>
