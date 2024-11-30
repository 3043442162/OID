<script setup>
import {ref, onMounted, reactive} from 'vue'
    import {getXmlList,deleteXml} from '@/apis/OID'
    import xmlOption from './XmlOptions.vue'
const onReload = ref(true)

    const processDailogRef = ref()
const key = ref(0)
    const tableData = reactive({data:[]})
    const checkDetail =async (line)=>{
        const res = await deleteXml(line)
        console.log(res)
        // console.log(line)
        await updateXmlList()
        // console.log(res)
    }
    const updateXmlList =          async () =>{
        const res = await getXmlList()
        // console.log(res.data)
        tableData.data = res.data
        onReload.value = true
        console.log("==================>",tableData.data)
    }
    const view = (id)=>{
      processDailogRef.value.onChangeVisable(id);
    }
    const c1 = () => {
      processDailogRef.value.onChangeVisable()
    };
    const refreshData = (message)=>{
      console.log(message)
      onReload.value = false
      updateXmlList()
      // location. reload()
    }
    onMounted( () => {
        updateXmlList()
    })
</script>
<template>
    <!-- 新增xml模板-->
    <el-container v-if="onReload">
        <el-header>xml管理系统</el-header>
        <el-container>
<!--            <el-aside width="200px">-->

<!--            </el-aside>-->
          <el-header>
            <el-button>
              <el-button @click="c1">新增xml</el-button>
              <teleport to="body">
                <xmlOption
                    :data="syncProcess"
                    ref="processDailogRef"
                @close-component="refreshData"
                >
                </xmlOption>
              </teleport>
            </el-button>
          </el-header>
            <el-main>
<!--              <teleport to="body">-->
<!--                <xmlOption-->
<!--                    :data="syncProcess"-->
<!--                    ref="processDailogRef">-->

<!--                </xmlOption>-->
<!--              </teleport>-->
                <el-table :data="tableData.data" stripe style="width: 100%" :key="key">
<!--                    <el-table-column prop="date" label="Date" width="180" />-->
<!--                    <el-table-column prop="name" label="Name" width="180" />-->
                    <el-table-column prop="xmlName" label="xml模板名" />
                    <el-table-column label="操作" align="center" min-width="100">
                        <template v-slot="scope">
                            <el-button  @click="checkDetail(scope.row.id)">删除xml</el-button>
                          　<el-button  @click="view(scope.row.id)">查看xml</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-main>
        </el-container>
    </el-container>


</template>
<style>
    .demo-form-inline .el-input {
        --el-input-width: 50px;
    }

    /*.demo-form-inline .el-select {*/
    /*    --el-select-width: 220px;*/
    /*}*/
</style>
