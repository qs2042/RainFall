<template>
  <template v-if="form.dynamicValidateForm.domains.length === 0">
    <error-view />
  </template>
  <a-form v-else ref="formRef" name="dynamic_form_item"
          :model="form.dynamicValidateForm" v-bind="form.itemLayoutWithOutLabel"
  >
    <!-- 动态表单项 -->
    <!--
     key    唯一ID
     v-bind 布局
     label  标签名称
     name   ...
     rules  规则
     -->
    <a-form-item v-for="(domain, index) in form.dynamicValidateForm.domains" :key="domain.key"
                 v-bind="form.itemLayout"
                 :label="domain.label"
                 :name="['domains', index, 'value']"
                 :rules="{ required: true, message: `${domain.key} 不能为空`, trigger: 'change', }"
    >
      <!-- 输入框 -->
      <a-input v-model:value="domain.value" :placeholder="`请输入 ${domain.key} `" />
      <!-- 删除 -->
      <!--
          v-if="form.dynamicValidateForm.domains.length > 1"
          :disabled="form.dynamicValidateForm.domains.length === 1"
       -->
      <MinusCircleOutlined
          v-if="domain.value.length < 1 && isRemove"
          class="dynamic-delete-button"
          @click="form.onRemoveDomain(domain)"
      />
    </a-form-item>

    <!-- 添加 -->
    <a-form-item v-if="isAdd" v-bind="form.itemLayoutWithOutLabel">
      <a-space>
        <a-input type="text" v-model:value="form.fieldName"></a-input>
        <a-button type="dashed" @click="form.onAddDomain">
          <PlusOutlined/>
          添加字段
        </a-button>
      </a-space>
    </a-form-item>

    <!-- 按钮 -->
    <a-form-item v-if="isSubmit" v-bind="form.itemLayoutWithOutLabel">
      <a-button type="primary" html-type="submit" @click="form.onSubmit">Submit</a-button>
      <a-button style="margin-left: 10px" @click="form.onReset">Reset</a-button>
    </a-form-item>
  </a-form>

</template>

<script lang="ts" setup>
// vue
import {reactive, ref, defineProps, PropType, toRefs, defineEmits, watch} from 'vue';

// antd
import {MinusCircleOutlined, PlusOutlined} from '@ant-design/icons-vue';
import type {FormInstance} from 'ant-design-vue';

// view
import ErrorView from "@/views/meta/ErrorView.vue";

const emits = defineEmits(['update']);

interface IDomain {
  value: string;
  key: string;
  label: string;
}

const props = defineProps({
  isSubmit: {type: Boolean, default: () => false},
  isAdd: {type: Boolean, default: () => false},
  isRemove: {type: Boolean, default: () => false},

  fields: {type: Array as PropType<IDomain[]>, default: () => []}
})

const { fields } = toRefs(props)

watch(fields.value, (newValue, oldValue) => {
  form.dynamicValidateForm.domains = fields.value
})

const formRef = ref<FormInstance>();
const form = reactive({
  itemLayout: {
    labelCol: {
      xs: {span: 24},
      sm: {span: 4},
    },
    wrapperCol: {
      xs: {span: 24},
      sm: {span: 20},
    },
  },
  itemLayoutWithOutLabel: {
    wrapperCol: {
      xs: {span: 24, offset: 0},
      sm: {span: 20, offset: 4},
    },
  },
  dynamicValidateForm: {
    domains: [] as IDomain[],
  },
  fieldName: "",

  onSubmit: () => {
    formRef.value
        .validate()
        .then(() => {
          console.log('values', form.dynamicValidateForm.domains);
        })
        .catch(error => {
          console.log('error', error);
        });
  },
  onReset: () => {
    formRef.value.resetFields();
  },
  onRemoveDomain: (item: IDomain) => {
    let index = form.dynamicValidateForm.domains.indexOf(item);
    if (index !== -1) {
      form.dynamicValidateForm.domains.splice(index, 1);
    }
  },
  onAddDomain: () => {
    // 判断字段是否为空
    let k = form.fieldName.length === 0 ? Date.now().toString() : form.fieldName
    // 判断字段是否重复
    form.dynamicValidateForm.domains.push(<IDomain>{
      value: '',
      key: k,
    });
  }
})

form.dynamicValidateForm.domains.push(...fields.value)
</script>

<style scoped>

</style>
