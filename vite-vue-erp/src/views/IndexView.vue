<template xmlns="http://www.w3.org/1999/html">
  <a-layout>
    <!-- left -->
    <a-layout-sider style="max-height: 100vh; overflow: hidden" :collapsed="storeSettings.menuCollapsed"
                    :theme="storeSettings.getTheme" v-if="storeSettings.menu">
      <!-- logo -->
      <div :style="{'color': storeSettings.getThemeStyles.color}">
        <!-- 展开状态(隐藏) -->
        <div v-if="!storeSettings.logo" style="height: 64px"></div>
        <!-- 展开状态(显示 + 折叠) -->
        <div class="d-flex" v-else-if="storeSettings.menuCollapsed" style="min-height: 64px; max-height: 64px">
          <component class="m-auto fs-2" :is="iconUtil.iconMap.get(storeSettings.logoIcon)"></component>
        </div>
        <!-- 展开状态(显示) -->
        <div v-else style="min-height: 64px; max-height: 64px" class="d-flex fw-bold">
          <a-space class="m-auto fs-5">
            <component class="d-block" :is="iconUtil.iconMap.get(storeSettings.logoIcon)"></component>
            <div class="text-truncate" style="max-width: 7em">{{ storeSettings.logoTitle }}</div>
          </a-space>
        </div>
      </div>

      <!-- 菜单 @select="menuState.onSelect" -->
      <a-menu :theme="storeSettings.getTheme" mode="inline"
              v-model:selectedKeys="menuState.selectedKeys"
              v-model:openKeys="menuState.openKeys"
              @openChange="menuState.onOpenChange"
              @click="menuState.onClick"
      >
        <template v-for="(item, index) in menuState.tree" :key="index">
          <!-- 如果没有子菜单 -->
          <a-menu-item v-if="item.children === null" :key="item.id">
            <template #icon>
              <component :is="iconUtil.iconMap.get(item.icon)"></component>
            </template>

            {{ item.title }}
          </a-menu-item>

          <!-- 如果有子菜单 -->
          <a-sub-menu v-else :key="item.id">
            <!-- icon -->
            <template #icon>
              <component :is="iconUtil.iconMap.get(item.icon)"></component>
            </template>

            <!-- title -->
            <template #title>{{ item.title }}</template>

            <a-menu-item v-for="(item2) in item.children" :key="item2.id">
              {{ item2.title }}
            </a-menu-item>

            <!-- TODO: 没有考虑二级菜单之后的事情 -->
            <!--            <a-sub-menu key="sub1-2" title="Submenu">
                          <a-menu-item key="5">Option 5</a-menu-item>
                          <a-menu-item key="6">Option 6</a-menu-item>
                        </a-sub-menu>-->
          </a-sub-menu>
        </template>
      </a-menu>
    </a-layout-sider>

    <!-- right -->
    <a-layout id="index" class="overflow-auto" style="max-height: 100vh">
      <!-- header -->
      <header-view v-if="storeSettings.headers"/>

      <!-- 标签栏 -->
      <div v-if="storeSettings.tags && component.showList.length >= 1"
           @contextmenu="rightMenu.showContextMenu" @click="rightMenu.closeRightMenu"
           class="bg-white bg-opacity-75 pt-2 ps-2 pe-2 position-relative">
        <template v-for="item in component?.showList" :key="item.key">
          <a-tag class="mb-2" closable
                 :color="menuState.selectedKeys[0] === item.key ? 'processing' : 'green'"
                 @click="component.click(item)"
                 @close="component.close(item)">
            {{ item.name }}
          </a-tag>
        </template>
        <div v-if="rightMenu.visible"
             class="position-absolute bg-white border border-1 shadow-sm p-2"
             style="width: 10em; z-index: 999"
             :style="rightMenu.styles">
          <template v-for="(item, index) in rightMenu.menu" :key="index">
            <div v-if="item !== undefined" class="d-flex" @click="item.onClick">
              <div class="me-2"><component class="ant-bug-img" :is="iconUtil.getRandomIcon()" /></div>
              <div>{{ item.label }}</div>
            </div>
            <div v-else>
              <hr class="mt-1 mb-1">
            </div>
          </template>
        </div>
      </div>

      <!-- center -->
      <a-layout-content class="p-2">
        <!-- 面包屑 -->
        <a-breadcrumb v-if="storeSettings.breadcrumb && component.showList.length >= 1" class="mb-2 bg-white p-2">
          <a-breadcrumb-item>首页</a-breadcrumb-item>
          <!-- 如果是key -->
          <a-breadcrumb-item v-if="component?.currentMenu?.parentId!==null"><a
              href="">{{ menuState.getMenuById(component?.currentMenu?.parentId)?.title }}</a></a-breadcrumb-item>
          <a-breadcrumb-item>{{ component?.currentMenu?.title }}</a-breadcrumb-item>
        </a-breadcrumb>

        <!-- 内容 -->
        <div class="bg-white p-2 mb-2 min-vh-100">
          <!-- 未加载 -->
          <template v-if="component?.showList?.length === 0">
            <home-view/>
          </template>

          <!-- 加载成功: 外链 -->
          <template v-else-if="component?.currentMenu?.isRedirect">
            <iframe-plus :url="component?.currentMenu.url"/>
          </template>

          <!-- 加载成功: 内链 -->
          <template v-else-if="currentComponent?.bean !== undefined">
            <keep-alive>
              <component :is="currentComponent?.bean"></component>
            </keep-alive>
          </template>

          <!-- 加载失败 -->
          <template v-else>
            <h1 class="text-center">加载错误</h1>
            msg: "{{ currentComponent?.name }}" 无法被加载
            <br>
            bean: {{ component?.currentMenu }}
            <br>
            错误原因: 找不到 {{ component?.currentMenu?.selfPath }} 文件
            <br>
          </template>
        </div>

        <!-- footer -->
        <footer-view />
      </a-layout-content>
    </a-layout>
    <test-a-view ref="childRef" />
  </a-layout>
