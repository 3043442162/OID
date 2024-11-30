<template>
      <el-container>
        <el-header>
<!--          <RouterLink to="/xml">-->
            <el-button type="primary" @click="setFlag()">xml功能</el-button>
<!--          </RouterLink>-->
<!--          <RouterLink to="/tree" >-->
            <el-button type="primary" @click="setFlag()">树形功能</el-button>
<!--          </RouterLink>-->
        </el-header>
        <el-main>
<!--          <RouterView />-->
          <tree v-if="flag" />
          <xml v-if="!flag"/>
        </el-main>
      </el-container>
</template>
<script setup>
import {ref} from 'vue';
import Tree from "@/components/Tree.vue";
import Xml from "@/components/XML.vue"


const flag = ref(true)
const setFlag = ()=>{
  flag.value = !flag.value
}
const xmlKey = ref(0)
const treeKey = ref(0)
// const flag = ref("/tree")

// const changeToTree = ()=>{
//   flag.value = "/tree"
// }
// const changeToXml = ()=>{
//   flag.value = "/xml"

// }
</script>
