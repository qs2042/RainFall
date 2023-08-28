<template>
  <main>
    <!--  -->
    <div class="mb-2">
      <a-space>
        <a-select size="small" v-model:value="where.selectCurrent"  @change="where.selectHandleChange" style="min-width: 120px">
          <!-- @focus="focus" -->
          <template v-for="(item, index) in where.selectList" :key="index">
            <a-select-option :value="item">{{ item }}</a-select-option>
          </template>
        </a-select>
        <a-button size="small" @click="loadAll(true)" type="primary">刷新</a-button>
      </a-space>
    </div>

    <!--  -->
    <a-row :gutter="16" justify="space-around">
      <!-- CPU -->
      <a-col :sm="24" :md="12" :lg="8">
        <a-card class="shadow-sm" size="small" title="CPU">
          <template #extra><a href="#">详细</a></template>
          <div>逻辑核心 <span class="float-end">{{ d.cpu?.LogicalCore }}</span></div>
          <div>物理核心 <span class="float-end">{{ d.cpu?.PhysicalCore }}</span></div>
          <div>详细信息 <span class="float-end text-truncate w-50">{{ d.cpu?.Model }}</span></div>
          <div>线程数量 <span class="float-end">{{ d.cpu?.ThreadCount }}</span></div>

          <div>守护线程 <span class="float-end">{{ d.cpu?.DaemonThreadsCount }}</span></div>
          <div>使用率 <span class="float-end">{{ d.cpu?.Usage }}</span></div>
          <div>空闲率 <span class="float-end">{{ d.cpu?.IdleRatio }}</span></div>
          <div>地址位宽 <span class="float-end">{{ d.cpu?.AddressWidth }}</span></div>
          <div>数据位宽 <span class="float-end">{{ d.cpu?.DataWidth }}</span></div>
          <div>当前电压 <span class="float-end">{{ d.cpu?.CurrentVoltage }}</span></div>
          <div>名称 <span class="float-end">{{ d.cpu?.Name }}</span></div>
          <div>制造商 <span class="float-end">{{ d.cpu?.Manufacturer }}</span></div>
          <div>核心数量 <span class="float-end">{{ d.cpu?.NumberOfCores }}</span></div>
          <div>逻辑处理器数量 <span class="float-end">{{ d.cpu?.NumberOfLogicalProcessors }}</span></div>
          <div>负载状态 <span class="float-end">{{ d.cpu?.LoadPercentage }}%</span></div>
          <div>当前时钟速度 <span class="float-end">{{ d.cpu?.CurrentClockSpeed }}</span></div>
          <div>最大时钟速度 <span class="float-end">{{ d.cpu?.MaxClockSpeed }}</span></div>
          <div>插座名称 <span class="float-end">{{ d.cpu?.SocketDesignation }}</span></div>
          <div>状态 <span class="float-end">{{ d.cpu?.Status }}</span></div>
          <div>是否支持电源管理 <span class="float-end">{{ d.cpu?.PowerManagementSupported }}</span></div>
          <div>唯一标识符 <span class="float-end">{{ d.cpu?.ProcessorId }}</span></div>
          <div>详细描述 <span class="float-end">{{ d.cpu?.Description }}</span></div>
          <div>设备ID <span class="float-end">{{ d.cpu?.DeviceId }}</span></div>
          <div>家族名称 <span class="float-end">{{ d.cpu?.Family }}</span></div>
          <div>体系结构 <span class="float-end">{{ d.cpu?.Architecture }}</span></div>
          <div>可用性 <span class="float-end">{{ d.cpu?.Availability }}</span></div>
          <div>简短描述 <span class="float-end">{{ d.cpu?.Caption }}</span></div>
          <div>CPU实例名称 <span class="float-end">{{ d.cpu?.CreationClassName }}</span></div>
          <div>L2缓存大小 <span class="float-end">{{ d.cpu?.L2CacheSize }}</span></div>
          <div>缓存级别 <span class="float-end">{{ d.cpu?.Level }}</span></div>
          <div>状态信息 <span class="float-end">{{ d.cpu?.StatusInfo }}</span></div>
          <div>步进号 <span class="float-end">{{ d.cpu?.Stepping }}</span></div>
          <div>系统实例名称 <span class="float-end">{{ d.cpu?.SystemCreationClassName }}</span></div>
          <div>系统名称 <span class="float-end">{{ d.cpu?.SystemName }}</span></div>
          <div>升级方法 <span class="float-end">{{ d.cpu?.UpgradeMethod }}</span></div>
          <div>版本 <span class="float-end">{{ d.cpu?.Version }}</span></div>
          <div>角色 <span class="float-end">{{ d.cpu?.Role }}</span></div>
          <div>类型 <span class="float-end">{{ d.cpu?.ProcessorType }}</span></div>
          <div>修订号 <span class="float-end">{{ d.cpu?.Revision }}</span></div>
        </a-card>
      </a-col>
      <!-- 内存 -->
      <a-col :sm="24" :md="12" :lg="8">
        <a-card class="shadow-sm" size="small" title="内存">
          <template #extra><a href="#">详细</a></template>
          <div>总共内存 <span class="float-end">{{ d.physicalMemory?.TotalSize }} MB</span></div>
          <div>剩余内存 <span class="float-end">{{ d.physicalMemory?.FreeSize }} MB</span></div>
          <div>已用内存 <span class="float-end">{{ d.physicalMemory?.UsedSize }} MB</span></div>
          <div>使用率 <span class="float-end">{{ d.physicalMemory?.Usage }}</span></div>
          <div>空闲率 <span class="float-end">{{ d.physicalMemory?.IdleRatio }}</span></div>
        </a-card>
      </a-col>
      <!-- 磁盘 -->
      <a-col :sm="24" :md="12" :lg="8">
        <a-card class="shadow-sm ant-card-body-p-0" size="small" title="磁盘">
          <template #extra><a href="#">详细</a></template>
          <a-table :pagination="d.hddPagination" size="small" :data-source="d.hdd" :columns='d.hddColumns' />
        </a-card>
      </a-col>

      <!-- Java/JVM -->
      <a-col :sm="24" :md="12" :lg="8">
        <a-card class="shadow-sm" size="small" title="Java/JVM">
          <template #extra><a href="#">详细</a></template>
          <div>供应商 <span class="float-end">{{ d?.jvm?.JavaVendor }}</span></div>
          <div>版本 <span class="float-end">{{ d?.jvm?.JavaVersion }}</span></div>
          <div>安装路径 <span class="float-end">{{ d?.jvm?.JavaPath }}</span></div>
          <div>总共内存 <span class="float-end">{{ d?.jvm?.jvmTotalMemory }} MB</span></div>
          <div>已用内存 <span class="float-end">{{ d?.jvm?.jvmUsedMemory }} MB</span></div>
          <div>剩余内存 <span class="float-end">{{ d?.jvm?.jvmFreeMemory }} MB</span></div>
          <div>使用率 <span class="float-end">{{ d?.jvm?.jvmMemoryUsage }}</span></div>
          <div>空闲率 <span class="float-end">{{ d?.jvm?.jvmMemoryIdleRatio }}</span></div>
          <template v-for="(v, k, i) in d.jvm?.jvmAllGarbageCollectorInfo" :key="i">
            <div>{{ k }} <span class="float-end">已清理{{ v.count }}个垃圾, 耗时{{ v.Time }}</span></div>
          </template>
        </a-card>
      </a-col>
      <!-- 系统 -->
      <a-col :sm="24" :md="12" :lg="8">
        <a-card class="shadow-sm" size="small" title="系统">
          <template #extra><a href="#">详细</a></template>
          <div>环境变量(Path) <span class="float-end text-truncate w-50">{{ d?.raw?.SystemPath }}</span></div>
          <div>系统信息 <span class="float-end">{{ d?.raw?.SystemOs }}</span></div>
          <div>系统名称 <span class="float-end">{{ d?.raw?.SystemOsName }}</span></div>
          <div>系统版本 <span class="float-end">{{ d?.raw?.SystemOsVersion }}</span></div>
          <div>系统架构 <span class="float-end">{{ d?.raw?.SystemOsArch }}</span></div>
          <div>用户名称 <span class="float-end">{{ d?.raw?.SystemUser }}</span></div>
          <div>用户国家 <span class="float-end">{{ d?.raw?.SystemUserCountry }}</span></div>
          <div>设备名称 <span class="float-end">{{ d?.raw?.ComputerName }}</span></div>
          <div>ComputerUptime <span class="float-end">{{ d?.raw?.ComputerUptime }}</span></div>
          <div>ComputerStartTime <span class="float-end">{{ d?.raw?.ComputerStartTime }}</span></div>
          <div>ComputerComputerPowerUsage <span class="float-end">{{ d?.raw?.ComputerComputerPowerUsage }}</span></div>
          <div>ComputerBiosVersion <span class="float-end">{{ d?.raw?.ComputerBiosVersion }}</span></div>
          <div>ComputerMeVersion <span class="float-end">{{ d?.raw?.ComputerMeVersion }}</span></div>
        </a-card>
      </a-col>
      <!-- 项目 -->
      <a-col :sm="24" :md="12" :lg="8">
        <a-card class="shadow-sm" size="small" title="项目">
          <template #extra><a href="#">详细</a></template>
          <div>启动时间 <span class="float-end">{{ d?.project?.ProjectStartTime }}</span></div>
          <div>运行时长 <span class="float-end">{{ d?.project?.ProjectUptime }}</span></div>
          <div>项目路径 <span class="float-end text-truncate w-50"><a-tooltip :title="d?.project?.ProjectPath">{{ d?.project?.ProjectPath }}</a-tooltip></span></div>
          <div>运行参数 <span class="float-end">{{ d?.project?.ProjectArguments }}</span></div>
          <div>进程ID <span class="float-end">{{ d?.project?.Pid }}</span></div>
        </a-card>
      </a-col>
      <!-- 网络 -->
      <a-col :sm="24" :md="12" :lg="8">
        <a-card class="shadow-sm" size="small" title="网络">
          <template #extra><a href="#">详细</a></template>
          <div>下载速度 <span class="float-end">{{ d?.network?.DownloadSpeed }}</span></div>
          <div>上传速度 <span class="float-end">{{ d?.network?.UploadSpeed }}</span></div>
          <div>延迟 <span class="float-end">{{ d?.network?.Latency }}</span></div>
          <div>网卡 <span class="float-end"> {{ d?.network?.Interfaces }} </span></div>
          <div>内网IP <span class="float-end">{{ d?.network?.LocalHost }}</span></div>
        </a-card>
      </a-col>
    </a-row>
  </main>
