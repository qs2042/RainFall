<template>
  <main>
    <div class="mb-2 d-flex flex-wrap gap-2 justify-content-between">
      <card style="flex: 1" type="default" label="今日新增用户" />
      <card style="flex: 1" type="success" label="今日分享统计" />
      <card style="flex: 1" type="warning" label="今日访问时长" />
      <card style="flex: 1" type="primary" label="今日访问次数" />
    </div>

    <div class="mb-2 d-flex flex-wrap gap-2 justify-content-between">
      <card size="small" style="flex: 1" type="default" />
      <card size="small" style="flex: 1" type="dark" />
      <card size="small" style="flex: 1" type="success" />
      <card size="small" style="flex: 1" type="warning" />
      <card size="small" style="flex: 1" type="primary" />
      <card size="small" style="flex: 1" type="danger" />
    </div>

    <a-row :gutter="8">
      <a-col class="mb-2" :span="12">
        <a-card class="shadow-sm border border-1 ant-card-body-p-0" size="small" title="访客来源">
          <template #extra><a href="#">详细</a></template>
          <div ref="trafficSources" class="w-100" style="min-height: 380px"></div>
        </a-card>
      </a-col>
      <a-col class="mb-2" :span="12">
          <a-card class="shadow-sm border border-1 ant-card-body-p-0" size="small" title="独立访客">
            <template #extra><a href="#">详细</a></template>
            <div ref="uniqueVisitors" class="w-100" style="min-height: 380px"></div>
          </a-card>
      </a-col>

      <a-col class="mb-2" :span="12">
        <a-card class="shadow-sm border border-1 ant-card-body-p-0" size="small" title="页面浏览量">
          <template #extra><a href="#">详细</a></template>
          <div ref="pageViews" class="w-100" style="min-height: 380px"></div>
        </a-card>
      </a-col>
      <a-col class="mb-2" :span="12">
        <a-card class="shadow-sm border border-1 ant-card-body-p-0" size="small" title="页面停留时间">
          <template #extra><a href="#">详细</a></template>
          <div ref="timeOnPage" class="w-100" style="min-height: 380px"></div>
        </a-card>
      </a-col>

      <a-col class="mb-2" :span="12">
        <a-card class="shadow-sm border border-1 ant-card-body-p-0" size="small" title="用户转换率">
          <template #extra><a href="#">详细</a></template>
          <div ref="userConversionRate" class="w-100" style="min-height: 380px"></div>
        </a-card>
      </a-col>
      <a-col class="mb-2" :span="12">
        <a-card class="shadow-sm border border-1 ant-card-body-p-0" size="small" title="设备分类统计">
          <template #extra><a href="#">详细</a></template>
          <div ref="equipmentClassification" class="w-100" style="min-height: 380px"></div>
        </a-card>
      </a-col>

      <a-col class="mb-2" :span="12">
        <a-card class="shadow-sm border border-1 ant-card-body-p-0" size="small" title="状态码统计">
          <template #extra><a href="#">详细</a></template>
          <div ref="status" class="w-100" style="min-height: 380px"></div>
        </a-card>
      </a-col>

    </a-row>
  </main>
</template>

<script lang="ts" setup>
// TODO: 真实数据
// vue
import {ref, reactive, onMounted} from "vue";

// antd
import {RightCircleOutlined} from "@ant-design/icons-vue";
import {iconUtil} from "@/utils/projectUtil";

// store
import settingsStore from "@/plugins/pinia/settingsStore";
const storeSettings = settingsStore()

// echarts
import * as echarts from "echarts";
import Card from "@/components/Card.vue";


onMounted(() => {
  pageViewsData.init()
  timeOnPageData.init()

  trafficSourcesData.init()
  uniqueVisitorsData.init()

  userConversionRateData.init()
  equipmentClassificationData.init()
  // otherData.init()
  statusData.init()
})


