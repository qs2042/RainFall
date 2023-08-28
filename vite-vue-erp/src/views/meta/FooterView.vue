<template>
  <main>
    <a-back-top :target="footer.test" :visibilityHeight="20">
      <div class="ant-back-top-inner">UP</div>
    </a-back-top>

    <div class="p-2 text-center" :style="storeSettings.getThemeStyles">
      {{ storeSettings.copyrightInformation }}
    </div>
  </main>
</template>

<script lang="ts" setup>
// vue
import {reactive} from "vue";

// store
import settingsStore from "@/plugins/pinia/settingsStore";
const storeSettings = settingsStore()

const footer = reactive({
  test: () => document.getElementById("index")
})
</script>

<style scoped>
.ant-back-top {
  bottom: 100px;
}

.ant-back-top-inner {
  height: 40px;
  width: 40px;
  line-height: 40px;
  border-radius: 4px;
  background-color: #1088e9;
  color: #fff;
  text-align: center;
  font-size: 20px;
}
</style>
