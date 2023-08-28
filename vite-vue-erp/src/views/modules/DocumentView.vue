<template>
  <main class="position-relative">
    <a-row gutter="16">
      <a-col :span="4">
        <div class="p-2 bg-dark bg-opacity-10 shadow-sm rounded-2">
          <template v-for="(item, index) in list">
            <a class="d-block fs-5"
               :class="`${ index === listIndex ? 'text-success fw-bold' : 'text-dark'}`"
               @click="listIndex=index">
              {{index}}.{{ item.title }}
            </a>
          </template>
        </div>
      </a-col>
      <a-col class="p-2 bg-success bg-opacity-10 shadow-sm rounded-2" :span="20">
        <div class="overflow-auto" style="min-height: 80vh; max-height: 80vh">
          <div class="fs-1 fw-bold text-center">{{ list[listIndex].title }}</div>
          <div v-html="strUtil.convertNewlinesToBr(list[listIndex].content.trim())"></div>
        </div>
      </a-col>
    </a-row>

<!--    <div class="position-absolute p-2 ps-3 pe-3 shadow-sm bg-success bg-opacity-25 rounded-2">
      <template v-for="(item, index) in list">
        <a class="d-block" :href="`#${index}_${item.title}`">{{index}}.{{ item.title }}</a>
      </template>
    </div>
    <div class="text-center">
      <template v-for="(item, index) in list">
        <div class="fs-1 fw-bold" :id="`${index}_${item.title}`">{{ item.title }}</div>
        <div v-html="strUtil.convertNewlinesToBr(item.content.trim())"></div>
      </template>
    </div>-->
  </main>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import {strUtil} from "@/utils/util";

const content = ref([])

const listIndex = ref(0)
let list = [
  {
    title: "介绍", content: `
    能看到这个页面,说明你已经弄完基础的环境,并且成功将程序跑起来了
    接下来就可以开始准备二次开发了, 该文档会重点讲述流程
    \n\n\n\n\n\n\n
    第一章完结
  `
  }, {
    title: "基本的增删改查", content: `
    0.创建自己的模块
    0.增加菜单
    0.生成增删改查
    0.修改网关
    0.测试功能
    \n\n\n\n\n\n\n\n\n\n\n\n\n\n
    ...
    9
  `
  },{
    title: "自定义页面内容", content: `
    1
    \n\n\n\n\n\n\n\n\n\n\n\n\n\n
    ...
    9
  `
  },{
    title: "接入在线人数模块", content: `
    1
    \n\n\n\n\n\n\n\n\n\n\n\n\n\n
    ...
    9
  `
  },{
    title: "接入定时任务模块", content: `
    1
    \n\n\n\n\n\n\n\n\n\n\n\n\n\n
    ...
    9
  `
  },
]

</script>

<style scoped>

</style>
