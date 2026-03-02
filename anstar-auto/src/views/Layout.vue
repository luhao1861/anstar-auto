<template>
  <div class="layout-container">
    <!-- 侧边栏 -->
    <div class="sidebar">
      <div class="logo">
        <div class="logo-icon">A</div>
        <div class="logo-text">
          <h2>安星汽车</h2>
          <p>Anstar Auto</p>
        </div>
      </div>
      
      <el-menu
        :default-active="activeMenu"
        class="sidebar-menu"
        background-color="#1a1a2e"
        text-color="#8c92a4"
        active-text-color="#fff"
        :router="false"
      >
        <el-menu-item index="/dashboard" @click="goTo('/dashboard')">
          <span class="menu-icon">📊</span>
          <span>仪表盘</span>
        </el-menu-item>
        <el-menu-item index="/vehicles" @click="goTo('/vehicles')">
          <span class="menu-icon">🚗</span>
          <span>车辆管理</span>
        </el-menu-item>
        <el-menu-item index="/customers" @click="goTo('/customers')">
          <span class="menu-icon">👥</span>
          <span>客户管理</span>
        </el-menu-item>
        <el-menu-item index="/rentals" @click="goTo('/rentals')">
          <span class="menu-icon">📋</span>
          <span>租赁管理</span>
        </el-menu-item>
        <el-menu-item index="/maintenance" @click="goTo('/maintenance')">
          <span class="menu-icon">🔧</span>
          <span>维修保养</span>
        </el-menu-item>
        <el-menu-item index="/stores" @click="goTo('/stores')">
          <span class="menu-icon">🏪</span>
          <span>门店管理</span>
        </el-menu-item>
        <el-menu-item index="/reports" @click="goTo('/reports')">
          <span class="menu-icon">📈</span>
          <span>数据报表</span>
        </el-menu-item>
        <el-menu-item index="/contracts" @click="goTo('/contracts')">
          <span class="menu-icon">📄</span>
          <span>合同管理</span>
        </el-menu-item>
      </el-menu>
      
      <div class="sidebar-footer">
        <div class="version">v1.0.0</div>
      </div>
    </div>
    
    <!-- 主体内容 -->
    <div class="main-content">
      <!-- 头部 -->
      <div class="header">
        <div class="header-left">
          <h3>{{ pageTitle }}</h3>
        </div>
        <div class="header-right">
          <div class="header-time">{{ currentTime }}</div>
          <el-dropdown @command="handleCommand">
            <div class="user-profile">
              <div class="user-avatar">A</div>
              <span class="user-name">管理员</span>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      
      <!-- 内容区 -->
      <div class="content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, onMounted, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const currentTime = ref('')

const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', { 
    year: 'numeric', 
    month: '2-digit', 
    day: '2-digit',
    hour: '2-digit', 
    minute: '2-digit'
  })
}

let timer
onMounted(() => {
  updateTime()
  timer = setInterval(updateTime, 60000)
})

onUnmounted(() => {
  clearInterval(timer)
})

const activeMenu = computed(() => route.path)

const pageTitle = computed(() => {
  const titles = {
    '/dashboard': '仪表盘',
    '/vehicles': '车辆管理',
    '/customers': '客户管理',
    '/rentals': '租赁管理',
    '/maintenance': '维修保养',
    '/stores': '门店管理',
    '/reports': '数据报表',
    '/contracts': '合同管理'
  }
  return titles[route.path] || '安星汽车'
})

const goTo = (path) => {
  router.push(path)
}

const handleCommand = (command) => {
  if (command === 'logout') {
    localStorage.removeItem('token')
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container { display: flex; min-height: 100vh; background: #f0f2f5; }
.sidebar { width: 260px; background: linear-gradient(180deg, #1a1a2e 0%, #111936 100%); position: fixed; height: 100vh; overflow-y: auto; z-index: 100; box-shadow: 4px 0 20px rgba(0,0,0,0.15); }
.logo { padding: 30px 20px; display: flex; align-items: center; gap: 15px; border-bottom: 1px solid rgba(255,255,255,0.08); }
.logo-icon { width: 50px; height: 50px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 12px; display: flex; align-items: center; justify-content: center; font-size: 24px; font-weight: bold; color: white; box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4); }
.logo-text h2 { font-size: 18px; margin: 0; color: white; font-weight: 600; }
.logo-text p { font-size: 12px; color: #8c92a4; margin: 4px 0 0 0; }
.sidebar-menu { border-right: none; background: transparent !important; padding: 20px 0; }
.sidebar-menu .el-menu-item { height: 52px; line-height: 52px; margin: 4px 12px; border-radius: 10px; transition: all 0.3s; }
.sidebar-menu .el-menu-item:hover { background: rgba(255,255,255,0.08) !important; }
.sidebar-menu .el-menu-item.is-active { background: linear-gradient(90deg, rgba(102,126,234,0.3) 0%, rgba(102,126,234,0.1) 100%) !important; }
.sidebar-menu .el-menu-item.is-active::before { content: ''; position: absolute; left: 0; top: 50%; transform: translateY(-50%); width: 4px; height: 24px; background: #667eea; border-radius: 0 4px 4px 0; }
.menu-icon { margin-right: 12px; font-size: 18px; }
.sidebar-footer { position: absolute; bottom: 0; left: 0; right: 0; padding: 20px; text-align: center; border-top: 1px solid rgba(255,255,255,0.08); }
.version { color: #555; font-size: 12px; }
.main-content { margin-left: 260px; flex: 1; min-height: 100vh; }
.header { height: 70px; background: white; display: flex; align-items: center; justify-content: space-between; padding: 0 30px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
.header-left h3 { margin: 0; font-size: 20px; color: #1a1a2e; font-weight: 600; }
.header-right { display: flex; align-items: center; gap: 20px; }
.header-time { color: #8c92a4; font-size: 14px; }
.user-profile { display: flex; align-items: center; gap: 10px; cursor: pointer; padding: 6px 12px; border-radius: 8px; transition: background 0.3s; }
.user-profile:hover { background: #f5f7fa; }
.user-avatar { width: 36px; height: 36px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 10px; display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; font-size: 14px; }
.user-name { color: #1a1a2e; font-weight: 500; }
.content { padding: 24px 30px; }
</style>
