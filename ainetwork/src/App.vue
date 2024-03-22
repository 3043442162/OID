<template>
    <div style="height: 1000px; background: antiquewhite">
        <vue3-tree-org
                :data="data"
                :center="true"
                :collapsable="true"
                :scalable="true"
                @on-node-dblclick=""
        />
    </div>


<!--    <div>-->
<!--        <listDaemo />-->
<!--    </div>-->
</template>

<script>
    // import listDaemo from './ListDaemo.vue'
    export default {
        // name: "test",
        // components: listDaemo,
        data() {
            return {
                data: {
                    id : 0, label: "XXX科技有限公司",
                    children: [
                        {id: 3, pid:0, label: "产品研发部",
                            children: [
                                {id: 5,pid:3, label: "研发-前端"},
                                {id: 6,pid:3, label: "研发-后端"},
                                {id: 9,pid:3, label: "UI设计"},
                                {id: 10,pid:3, label: "产品经理"}
                            ]
                        },
                        {id: 4, pid:0, label: "销售部",
                            children: [
                                {id: 7,pid:4, label: "销售一部"},
                                {id: 8,pid:4, label: "销售二部"}
                            ]
                        },
                        {id: 4, pid:0, label: "财务部"},
                        // {id: 9, label: "HR人事"}
                    ]
                },
                style: {
                    background: "#f1f",
                    color: "#5e6d82",
                }
            }
        },
        methods(){
            function myClick(){
                // 发送请求
            }
        }
        // methods(){
        //     indexs:function(){
        //         this.$http.get("http://localhost:7379/OID/userOwner?page=1&pageSize=1&oid=1").then((response) => {
        //             console.log(response.data())
        //         })
        //     },
        //     create(){
        //         this.indexs();
        //     }
        // }
    }
</script>
<style>

</style>
