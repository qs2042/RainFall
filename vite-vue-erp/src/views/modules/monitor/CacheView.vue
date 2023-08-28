<template>
  <error-view code="500" v-if="!d.isLoad"  />
  <main v-else>
    <a-space class="mb-2">
      <a-button size="small" type="primary">刷新</a-button>
      <a-button size="small" type="danger">刷新(无视缓存)</a-button>
      <a-button size="small"> 当前数量: {{ Object.keys(d.raw).length }}</a-button>
    </a-space>
    <a-row :gutter="16" justify="space-around">
      <!-- Persistence -->
      <a-col v-if="d.raw?.Persistence" :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="Persistence">
          <template #extra><a href="#">详细</a></template>
          <div>当前保存键总数 <span class="float-end">{{ d.raw?.Persistence?.current_save_keys_total }}</span></div>
          <div>当前 COW 大小 <span class="float-end">{{ d.raw?.Persistence?.current_cow_size }}</span></div>
          <div>异步加载中 <span class="float-end">{{ d.raw?.Persistence?.async_loading }}</span></div>
          <div>当前 RDB 后台存储时间(秒) <span class="float-end">{{ d.raw?.Persistence?.rdb_current_bgsave_time_sec }}</span>
          </div>
          <div>上次 AOF COW 大小 <span class="float-end">{{ d.raw?.Persistence?.aof_last_cow_size }}</span></div>
          <div>上次 RDB 加载过期键数量 <span class="float-end">{{ d.raw?.Persistence?.rdb_last_load_keys_expired }}</span></div>
          <div>当前正在处理的保存键数 <span class="float-end">{{ d.raw?.Persistence?.current_save_keys_processed }}</span></div>
          <div>AOF 重写次数 <span class="float-end">{{ d.raw?.Persistence?.aof_rewrites }}</span></div>
          <div>模块 fork 上次 COW 大小 <span class="float-end">{{ d.raw?.Persistence?.module_fork_last_cow_size }}</span>
          </div>
          <div>AOF 重写计划时间 <span class="float-end">{{ d.raw?.Persistence?.aof_rewrite_scheduled }}</span></div>
          <div>当前 fork 使用率 <span class="float-end">{{ d.raw?.Persistence?.current_fork_perc }}</span></div>
          <div>当前 COW 大小时效 <span class="float-end">{{ d.raw?.Persistence?.current_cow_size_age }}</span></div>
          <div>上次 RDB 存储以来更改数量 <span class="float-end">{{ d.raw?.Persistence?.rdb_changes_since_last_save }}</span>
          </div>
          <div>上次 AOF 后台重写状态 <span class="float-end">{{ d.raw?.Persistence?.aof_last_bgrewrite_status }}</span></div>
          <div>上次 AOF 重写时间(秒) <span class="float-end">{{ d.raw?.Persistence?.aof_last_rewrite_time_sec }}</span></div>
          <div>当前 AOF 重写时间(秒) <span class="float-end">{{ d.raw?.Persistence?.aof_current_rewrite_time_sec }}</span>
          </div>
          <div>AOF 是否启用 <span class="float-end">{{ d.raw?.Persistence?.aof_enabled }}</span></div>
          <div>AOF 重写是否正在进行中 <span class="float-end">{{ d.raw?.Persistence?.aof_rewrite_in_progress }}</span></div>
          <div>AOF 重写连续失败次数 <span class="float-end">{{ d.raw?.Persistence?.aof_rewrites_consecutive_failures }}</span>
          </div>
          <div>上次 RDB 存储时间 <span class="float-end">{{ d.raw?.Persistence?.rdb_last_save_time }}</span></div>
          <div>上次 RDB 后台存储时间(秒) <span class="float-end">{{ d.raw?.Persistence?.rdb_last_bgsave_time_sec }}</span></div>
          <div>上次 RDB 后台存储状态 <span class="float-end">{{ d.raw?.Persistence?.rdb_last_bgsave_status }}</span></div>
          <div>当前 COW 峰值 <span class="float-end">{{ d.raw?.Persistence?.current_cow_peak }}</span></div>
          <div>模块 fork 是否正在进行中 <span class="float-end">{{ d.raw?.Persistence?.module_fork_in_progress }}</span></div>
          <div>加载中 <span class="float-end">{{ d.raw?.Persistence?.loading }}</span></div>
          <div>上次 AOF 写入状态 <span class="float-end">{{ d.raw?.Persistence?.aof_last_write_status }}</span></div>
          <div>RDB 存储 0 <span class="float-end">{{ d.raw?.Persistence?.rdb_saves0 }}</span></div>
          <div>上次 RDB 加载键数量 <span class="float-end">{{ d.raw?.Persistence?.rdb_last_load_keys_loaded }}</span></div>
          <div>上次 RDB COW 大小 <span class="float-end">{{ d.raw?.Persistence?.rdb_last_cow_size }}</span></div>
          <div>RDB 后台存储是否正在进行中 <span class="float-end">{{ d.raw?.Persistence?.rdb_bgsave_in_progress }}</span></div>
        </a-card>
      </a-col>

      <!-- 服务 -->
      <a-col :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="服务">
          <template #extra><a href="#">详细</a></template>
          <div>tcp端口 <span class="float-end">{{ d.raw?.Server?.tcp_port }}</span></div>
          <div>进程ID <span class="float-end">{{ d.raw?.Server?.process_id }}</span></div>
          <div>运行ID <span class="float-end">{{ d.raw?.Server?.run_id }}</span></div>
          <div>redis模式 <span class="float-end">{{ d.raw?.Server?.redis_mode }}</span></div>
          <div>系统 <span class="float-end">{{ d.raw?.Server?.os }}</span></div>
          <div>运行时间 <span class="float-end">{{ d.raw?.Server?.uptime_in_seconds }}秒</span></div>
          <div>运行时间 <span class="float-end">{{ d.raw?.Server?.uptime_in_days }}天</span></div>
          <div>gcc版本 <span class="float-end">{{ d.raw?.Server?.gcc_version }}</span></div>
          <div>是否使用单调时钟 <span class="float-end">{{ d.raw?.Server?.monotonic_clock }}</span></div>
          <div>当前是否正在使用 I/O 线程 <span class="float-end">{{ d.raw?.Server?.io_threads_active }}</span></div>
          <div>当前时间戳 <span class="float-end">{{ d.raw?.Server?.server_time_usec }}</span></div>
          <div>可执行文件路径 <span class="float-end">{{ d.raw?.Server?.executable }}</span></div>
          <div>redis版本 <span class="float-end">{{ d.raw?.Server?.redis_version }}</span></div>
          <div>架构位宽 <span class="float-end">{{ d.raw?.Server?.arch_bits }}</span></div>
          <div>执行周期性任务的频率 <span class="float-end">{{ d.raw?.Server?.hz }}</span></div>
          <div>构建 ID <span class="float-end">{{ d.raw?.Server?.redis_build_id }}</span></div>
          <div>事件处理机制 <span class="float-end">{{ d.raw?.Server?.multiplexing_api }}</span></div>
          <div>配置文件中设置的执行周期性任务的频率 <span class="float-end">{{ d.raw?.Server?.configured_hz }}</span></div>
          <div>进程监控模式 <span class="float-end">{{ d.raw?.Server?.process_supervised }}</span></div>
          <div>配置路径 <span class="float-end">{{ d.raw?.Server?.config_file }}</span></div>
          <div>LRU 时钟值 <span class="float-end">{{ d.raw?.Server?.lru_clock }}</span></div>
          <!--          <div>Redis Git 仓库中的 SHA1 标识符 <span class="float-end">{{ d.raw?.Server?.redis_git_sha1 }}</span></div>-->
          <!--          <div>标识 Redis 服务器所使用的 Git 版本控制系统的代码库是否有未提交的更改 <span class="float-end">{{ d.raw?.Server?.redis_git_dirty }}</span></div>-->
          <!--          <div>atomicvar_api <span class="float-end">{{ d.raw?.Server?.atomicvar_api }}</span></div>-->
        </a-card>
      </a-col>

      <!-- 内存 -->
      <a-col :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="内存">
          <template #extra><a href="#">详细</a></template>
          <div>分配的内存总量 <span class="float-end">{{ d.raw?.Memory?.used_memory_human }}</span></div>
          <div>redis的内存消耗峰值 <span class="float-end">{{ d.raw?.Memory?.used_memory_peak_human }}</span></div>
          <div>向操作系统申请的内存大小 <span class="float-end">{{ d.raw?.Memory?.used_memory_rss }} byte</span></div>
          <div>向操作系统申请的内存大小 <span class="float-end">{{ d.raw?.Memory?.used_memory_rss_human }}</span></div>
          <div>已用内存 <span class="float-end">{{ d.raw?.Memory?.used_memory }}</span></div>
          <div>已用内存峰值(百分比) <span class="float-end">{{ d.raw?.Memory?.used_memory_peak_perc }}</span></div>
          <div>已用内存峰值 <span class="float-end">{{ d.raw?.Memory?.used_memory_peak }}</span></div>
        </a-card>
      </a-col>

      <!-- Modules -->
      <a-col :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="Modules">
          <template #extra><a href="#">详细</a></template>
          <!--          <div>db0 <span class="float-end">{{ d.raw?.Keyspace?.db0 }}</span></div>-->
        </a-card>
      </a-col>

      <!-- Errorstats -->
      <a-col :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="Errorstats">
          <template #extra><a href="#">详细</a></template>
          <!--          <div>db0 <span class="float-end">{{ d.raw?.Keyspace?.db0 }}</span></div>-->
        </a-card>
      </a-col>

      <!-- Keyspace -->
      <a-col :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="Keyspace">
          <template #extra><a href="#">详细</a></template>
          <div>db0 <span class="float-end">{{ d.raw?.Keyspace?.db0 }}</span></div>
        </a-card>
      </a-col>

      <!-- status -->
      <a-col :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="Status">
          <template #extra><a href="#">详细</a></template>
          <div>IO 线程读取处理 <span class="float-end">{{ d.raw?.Stats?.io_threaded_reads_processed }}</span></div>
          <div>总读取处理数 <span class="float-end">{{ d.raw?.Stats?.total_reads_processed }}</span></div>
          <div>发布/订阅分片通道数 <span class="float-end">{{ d.raw?.Stats?.pubsubshard_channels }}</span></div>
          <div>总网络输入字节数 <span class="float-end">{{ d.raw?.Stats?.total_net_input_bytes }}</span></div>
          <div>过期周期 CPU 毫秒数 <span class="float-end">{{ d.raw?.Stats?.expire_cycle_cpu_milliseconds }}</span></div>
          <div>同步部分错误数 <span class="float-end">{{ d.raw?.Stats?.sync_partial_err }}</span></div>
          <div>瞬时输入复制吞吐量 (kbps) <span class="float-end">{{ d.raw?.Stats?.instantaneous_input_repl_kbps }}</span></div>
          <div>被逐出的客户端数 <span class="float-end">{{ d.raw?.Stats?.evicted_clients }}</span></div>
          <div>总网络复制输出字节数 <span class="float-end">{{ d.raw?.Stats?.total_net_repl_output_bytes }}</span></div>
          <div>跟踪总前缀数 <span class="float-end">{{ d.raw?.Stats?.tracking_total_prefixes }}</span></div>
          <div>跟踪总键数 <span class="float-end">{{ d.raw?.Stats?.tracking_total_keys }}</span></div>

          <div>被逐出的键数 <span class="float-end">{{ d.raw?.Stats?.evicted_keys }}</span></div>
          <div>回复缓冲区收缩数 <span class="float-end">{{ d.raw?.Stats?.reply_buffer_shrinks }}</span></div>
          <div>IO 线程写入处理 <span class="float-end">{{ d.raw?.Stats?.io_threaded_writes_processed }}</span></div>
          <div>瞬时操作数每秒 <span class="float-end">{{ d.raw?.Stats?.instantaneous_ops_per_sec }}</span></div>
          <div>总错误回复数 <span class="float-end">{{ d.raw?.Stats?.total_error_replies }}</span></div>
          <div>当前活动碎片整理时间 <span class="float-end">{{ d.raw?.Stats?.current_active_defrag_time }}</span></div>
          <div>回复缓冲区扩展数 <span class="float-end">{{ d.raw?.Stats?.reply_buffer_expands }}</span></div>
          <div>全量同步数 <span class="float-end">{{ d.raw?.Stats?.sync_full }}</span></div>
          <div>总活动碎片整理时间 <span class="float-end">{{ d.raw?.Stats?.total_active_defrag_time }}</span></div>

          <div>瞬时输出复制吞吐量 (kbps) <span class="float-end">{{ d.raw?.Stats?.instantaneous_output_repl_kbps }}</span></div>
          <div>总写入处理数 <span class="float-end">{{ d.raw?.Stats?.total_writes_processed }}</span></div>
          <div>总网络输出字节数 <span class="float-end">{{ d.raw?.Stats?.total_net_output_bytes }}</span></div>
          <div>发布/订阅通道数 <span class="float-end">{{ d.raw?.Stats?.pubsub_channels }}</span></div>
          <div>到期时间限制已达上限的次数 <span class="float-end">{{ d.raw?.Stats?.expired_time_cap_reached_count }}</span></div>
          <div>意外错误回复数 <span class="float-end">{{ d.raw?.Stats?.unexpected_error_replies }}</span></div>
          <div>活动碎片整理命中键数 <span class="float-end">{{ d.raw?.Stats?.active_defrag_key_hits }}</span></div>
          <div>瞬时输入吞吐量 (kbps) <span class="float-end">{{ d.raw?.Stats?.instantaneous_input_kbps }}</span></div>
          <div>到期键百分比 <span class="float-end">{{ d.raw?.Stats?.expired_stale_perc }}</span></div>
          <div>活动碎片整理未命中键数 <span class="float-end">{{ d.raw?.Stats?.active_defrag_misses }}</span></div>

          <!--
