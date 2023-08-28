<template>
  <div class="d-flex shadow-sm p-3" :style="storeSettings.getThemeStylesReverse">
    <!-- 展开 -->
    <a-button class="me-auto" type="text">
      <template #icon>
        <div :style="{'color': storeSettings.getThemeStylesReverse.color}" @click="storeSettings.menuCollapsed = !storeSettings.menuCollapsed">
          <menu-unfold-outlined v-if="storeSettings.menuCollapsed" />
          <menu-fold-outlined v-else />
        </div>
      </template>
    </a-button>

    <template v-for="(item, index) in funList" :key="index">
      <a-tooltip>
        <template #title>{{ item.tip }}</template>
        <a-button @click="item.onClick()" type="text">
          <template #icon>
            <component :is="iconUtil.iconMap.get(item.icon)" :style="{'color': storeSettings.getThemeStylesReverse.color}"></component>
          </template>
        </a-button>
      </a-tooltip>
    </template>

    <!-- 用户 -->
    <a-popover class="ms-3" placement="topLeft">
      <template #content>
        <div>
          <div class="mb-1">个人中心</div>
          <div class="mb-1">布局设置</div>
          <hr class="m-0 mb-1">
          <div class="mb-1" @click="logout">退出登录</div>
        </div>
      </template>
      <a-avatar shape="square" :size="32" :src="resource.getAssetsFile('vue.svg')">
        <template #icon>
          <user-outlined />
        </template>
      </a-avatar>
    </a-popover>

  </div>
</template>

<script lang="ts" setup>
// vue
import {onMounted, reactive, ref, watch} from "vue";

// antd
import {message} from "ant-design-vue";
import {
  MenuFoldOutlined, MenuUnfoldOutlined,
  UserOutlined, SoundOutlined, BellOutlined, ExpandOutlined, GithubOutlined
} from '@ant-design/icons-vue';

// util
import {resource, iconUtil} from "@/utils/projectUtil";
import {styleUtil, network} from "@/utils/util";

// store
import settingsStore from "@/plugins/pinia/settingsStore";
const storeSettings = settingsStore()

const funList = ref([
  {
    icon: "expand-outlined",
    onClick: () => {
      message.success("正在对当前页面进行截图")
      // 将指定 DOM 元素转成 Canvas
      html2canvas(document.querySelector("#app")).then(canvas => {
        // 将 Canvas 转换为数据 URL
        const dataURL = canvas.toDataURL("image/png");
        // 创建一个链接元素,指向数据 URL
        const link = document.createElement("a");
        link.href = dataURL;
        // 设置链接元素的下载属性,指定文件名为 my-canvas.png
        link.download = "my-canvas.png";
        // 将链接元素添加到页面中
        document.body.appendChild(link);
        // 模拟用户单击链接元素,下载文件
        link.click();
        // 将链接元素从页面中删除
        document.body.removeChild(link);
      });
    },
    tip: "截图"
  },
  { icon: "bell-outlined", onClick: () => message.success("当前并无消息"), tip: "消息" },
  {
    icon: "github-outlined",
    onClick: () => {
      network.openInNewTab("https://github.com/qs2042/RainFall")
    },
    tip: "GitHub"
  },
  { icon: "sound-outlined", onClick: () => message.success("暂未完成背景音乐"), tip: "背景音乐" },
])

const logout = () => {
  localStorage.removeItem("user")
  location.reload();
}

</script>

<style scoped>

</style>
