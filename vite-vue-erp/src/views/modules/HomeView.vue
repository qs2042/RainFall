<template>
  <a-row :gutter="16">
    <!-- 日期, 走马灯 -->
    <a-col class="mb-4" :span="24">
      <div class="bg-primary bg-opacity-25 text-white shadow-sm fw-bold mb-2 ps-2 pe-2 d-flex">
        <div class="me-auto" @click="notice.updateEpitaph">
          {{ notice.epitaph }}
        </div>
        <div>
          {{ notice.localeTimeString }}
        </div>
      </div>

      <a-carousel :after-change="notice.onCarouselChange" effect="fade" autoplay>
        <template v-for="(item, index) in notice.carouseList" :key="index">
          <a-image class="object-fit-cover" height="200px" width="100%" :src="item.src"></a-image>
        </template>
      </a-carousel>
    </a-col>

    <!-- 项目介绍, 联系方式 -->
    <a-col class="mb-4" :span="12">
      <div class="fw-bold fs-3">{{ info.title }}</div>
      <!-- 内容 -->
      <div class="mb-2">
        <template v-for="(item, index) in info.text.split('\n')" :key="index">
          <div class="text-truncate">{{ item }}</div>
        </template>
      </div>

      <!-- 标签 -->
      <div class="mb-2">
        <template v-for="(item, index) in info.tags" :key="index">
          <a-tag :color="item.color">{{ item.name }}</a-tag>
        </template>
      </div>

      <!-- 按钮 -->
      <a-space>
        <template v-for="(item, index) in info.buttons" :key="index">
          <a-button @click="item.fun">{{ item.name }}</a-button>
        </template>
      </a-space>
    </a-col>
    <a-col class="mb-4" :span="12">
      <div class="fw-bold fs-3">联系方式</div>
      <a-descriptions bordered>
        <a-descriptions-item :span="3" label="官网">
          <template v-for="(item, index) in contact.website">
            <a class="text-success" :href="item">{{ item }}</a> <br>
          </template>
        </a-descriptions-item>
        <a-descriptions-item :span="3" label="QQ群">
          <template v-for="(item, index) in contact.qqGroup">
            <a-tag :color="index+3 < contact.qqGroup.length ? 'error' : 'success'">{{ item }}</a-tag>
          </template>
        </a-descriptions-item>
        <a-descriptions-item label="微信">{{ contact.wx }}</a-descriptions-item>
        <a-descriptions-item label="支付宝">{{ contact.zfb }}</a-descriptions-item>
      </a-descriptions>
    </a-col>

    <!-- github信息, 更新日志 -->
    <a-col class="mb-4" :span="12">
      <div class="fw-bold fs-3">Github信息</div>
      <a-row :gutter="16">
        <!-- 名称, 介绍 -->
        <a-col class="mb-2" :span="12">
          <a-card>
            <a-statistic title="名称" :value="github.raw.name"></a-statistic>
          </a-card>
        </a-col>
        <a-col class="mb-2" :span="12">
          <a-card>
            <a-statistic title="介绍" :value="github.raw.description">
            </a-statistic>
          </a-card>
        </a-col>

        <!-- star, 订阅 -->
        <a-col class="mb-2" :span="12">
          <a-card>
            <a-statistic title="star" :value="github.raw.stargazers_count">
              <template #suffix>
                <like-outlined/>
              </template>
            </a-statistic>
          </a-card>
        </a-col>
        <a-col class="mb-2" :span="12">
          <a-card>
            <a-statistic title="当前订阅人数/预期订阅人数" :value="github.raw.subscribers_count" class="demo-class">
              <template #suffix>
                <span>/ 100</span>
              </template>
            </a-statistic>
          </a-card>
        </a-col>

        <!-- fork, 语言 -->
        <a-col class="mb-2" :span="12">
          <a-card>
            <a-statistic title="fork次数" :value="github.raw.forks_count"></a-statistic>
          </a-card>
        </a-col>
        <a-col class="mb-2" :span="12">
          <a-card>
            <a-statistic title="使用语言" :value="github.raw.language"></a-statistic>
          </a-card>
        </a-col>

        <!-- other -->
        <a-col class="mb-2" :span="12">
          <a-card>
            <a-statistic title="项目使用人数提升率" suffix="%"
                         :value="11.28" :precision="2" :value-style="{ color: '#3f8600' }">
              <template #prefix>
                <arrow-up-outlined/>
              </template>
            </a-statistic>
          </a-card>
        </a-col>
        <a-col class="mb-2" :span="12">
          <a-card>
            <a-statistic title="项目提交Pr提升率" suffix="%"
                         :value="9.3" :precision="2"
                         :value-style="{ color: '#cf1322' }"
            >
              <template #prefix>
                <arrow-down-outlined/>
              </template>
            </a-statistic>
          </a-card>
        </a-col>
      </a-row>
    </a-col>
    <a-col class="mb-4" :span="12">
      <div class="fw-bold fs-3">更新日志</div>
      <a-radio-group class="mb-3" v-model:value="updateLogs.mode">
        <template v-for="item in updateLogs.modeList">
          <a-radio-button :value="item.value">{{ item.label }}</a-radio-button>
        </template>
      </a-radio-group>
      <a-timeline>
        <a-timeline-item>Create a services site 2015-09-01</a-timeline-item>
        <a-timeline-item>Solve initial network problems 2015-09-01</a-timeline-item>
        <a-timeline-item>Technical testing 2015-09-01</a-timeline-item>
        <a-timeline-item>Network problems being solved 2015-09-01</a-timeline-item>
      </a-timeline>
    </a-col>

    <!-- 技术栈 -->
    <a-col class="mb-4" :span="24">
      <div class="fw-bold fs-3">项目技术栈</div>
      <a-table row-key="id" :dataSource="table.dataSource" :columns="table.columns"/>
    </a-col>
  </a-row>
