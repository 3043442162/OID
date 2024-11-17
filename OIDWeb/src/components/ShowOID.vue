<script setup>
import {onMounted, ref} from 'vue'
import {queryOidInformation} from "@/apis/OID";
// const props = defineProps(["data"])
const showData = ref({
  describe:"",
  preOID:"",
  xmlContent:"",
  xmlName:""
})



const dialogFormVisible = ref(false);
const onChangeVisable = (id) => {
  queryOidInformation(id).then(
      res=> {
        // Object.assign(showData.value , res.data)
        showData.value.describe = res.data.describe
        showData.value.preOID = res.data.preOID
        showData.value.xmlName = res.data.xmlName
        showData.value.xmlContent = res.data.xmlContent
        console.log(showData.value)
      }
  )
  dialogFormVisible.value = true;
  console.log(showData.value)
};

defineExpose({ onChangeVisable });

</script>

<template>
  <el-dialog
      v-model="dialogFormVisible"
      :close-on-click-modal="false"
      title="OID"
  >
    <el-descriptions title="OID" :column="3"  border>
      <el-descriptions-item >{{showData.preOID}}</el-descriptions-item>
    </el-descriptions>

    <el-descriptions title="OID 描述">
      <el-descriptions-item >{{showData.describe}}</el-descriptions-item>
    </el-descriptions>

    <el-descriptions title="OID 模板名">
      <el-descriptions-item >{{showData.xmlName}}</el-descriptions-item>
    </el-descriptions>

    <el-descriptions title="OID xml文件内容">
      <el-descriptions-item >
        <el-text class="mx-1" type="danger">
        {{showData.xmlContent}}
        </el-text>
      </el-descriptions-item>

    </el-descriptions>
  </el-dialog>

</template>

<style scoped>

</style>
