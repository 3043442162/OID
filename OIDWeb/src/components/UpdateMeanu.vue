<template>
  <el-dialog
      v-model="flag"
      :close-on-click-modal="false"
      title="updateOID"
  >
    <el-form v-if="flag" ref="addFormRef" :model="form" label-width="auto" style="max-width: 600px">
      <el-form-item label="当前OID">
        <!--                <el-input v-model="form.number" autosize/>-->
        <el-input-number v-model="form.oid" :min="minValue" :max="2147483647" />
      </el-form-item>
      <el-form-item label="描述信息">
        <el-input v-model="form.describe" autosize/>
      </el-form-item>
      <el-form-item label="xml模板名">
        <!--                            <el-input v-model="form.ipAddress" autosize/>-->
        <!--                            <el-cascader v-model="value" :options="options" @change="handleChange" />-->
        <el-select v-model="selectValue" placeholder="Select" style="width: 240px" @change="selectXml">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script setup>
import {onMounted, reactive, ref} from 'vue'
import {getXmlList, queryFatherMaxOID, queryOIDMax, registerOID, updateOID} from "@/apis/OID";
    const flag = ref(false)
    const sssssss = ref(false)
    const nodeId = ref(null)
    const fatherId = ref(null)
    const minValue = ref()
    const addFormRef = ref()
    const selectValue = ref('')
    const form = reactive({
      id:null,
      oid: 0,
      describe: '',
      oidXml: '',
    })
const options = ref([
  {
    value: 'node1', // 业务上的值
    label: '没数据', // label是展示的值
  }
])
    const onSubmit = ()=>{
      // 如果sign为真，则是新增，否则是修改
      if(sssssss.value) {
        registerOID(JSON.stringify(form), fatherId)
      }else{
        form.value.id = nodeId.value
        updateOID(JSON.stringify(form))
      }
      // console.log(res.data, "resData==============>???????????????")

    }
    let selectXml = (data) =>{
      // console.log(data + "data--------------------------")
      form.oidXml = data
    }
    // 父节点id和id只需要传一个
    const  arouseMenu =  (id,sign) => {
      flag.value = true
      // 如果sign为ture说明是新增，否则就是修改

      if(sign){
        fatherId.value =id
        queryOIDMax(id).then((response)=>{
          minValue.value = response.data+1
        })
      }else{
        nodeId.value = id
        queryFatherMaxOID(id).then((response)=>{
          console.log("=================",response)
          minValue.value = response.data+1
        })
      }
      sssssss.value = sign
    }

    defineExpose({ arouseMenu });
onMounted(
    async () => {
      const res = await getXmlList()
      // console.log(res.data)
      options.value = res.data.map( item=> ({
            value:item.id,
            label:item.xmlName
          })
      )
    }
)
</script>
