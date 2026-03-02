<template>
  <div class="login-page">
    <div class="login-box">
      <h1>Asian Service Inc</h1>
      <h2>{{ isCN ? '注册' : 'Register' }}</h2>
      <el-form :model="form">
        <el-form-item><el-input v-model="form.name" :placeholder="isCN ? '姓名' : 'Name'" /></el-form-item>
        <el-form-item><el-input v-model="form.phone" :placeholder="isCN ? '电话' : 'Phone'" /></el-form-item>
        <el-form-item><el-input v-model="form.email" :placeholder="isCN ? '邮箱' : 'Email'" /></el-form-item>
        <el-form-item><el-input v-model="form.password" type="password" :placeholder="isCN ? '密码' : 'Password'" /></el-form-item>
        <el-form-item><el-button type="primary" @click="register" style="width: 100%">{{ isCN ? '注册' : 'Register' }}</el-button></el-form-item>
      </el-form>
      <p>{{ isCN ? '已有账号？' : 'Has account?' }} <router-link to="/login">{{ isCN ? '登录' : 'Login' }}</router-link></p>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue'
import { useRouter } from 'vue-router'

const isCN = inject('isCN')
const router = useRouter()
const form = ref({ name: '', phone: '', email: '', password: '' })

const register = async () => {
  const res = await fetch('/api/users/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(form.value)
  })
  const data = await res.json()
  if (data.success) {
    router.push('/login')
  }
}
</script>

<style scoped>
.login-page { min-height: 100vh; display: flex; align-items: center; justify-content: center; background: linear-gradient(135deg, #1a1a2e, #16213e); padding: 20px; }
.login-box { background: white; padding: 40px; border-radius: 12px; width: 100%; max-width: 400px; text-align: center; }
.login-box h1 { color: #1a1a2e; margin-bottom: 10px; }
.login-box h2 { color: #666; margin-bottom: 30px; font-size: 16px; }
</style>
