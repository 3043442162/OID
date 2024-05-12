<template>
    <el-form v-if:="flag" :model="form" label-width="auto" ref="form" style="max-width: 600px">
        <el-form-item label="当前OID">
            <!--                <el-input v-model="form.number" autosize/>-->
            <el-input-number v-model="form.oid" :min="minValue" :max="2147483647" />
        </el-form-item>
        <el-form-item label="描述信息">
            <el-input v-model="form.describe" autosize/>
        </el-form-item>
        <el-form-item label="ip地址">
            <el-input v-model="form.ipAddress" autosize/>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit('form')">提交</el-button>
        </el-form-item>
    </el-form>
</template>
<script setup>
    import {ref} from 'vue'
    const flag = true
    const form = ref({
        id:null,
        oid: 0,
        describe: '',
        ipAddress: '',
    })
    const onSubmit = (str)=>{}
    const minValue = 1
</script>
