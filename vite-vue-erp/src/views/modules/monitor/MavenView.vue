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

        <a-input-search v-model:value="where.search" placeholder="Search"/>
      </a-space>
    </div>

    <a-row>
      <a-col :span="6">
        <a-directory-tree class="overflow-auto" style="max-height: 70vh"
                          multiple
                          v-model:selectedKeys="selectedKeys"
                          :fieldNames="d.fieldNames"
                          :tree-data="d?.raw?.children"
                          @select="handleSelect"
        ></a-directory-tree>
      </a-col>
      <a-col class="p-2" :span="18">
        <div class="fs-1 fw-bold text-center">{{ currentNode?.name }}</div>
        <a-list item-layout="horizontal">
          <a-list-item v-for="(v, k) in currentNode">
            <a-list-item-meta :description="v">
              <template #title>
                {{ k }}
              </template>
            </a-list-item-meta>
          </a-list-item>
        </a-list>
      </a-col>

      <a-col :span="24">
        <div class="bg-success bg-opacity-25 text-white fw-bold p-1">
          {{ currentNode?.name === undefined ? "当前未选中节点" : currentNode?.name }}
        </div>
      </a-col>
    </a-row>
  </main>
</template>

<script lang="ts" setup>
// vue
import {onMounted, reactive, ref, watch} from "vue";

// antd
import {message} from "ant-design-vue";
import {CarryOutOutlined, SmileTwoTone} from "@ant-design/icons-vue"

// api
import {monitorApi} from "@/api/Other";
import {strUtil} from "@/utils/util";


const where = reactive({
  selectList: [],
  selectCurrent: "",
  selectHandleChange: (serviceName)=>{
    let sn = strUtil.removePrefix(serviceName, "rainfall-")
    loadData(sn === "gateway" ? null : sn)
    where.selectCurrent = serviceName
    message.success("加载成功")
  },

  search: ""
})

const selectedKeys = ref<string[]>([])

const currentNode = reactive({})
const d = reactive({
  raw: {},

  fieldNames: {children: 'children', title: 'name', key: 'key'},
})

const onSelect = (selectedKeys, info) => {
  console.log('selected', selectedKeys, info);
};

const handleSelect = function (selectedKeys, e) {
  // const selectedValue = e.node.value;
  // console.log(e, ": ", selectedValue);
  currentNode.name = e.node.name
  currentNode.value = e.node.value
  currentNode.key = e.node.key
  currentNode.eventKey = e.node.eventKey
  // currentNode.children = e.node.children
}

const loadData = (module = null) => {
  monitorApi.maven(module).then(({data}) => {
    d.raw = data.tree
  })
}

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
  loadData()
})
</script>

<style scoped>


</style>
