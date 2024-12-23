<script setup>

import {ref} from 'vue'
import {useRouter} from 'vue-router'
import axios from 'axios'

let loginForm = ref({username:"", password:""})
let router = useRouter()
let passwordType = ref("password")
let loading = ref(false)
let         loginRules = ref({
      username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
      password: [{ required: true, trigger: 'blur', message: '请输入密码' },
        { required: true, min: 6, max: 20, trigger: 'blur', message: '密码长度应该为6-16之间' }],
      code: [{ required: true, trigger: 'blur', message: '请输入验证码' },
        { required: true, min: 5, max: 5, trigger: 'blur', message: '验证码长度不正确' }
      ]
    })
let showPwd = () => {
  if (passwordType.value === 'password') {
    passwordType.value = ''
  } else {
    passwordType.value = 'password'
  }
  this.$nextTick(() => {
    this.$refs.password.focus()
  })
}

let handleLogin = () => {
  axios.post("http://aien-china.com/api/user/login", {
    account:loginForm.value.username,
    password: loginForm.value.password
  }).then(resp => {
    if(resp.data.code === 500){
      alert(resp.data.message)
    }
    if(resp.data.code === 200){
      console.log("login===>res", resp)
      localStorage.setItem("id", resp.data.data.id)
      localStorage.setItem("satoken",resp.data.data.satoken)
      router.push("/menu")
      console.log(localStorage.getItem("id")
      )
    }
  })


  console.log(localStorage.getItem("satoken"))
  const toeken = localStorage.getItem("satoken")
  }
const instance = axios.create({
  baseURL: 'http://aien-china.com/api/',
  timeout: 1000,
  headers: {'satoken': toeken}
});
</script>

<template>
  <div class="common-layout">
    <el-container class="container">
      <!-- <el-header>Header</el-header> -->
      <el-container>
        <el-aside style="width:70%;">
          <!-- <img :src="img/undraw_favorite_sb7f.svg" /> -->
          <el-image :src="'../../img/undraw_open_note_6nva.svg'" :fit="scale-down" style="margin-top: 10%;" />
        </el-aside>
        <el-main>

          <el-form  :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

            <div class="title-container">
              <h3 class="title">OID Manager system</h3>
            </div>

            <el-form-item >
    <span class="svg-container">
      <svg-icon icon-class="user" />
    </span>
              <el-input
                  v-model="loginForm.username"
                  placeholder="请输入用户名"
                  name="username"
                  type="text"
                  tabindex="1"
                  auto-complete="on"
              />
            </el-form-item>

            <el-form-item >
    <span class="svg-container">
      <svg-icon icon-class="password" />
    </span>
              <el-input
                  :key="passwordType"
                  ref="password"
                  v-model="loginForm.password"
                  :type="passwordType"
                  placeholder="请输入密码"
                  name="password"
                  tabindex="2"
                  auto-complete="on"
              />
              <span class="show-pwd" @click="showPwd">
      <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
    </span>
            </el-form-item>
            <div class="captcha-container">
              <el-form-item prop="code" class="code-input">
    <span class="svg-container">
      <svg-icon icon-class="code" />
    </span>
<!--                <el-input-->
<!--                    ref="code"-->
<!--                    v-model="loginForm.code"-->
<!--                    placeholder="请输入验证码"-->
<!--                    name="code"-->
<!--                    type="text"-->
<!--                    tabindex="3"-->
<!--                    auto-complete="on"-->
<!--                    @keyup.enter.native="handleLogin"-->
<!--                />-->
              </el-form-item>
<!--              <div @click="refreshImage" >-->
<!--                <el-image-->
<!--                    style="width: 160px; height:50px; margin:0px 20px 20px 50px;"-->
<!--                ></el-image></div>-->
            </div>
            <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click="handleLogin">登录</el-button>
          </el-form>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.container{
  height: 100%;
}
/* .el-header, */
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}
/* .el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 100%;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
} */
</style>
