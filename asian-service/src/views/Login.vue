<template>
  <div class="login-page">
    <div class="login-box">
      <h1>Asian Service Inc</h1>
      <h2>{{ isCN ? '登录' : 'Login' }} / {{ isCN ? '注册' : 'Register' }}</h2>
      <el-form :model="form">
        <el-form-item>
          <el-input v-model="form.phone" :placeholder="isCN ? '电话' : 'Phone'" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" :placeholder="isCN ? '密码' : 'Password'" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login" style="width: 100%">{{ isCN ? '登录' : 'Login' }}</el-button>
        </el-form-item>
      </el-form>
      <p>{{ isCN ? '没有账号？' : 'No account?' }} <router-link to="/register">{{ isCN ? '注册' : 'Register' }}</router-link></p>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue'
import { useRouter } from 'vue-router'

const isCN = inject('isCN')
const router = useRouter()
const form = ref({ phone: '', password: '' })

const login = async () => {
  const res = await fetch('/api/users/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(form.value)
  })
  const data = await res.json()
  if (data.success) {
    localStorage.setItem('user', JSON.stringify(data))
    if (data.role === 'ADMIN') router.push('/admin/orders')
    else router.push('/dashboard')
  } else {
    alert(isCN.value ? '登录失败' : 'Login failed')
  }
}
</script>

<style scoped>
.login-page { min-height: 100vh; display: flex; align-items: center; justify-content: center; background: linear-gradient(135deg, #1a1a2e, #16213e); padding: 20px; }
.login-box { background: white; padding: 40px; border-radius: 12px; width: 100%; max-width: 400px; text-align: center; }
.login-box h1 { color: #1a1a2e; margin-bottom: 10px; }
.login-box h2 { color: #666; margin-bottom: 30px; font-size: 16px; }
</style>
