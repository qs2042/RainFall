<template>
  <main>
    <div class="mb-2">
      <a-space>
        <!-- input -->
        <a-select v-model:value="where.selectCurrent" @change="where.selectHandleChange" style="min-width: 120px">
          <!-- @focus="focus" @change="handleChange" -->
          <template v-for="(item, index) in where.selectList" :key="index">
            <a-select-option :value="item">{{ item }}</a-select-option>
          </template>
        </a-select>

        <!-- button -->
        <template v-for="(item, index) in where.buttonList" :key="index">
          <a-button @click="item.onClick">
            <template #icon>
              <component :is="iconUtil.getIcon(item.icon)" />
            </template>
            {{ item.title }}
          </a-button>
        </template>

        <a-select v-model:value="where.pageCurrent" @change="where.pageHandleChange" style="min-width: 120px">
          <!-- @focus="focus"  -->
          <template v-for="(item, index) in where.pageList" :key="index">
            <a-select-option :value="item.title">{{ item.title }}</a-select-option>
          </template>
        </a-select>
      </a-space>
    </div>

    <iframe-plus v-model:url="url" />
  </main>
</template>

<script lang="ts" setup>
// vue
import {onMounted, reactive, ref} from "vue";

// antd
import {message} from "ant-design-vue";

// api
import {apiUtil, iconUtil} from "@/utils/projectUtil";
import {monitorApi} from "@/api/Other";

// view
import IframePlus from "@/components/IframePlus.vue";

const url = ref("")

// nginx代理网关
// const url = apiUtil.getApiUrl() + "/system/druid/login.html"
// const url = "http://localhost/proxy/test/api/monitor/druid"

const where = reactive({
  selectList: [],
  selectCurrent: "",
  selectHandleChange: (name)=>{
    let p = where.pageList.find(v => v.title === where.pageCurrent)
    p.onClick()
  },

  pageList: [
    {
      title: "druid", icon: "appstore-outlined", onClick: () => {
        url.value = apiUtil.getApiUrl() + `/${where.selectCurrent.split('-')[1]}/druid/login.html`
      }
    },
    {
      title: "kibana", icon: "appstore-outlined", onClick: () => {
        url.value = "http://192.168.126.128:5601/app/kibana"
      }
    },
    {
      title: "minIO", icon: "appstore-outlined", onClick: () => {
        url.value = "http://192.168.126.128:9090/"
      }
    },
    {
      title: "nacos", icon: "appstore-outlined", onClick: () => {
        url.value = "http://localhost:8848/nacos/#/serviceManagement"
      }
    },
  ],
  pageCurrent: 'druid',
  pageHandleChange: (title)=>{
    let p = where.pageList.find(v => v.title === title)
    p.onClick()
  },

  buttonList: [
    // {title: "搜索", icon: "search-outlined", onClick: null},
    // {title: "重置", icon: "appstore-outlined", onClick: null},
  ]
})

onMounted(() => {
  monitorApi.getServiceList().then(({data}) => {
    let list = data.data.list
    if (list.length === 0) {
      message.error("您似乎还没开启服务")
      return;
    }
    where.selectList = list
    where.selectCurrent = list[0]
    // url.value = apiUtil.getApiUrl() + `/${where.selectCurrent.split('-')[1]}/druid/login.html`
    //
  })
})


</script>

<style scoped>

</style>