sync_partial_ok
total_net_repl_input_bytes
pubsub_patterns
slave_expires_tracked_keys
latest_fork_usec
rejected_connections
current_eviction_exceeded_time
tracking_total_items
total_commands_processed
expired_keys
total_forks
dump_payload_sanitizations
keyspace_misses
active_defrag_key_misses
total_eviction_exceeded_time
instantaneous_output_kbps
keyspace_hits
total_connections_received
migrate_cached_sockets
active_defrag_hits
 -->
          <div>Sync partial OK <span class="float-end">{{ d.raw?.Stats?.sync_partial_ok }}</span></div>
          <div>Total net replication input bytes <span class="float-end">{{ d.raw?.Stats?.total_net_repl_input_bytes }}</span></div>
          <div>Pub/sub patterns <span class="float-end">{{ d.raw?.Stats?.pubsub_patterns }}</span></div>
          <div>Slave expires tracked keys <span class="float-end">{{ d.raw?.Stats?.slave_expires_tracked_keys }}</span></div>
          <div>Latest fork microseconds <span class="float-end">{{ d.raw?.Stats?.latest_fork_usec }}</span></div>
          <div>Rejected connections <span class="float-end">{{ d.raw?.Stats?.rejected_connections }}</span></div>
          <div>Current eviction time limit exceeded <span class="float-end">{{ d. raw?.Stats?.current_eviction_exceeded_time }}</span></div>
          <div>Tracking total items <span class="float-end">{{ d.raw?.Stats?.tracking_total_items }}</span></div>
          <div>Total commands processed <span class="float-end">{{ d.raw?.Stats?.total_commands_processed }}</span></div>
          <div>Expired keys <span class="float-end">{{ d.raw?.Stats?.expired_keys }}</span></div>
          <div>Total forks <span class="float-end">{{ d.raw?.Stats?.total_forks }}</span></div>
          <div>Dump payload sanitizations <span class="float-end">{{ d.raw?.Stats?.dump_payload_sanitizations }}</span></div>
          <div>Keyspace misses <span class="float-end">{{ d.raw?.Stats?.keyspace_misses }}</span></div>
          <div>Active defragmentation misses on keys <span class="float-end">{{ d.raw?.Stats?.active_defrag_key_misses }}</span></div>
          <div>Total eviction time limit exceeded <span class="float-end">{{ d.raw?.Stats?.total_eviction_exceeded_time }}</span></div>
          <div>Instantaneous output throughput (kbps) <span class="float-end">{{ d.raw?.Stats?.instantaneous_output_kbps }}</span></div>
          <div>Keyspace hits <span class="float-end">{{ d.raw?.Stats?.keyspace_hits }}</span></div>
          <div>Total connections received <span class="float-end">{{ d.raw?.Stats?.total_connections_received }}</span></div>
          <div>Migrate cached sockets <span class="float-end">{{ d.raw?.Stats?.migrate_cached_sockets }}</span></div>
          <div>Active defragmentation hits <span class="float-end">{{ d.raw?.Stats?.active_defrag_hits }}</span></div>
        </a-card>
      </a-col>

      <!-- Replication -->
      <a-col :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="Replication">
          <template #extra><a href="#">详细</a></template>
          <div>second_repl_offset <span class="float-end">{{ d.raw?.Replication?.second_repl_offset }}</span></div>
          <div>repl_backlog_first_byte_offset <span class="float-end">{{ d.raw?.Replication?.repl_backlog_first_byte_offset }}</span></div>
          <div>master_failover_state <span class="float-end">{{ d.raw?.Replication?.master_failover_state }}</span></div>
          <div>role <span class="float-end">{{ d.raw?.Replication?.role }}</span></div>
          <div>repl_backlog_active <span class="float-end">{{ d.raw?.Replication?.repl_backlog_active }}</span></div>
          <div>repl_backlog_size <span class="float-end">{{ d.raw?.Replication?.repl_backlog_size }}</span></div>
          <div>connected_slaves <span class="float-end">{{ d.raw?.Replication?.connected_slaves }}</span></div>
          <div>repl_backlog_histlen <span class="float-end">{{ d.raw?.Replication?.repl_backlog_histlen }}</span></div>
          <div>master_replid <span class="float-end">{{ d.raw?.Replication?.master_replid }}</span></div>
          <div>master_replid2 <span class="float-end">{{ d.raw?.Replication?.master_replid2 }}</span></div>
          <div>master_repl_offset <span class="float-end">{{ d.raw?.Replication?.master_repl_offset }}</span></div>
        </a-card>
      </a-col>

      <!-- CPU -->
      <a-col :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="CPU">
          <template #extra><a href="#">详细</a></template>
          <div>used_cpu_sys <span class="float-end">{{ d.raw?.CPU?.used_cpu_sys }}</span></div>
          <div>used_cpu_user <span class="float-end">{{ d.raw?.CPU?.used_cpu_user }}</span></div>
          <div>used_cpu_user_main_thread <span class="float-end">{{ d.raw?.CPU?.used_cpu_user_main_thread }}</span></div>
          <div>used_cpu_sys_children <span class="float-end">{{ d.raw?.CPU?.used_cpu_sys_children }}</span></div>
          <div>used_cpu_sys_main_thread <span class="float-end">{{ d.raw?.CPU?.used_cpu_sys_main_thread }}</span></div>
          <div>used_cpu_user_children <span class="float-end">{{ d.raw?.CPU?.used_cpu_user_children }}</span></div>
        </a-card>
      </a-col>

      <!-- Cluster -->
      <a-col :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="Cluster">
          <template #extra><a href="#">详细</a></template>
          <div>cluster_enabled <span class="float-end">{{ d.raw?.Cluster?.cluster_enabled }}</span></div>
        </a-card>
      </a-col>

      <!-- 客户端 -->
      <a-col :sm="24" :md="12" :lg="12">
        <a-card class="shadow-sm" size="small" title="客户端">
          <template #extra><a href="#">详细</a></template>
          <div>最大输入缓冲区 <span class="float-end">{{ d.raw?.Clients?.client_recent_max_input_buffer }}</span></div>
          <div>当前 Redis 节点的客户端连接数 <span class="float-end">{{ d.raw?.Clients?.clients_in_timeout_table }}</span></div>
          <div>正在追踪的客户端数量 <span class="float-end">{{ d.raw?.Clients?.tracking_clients }} byte</span></div>
          <div>Redis 集群中的连接数量 <span class="float-end">{{ d.raw?.Clients?.cluster_connections }}</span></div>
          <div>连接到 Redis 服务器的客户端数量 <span class="float-end">{{ d.raw?.Clients?.connected_clients }}</span></div>
          <div>Redis 服务器中客户端输出缓冲区的最大大小 <span class="float-end">{{
              d.raw?.Clients?.client_recent_max_output_buffer
            }}</span></div>
          <div>正在执行阻塞命令的客户端数 <span class="float-end">{{ d.raw?.Clients?.blocked_clients }}</span></div>
        </a-card>
      </a-col>

