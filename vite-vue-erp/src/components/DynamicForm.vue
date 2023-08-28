<template>
  <main>
    数据: {{ data }}
    <hr>
    {{ data['nick'] }}

  </main>
</template>

<script lang="ts" setup>
// vue
import {defineEmits, defineProps} from "vue";

const emits = defineEmits(['update']);

const props = defineProps({
  data: { type: Object, default: () => {} },
  labelName: { type: String, default: 'label' },
  keyName: { type: String, default: 'key' },
  valueName: { type: String, default: 'value' },
})




</script>

<style scoped>

</style>
