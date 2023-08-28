<template>
  <main>
    <!-- 功能区域 -->
    <a-space class="mb-2">
      <template v-for="(item, index) in funs" :key="index">
        <a-button type="primary" :size="antdUtil.tableSizeToOtherSize(table.size)" @click="item.onClick()">
          <component :is="iconUtil.getIcon(item.icon)"/>
          {{ item.name }}
        </a-button>
      </template>

      <a-radio-group v-model:value="table.size" :size="antdUtil.tableSizeToOtherSize(table.size)">
        <template v-for="(item, index) in table.sizeList" :key="index">
          <a-radio-button :value="item.value">{{ item.label }}</a-radio-button>
        </template>
      </a-radio-group>

      <a-input-number style="width: 8em" min="1" max="30" :size="antdUtil.tableSizeToOtherSize(table.size)"
                      v-model:value="table.show" addon-after="个"></a-input-number>
    </a-space>

    <!-- 表格 -->
    <a-table row-key="id" :size="table.size"
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
      <template #customFilterDropdown="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }">
        <div style="padding: 8px">
          <a-input ref="tableFilterSearchInput" style="min-width: 180px; margin-bottom: 8px; display: block"
                   :placeholder="`Search ` + column.dataIndex"
                   :value="selectedKeys[0]"
                   @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                   @pressEnter="table.handleSearch(selectedKeys, confirm, column.dataIndex)"/>
          <a-button type="primary" size="small" style="width: 90px; margin-right: 8px"
                    @click="table.handleSearch(selectedKeys, confirm, column.dataIndex)">
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
        <template v-else-if="column.dataIndex === 'flag'">
          <a-switch @change="table.onUpdate(record)" v-model:checked="record.flag"></a-switch>
        </template>

        <!-- 如果是需要被编辑的字段(str, int) -->
        <template v-else-if="['id','uuid','account','password','notes'].includes(column.dataIndex)">
          <!-- 如果正在编辑字段中 -->
          <a-input v-if="table.editableData[record.id]"
                   v-model:value="table.editableData[record.id][column.dataIndex]"
                   style="margin: -5px 0"></a-input>
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
                <a-popconfirm placement="topLeft" title="是否要删除?" @confirm="table.onDelete(record.id)">
                  <a-button size="small" type="danger">删除</a-button>
                </a-popconfirm>
                <a-button @click="table.edit(record.id)" size="small">修改</a-button>
              </a-space>
            </div>
          </div>
        </template>

        <!-- 如果是特殊字段 -->
        <template v-if="modalMore.list">
          <template v-for="(item, index) in modalMore.list" :key="index">
            <template v-if="column.dataIndex === item.key">
              <a-button @click="modalMore.showModal(item.id, record[item.key])" size="small">修改</a-button>
            </template>
          </template>
        </template>
      </template>
    </a-table>

    <!-- modal -->
    <a-modal title="新增user数据" v-model:visible="modal.visible" @ok="modal.handleOk">
      <a-form layout="horizontal" v-bind="{ labelCol: { span: 4 }, wrapperCol: { span: 14 } }"
              :model="form" class="overflow-auto" style="max-height: 64vh">
        <!-- 如果是date -->
        <!-- <a-time-picker class="mb-1" v-model:value="" format="HH:mm:ss" /> -->
<!--        <a-form-item label="ID">
          <a-input-number class="mb-1 w-100" v-model:value="form.id" placeholder="请输入: ID"></a-input-number>
        </a-form-item>
        <a-form-item label="UUID">
          <a-input class="mb-1" v-model:value="form.uuid" placeholder="请输入: UUID"></a-input>
        </a-form-item>-->
        <a-form-item label="账号">
          <a-input class="mb-1" v-model:value="form.account" placeholder="请输入: 账号"></a-input>
        </a-form-item>
        <a-form-item label="密码">
          <a-input class="mb-1" v-model:value="form.password" placeholder="请输入: 密码"></a-input>
        </a-form-item>
        <a-form-item label="逻辑删除">
          <a-switch class="mb-1" v-model:checked="form.flag" checked-children="开" un-checked-children="关"></a-switch>
        </a-form-item>
        <a-form-item label="备注">
          <a-input class="mb-1" v-model:value="form.notes" placeholder="请输入: 备注"></a-input>
        </a-form-item>
      </a-form>
    </a-modal>

    <a-modal title="查看更多信息" class="w-100" v-model:visible="modalMore.visible" @ok="modalMore.handleOk">
      <!-- TODO: 1,4,5的更新没写 -->
      <template v-if="modalMore.mode === 1">
        <user-info-view :data="modalMore.record"/>
      </template>
      <!-- TODO: 2,3没写完 -->
      <template v-if="modalMore.mode === 2">
        <user-inventory-view :data="modalMore.record"/>
      </template>
      <template v-if="modalMore.mode === 4">
        <user-level-view :data="modalMore.record"/>
      </template>
      <template v-if="modalMore.mode === 5">
        <user-security-view :data="modalMore.record"/>
      </template>
    </a-modal>
  </main>
