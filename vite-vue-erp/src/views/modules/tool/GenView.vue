<template>
  <main>
    <!-- 功能区 -->
    <a-space class="mb-2">
      <a-select show-search v-model:value="where.current" :size="antdUtil.tableSizeToOtherSize(table.size)"
                style="min-width: 10em"
                @change="where.handleChange"
      >
        <template v-for="item in where.list">
          <a-select-option :value="item">{{ item }}</a-select-option>
        </template>
      </a-select>

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

      <a-input-number style="width: 8em" min="1" max="30" :size="antdUtil.tableSizeToOtherSize(table.size)" v-model:value="table.pagination.pageSize" addon-after="个"></a-input-number>
    </a-space>

    <a-modal v-model:visible="modalSettings.visible" title="设置" @ok="modalSettings.handleOk">
      <dynamic-validate-form :fields="modalSettings.fields"/>
      <a-button>保存(持久化)</a-button>
    </a-modal>

    <!-- 展示区 -->
    <a-table row-key="name" :size="table.size"
             :row-selection="{ selectedRowKeys: table.selectedRowKeys, onChange: table.onSelectChange }"
             :data-source="table.datasource"
             :columns="table.columns"
             :pagination="table.pagination"
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

        <!-- 如果是需要被编辑的字段 -->
        <template v-else-if="['name', 'remarks', 'cat'].includes(column.dataIndex)">
          <!-- 如果数据是正确的 -->
          <a-input v-if="table.editableData[record.name]"
              v-model:value="table.editableData[record.name][column.dataIndex]"
              style="margin: -5px 0"
          />
        </template>

        <!-- 如果是operation -->
        <template v-else-if="column.dataIndex === 'operation'">
          <div class="editable-row-operations">
            <!-- 如果是编辑状态 -->
            <a-space v-if="table.editableData[record.name]">
              <a-button size="small" @click="table.save(record.name)">保存</a-button>
              <a-popconfirm placement="topLeft" title="是否要取消" @confirm="table.cancel(record.name)">
                <a-button size="small">取消</a-button>
              </a-popconfirm>
            </a-space>

            <!-- 如果是正常状态 -->
            <a-space v-else>
              <a-button size="small" type="primary" @click="model.onOpen(record)">预览</a-button>
              <a-button size="small" type="danger" @click="table.edit(record.name)">删除</a-button>
              <a-button size="small" @click="table.edit(record.name)">编辑</a-button>
          </a-space>
          </div>
        </template>
      </template>
    </a-table>

    <!-- modal -->
    <a-modal class="w-50" title="代码预览" centered
             v-model:visible="model.visible"
             @ok="model.onClose()"
    >
      <a-button @click="code.getZip">下载ZIP</a-button>
      <a-tabs v-model:activeKey="model.tabsActiveKey" class="overflow-auto position-relative"
              style="max-height: 75vh; min-height: 75vh">
        <template v-for="(item, index) in code.list" :key="index">
          <a-tab-pane :tab="`${item?.fileType}.${item?.language}`">
            <a-button class="position-absolute" style="right: 40px" @click="otherUtil.copyText(item?.code)">复制文本</a-button>
            <pre><code :class="'language-'+item?.language">{{ item?.code }}</code></pre>
          </a-tab-pane>
        </template>
      </a-tabs>
    </a-modal>

    <a-modal title="新增task数据" v-model:visible="modal.visible" @ok="modal.handleOk">
      <a-form layout="horizontal"
              :model="form" v-bind="{ labelCol: { span: 4 }, wrapperCol: { span: 20 } }"
              class="overflow-auto"
              style="max-height: 64vh">
        <a-form-item label="表格名称">
          <a-input class="mb-1" v-model:value="form.tableName" placeholder="请输入: 名称"></a-input>
        </a-form-item>
        <a-form-item label="表格描述">
          <a-input class="mb-1" v-model:value="form.describe" placeholder="请输入: 描述"></a-input>
        </a-form-item>
        <a-form-item label="列">
          <a-space>
            <a-input type="text" placeholder="名称" v-model:value="formCustomer.fieldName"></a-input>
            <a-select
                ref="select"
                v-model:value="formCustomer.fieldType"
                style="min-width: 120px"
            >
              <!--
                @focus="focus"
                @change="handleChange"
               -->
              <template v-for="(item, index) in formCustomer.fieldTypeList" :key="index">
                <a-select-option :value="item">{{ item }}</a-select-option>
              </template>
            </a-select>
            <a-button type="dashed" @click="formCustomer.onAddDomain()">
              <component :is="iconUtil.getIcon('plus-outlined')" />
              添加字段
            </a-button>
          </a-space>
        </a-form-item>
      </a-form>
      <dynamic-validate-form v-if="formCustomer.fields.length >= 1" v-model:fields="formCustomer.fields" />
    </a-modal>
  </main>