<!--      <template v-for="(v, k, i) in d.raw" :key="i">
        <a-col :sm="24" :md="12" :lg="12">
          <a-card class="shadow-sm" size="small" :title="k">
            <template #extra><a href="#">详细</a></template>
            <template v-for="(v2, k2, i2) in v" :key="i2">
              <div>{{ k2 }} <span class="float-end">&lt;!&ndash; {{ v2 }} &ndash;&gt;</span></div>
            </template>
          </a-card>
        </a-col>
      </template>-->

    </a-row>
  </main>
</template>

<script lang="ts" setup>
// vue
import {reactive, onMounted} from "vue";

// api
import {monitorApi} from "@/api/Other";

// view
import ErrorView from "@/views/meta/ErrorView.vue";

interface IRaw {
  Persistence: any
  Server: any
  Memory: any
  Status: any
  Clients: any
}

const d = reactive({
  isLoad: false,
  raw: {} as IRaw
})

onMounted(() => {
  monitorApi.redis().then(({data}) => {
    d.isLoad = data.code === 200
    d.raw = data.data
  })
})
</script>

<style scoped>
/* 固定一下高度 */
.ant-card {
  min-height: 300px !important;
  max-height: 300px !important;
  overflow-y: auto;
  overflow-x: hidden;
  margin-bottom: 10px;
}

.ant-card-body-p-0 > .ant-card-body {
  padding: 0 !important;
}


.card {
  transition: transform 0.2s ease-out;
}
.card:hover {
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  transform: translateY(-10px);
  /* transform: translateY(-10px) scale(1.05)*/
}


.card-2 {
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  transition: box-shadow 0.3s ease-out;
}

.card-2:hover {
  box-shadow: 0 0 20px rgba(0,0,0,0.3);
}
</style>
