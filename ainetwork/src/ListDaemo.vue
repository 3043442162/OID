<template>
    <el-card>
        <el-row :gutter="20"
        ><!-- gutter是指定每个分栏的间隔 -->
            <!-- 分栏一共占24格，给搜索框7格，添加按钮4格 -->
            <el-col :span="7">
                <!-- 搜索与添加区域 -->
                <el-input placeholder="请输入内容">
                    <el-button slot="append" icon="el-icon-search"></el-button>
                </el-input>
            </el-col>
            <el-col :span="4">
                <el-button type="primary">添加用户</el-button>
            </el-col>
        </el-row>
        <el-table :data="userList" border stripe><!-- 带边框、斑马纹 -->
            <el-table-column label="姓名" prop="username"></el-table-column>
            <el-table-column label="邮箱" prop="email"></el-table-column>
            <el-table-column label="电话" prop="mobile"></el-table-column>
            <el-table-column label="角色" prop="role_name"></el-table-column>
            <el-table-column label="状态" prop="mg_state"></el-table-column>
            <el-table-column label="操作"></el-table-column>
        </el-table>
    </el-card>
</template>

<script>
    export default {
        data(){
            return {
                queryInfo: {
                    query: "",
                    pagenum: 1,
                    pagesize: 2
                },
                userList: [],
                total: 0
            }
        },
        created() {
            this.getUserList();
        },
        methods:{
            async getUserList(){
                const {data: res} = await this.$http.get("users", {
                    params: this.queryInfo,
                });
                console.log(res)
                if(res.meta.status != 200){
                    return this.$message.error("获取用户列表失败")
                }
                console.log(res)
                this.userList = res.data.users;
                this.total = res.data.total;
            }
        }
    }
</script>

<style>

</style>