// 访客来源
const trafficSources = ref()
const trafficSourcesData = reactive({
  option: {
    title: {
      text: '访客来源',
      subtext: '数据时间: 2023年6月28日15:01:18',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: [
          {value: 1048, name: '搜索引擎'},
          {value: 735, name: '邮箱'},
          {value: 580, name: '哔哩哔哩'},
          {value: 484, name: 'YouTube'},
          {value: 300, name: 'GitHub'}
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  },
  init: () => {
    const myChart = echarts.init(trafficSources.value, storeSettings.getTheme);
    myChart.setOption(trafficSourcesData.option);
  }
})

// 独立访客
const uniqueVisitors = ref()
const uniqueVisitorsData = reactive({
  option: {
    title: {
      text: '独立访客',
      left: 'center'
    },
    tooltip: {},
    legend: {
      data: ['浏览量']
    },
    xAxis: {
      data: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期天']
    },
    yAxis: {},
    series: [
      {
        name: '访客人数',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20, 60]
      }
    ]
  },
  init: () => {
    const myChart = echarts.init(uniqueVisitors.value, storeSettings.getTheme);
    myChart.setOption(uniqueVisitorsData.option);
  }
})

// 页面浏览量(pageViews)
const pageViews = ref()
const pageViewsData = reactive({
  option: {
    title: {
      text: '页面浏览量',
      left: "center"
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      top: "8%"
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'value',
      boundaryGap: [0, 0.01]
    },
    yAxis: {
      type: 'category',
      data: ['首页', '后台', '文章模块', '游戏模块', 'ERP模块', 'OA模块']
    },
    series: [
      {
        name: '昨天',
        type: 'bar',
        data: [100, 200, 300, 400, 500, 600]
      },
      {
        name: '今天',
        type: 'bar',
        data: [600, 500, 400, 300, 200, 100]
      },
      {
        name: '总共',
        type: 'bar',
        data: [700, 700, 700, 700, 700, 700]
      }
    ]
  },
  init: () => {
    const myChart = echarts.init(pageViews.value, storeSettings.getTheme);
    myChart.setOption(pageViewsData.option);
  }
})

// 页面停留时间
const timeOnPage = ref()
const timeOnPageData = reactive({
  option: {
    title: {
      text: '页面停留时间',
      left: "center"
    },
    legend: {
      show: true,
      top: "8%"
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    tooltip: {},
    dataset: {
      source: [
        ['product', '首页', '后台', '文章模块', '游戏模块', 'ERP模块', 'OA模块'],
        ['昨天', 18203, 23489, 29034, 104970, 131744, 630230],
        ['今天', 19325, 23438, 31000, 121594, 134141, 681807],
        ['总共', 37528, 46927, 60034, 226564, 265885, 1312037],
      ]
    },
    xAxis: {type: 'category'},
    yAxis: {},
    // Declare several bar series, each will be mapped
    // to a column of dataset.source by default.
    series: [{type: 'bar'}, {type: 'bar'}, {type: 'bar'}, {type: 'bar'}, {type: 'bar'}, {type: 'bar'}]
  },
  init: () => {
    const myChart = echarts.init(timeOnPage.value, storeSettings.getTheme);
    myChart.setOption(timeOnPageData.option);
  }
})

// 用户转换率
const userConversionRate = ref()
const userConversionRateData = reactive({
  option: {
    title: {
      text: '用户转换率',
      subtext: '数据时间: 2023年6月28日15:01:18',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: [
          // bounceRate
          {value: 70, name: '跳出率'},
          {value: 20, name: '转化率(常驻用户)'},
          {value: 10, name: '转化率(资深用户)'},
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  },
  init: () => {
    const myChart = echarts.init(userConversionRate.value, storeSettings.getTheme);
    myChart.setOption(userConversionRateData.option);
  }
})

// 设备分类统计
const equipmentClassification = ref()
const equipmentClassificationData = reactive({
  option: {
    title: {
      text: '设备分类统计',
      subtext: '数据时间: 2023年6月28日15:01:18',
      left: 'center'
    },
    legend: {
      show: true,
      top: "16%"
    },
    tooltip: {},
    dataset: {
      source: [
        ['product', '2012', '2013', '2014', '2015', '2016', '2017'],
        ['windows', 55.1, 67.2, 79.5, 86.4, 65.2, 82.5],
        ['android', 86.5, 92.1, 85.7, 83.1, 73.4, 55.1],
        ['ios', 41.1, 30.4, 65.1, 53.3, 83.8, 98.7],
        ['linux', 10.2, 11.1, 12.2, 13.4, 14.9, 15.1],
        ['macOS', 55.2, 67.1, 69.2, 72.4, 53.9, 39.1]
      ]
    },
    series: [
      {
        type: 'pie',
        radius: '50%',
        encode: {
          itemName: 'product',
          value: '2014'
        }
      },
    ]
  },
  init: () => {
    const myChart = echarts.init(equipmentClassification.value, storeSettings.getTheme);
    myChart.setOption(equipmentClassificationData.option);
  }
})

// 状态码统计
const status = ref()
const statusData = reactive({
  option: {
    title: {
      text: '状态码统计',
      left: "center"
    },
    legend: {
      show: true,
      top: "8%"
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    tooltip: {},
    dataset: {
      source: [
        ['product', 'ok', 'warning', 'error'],
        ['昨天', 755, 300, 500],
        ['今天', 935, 150, 250],
        ['总共', 752, 75, 125],
      ]
    },
    xAxis: {type: 'category'},
    yAxis: {},
    series: [{type: 'bar'}, {type: 'bar'}, {type: 'bar'}]
  },
  init: () => {
    const myChart = echarts.init(status.value, storeSettings.getTheme);
    myChart.setOption(statusData.option);
  }
})

const other = ref()
const otherData = reactive({
  option: {
    title: {
      text: '用户转换率',
      subtext: '数据时间: 2023年6月28日15:01:18',
      left: 'center'
    },
    legend: {
      show: true,
      top: "16%"
    },
    tooltip: {},
    dataset: {
      source: [
        ['product', '2012', '2013', '2014', '2015', '2016', '2017'],
        ['Milk Tea', 86.5, 92.1, 85.7, 83.1, 73.4, 55.1],
        ['Matcha Latte', 41.1, 30.4, 65.1, 53.3, 83.8, 98.7],
        ['Cheese Cocoa', 24.1, 67.2, 79.5, 86.4, 65.2, 82.5],
        ['Walnut Brownie', 55.2, 67.1, 69.2, 72.4, 53.9, 39.1]
      ]
    },
    series: [
      {
        type: 'pie',
        radius: '50%',
        encode: {
          itemName: 'product',
          value: '2014'
        }
      },
    ]
  },
  init: () => {
    const myChart = echarts.init(other.value, storeSettings.getTheme);
    myChart.setOption(otherData.option);
  }
})

</script>

<style scoped>

</style>
