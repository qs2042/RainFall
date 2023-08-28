<template>
  <error-view code="500" v-if="!d.isLoad" />
  <main v-else>
    <a-space class="mb-2">
      <a-button size="small" type="primary">刷新</a-button>
      <a-button size="small" type="primary">刷新(无视缓存)</a-button>
    </a-space>
    <a-spin :spinning="!d.isLoad" tip="Loading...">
      <a-row :gutter="16" justify="space-around" style="min-height: 20vh">
        <template v-for="(v, k, i) in d?.raw" :key="i">
          <a-col :sm="24" :md="12" :lg="12">
            <a-card class="shadow-sm" size="small" :title="k">
              <template #extra><a href="#">详细</a></template>
              <template v-for="(v2, k2, i2) in v" :key="i2">
                <div>{{ k2 }} <span class="float-end">{{ v2 }}</span></div>
              </template>
            </a-card>
          </a-col>
        </template>

      </a-row>
    </a-spin>
  </main>
</template>

<script lang="ts" setup>
// vue
import {reactive} from "vue";

// api
import {monitorApi} from "@/api/Other";

// view
import ErrorView from "@/views/meta/ErrorView.vue";

const d = reactive({
  isLoad: false,
  raw: {}
})

monitorApi.docker().then(({data}) => {
  d.isLoad = data.code === 200
  d.raw = data.data
})


</script>

<style scoped>
/* 固定一下高度 */
.ant-card {
  min-height: 300px !important;
  max-height: 300px !important;
  overflow-y: auto;
  overflow-x: hidden;
  margin-bottom: 10px;
}

.ant-card-body-p-0 > .ant-card-body {
  padding: 0 !important;
}
</style>
