<template>
  <main>
    <!-- funs -->
    <a-space class="mb-2">
      <template v-for="(item, index) in funs" :key="index">
        <a-button type="primary" @click="item.onClick()">
          <component :is="iconUtil.getIcon(item.icon)" />
          {{ item.name }}
        </a-button>
      </template>

      <a-radio-group v-model:value="table.size">
        <template v-for="(item, index) in table.sizeList" :key="index">
          <a-radio-button :value="item.value">{{ item.label }}</a-radio-button>
        </template>
      </a-radio-group>

    </a-space>

    <!-- table -->
    <a-table
        row-key="id" :size="table.size"
        :row-selection="{ selectedRowKeys: table.selectedRowKeys, onChange: table.onSelectChange }"
        :data-source="table.datasource"
        :columns="table.columns"
        :pagination="table.pagination"
        @change="table.onChange"
        bordered>
      <!-- 自定义过滤菜单 -->
      <template #customFilterIcon="{ filtered }">
        <search-outlined :style="{ color: filtered ? '#108ee9' : undefined }"/>
      </template>
      <template #customFilterDropdown="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }" >
        <div style="padding: 8px">
          <a-input
              ref="tableFilterSearchInput"
              :placeholder="`Search ` + column.dataIndex"
              :value="selectedKeys[0]"
              style="width: 188px; margin-bottom: 8px; display: block"
              @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
              @pressEnter="table.handleSearch(selectedKeys, confirm, column.dataIndex)"
          />
          <a-button
              type="primary"
              size="small"
              style="width: 90px; margin-right: 8px"
              @click="table.handleSearch(selectedKeys, confirm, column.dataIndex)"
          >
            <template #icon>
              <SearchOutlined/>
            </template>
            Search
          </a-button>
          <a-button size="small" style="width: 90px" @click="table.handleReset(clearFilters)">
            Reset
          </a-button>
        </div>
      </template>

      <!-- 自定义表格体 -->
      <template #bodyCell="{ column, text, record }">
        <!-- 防止else -->
        <template v-if="false"></template>

        <!-- 如果是搜索结果 -->
        <span v-if="table.filterSearchText && table.filterChedColumn === column.dataIndex">
                <template v-for="(fragment, i) in text.toString()
                    .split(table.getRegExp())"
                >
                  <mark v-if="fragment.toLowerCase() === table.filterSearchText.toLowerCase()" :key="i" class="highlight">
                    {{ fragment }}
                  </mark>
                  <template v-else>{{ fragment }}</template>
                </template>
              </span>

        <!-- 如果是需要被编辑的字段(str, int) -->
        <template v-else-if="['id','key','value','type','description'].includes(column.dataIndex)">
          <!-- 如果数据是正确的 -->
          <a-input v-if="table.editableData[record.id]"
                   v-model:value="table.editableData[record.id][column.dataIndex]"
                   style="margin: -5px 0"></a-input>
          <!-- 如果数据是错误的 -->
          <template v-else>
            {{ text }}
          </template>
        </template>

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
                <a-popconfirm title="是否要删除?" @confirm="table.onDelete(record.id)" >
                  <a-button size="small" type="danger">删除</a-button>
                </a-popconfirm>
                <a-button @click="table.edit(record.id)" size="small">修改</a-button>
              </a-space>
            </div>
          </div>
        </template>
      </template>
    </a-table>

    <!-- modal -->
    <a-modal title="新增settings数据" v-model:visible="modal.visible" @ok="modal.handleOk">
      <a-form layout="horizontal"
              :model="form" v-bind="{ labelCol: { span: 4 }, wrapperCol: { span: 14 } }"
              class="overflow-auto"
              style="max-height: 64vh">
        <a-form-item label="id">
          <a-input-number class="mb-1 w-100" v-model:value="form.id" placeholder="请输入: id"></a-input-number>
        </a-form-item>
        <a-form-item label="健">
          <a-input class="mb-1" v-model:value="form.key" placeholder="请输入: 健"></a-input>
        </a-form-item>
        <a-form-item label="值">
          <a-input class="mb-1" v-model:value="form.value" placeholder="请输入: 值"></a-input>
        </a-form-item>
        <a-form-item label="类型">
          <a-input class="mb-1" v-model:value="form.type" placeholder="请输入: 类型"></a-input>
        </a-form-item>
        <a-form-item label="描述">
          <a-input class="mb-1" v-model:value="form.description" placeholder="请输入: 描述"></a-input>
        </a-form-item>
        <a-form-item label="创建时间">
          <a-input class="mb-1" v-model:value="form.createTime" placeholder="请输入: 创建时间"></a-input>
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
import { ISettings, settingsApi } from "@/api/System.ts";
import {iconUtil, antdUtil} from "@/utils/projectUtil";

// 功能区
const funs = ref([
  {name: "新增数据", icon: "file-add-outlined", onClick: () => modal.showModal()},
  {
    name: "批量删除", icon: "delete-outlined", onClick: () => {
      const ids: Array<number> = table.selectedRowKeys.filter((item) => typeof item === "number") as Array<number>;
      if (ids.length === 0) {
        message.error("您还未选择item")
        return
      }
      settingsApi.removeList(ids).then(() => {
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
])

// 需要重复用的API
const a = {
  queryPage: (page) => {
    settingsApi.queryPage(page).then(({data}) => {
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

// 加载数据
onMounted(() => {
  a.queryPage(1)
})

// 表格
const tableFilterSearchInput = ref()
const table = reactive({
  // 数据源, 展示列
  datasource: [],
  columns: [
    {
      title: 'id', dataIndex: 'id', key: 'id',
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
    },
    {
      title: '健', dataIndex: 'key', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.key.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '值', dataIndex: 'value', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.value.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '类型', dataIndex: 'type', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.type.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '描述', dataIndex: 'description', key: 'id',
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
      title: '创建时间', dataIndex: 'createTime', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.createTime.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
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
    settingsApi.remove(id).then(() => {
      message.success("删除成功")
      a.queryPage(table.pagination.current)
    }).catch(() => {
      message.error("删除失败")
    })
  },

  // 更新
  onUpdate: (item) => {
    settingsApi.update(item).then(() => {
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
    settingsApi.update(arr[0]).then(() => {
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
interface IForm {
  id: number;
  key: string;
  value: string;
  type: string;
  description: string;
  createTime: string;
}

const form = reactive<IForm>({
  id: null as number,
  key: "",
  value: "",
  type: "",
  description: "",
  createTime: "",
})

const modal = reactive({
  visible: false,
  showModal: () => {
    modal.visible = true;
  },
  handleOk: (e: MouseEvent) => {
    modal.visible = false
    settingsApi.add(form).then(()=> {
      message.success("添加成功")
      a.queryPage(table.pagination.current)
    }).catch(() => {
      message.error("添加失败")
    })
  }
})

</script>

<style>

</style>
