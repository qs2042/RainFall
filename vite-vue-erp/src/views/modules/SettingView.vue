<template>
  <main>
    <a-form :label-col="{ span: 4 }" :wrapper-col="{span: 20}">
      <!-- 组件是否开启 -->
      <a-form-item label="开启菜单栏">
        <a-switch v-model:checked="storeSettings.menu"/>
      </a-form-item>
      <a-form-item label="开启导航栏">
        <a-switch v-model:checked="storeSettings.headers"/>
      </a-form-item>
      <a-form-item label="开启面包屑">
        <a-switch v-model:checked="storeSettings.breadcrumb"/>
      </a-form-item>
      <a-form-item label="开启标签栏">
        <a-switch v-model:checked="storeSettings.tags"/>
      </a-form-item>
      <a-form-item label="开启Logo">
        <a-switch v-model:checked="storeSettings.logo"/>
      </a-form-item>
      <a-form-item label="开启欢迎界面">
        <a-switch v-model:checked="storeSettings.welcomeScreen"/>
      </a-form-item>

      <!-- SEO -->
      <a-form-item label="网站图标">
        <a-input v-model:value="storeSettings.icon"/>
      </a-form-item>
      <a-form-item label="标题栏前缀">
        <a-input v-model:value="storeSettings.title"/>
      </a-form-item>
      <a-form-item label="网站描述">
        <a-textarea v-model:value="storeSettings.introduce"/>
      </a-form-item>
      <a-form-item label="版权信息">
        <a-textarea v-model:value="storeSettings.copyrightInformation"/>
      </a-form-item>
      <a-form-item label="关键词">
        <a-select
            v-model:value="value"
            mode="tags"
            style="width: 100%"
            placeholder="Tags Mode"
            :options="options"
            @change="handleChange"
        ></a-select>
      </a-form-item>
      <a-form-item label="LOGO标题/图标">
        <a-space>
          <a-input v-model:value="storeSettings.logoTitle"/>
          <icon-input v-model:value="storeSettings.logoIcon"/>
        </a-space>
      </a-form-item>

      <!-- 主题 -->
      <a-form-item label="更改主题">
        <a-switch checked-children="light"
                  un-checked-children="dark"
                  @change="state.changeTheme"
                  v-model:checked="storeSettings.theme"/>
      </a-form-item>
      <a-form-item label="字体样式">
        <a-radio-group v-model:value="storeSettings.font">
          <template v-for="(item, index) in storeSettings.fontList" :key="index">
            <a-radio :value="item.value">{{ item.name }}</a-radio>
          </template>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="字体大小">
        <a-input-number v-model:value="storeSettings.fontSize" addon-after="px"/>
      </a-form-item>

      <!-- TODO: 从这里开始, 下面都是未完成 -->
      <a-form-item label="背景图片/颜色">
        <a-space>
          <!--
           v-model:file-list="fileList"
            :headers="headers"
            @change="handleChange"
           -->
          <a-upload
              name="file"
              action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
          >
            <a-button>
              <upload-outlined></upload-outlined>
              点击上传
            </a-button>
          </a-upload>
          <a-input style="min-width: 4em" v-model:value="storeSettings.bgColor" type="color"></a-input>
        </a-space>
      </a-form-item>
      <a-form-item label="背景音乐">
        <a-space>
          <!--
           TODO:
           v-model:file-list="fileList"
            :headers="headers"
            @change="handleChange"
           -->
          <a-upload
              name="file"
              action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
          >
            <a-button>
              <upload-outlined></upload-outlined>
              点击上传
            </a-button>
          </a-upload>
          <!--
              @focus="focus"
              @change="handleChange"
           -->
          <a-select
              ref="select"
              v-model:value="storeSettings.bgMusic"
              style="min-width: 8em"
          >
            <template v-for="(item, index) in storeSettings.bgMusicList" :key="index">
              <a-select-option :value="item.id">{{ item.name }}</a-select-option>
            </template>
          </a-select>
        </a-space>
      </a-form-item>

      <a-form-item label="国际化">
        <a-select
            ref="select"
            v-model:value="storeSettings.language"
        >
          <template v-for="(item, index) in storeSettings.languageList" :key="index">
            <a-select-option :value="item.value">{{ item.name }}</a-select-option>
          </template>
        </a-select>
      </a-form-item>

      <a-form-item label="组件颜色混搭/透明度">
        <a-space>
          <a-checkbox-group v-model:value="formState.type">
            <a-checkbox value="1" name="type">white</a-checkbox>
            <a-checkbox value="2" name="type">black</a-checkbox>
            <a-checkbox value="3" name="type">green</a-checkbox>
          </a-checkbox-group>
          <a-slider id="test" v-model:value="storeSettings.componentAlpha"/>
        </a-space>
      </a-form-item>

      <a-form-item label="组件大小">
        <a-radio-group v-model:value="storeSettings.componentSizeButton">
          <template v-for="(item, index) in storeSettings.componentSizeList" :key="index">
            <a-radio-button :value="item.value">{{ item.label }}</a-radio-button>
          </template>
        </a-radio-group>
      </a-form-item>

      <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-space>
          <a-button>保存(持久化)</a-button>
          <a-button>重置</a-button>
          <a-button>初始化</a-button>
        </a-space>
      </a-form-item>
    </a-form>

  </main>
</template>

<script lang="ts" setup>
import {reactive, UnwrapRef, toRaw, ref} from "vue";
import {UploadOutlined} from "@ant-design/icons-vue";

// store
import settingsStore from "@/plugins/pinia/settingsStore";
import IconInput from "@/components/IconInput.vue";

const storeSettings = settingsStore()

const state = reactive({
  // 改变样式
  changeTheme: (checked: boolean) => {
    storeSettings.theme = checked;
  }
})

const formState = reactive({
  type: [],
});

const handleChange = (value: string) => {
  console.log(`selected ${value}`);
};
const value = ref([
  "java", "后台管理系统", "ERP", "OA", "CRM"
]);
const options = [...Array(25)].map((_, i) => ({value: (i + 10).toString(36) + (i + 1)}));


</script>

<style scoped>
#test {
  width: 200px;
}
</style>
