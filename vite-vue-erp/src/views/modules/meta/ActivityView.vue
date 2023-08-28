<template>
  <main>
    <!-- 功能区域 -->
    <a-space class="mb-2">
      <template v-for="(item, index) in funs" :key="index">
        <a-button type="primary" :size="antdUtil.tableSizeToOtherSize(table.size)" @click="item.onClick()">
          <component :is="iconUtil.getIcon(item.icon)" /> {{ item.name }}
        </a-button>
      </template>

      <a-radio-group v-model:value="table.size" :size="antdUtil.tableSizeToOtherSize(table.size)">
        <template v-for="(item, index) in table.sizeList" :key="index">
          <a-radio-button :value="item.value">{{ item.label }}</a-radio-button>
        </template>
      </a-radio-group>

      <a-input-number style="width: 8em" min="1" max="30" :size="antdUtil.tableSizeToOtherSize(table.size)" v-model:value="table.show" addon-after="个"></a-input-number>
    </a-space>

    <!-- 表格 -->
    <a-table row-key="id" :size="table.size" :scroll="{ x: 1000, y: 1000 }"
             :row-selection="{ selectedRowKeys: table.selectedRowKeys, onChange: table.onSelectChange }"
             :data-source="table.datasource"
             :columns="table.columns"
             :pagination="table.pagination"
             @change="table.onChange"
             bordered>
      <!-- 自定义过滤菜单: 图标, 下拉菜单 -->
      <template #customFilterIcon="{ filtered }">
        <search-outlined :style="{ color: filtered ? '#108ee9' : undefined }"/>
      </template>
      <template #customFilterDropdown="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }" >
        <div style="padding: 8px">
          <a-input ref="tableFilterSearchInput" style="min-width: 180px; margin-bottom: 8px; display: block"
                   :placeholder="`Search ` + column.dataIndex"
                   :value="selectedKeys[0]"
                   @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                   @pressEnter="table.handleSearch(selectedKeys, confirm, column.dataIndex)" />
          <a-button type="primary" size="small" style="width: 90px; margin-right: 8px"
                    @click="table.handleSearch(selectedKeys, confirm, column.dataIndex)">
            <template #icon> <SearchOutlined/> </template>
            Search
          </a-button>
          <a-button size="small" style="width: 90px" @click="table.handleReset(clearFilters)">
            Reset
          </a-button>
        </div>
      </template>

      <!-- 自定义表格列(有需求自己写) -->

      <!-- 自定义表格体 -->
      <template #bodyCell="{ column, text, record }">
        <!-- 防止else -->
        <template v-if="false"></template>

        <!-- 如果是搜索结果 -->
        <span v-else-if="table.filterSearchText && table.filterChedColumn === column.dataIndex">
          <template v-for="(fragment, i) in text.toString().split(table.getRegExp())">
            <mark v-if="fragment.toLowerCase() === table.filterSearchText.toLowerCase()" :key="i" class="highlight">
              {{ fragment }}
            </mark>
            <template v-else>{{ fragment }}</template>
          </template>
        </span>

        <!-- 如果是特殊字段(Boolean, Date) -->

        <!-- 如果是需要被编辑的字段(str, int) -->
        <template v-else-if="['id','name','description','location','organizerId','maxAttendees'].includes(column.dataIndex)">
          <!-- 如果正在编辑字段中 -->
          <a-input v-if="table.editableData[record.id]"
                   v-model:value="table.editableData[record.id][column.dataIndex]"
                   style="margin: -5px 0"></a-input>

          <template v-else-if="column.dataIndex === 'description'">
            <a-tooltip :title="text">
              <div class="text-truncate">{{ text }}</div>
            </a-tooltip>
          </template>
        </template>

        <!-- 如果是不需要被编辑的字段(需要自定义的) -->

        <!-- operation -->
        <template v-else-if="column.dataIndex === 'operation'">
          <div class="editable-row-operations">
            <!-- 如果是编辑状态 -->
            <div v-if="table.editableData[record.id]">
              <a-space>
                <a-button @click="table.save(record.id)" size="small">保存</a-button>
                <a-popconfirm placement="topLeft" title="是否要取消" @confirm="table.cancel(record.id)">
                  <a-button size="small">取消</a-button>
                </a-popconfirm>
              </a-space>
            </div>

            <!-- 如果是正常状态 -->
            <div v-else>
              <a-space>
                <a-popconfirm placement="topLeft" title="是否要删除?" @confirm="table.onDelete(record.id)" >
                  <a-button size="small" type="danger">删除</a-button>
                </a-popconfirm>
                <a-button @click="table.edit(record.id)" size="small">修改</a-button>
              </a-space>
            </div>
          </div>
        </template>

        <!-- 最后不需要else, antd自己会处理 -->
      </template>
    </a-table>

    <!-- modal -->
    <a-modal title="新增activity数据" v-model:visible="modal.visible" @ok="modal.handleOk">
      <a-form layout="horizontal" v-bind="{ labelCol: { span: 4 }, wrapperCol: { span: 14 } }"
              :model="form" class="overflow-auto" style="max-height: 64vh">
        <!-- 如果是date -->
        <!-- <a-time-picker class="mb-1" v-model:value="" format="HH:mm:ss" /> -->
        <a-form-item label="活动ID">
          <a-input-number class="mb-1 w-100" v-model:value="form.id" placeholder="请输入: 活动ID"></a-input-number>
        </a-form-item>
        <a-form-item label="活动名称">
          <a-input class="mb-1" v-model:value="form.name" placeholder="请输入: 活动名称"></a-input>
        </a-form-item>
        <a-form-item label="活动描述">
          <a-input class="mb-1" v-model:value="form.description" placeholder="请输入: 活动描述"></a-input>
        </a-form-item>
        <a-form-item label="活动开始时间">
          <a-input class="mb-1" v-model:value="form.startTime" placeholder="请输入: 活动开始时间"></a-input>
        </a-form-item>
        <a-form-item label="活动结束时间">
          <a-input class="mb-1" v-model:value="form.endTime" placeholder="请输入: 活动结束时间"></a-input>
        </a-form-item>
        <a-form-item label="活动地点">
          <a-input class="mb-1" v-model:value="form.location" placeholder="请输入: 活动地点"></a-input>
        </a-form-item>
        <a-form-item label="组织者ID">
          <a-input-number class="mb-1 w-100" v-model:value="form.organizerId" placeholder="请输入: 组织者ID"></a-input-number>
        </a-form-item>
        <a-form-item label="最大参与人数">
          <a-input-number class="mb-1 w-100" v-model:value="form.maxAttendees" placeholder="请输入: 最大参与人数"></a-input-number>
        </a-form-item>
        <a-form-item label="创建时间">
          <a-input class="mb-1" v-model:value="form.createdAt" placeholder="请输入: 创建时间"></a-input>
        </a-form-item>
        <a-form-item label="更新时间">
          <a-input class="mb-1" v-model:value="form.updatedAt" placeholder="请输入: 更新时间"></a-input>
        </a-form-item>
      </a-form>
    </a-modal>
  </main>
