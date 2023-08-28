<template>
  <main>
    <!-- 功能区域 -->
    <a-space class="mb-2">
      <template v-for="(item, index) in funs" :key="index">
        <a-button type="primary" :size="antdUtil.tableSizeToOtherSize(table.size)" @click="item.onClick()">
          <component :is="iconUtil.getIcon(item.icon)" /> {{ item.name }}
        </a-button>
      </template>

      <a-radio-group :size="antdUtil.tableSizeToOtherSize(table.size)" v-model:value="table.size">
        <template v-for="(item, index) in table.sizeList" :key="index">
          <a-radio-button :value="item.value">{{ item.label }}</a-radio-button>
        </template>
      </a-radio-group>

      <a-input-number style="width: 8em" min="1" max="30" :size="antdUtil.tableSizeToOtherSize(table.size)" v-model:value="table.show" addon-after="个"></a-input-number>
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
        <template v-else-if="column.dataIndex === 'isRedirect'">
          <a-switch @change="table.onUpdate(record)" v-model:checked="record.isRedirect"></a-switch>
        </template>
        <template v-else-if="column.dataIndex === 'flag'">
          <a-switch @change="table.onUpdate(record)" v-model:checked="record.flag"></a-switch>
        </template>

        <!-- 如果是需要被编辑的字段(str, int) -->
        <!--
         TODO
          Q: 如果有children并且处于编辑状态的时候, 展开按钮会和编辑框进行重叠
          A: 监听编辑列表: table.editableData
             每当有编辑字段加入或离开, 就对它的ICON做出反应
             ICON的关键词：data-row-key="[表格的key]" ant-table-row-expand-icon-collapsed
         -->
        <template v-else-if="['id','parentId','title','icon','url','content','sort'].includes(column.dataIndex)">
          <!-- 如果正在编辑字段中 -->
          <a-input v-if="table.editableData[record.id]"
                   v-model:value="table.editableData[record.id][column.dataIndex]"
                   style="margin: -5px 0"></a-input>

          <!-- 既需要被编辑, 也需要自定义 -->
          <template v-else-if="column.dataIndex === 'icon'">
            <component :is="iconUtil.iconMap.get(text)"></component>
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
    <a-modal title="新增menu数据" v-model:visible="modal.visible" @ok="modal.handleOk">
      <a-form layout="horizontal" v-bind="{ labelCol: { span: 4 }, wrapperCol: { span: 14 } }"
              :model="form" class="overflow-auto" style="max-height: 64vh">
        <!-- 如果是date -->
        <!-- <a-time-picker class="mb-1" v-model:value="" format="HH:mm:ss" /> -->
<!--        <a-form-item label="ID">
          <a-input-number class="mb-1 w-100" v-model:value="form.id" placeholder="请输入: ID"></a-input-number>
        </a-form-item>-->

        <a-form-item label="上级菜单">
          <a-tree-select class="w-100" placeholder="请选择上级菜单"
                         :tree-data="table.datasource"
                         v-model:value="form.parentId"
                         :fieldNames="{children: 'children', label: 'title', value: 'id'}"
                         show-search allow-clear tree-default-expand-all treeLine
                         :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                         tree-node-filter-prop="title"
          >
          </a-tree-select>
        </a-form-item>

        <a-form-item label="标题">
          <a-input class="mb-1" v-model:value="form.title" placeholder="请输入: 标题"></a-input>
        </a-form-item>

        <a-form-item label="图标选择">
          <a-popover title="Title" trigger="click">
            <template #content>
              <a-row justify="space-around" style="width: 204px" gutter="20">
                <template v-for="(v, k) in iconUtil.iconJson">
                  <a-col>
                    <component @click="form.icon = k" :is="v"></component>
                  </a-col>
                </template>
              </a-row>
            </template>
            <a-input v-model:value="form.icon" allow-clear></a-input>
          </a-popover>
        </a-form-item>

        <a-form-item label="组件路径(虚拟字段, 由java自动生成, URL+View.vue)">
          <a-input class="mb-1" v-model:value="form.url" placeholder="请输入: 组件路径(虚拟字段, 由java自动生成, URL+View.vue)"></a-input>
        </a-form-item>
        <a-form-item label="是否为重定向">
          <a-switch class="mb-1" v-model:checked="form.isRedirect" checked-children="开" un-checked-children="关"></a-switch>
        </a-form-item>
        <a-form-item label="介绍">
          <a-input class="mb-1" v-model:value="form.content" placeholder="请输入: 介绍"></a-input>
        </a-form-item>
        <a-form-item label="排序">
          <a-input-number class="mb-1 w-100" v-model:value="form.sort" placeholder="请输入: 排序"></a-input-number>
        </a-form-item>
        <a-form-item label="逻辑删除">
          <a-switch class="mb-1" v-model:checked="form.flag" checked-children="开" un-checked-children="关"></a-switch>
        </a-form-item>
        <!--        <a-form-item label="创建时间">-->
        <!--          <a-input class="mb-1" v-model:value="form.createdAt" placeholder="请输入: 创建时间"></a-input>-->
        <!--        </a-form-item>-->
        <!--        <a-form-item label="修改时间">-->
        <!--          <a-input class="mb-1" v-model:value="form.updatedAt" placeholder="请输入: 修改时间"></a-input>-->
        <!--        </a-form-item>-->
      </a-form>
    </a-modal>
  </main>
