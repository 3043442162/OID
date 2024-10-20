<script setup>
    import {ref, reactive, onMounted} from 'vue'
    import {uploadXml,getXmlList,deleteXml} from '../apis/OID'
    const input = ref("")
    const temp = {
        key: '',
        value: '',
    }
    const formInline = ref([temp])
    // const fileName=ref()
    const onSubmit = () => {
        console.log(formInline.value)
        uploadXml(formInline.value, input.value)
        updateXmlList()
        input.value = null
        formInline.value = []
        // console.log(formInline.value)
    }
    const addForm = ()=>{
        formInline.value.push({key:"", value:""})
    }

    // const tableData = [
    //     {   id: "",
    //         xmlName: 'test xml Name',
    //     }]
    const tableData = ref([])

    const checkDetail =async (line)=>{
        const res = await deleteXml(line)
        console.log(res)
        // console.log(line)
        await updateXmlList()
        // console.log(res)
    }
    const updateXmlList =          async () =>{
        const res = await getXmlList()
        console.log(res.data)
        tableData.value = res.data
        console.log(tableData.value)
    }
    // const deleteXml = async (xmlId)=>{
    //     const res = await deleteXml(xmlId)
    //     console.log(res)
    //
    // }
    onMounted( () => {
        updateXmlList()
    }
    )
</script>
<template>
    <!-- 新增xml模板-->
    <el-container>
        <el-header>xml管理系统</el-header>
        <el-container>
            <el-aside width="200px">
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
                            <el-input v-model="item.value" placeholder="value" value />
                        </el-form-item>
                    </ul>
                    <el-form-item>
                        <el-button type="primary" @click="addForm">添加kv对</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">提交</el-button>
                    </el-form-item>
                </el-form>
            </el-aside>
            <el-main>
                <el-table :data="tableData" stripe style="width: 100%">
<!--                    <el-table-column prop="date" label="Date" width="180" />-->
<!--                    <el-table-column prop="name" label="Name" width="180" />-->
                    <el-table-column prop="xmlName" label="xml模板名" />
                    <el-table-column label="操作" align="center" min-width="100">
                        <template v-slot="scope">
                            　<el-button type="text" @click="checkDetail(scope.row.id)">删除xml</el-button>
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