</template>

<script lang="ts" setup>
// vue
import { ref, reactive, onMounted } from "vue";
import {cloneDeep} from 'lodash-es';

// antd
import {SearchOutlined} from '@ant-design/icons-vue';
import { message } from "ant-design-vue";

// api
import { IActivity, activityApi } from "@/api/erp_system/Activity.ts";
import {iconUtil, antdUtil} from "@/utils/projectUtil";

// 需要重复用的API
const a = {
  queryPage: (page) => {
    activityApi.queryPage(page, table.show).then(({data}) => {
      let page = data.data.page
      // 内容
      table.datasource = page.content
      // 当前页码数
      table.pagination.current = page.number + 1
      // 最大页码数page.totalPages
      // 显示数量 page.size
      table.pagination.pageSize = page.size
      // 总共元素 page.totalElements
      table.pagination.total = page.totalElements
    }).catch(() => {
      message.error("获取数据失败")
    })
  }
}

// 功能区
const funs = ref([
  {
    name: "新增数据", icon: "file-add-outlined", onClick: () => modal.showModal()
  },
  {
    name: "批量删除", icon: "delete-outlined", onClick: () => {
      const ids: Array<number> = table.selectedRowKeys.filter((item) => typeof item === "number") as Array<number>;
      if (ids.length === 0) {
        message.error("您还未选择item")
        return
      }
      activityApi.removeList(ids).then(() => {
        message.success("删除成功")
        a.queryPage(table.pagination.current)
      }).catch(() => {
        message.error("删除失败")
      })
    }
  },
  {
    name: "刷新数据", icon: "retweet-outlined", onClick: () => {
      message.success("刷新成功")
      a.queryPage(table.pagination.current)
    }
  },
  { name: "刷新缓存", icon: "reload-outlined", onClick: null }
])