</template>

<script lang="ts" setup>
// vue
import { ref, reactive, onMounted } from "vue";
import {cloneDeep} from 'lodash-es';

// antd
import {SearchOutlined, CheckOutlined, EditOutlined} from '@ant-design/icons-vue';
import { message } from "ant-design-vue";

// api
import { IMenu, menuApi } from "@/api/erp_system/Menu.ts";
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
      menuApi.removeList(ids).then(() => {
        message.success("删除成功")
        a.queryTree()
      }).catch(() => {
        message.error("删除失败")
      })
    }
  },
  {
    name: "刷新数据", icon: "retweet-outlined", onClick: () => {
      message.success("刷新成功")
      a.queryTree()
    }
  },
  { name: "刷新缓存", icon: "reload-outlined", onClick: null }
])

// 需要重复用的API
const a = {
  queryPage: (page) => {
    menuApi.queryPage(page, table.show).then(({data}) => {
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
  },

  queryTree: (flag = undefined) => {
    menuApi.queryTree(flag).then(({data}) => {
      table.datasource = data.data.tree
    }).catch(() => {
      message.error("获取数据失败")
    })
  }
}

// 加载数据
onMounted(() => {
  a.queryTree()
})

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
    /*{
      title: '自身ID', dataIndex: 'parentId', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.parentId.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },*/
    {
      title: '标题', dataIndex: 'title', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.title.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '图标', dataIndex: 'icon', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.icon.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '组件路径', dataIndex: 'url', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.url.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    { title: '是否为重定向', dataIndex: 'isRedirect', key: 'id'},
    {
      title: '介绍', dataIndex: 'content', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.content.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {
      title: '排序', dataIndex: 'sort', key: 'id',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.sort.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    { title: '逻辑删除', dataIndex: 'flag', key: 'id'},
    {
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
      title: '修改时间', dataIndex: 'updatedAt', key: 'id',
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
    },
    { title: 'operation', dataIndex: 'operation' },
  ],

  // 分页
  pagination: {
    pageSize: undefined,
    current: undefined, total: undefined
  },
  onChange: (page) => {
    a.queryTree()
  },

  // 选择的列
  selectedRowKeys: [],
  onSelectChange: (selectedRowKeysNew) => {
    table.selectedRowKeys = selectedRowKeysNew;
    console.log(table.selectedRowKeys)
  },

  // 删除
  onDelete: (id) => {
    menuApi.remove(id).then(() => {
      message.success("删除成功")
      a.queryTree()
    }).catch(() => {
      message.error("删除失败")
    })
  },

  // 更新
  onUpdate: (item) => {
    menuApi.update(item).then(() => {
      message.success("修改成功")
      a.queryTree()
    }).catch(() => {
      message.error("修改失败")
    })
  },

  // 编辑字段
  findItemById: (list, key) => {
    const item = list.find(item => item.id === key)
    if (item) { return item }
    for (const child of list) {
      if (child.children) {
        const found = table.findItemById(child.children, key)
        if (found) {
          return found
        }
      }
    }
    return undefined
  },
  editableData: {},
  edit: (key: string) => {
    //table.editableData[key] = cloneDeep(table.datasource.filter(item => key === item.id)[0]);
    table.editableData[key] = cloneDeep(table.findItemById(table.datasource, key));
  },
  save: (key: string) => {
    let item = table.findItemById(table.datasource, key)
    if (item === undefined) {
      message.error("修改错误, 找不到id为" + key + "的item")
      return
    }
    Object.assign(item, table.editableData[key]);
    delete table.editableData[key];
    menuApi.update(item).then(() => {
      message.success("修改成功")
      a.queryTree()
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
const form = reactive<IMenu>({
  id: null as number,
  parentId: null as number,
  title: "",
  icon: "",
  url: "",
  isRedirect: false,
  content: "",
  sort: null as number,
  flag: false,
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
    menuApi.add(form).then(()=> {
      message.success("添加成功")
      a.queryTree()
    }).catch(() => {
      message.error("添加失败")
    })
  }
})

</script>

<style>

</style>