</template>

<script lang="ts" setup>
// vue
import {
  reactive, defineAsyncComponent, shallowRef, ref, onMounted, shallowReactive,
  nextTick, watch, getCurrentInstance, resolveComponent,
} from 'vue';

// antd
import {message} from 'ant-design-vue';

// util
import {iconUtil} from "@/utils/projectUtil";

// api
import {menuApi, IMenu} from "@/api/System";

// view
import HeaderView from "./meta/HeaderView.vue";
import HomeView from "./modules/HomeView.vue";
import IframePlus from "@/components/IframePlus.vue";
import FooterView from "@/views/meta/FooterView.vue";

// store
import commonStore from "@/plugins/pinia/commonStore";
import settingsStore from "@/plugins/pinia/settingsStore";

const store = commonStore()
const storeSettings = settingsStore()

// 动态组件
interface IComponent {
  key: number
  name: string
  bean: any
}

// 组件
const internalInstance = getCurrentInstance();

const currentComponent = shallowRef<IComponent>() // 使用了setup语法糖就不能用组件的字符串名称作为参数了
const component = reactive({
  // 是否加载成功
  isLoad: true,
  // 当前组件
  currentMenu: null as IMenu,
  // 已加载的组件
  list: [] as Array<IComponent>,
  // 显示的组件
  showList: [] as Array<IComponent>,
  // 选择组件(菜单ID)
  chooseByMenu: (menu: IMenu) => {
    // 如果是第一次加载组件
    let bean = component.list.find(v => v.key === menu.id)
    if (bean === undefined) {
      // 路径
      let path = menu.parentId === null ? menu.selfPath : menu.path

      // 结构
      bean = {
        key: menu.id,
        name: menu.title,
        bean: internalInstance.appContext.components["./views/modules/" + path],
      }
    }

    // 如果是第一次加载标签
    let tag = component.showList.find(v => v.key === menu.id)
    if (tag === undefined) {
      component.showList.push(bean)
    }

    // 保存选中的组件
    component.currentMenu = menu

    // 最后变更当前组件
    currentComponent.value = bean
  },
  // 关闭标签
  close: async (item) => {
    // 删除item(1)
    let index = component.showList.findIndex(value => value.key === item.key)
    component.showList.splice(index, 1)

    // 删除item(2)
    // component.showList = component.showList.filter(i => i.key !== item.key);

    // 延迟更新 DOM
    await nextTick();
  },
  // 点击标签
  click: (item) => {
    let menu = menuState.getMenuById(item.key)
    if (menu === undefined) {
      message.error("找不到Menu")
      return
    }
    component.currentMenu = menu
    currentComponent.value = item
    menuState.selectedKeys = [item.key]
  },
})