</template>

<script lang="ts" setup>
// vue
import {reactive, onMounted, onUnmounted} from "vue";

// antd
import {
  LikeOutlined, LeftCircleOutlined,
  RightCircleOutlined,
  ArrowUpOutlined, ArrowDownOutlined
} from '@ant-design/icons-vue';

// util&&api
import {resource} from "@/utils/projectUtil";
import {random, date} from "@/utils/util";
import {monitorApi} from "@/api/Other";

// store
import commonStore from "@/plugins/pinia/commonStore";
const store = commonStore()

// 公告
const notice = reactive({
  // 时间
  localeTimeString: "",
  timerId: -1 as number,
  // tips
  epitaph: random.getEpitaph(),
  updateEpitaph: () => {
    notice.epitaph = random.getEpitaph()
  },
  // Carouse
  carouseList: [
    { src: resource.getAssetsFile('bg/24648275_p0.jpg')},
    { src: resource.getAssetsFile('bg/91349043_p0.jpg')},
    { src: resource.getAssetsFile('bg/99138484_p0.jpg')},
  ],
  onCarouselChange:(current: number) => {
    console.log(current);
  }
})

// 信息
const info = reactive({
  title: "ERP后台管理框架",
  text: `
  虽然市面上有很多优秀的开源项目可以提供快速的二次开发
  但还是想自己尝试一下,做出一款后台管理系统, 不过在这之前一直碍于不懂行业内规范和数据
  拿CRM举例说, 我手上并没有真实的企业数据, 不知道该如何处理客户信息、订单、合同等数据
  也就更不用说客户管理、销售管理、市场营销等功能模块了
  因此作者开始利用空闲休息时间,通过爬虫获取企业公布的数据,恶补其相关的知识
  如此有了当前这个项目,它可以用于所有的Web应用程序.
  如ERP, CMS, CRM, HRM, BI, SCM, EAM, WMS, OA等后台管理系统.
  当然您也可以对它进行深度定制,以做出更强系统
  `,
  buttons: [
    {
      name: '访问GitHub', fun: () => {
      }
    },
    {
      name: '访问GitEE', fun: () => {
      }
    },
    {
      name: '访问BBS', fun: () => {
      }
    }
  ],
  tags: [
    {color: "success", name: "免费开源"},
    {color: "success", name: "快速开发"},
  ]
})

// 联系方式
const contact = reactive({
  title: "联系方式",
  website: [
    "http://106.52.46.241/bbs/#/", "http://qs2042.github.io"
  ],
  qqGroup: [
    "114514", "123456789", "1919", "11111", "222222", "987654321", "2042"
  ],
  wx: "wx_2042136767",
  zfb: "zfb_2042136767"
})

// 更新日志
const updateLogs = reactive({
  mode: "brief",
  modeList: [
    { label:"简略", value:"brief", onClick: () => null },
    { label:"详细", value:"detailed", onClick: () => null },
    { label:"内容", value:"content", onClick: () => null },
  ]
})

