<template>
  <main>
    <a-row class="mb-2" :gutter="16">
      <a-col><a-input size="small" style="min-width: 20em" v-model:value="url" @input="updateValue($event.target.value)"></a-input></a-col>
      <a-col><a-button type="primary" size="small" @click="network.openInNewTab(url)">跳转至目标网站</a-button></a-col>
      <a-col><a-button size="small">刷新</a-button></a-col>
      <a-col><a-button size="small">爬取重要信息</a-button></a-col>
      <a-col><a-button size="small">爬取音频/视频</a-button></a-col>
      <a-col><a-button size="small">爬取XHR数据</a-button></a-col>
    </a-row>

    <template v-if="url.length === 0">
      <error-view class="border border-1" style="min-height: 90vh" msg="1" />
    </template>
    <template v-else>
      <iframe frameborder="no" scrolling="auto" class="w-100 border border-1" style="min-height: 90vh" :src="url"></iframe>
    </template>
  </main>
</template>

<script lang="ts" setup>
import {defineEmits, defineExpose, defineProps, toRef, toRefs} from "vue";
import {network} from "@/utils/util";
import ErrorView from "@/views/meta/ErrorView.vue";

const props = defineProps({
  url: { type: String, default: ()=> "" }
})
const { url } = toRefs(props)

const emits = defineEmits(['update:url']);

const updateValue = (valueNew) => {
  emits('update:url', valueNew);
};

defineExpose({
  url,
  updateValue
});

</script>

<style scoped>

</style>