</template>

<script lang="ts" setup>
// vue
import {reactive, onMounted} from "vue";

// antd
import {message} from 'ant-design-vue';

// api
import {monitorApi} from "@/api/Other";
import {strUtil} from "@/utils/util";

const where = reactive({
  selectCurrent: null as string,
  selectList: [],
  selectHandleChange: (serviceName) => {
    let sn = strUtil.removePrefix(serviceName, "rainfall-")
    loadAll(false, sn === "gateway" ? null : sn)
    where.selectCurrent = serviceName
    message.success("加载成功")
  }
})

let loadAll = (isCache=false, module=null, ) => {
  if (isCache) {
    message.success("刷新成功")
  }

  monitorApi.getServiceList(module).then(({data}) => {
    let list = data.data.list
    if (list.length === 0) {
      message.error("您似乎还没开启服务")
      return;
    }
    where.selectList = list
    // 如果已经有了选择, 那就不指定
    if (where.selectCurrent == null) {
      where.selectCurrent = list[0]
    } else {
      // 如果选择的服务已下线
      let isExist = list.includes(where.selectCurrent);
      if (!isExist) {
        where.selectCurrent = list[0]
      }
    }

  })

  monitorApi.all(module).then(({data}) => {
    d.raw = data.data
  })

  monitorApi.cpu(module).then(({data}) => {
    d.cpu = data.data.map
  })

  monitorApi.physicalMemory(module).then(({data}) => {
    d.physicalMemory = data.data.map
  })

  monitorApi.hdd(module).then(({data}) => {
    d.hdd = data.data.list
  })

  monitorApi.network(module).then(({data}) => {
    d.network = data.data.map
  })

  monitorApi.project(module).then(({data}) => {
    d.project = data.data
  })

  monitorApi.jvm(module).then(({data}) => {
    d.jvm = data.data
  })
}

// 数据
const d = reactive({
  raw: {},

  cpu: {},
  physicalMemory: {},
  network: {},
  project: {},
  jvm: {},

  hdd: [],
  hddPagination: {
    hideOnSinglePage: true
  },
  hddColumns: [
    {title: "盘符", dataIndex: "key", key: "key"},
    {title: "总内存", dataIndex: "totalSpace", key: "key"},
    {title: "已使用", dataIndex: "usedSpace", key: "key"},
    {title: "未使用", dataIndex: "usableSpace", key: "key"},
  ]
})

onMounted(() => {
  // 加载数据
  loadAll()
})

</script>

<style>
/* 固定一下高度 */
.ant-card {
  min-height: 300px !important;
  max-height: 300px !important;
  overflow-y: auto;
  overflow-x: hidden;
  margin-bottom: 10px;
}


</style>