// 技术栈表
const table = reactive({
  dataSource: [
    // java预留ID: 1xx
    {id: '1', type: 'java', framework: 'java', version: "1.8(311)"},
    {
      id: '2', type: 'java', framework: 'maven', version: "3.6.3", children: [
        {id: '7', type: 'java', framework: 'mybatis-plus', version: "3.5.2"},
        {id: '8', type: 'java', framework: 'mysql', version: "8.0.29"},
        {id: '9', type: 'java', framework: 'minio', version: "8.4.6"},
        {id: '10', type: 'java', framework: 'elasticsearch-java', version: "7.17.8"},
        {id: '10', type: 'java', framework: 'redis', version: "7.0.5"},
        {id: '10', type: 'java', framework: 'kibana', version: "7.4.2"},
        {id: '10', type: 'java', framework: 'freemarker', version: "2.3.31"},
      ]
    },
    {
      id: '3', type: 'java', framework: 'springCloud', version: "2021.0.5", children: [
        {id: '3', type: 'java', framework: 'gateway', version: "3.6.3"},
        {id: '3', type: 'java', framework: 'openfeign', version: "{parent}"},
        {id: '3', type: 'java', framework: 'loadbalancer', version: "{parent}"},
      ]
    },
    {
      id: '4', type: 'java', framework: 'springCloudAlibaba', version: "2021.1", children: [
        {id: '4', type: 'java', framework: 'nacos-config', version: "{parent}"},
        {id: '4', type: 'java', framework: 'nacos-discovery', version: "{parent}"},
      ]
    },
    {
      id: '5', type: 'java', framework: 'springBoot', version: "2.7.7", children: [
        {id: '5', type: 'java', framework: 'validation', version: "{parent}"},
        {id: '5', type: 'java', framework: 'web', version: "{parent}"},
        {id: '5', type: 'java', framework: 'bootstrap', version: "{parent}"},
        {id: '5', type: 'java', framework: 'test', version: "{parent}"},
        {id: '5', type: 'java', framework: 'spring', version: "{parent}"},
        {id: '5', type: 'java', framework: 'jpa', version: "{parent}"},
        {id: '5', type: 'java', framework: 'jedis', version: "{parent}"},
        {id: '5', type: 'java', framework: 'quartz', version: "{parent}"},
        {id: '5', type: 'java', framework: 'websocket', version: "{parent}"},
        {id: '5', type: 'java', framework: 'thymeleaf', version: "{parent}"},
        {id: '5', type: 'java', framework: 'elasticsearch-rest-high-level-client', version: "{parent}"},
        {id: '5', type: 'java', framework: 'druid', version: "{parent}"},
      ]
    },
    {
      id: '6', type: 'java', framework: '其他依赖', version: "3.5.2", children: [
        {id: '6', type: 'java', framework: 'lombok', version: "1.18.24"},
        {id: '6', type: 'java', framework: 'commons-lang', version: "2.6"},
        {id: '6', type: 'java', framework: 'httpcore', version: "4.4.13"},
        {id: '6', type: 'java', framework: 'gson', version: "2.9.0"},
        {id: '6', type: 'java', framework: 'fastjson', version: "1.2.83"},
      ]
    },


    // web预留ID: 2xx
    {id: '200', type: 'web', framework: 'npm', version: "6.14.8"},
    {id: '201', type: 'web', framework: 'node', version: "14.15.0"},
    {id: '202', type: 'web', framework: 'vite', version: "4.3.9"},
    {
      id: '203', type: 'web', framework: 'vue', version: "3.2.47", children: [
        {id: '203', type: 'web', framework: 'ant-design-vue', version: "3.2.20"},
        {id: '203', type: 'web', framework: 'pinia', version: "2.1.4"},
      ]
    },
    {id: '204', type: 'web', framework: 'ts', version: "5.0.2"},
    {id: '205', type: 'web', framework: 'qs', version: "6.11.2"},
    {id: '206', type: 'web', framework: 'axios', version: "1.4.0"},
    {id: '207', type: 'web', framework: 'bootstrap', version: "5.2.3"},
    {id: '208', type: 'web', framework: 'nginx', version: "1.23.0"},

    // Other预留ID: 3xx
    {id: '300', type: 'other', framework: 'windows', version: "7"},
    {id: '301', type: 'other', framework: 'linux', version: "centos 8"},
    {id: '302', type: 'other', framework: 'docker', version: "20.10.21"},
    {id: '302', type: 'other', framework: 'VMWare', version: "15 Pro(15.5.7)"},

  ],

  columns: [
    /*{
      title: '类型',
      dataIndex: 'type',
      key: 'id',
    },*/
    {
      title: '技术',
      dataIndex: 'framework',
      key: 'id',
    },
    {
      title: '版本',
      dataIndex: 'version',
      key: 'id',
    },
  ],
})

// github
const github = reactive({
  raw: {}
})

onMounted(() => {
  // 每秒钟更新一次时间
  notice.timerId = setInterval(() => {
    notice.localeTimeString = date.getCurrentTimeString()
  }, 1000);

  // 加载数据
  monitorApi.repo().then(({data}) => {
    github.raw = data.data
  })
})

onUnmounted(() => {
  // 组件卸载时清除定时器
  if (notice.timerId !== -1) {
    clearInterval(notice.timerId);
    notice.timerId = -1;
  }
});

</script>
