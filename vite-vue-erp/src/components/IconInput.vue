<template>
  <a-popover title="Title" trigger="click">
    <template #content>
      <a-row justify="space-around" style="width: 204px" :gutter="20">
        <template v-for="(v, k) in iconUtil.iconJson">
          <a-col>
            <component @click="updateValue(k)" :is="v"></component>
          </a-col>
        </template>
      </a-row>
    </template>
    <a-input type="text" :value="value" placeholder="点击选择图标" @input="updateValue($event.target.value)" allow-clear></a-input>
  </a-popover>
</template>

<script lang="ts" setup>
// vue
import {ref, defineProps, reactive, defineExpose, toRefs, defineEmits} from "vue";

// api
import {iconUtil} from "@/utils/projectUtil";

const prop = defineProps({
  value: { type: String }
})

const { value } = toRefs(prop)

const emits = defineEmits(['update:value']);

const updateValue = (valueNew) => {
  emits('update:value', valueNew);
};

defineExpose({
  value,
  updateValue
});

</script>

<style scoped>

</style>