// 表格
const tableFilterSearchInput = ref()
const table = reactive({
  // 显示数量
  show: 10,

  // 数据源, 展示列
  datasource: [],
  columns: [
    /*{
      title: '活动ID', dataIndex: 'id', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.id.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },*/
    {
      title: '活动名称', dataIndex: 'name', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.name.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '活动描述', dataIndex: 'description', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.description.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '活动开始时间', dataIndex: 'startTime', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.startTime.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '活动结束时间', dataIndex: 'endTime', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.endTime.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '活动地点', dataIndex: 'location', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.location.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '组织者ID', dataIndex: 'organizerId', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.organizerId.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '最大参与人数', dataIndex: 'maxAttendees', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.maxAttendees.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    /*{
      title: '创建时间', dataIndex: 'createdAt', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.createdAt.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '更新时间', dataIndex: 'updatedAt', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.updatedAt.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },*/
    { title: 'operation', dataIndex: 'operation' },
  ],

  // 分页
  pagination: {
    pageSize: undefined,
    current: undefined, total: undefined
  },
  onChange: (page) => {
    a.queryPage(page.current)
  },

  // 选择的列
  selectedRowKeys: [],
  onSelectChange: (selectedRowKeysNew) => {
    table.selectedRowKeys = selectedRowKeysNew;
    console.log(table.selectedRowKeys)
  },

  // 删除
  onDelete: (id) => {
    activityApi.remove(id).then(() => {
      message.success("删除成功")
      a.queryPage(table.pagination.current)
    }).catch(() => {
      message.error("删除失败")
    })
  },

  // 更新
  onUpdate: (item) => {
    activityApi.update(item).then(() => {
      message.success("修改成功")
      a.queryPage(table.pagination.current)
    }).catch(() => {
      message.error("修改失败")
    })
  },

  // 编辑字段
  editableData: {},
  edit: (key: string) => {
    table.editableData[key] = cloneDeep(table.datasource.filter(item => key === item.id)[0]);
  },
  save: (key: string) => {
    let arr = table.datasource.filter(item => key === item.id)
    if (arr.length === 0) {
      message.error("修改错误, 找不到id为" + key + "的item")
      return
    }
    Object.assign(table.datasource.filter(item => key === item.id)[0], table.editableData[key]);
    delete table.editableData[key];
    activityApi.update(arr[0]).then(() => {
      message.success("修改成功")
      a.queryPage(table.pagination.current)
    }).catch(() => {
      message.error("修改失败")
    })
  },
  cancel: (key: string) => {
    delete table.editableData[key];
  },

  // 过滤字段
  filterSearchText: "",
  filterChedColumn: "",
  handleSearch: (selectedKeys, confirm, dataIndex) => {
    confirm();
    table.filterSearchText = selectedKeys[0];
    table.filterChedColumn = dataIndex;
  },
  handleReset: clearFilters => {
    clearFilters({confirm: true});
    table.filterSearchText = '';
  },
  getRegExp: () => {
    let re = "(?<=" + table.filterSearchText + ")|(?=" + table.filterSearchText + ")"
    return new RegExp(re, "i")
  },

  // 大小
  size: "middle",
  sizeList: [
    { id: 1, label: "大", value: "default" },
    { id: 2, label: "中", value: "middle" },
    { id: 3, label: "小", value: "small" },
  ],
})

// 表单
const form = reactive<IActivity>({
  id: null as number,
  name: "",
  description: "",
  startTime: "",
  endTime: "",
  location: "",
  organizerId: null as number,
  maxAttendees: null as number,
  createdAt: "",
  updatedAt: "",
})

const modal = reactive({
  visible: false,
  showModal: () => {
    modal.visible = true;
  },
  handleOk: (e: MouseEvent) => {
    modal.visible = false
    activityApi.add(form).then(()=> {
      message.success("添加成功")
      a.queryPage(table.pagination.current)
    }).catch(() => {
      message.error("添加失败")
    })
  }
})

// 加载数据
onMounted(() => {
  a.queryPage(1)
})

</script>

<style>

</style>