</template>

<script lang="ts" setup>
// vue
import {reactive, onMounted, ref} from "vue";
import {cloneDeep} from 'lodash-es';

// antd
import {SearchOutlined} from '@ant-design/icons-vue';
import {message} from "ant-design-vue";

// api
import {genApi} from "@/api/System";
import {zipUtil} from "@/utils/zipUtil";
import {iconUtil, otherUtil, antdUtil} from "@/utils/projectUtil";

// view
import DynamicValidateForm from "@/components/DynamicValidateForm.vue";

// 筛选
const where = reactive({
  current: "",
  list: [],

  handleChange: (item) => {
    a.loadTable(item)
  },
})

// 功能区
const funs = ref([
  { name: "新增", icon: "file-add-outlined", onClick: () => modal.open() },
  {
    name: "批量删除", icon: "delete-outlined", onClick: () => {
      const tables: Array<string> = table.selectedRowKeys.filter((item) => typeof item === "string") as Array<string>;
      if (tables.length === 0) {
        message.error("您还未选择item")
        return
      }
      message.error("删除失败: " + tables)
    }
  },
  { name: "生成设置", icon: "setting-outlined", onClick: () => modalSettings.open() },
  { name: "下载所有", icon: "cloud-download-outlined", onClick: () => code.getZipAll() },
  {
    name: "刷新数据", icon: "reload-outlined", onClick: () => {
      message.success("刷新成功")
      // a.queryPage(table.pagination.current)
    }
  },
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
      title: '表名称', dataIndex: 'name', key: 'name',
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
      title: '表描述', dataIndex: 'remarks', key: 'remarks',
      customFilterDropdown: true,
      onFilter: (value, record) =>
          record.remarks.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            tableFilterSearchInput.value.focus();
          }, 100);
        }
      },
    },
    {title: '创建时间', dataIndex: 'createTime', key: 'createTime'},
    {title: '表操作', dataIndex: 'operation'},
  ],

  // 分页
  pagination: {
    pageSize: 10,
    // current: undefined, total: undefined
  },

  // 选择的列
  selectedRowKeys: [],
  onSelectChange: (selectedRowKeysNew) => {
    table.selectedRowKeys = selectedRowKeysNew;
    console.log(table.selectedRowKeys)
  },

  // 编辑字段
  editableData: {},
  edit: (key: string) => {
    table.editableData[key] = cloneDeep(table.datasource.filter(item => key === item.name)[0]);
  },
  save: (key: string) => {
    message.success("更新成功")
    // TODO: 修改表格信息
    message.error("暂未支持修改表格")
    Object.assign(table.datasource.filter(item => key === item.name)[0], table.editableData[key]);
    delete table.editableData[key];
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

// 弹窗
const model = reactive({
  visible: false,
  tabsActiveKey: 0,
  currentTable: {},

  onOpen: (item) => {
    model.currentTable = item
    model.visible = true
    let map = modalSettings.getFieldEntity()
    genApi.getTableCode(
        item.cat, item.name,
        map.get("packagePath"), map.get("comments"),
        map.get("author"), map.get("email")
    ).then(({data}) => {
      code.list = data.data.list
    })
  },
  onClose: () => model.visible = false
})

const modal = reactive({
  visible: false,
  open: () => modal.visible = true,
  close: () => modal.visible = false,
  handleOk: () => {
    modalSettings.visible = false
    message.success("保存成功")
  },
})
const modalSettings = reactive({
  visible: false,
  open: () => modalSettings.visible = true,
  close: () => modalSettings.visible = false,
  handleOk: () => {
    modalSettings.visible = false
    message.success("保存成功")
  },

  fields: [
    {key: "packagePath", label: "包路径", value: "com.qing.erp.system"},
    {key: "comments", label: "类介绍", value: "这是自动生成的类"},
    {key: "author", label: "作者", value: "halfRain"},
    {key: "email", label: "邮箱", value: "2042136767@qq.com"},
  ],
  getFieldEntity: () => {
    let r = new Map()
    modalSettings.fields.forEach(v => {
      r.set(v.key, v.value)
    })
    return r;
  }
})

// 生成代码
const code = reactive({
  list: [],

  getZip: function () {
    let files = []
    console.log(code.list)
    code.list.forEach(v => {
      files.push({ name: v.fileName + "." + v.language, folder: v.fileType, content: v.code })
    })
    message.info("正在生成中")
    zipUtil.zipFiles(files, { compression: 'DEFLATE' }).then(content => {
      message.success("生成成功")
      // blob to url
      const url = URL.createObjectURL(content)
      // 创建a标签
      const link = document.createElement('a')
      link.href = url
      link.download = `${model.currentTable.name}.zip`
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
      // 撤销 URL 对象,释放资源
      URL.revokeObjectURL(url)
    }).catch(() => {
      message.error("生成失败")
    })
  },

  getZipAll: () => {
    let map = modalSettings.getFieldEntity()
    genApi.getTableAllCode(
        where.current,
        map.get("packagePath"), map.get("comments"),
        map.get("author"), map.get("email")
    ).then(({data}) => {
      message.info("正在生成中")

      let m = data.data.map
      console.log(m)

      let files = []
      for (const key in m) {
        const array = m[key]
        for (const item in array) {
          let v = array[item]
          files.push({ name: v.fileName + "." + v.language, folder: v.fileType, content: v.code })
        }
      }

      zipUtil.zipFiles(files, { compression: 'DEFLATE' }).then(content => {
        message.success("生成成功")
        // blob to url
        const url = URL.createObjectURL(content)
        // 创建a标签
        const link = document.createElement('a')
        link.href = url
        link.download = `${model.currentTable.name}.zip`
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
        // 撤销 URL 对象,释放资源
        URL.revokeObjectURL(url)
      }).catch(() => {
        message.error("生成失败")
      })
    })
  }
})

// 表单
interface IForm {
  tableName: string;
  describe: string;
}
const form = reactive<IForm>({
  tableName: "",
  describe: "",
})

const formCustomer = reactive({
  fields: [],

  fieldName: "",
  fieldType: "",
  fieldTypeList: [
      'BIT', 'BOOL', 'BOOLEAN', 'TINYINT', 'SMALLINT', 'MEDIUMINT', 'INT', 'INTEGER',
    'BIGINT', 'FLOAT', 'DOUBLE', 'DECIMAL', 'DATE', 'DATETIME', 'TIMESTAMP', 'TIME',
    'YEAR', 'CHAR', 'VARCHAR', 'TINYTEXT', 'TEXT', 'MEDIUMTEXT', 'LONGTEXT',
    'BINARY', 'VARBINARY', 'TINYBLOB', 'BLOB', 'MEDIUMBLOB', 'LONGBLOB',
    'ENUM', 'SET'
  ],
  onAddDomain: () => {
    if (formCustomer.fieldName.length === 0) {
      message.error("未填写字段名称")
      return;
    }
    if (formCustomer.fieldType.length === 0) {
      message.error("未选择字段类型")
      return;
    }
    let isExist = formCustomer.fields.some((field) => field.key === formCustomer.fieldName);
    if (isExist) {
      message.error("出现重复的key")
      return
    }
    message.success("添加列成功")

    let l = formCustomer.fields
    l.push(
        { key: formCustomer.fieldName, value: formCustomer.fieldType, label: formCustomer.fieldName }
    )
    formCustomer.fields = l

  }
})

// 需要重复用到的api
let a = {
  loadTable: (databaseName) => {
    genApi.getTableMetaData(databaseName).then(({data}) => {
      table.datasource = data.data.list
    })
  }
}

// 加载
onMounted(() => {
  genApi.getDatabases().then(({data}) => {
    where.list = data.data.list
    where.current = where.list[0]
  }).then(() => {
    a.loadTable(where.current)
  })
})

</script>


<style scoped>
</style>
