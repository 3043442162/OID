<script setup>
import {ref} from "vue";
import {uploadXml,getXmlList} from "@/apis/OID";


const input = ref("")
const dialogFormVisible = ref(false)
const temp = {
  key: '',
  value: '',
}
const onChangeVisable = ()=>{
  dialogFormVisible.value = true;
}
const formInline = ref([temp])
// const fileName=ref()
const onSubmit = () => {
  console.log(formInline.value)
  uploadXml(formInline.value, input.value)
  getXmlList()
  input.value = null
  formInline.value = []
  dialogFormVisible.value = true
  // console.log(formInline.value)
}
const addForm = ()=>{
  formInline.value.push({key:"", value:""})
}
defineExpose({ onChangeVisable });
</script>

<template>
  <el-dialog
      v-model="dialogFormVisible"
      :close-on-click-modal="false"
      title="OID"
  >
  <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <!--        <el-form-item label="OID模版名">-->
    <!--            <el-input v-model="fileName" placeholder="OID模版名" 模板名 />-->
    <!--        </el-form-item>-->
    <el-form-item>
      <el-input v-model="input" style="width: 150px" placeholder="请输入xml名字" />
    </el-form-item>
    <ul v-for="item in formInline">
      <!--            {{ item.message }}-->
      <el-form-item label="key">
        <el-input v-model="item.key" placeholder="key" key />
      </el-form-item>
      <el-form-item label="value">
        <el-input v-model="item.value" placeholder="value"  />
      </el-form-item>
    </ul>
    <el-form-item>
      <el-button type="primary" @click="addForm">添加kv对</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">提交</el-button>
    </el-form-item>
  </el-form>
  </el-dialog>
</template>

<style scoped>

</style>