</template>

<script lang="ts" setup>
// vue
import {ref, reactive, onMounted} from "vue";
import {cloneDeep} from 'lodash-es';

// antd
import {SearchOutlined} from '@ant-design/icons-vue';
import {message} from "ant-design-vue";

// api
import {IUser, userApi} from "@/api/erp_member/User.ts";
import {iconUtil, antdUtil} from "@/utils/projectUtil";

// view
import UserInfoView from "@/views/modules/user/sub/UserInfoView.vue";
import UserLevelView from "@/views/modules/user/sub/UserLevelView.vue";
import UserSecurityView from "@/views/modules/user/sub/UserSecurityView.vue";
import UserInventoryView from "@/views/modules/user/sub/UserInventoryView.vue";

// 需要重复用的API
const a = {
  queryPage: (page) => {
    userApi.queryPage(page, table.show).then(({data}) => {
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
      userApi.removeList(ids).then(() => {
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
  {name: "刷新缓存", icon: "reload-outlined", onClick: null}
])

// 表格
const tableFilterSearchInput = ref()
const table = reactive({
  // 显示数量
  show: 10,

  // 数据源, 展示列
  datasource: [],
  columns: [
    {
      title: 'ID', dataIndex: 'id', key: 'id',
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
      title: 'UUID', dataIndex: 'uuid', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.uuid.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '账号', dataIndex: 'account', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.account.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '密码', dataIndex: 'password', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.password.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {title: '逻辑删除', dataIndex: 'flag', key: 'id'},
    {
      title: '备注', dataIndex: 'notes', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.notes.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {title: '信息', dataIndex: 'info'},
    {title: '等级', dataIndex: 'level'},
    {title: '安全', dataIndex: 'security'},
    {title: '背包', dataIndex: 'inventory'},
    {title: '背包(唯一)', dataIndex: 'inventorUnique'},
    {title: 'operation', dataIndex: 'operation'},
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
    userApi.remove(id).then(() => {
      message.success("删除成功")
      a.queryPage(table.pagination.current)
    }).catch(() => {
      message.error("删除失败")
    })
  },

  // 更新
  onUpdate: (item) => {
    userApi.update(item).then(() => {
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
    userApi.update(arr[0]).then(() => {
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
    {id: 1, label: "大", value: "default"},
    {id: 2, label: "中", value: "middle"},
    {id: 3, label: "小", value: "small"},
  ],
})

// 表单
const form = reactive<IUser>({
  id: null as number,
  uuid: "",
  account: "",
  password: "",
  flag: false,
  notes: "",
})

const modal = reactive({
  visible: false,
  showModal: () => {
    modal.visible = true;
  },
  handleOk: (e: MouseEvent) => {
    modal.visible = false
    userApi.add(form).then(() => {
      message.success("添加成功")
      a.queryPage(table.pagination.current)
    }).catch(() => {
      message.error("添加失败")
    })
  }
})

const modalMore = reactive({
  visible: false,

  mode: undefined as number,
  record: undefined,

  list: [
    {id: 1, key: "info"},
    {id: 2, key: "inventory", com: "UserInfoView"},
    {id: 3, key: "inventorUnique", com: "UserInfoView"},
    {id: 4, key: "level"},
    {id: 5, key: "security"},
  ],

  showModal: (mode, record) => {
    modalMore.mode = mode
    modalMore.record = record

    modalMore.visible = true;
  },
  handleOk: (e: MouseEvent) => {
    modalMore.visible = false
    console.log(modalMore.record)

    // 检查当前要修改的数据, 是否在请求的数据中 TODO: 这里应该要重新请求数据, 我这里图方便拿以前请求的数据
    let raw = table.datasource.find(value => value.id === modalMore.record.id)
    if (raw === undefined) {
      message.error("修改错误, 可能是因为在此期间数据被人删除了")
      return
    }

    message.success("保存成功")
  }
})

// 加载数据
onMounted(() => {
  a.queryPage(1)
})

</script>

<style>

</style>
