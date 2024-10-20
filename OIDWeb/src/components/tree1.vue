<template>
    <div style="height: 1000px; background: antiquewhite">
        <vue3-tree-org
                :data="data"
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
        />
    </div>
</template>
<script setup>
    import {ref,onMounted,watch} from 'vue'
    import {getOIDTree} from '@/apis/OID'
    const hualian = ref({"id":4,"label":"115288:重庆华联众智","children":[]})
    const data = ref({
        "id": 1,
            "label": "1:全球初始OID",
            "children": [
            {
                "id": 2,
                "label": "2:中国顶级OID",
                "children": [
                    {
                        "id":156,
                        "label":"156:中国次级OID",
                        "children":[hualian.value]
                    }
                ]
            }
        ]
    })
    const props = defineProps(["modelValue"])
    const emits = defineEmits(["update:modelValue"])
    /**
     * 初始化
     */
    const init = () => {

    }
    /**
     * 更新值
     */
    const updateDate = () => {

    }
    /**
     * 侦听变化，传给父组件
     */
    watch(()=>props.modelValue, (newModelValue)=>{

    },{
        deep:true
    })
    props.data = data
    const add= (node)=>{
        console.log("add",node)
    }
    const edit = ( node)=>{
        console.log("edit"+node)
    }
    const move =(node)=>{
        console.log("remove"+node)
    }
    const menus = [
        // { name: '复制文本', command: 'copy' },
        { name: '新增节点', command: 'add' },
        { name: '编辑节点', command: 'edit' },
        { name: '删除节点', command: 'delete' },
        { name: '查看下属子节点', command: 'copy'}
    ]
    const query=(node)=>{}
    const getTree = async () => {
        // const res = await getBeannerAPI()
        // console.log(res)
        // bannerList.value = res.result
        const res = await getOIDTree(hualian.value.id, 1,5)
        console.log(res)
        // console.log(res.data.children)
        hualian.value.children = res.data.children

    }
    onMounted(
        () => getTree()
    )

</script>
