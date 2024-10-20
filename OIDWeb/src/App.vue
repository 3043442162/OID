<script setup>
    import {ref} from 'vue'
    // const flag = ref(true)
    const flag = ref("/tree")

    const changeToTree = ()=>{
        flag.value = "/tree"
    }
    const changeToXml = ()=>{
        flag.value = "/xml"
    }
</script>
<template>
<!--    <el-button type="primary" @click="changeToXml">xml功能</el-button>-->
<!--    <el-button type="primary" @click="changeToTree">树形功能</el-button>-->


    <RouterLink to="/xml">
        <el-button type="primary">xml功能</el-button>
    </RouterLink>
    <RouterLink to="/tree" >
        <el-button type="primary">树形功能</el-button>
    </RouterLink>
    <RouterView />
</template>