// 菜单
const menuState = reactive({
  tree: undefined,

  // 当前选中了那些菜单项(Menu, key: list)
  selectedKeys: [],

  // 当前展开了那些副菜单项(SubMenu, key: list)
  openKeys: [],

  // 防止多个菜单同时打开
  rootSubmenuKeys: [],
  onOpenChange: (openKeys: string[]) => {
    // 如果打开的菜单少于2
    if (openKeys.length <= 1) {
      return
    }

    // 获取最新打开的菜单(即最后一个)
    const latestOpenKey = openKeys[openKeys.length - 1]

    // 如果这个菜单不在需要关闭的列表中
    if (!openKeys.includes(latestOpenKey)) {
      return;
    }

    if (menuState.rootSubmenuKeys.indexOf(latestOpenKey!) === -1) {
      menuState.openKeys = openKeys;
    } else {
      menuState.openKeys = latestOpenKey ? [latestOpenKey] : [];
    }
  },

  // 点击事件
  onClick: (item) => {
    let m = menuState.getMenuById(item?.key)
    component.chooseByMenu(<IMenu>m)
  },

  // 展开事件
  onSelect: (item) => {
    console.log("onSelect: ", item)
  },

  //
  getMenuById: (id: number, menuList: IMenu[] = undefined): IMenu | undefined => {
    if (menuList === undefined) {
      menuList = menuState.tree
    }

    for (const menu of menuList) {
      if (menu.id === id) {
        return menu;
      }
      if (menu.children) {
        const subMenu = menuState.getMenuById(id, menu.children);
        if (subMenu) {
          return subMenu;
        }
      }
    }
    return undefined;
  }

})

// TODO: 增加一个初始化功能, 如果获取到的菜单为0, 就改为获取逻辑删除true
onMounted(() => {
  menuApi.queryTree(false).then(({data}) => {
    // menus.value = data.data.tree
    menuState.tree = data.data.tree

    menuState.tree?.forEach(v => {
      // state.rootSubmenuKeys.push(v.id)
      menuState.rootSubmenuKeys.push(v.id)
    })
  })

  message.success("欢迎回到后台")
})

const rightMenu = reactive({
  visible: false,
  menu: [
    { label: '关闭左边标签', onClick: () => rightMenu.closeRightMenu() },
    { label: '关闭右边标签', onClick: () => rightMenu.closeRightMenu() },
    { label: '关闭所有标签', onClick: () => rightMenu.closeRightMenu() },
    undefined,
    { label: '返回', onClick: () => rightMenu.closeRightMenu() },
    { label: '前进', onClick: () => rightMenu.closeRightMenu() },
    { label: '刷新', onClick: () => rightMenu.closeRightMenu() }
  ],
  styles: {},
  closeRightMenu: () => rightMenu.visible = false,
  showContextMenu: (event) => {
    // 阻止默认的右键菜单弹出
    event.preventDefault();

    rightMenu.styles = {
      // top: `${event.clientX}px`,
      left: `${event.clientX - 200}px`,
    }

    rightMenu.visible = true
  }

})

</script>

<style scoped>

</style>


