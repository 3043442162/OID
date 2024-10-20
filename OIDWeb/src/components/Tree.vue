<script setup>
import {ref,onMounted,watch,reactive} from 'vue'
import {getOIDTree,deleteOID,queryOIDMax,registerOID,updateOID,queryFatherMaxOID,getXmlList} from '@/apis/OID';
// import table from './components/table.vue'
const flag = ref(false)
const originOID = ref({"id":1,"label":"1:初始OID","children":[]})
// const data = ref([{}])
const arr = ref([1,2,3,4])
const minValue = ref()
const selectValue = ref('')
const options = ref([
    {
        value: 'node1', // 业务上的值
        label: '展示node1', // label是展示的值
    },
    // {
    //     value: 'Option2',
    //     label: 'Option2',
    //     disabled: true,
    // }
])
let fatherId = 0
// 标记是修改行为还是新增行为
let sign = true
let operationNode=null

const add= (node)=>{
    console.log("add",node)
    flag.value=true
    queryOIDMax(node.id).then((response)=>{
        minValue.value = response.data+1
    })
    // console.log("minValue",minValue.value)
    fatherId=node.id
    operationNode = node
}

const edit = ( node)=>{
    // console.log("edit",getFatherMaxValue(node.id))
    // minValue.value =
    queryFatherMaxOID(node.id).then((response)=>{
        // console.log(response.data)
        minValue.value = response.data+1
    })
        // console.log("minvalue",)
    // console.log("minValue",getMinValue(node.id))
    // console.log("minValue",minValue.value)
    flag.value=true
    fatherId=node.id
    operationNode = node
    sign=false
}
const move =(node)=>{
    console.log("remove",node)
    // flag.value=true
    let father = originOID.value
    // 找到并删除节点
    for (let i = 0; i < father.children.length; i++) {
        removeNode(father.children[i], node.id)
    }
    deleteOID(node.id)
}
const query=(node)=>{
    getOIDTree(node.id, 1, 5).then( (response)=>{
        console.log(response)
        node.children = response.data.children
    })
}

const removeNode = (node, id)=>{
    if(node === null || node.children === null ) return;
    for(let i=0;i<node.children.length;i++){
        // console.log(id,node.children[i])
        if(node.children[i].id === id){
            // 只有一个节点
            if (node.children.length === 1) {node.children = []}
            // 有多个节点
            else {
                node.children = node.children.slice(0,i).concat(node.children.slice(i+1,node.children.length))
            }
            return;
        }
        removeNode(node.children[i],id)
    }
}
const copy = (node)=>{
    // console.log("show"+node)
    flag.value=true
}
const menus = [
    // { name: '复制文本', command: 'copy' },
    { name: '新增子节点', command: 'add' },
    { name: '编辑节点', command: 'edit' },
    { name: '删除节点', command: 'delete' },
    { name: '查看下属子节点', command: 'copy'}
]
const getTree = async () => {
    // const res = await getOIDTree(originOID.value.id, 1,5)
    // // console.log(res)
    // originOID.value.children = res.data.children
    let tempOID = originOID.value
    while(tempOID.id <= 4){
        const res = await getOIDTree(tempOID.id, 1,15)
        tempOID.children = res.data.children
        tempOID = tempOID.children[0]
    }
}

const form = reactive({
    id:null,
    oid: 0,
    describe: '',
    ipAddress: '',
})

const addFormRef = ref();


const onSubmit = ()=>{
    // sign为true时新增，为false时修改
    console.log("form=",form)
    if(sign){
        // 注册
        const res =  registerOID(JSON.stringify(form), fatherId)
        console.log(res.data)
        form.id = res.data
        operationNode.children.append(form.map(item=>({
            id:item.id,
            label:item.oid+""+item.describee,
            children:[]
            })
        ))
    }else{
        form.id=fatherId
        const res = updateOID(JSON.stringify(form))
        operationNode.id = form.id
        operationNode.label=form.oid+":"+form.describe
    }
    flag.value=false
    // console.log("reactive",addFormRef.value)
}

onMounted(
    () => {
        getTree()
        // options.value = getXmlList().data.map( item=> ({
        //         value:item.id,
        //         label:item.xmlName
        //     })
        // )
    }
)
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

<template>
    <div class="common-layout">
        <el-container>
            <el-header>OID管理</el-header>
            <el-container>
                <el-aside width="30%" >
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
                            <el-select v-model="selectValue" placeholder="Select" style="width: 240px">
                                <el-option
                                        v-for="item in options"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                />
                            </el-select>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="onSubmit('form')">提交</el-button>
                        </el-form-item>
                    </el-form>
<!--                    <RouterLink to="/about">-->
<!--                        <el-button type="primary">xml功能</el-button>-->
<!--                    </RouterLink>-->
<!--                    <RouterView></RouterView>-->
                </el-aside>
                <el-main>
<!--                    <Tree v-model="data"/>-->
                    <div style="height: 1000px; background: antiquewhite">
                        <vue3-tree-org
                                :default-expand-keys="arr"
                                :data="originOID"
                                :center="true"
                                :collapsable="true"
                                :scalable="true"
                                :toolBar = false
                                :node-add="add"
                                :node-draggable="false"
                                :node-edit="edit"
                                :node-delete="move"
                                :define-menus="menus"
                                :node-copy="query"
                                :default-expand-level="true"
                        />
                    </div>
                </el-main>
            </el-container>
        </el-container>
    </div>
<!--<Tree />-->
</template>

<style>
/*@import './assets/base.css';*/

/*#app{*/
/*    display: flex;*/
/*}*/
/*.demo-form-inline .el-input {*/
/*    --el-input-width: 220px;*/
/*}*/

/*.demo-form-inline .el-select {*/
/*    --el-select-width: 220px;*/
/*}*/

/*header {*/
/*  line-height: 1.5;*/
/*}*/

/*.logo {*/
/*  display: block;*/
/*  margin: 0 auto 2rem;*/
/*}*/

/*a,*/
/*.green {*/
/*  text-decoration: none;*/
/*  color: hsla(160, 100%, 37%, 1);*/
/*  transition: 0.4s;*/
/*}*/

/*@media (hover: hover) {*/
/*  a:hover {*/
/*    background-color: hsla(160, 100%, 37%, 0.2);*/
/*  }*/
/*}*/

/*@media (min-width: 1024px) {*/
/*  body {*/
/*    display: flex;*/
/*    place-items: center;*/
/*  }*/

/*  #app {*/
/*    display: grid;*/
/*    grid-template-columns: 1fr 1fr;*/
/*    padding: 0 2rem;*/
/*  }*/

/*  header {*/
/*    display: flex;*/
/*    place-items: center;*/
/*    padding-right: calc(var(--section-gap) / 2);*/
/*  }*/

/*  header .wrapper {*/
/*    display: flex;*/
/*    place-items: flex-start;*/
/*    flex-wrap: wrap;*/
/*  }*/

/*  .logo {*/
/*    margin: 0 2rem 0 0;*/
/*  }*/
/*}*/
</style>
