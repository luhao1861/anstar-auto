<template>
  <div class="track-page">
    <header class="header">
      <router-link to="/" class="logo">Asian Service Inc<br><span class="cn">华人服务中心</span></router-link>
      <nav class="nav">
        <router-link to="/">{{ isCN ? '首页' : 'Home' }}</router-link>
        <router-link to="/services">{{ isCN ? '服务' : 'Services' }}</router-link>
      </nav>
    </header>

    <div class="track-content">
      <h1>{{ isCN ? '追踪订单' : 'Track Your Order' }}</h1>
      <p class="subtitle">{{ isCN ? '输入订单号查看状态' : 'Enter your order number to check status' }}</p>

      <div class="track-form">
        <el-input v-model="orderNumber" :placeholder="isCN ? '输入订单号 (AS...)' : 'Enter Order Number (AS...)'" size="large" />
        <el-button type="primary" @click="trackOrder" :loading="loading">{{ isCN ? '追踪' : 'Track' }}</el-button>
      </div>

      <div v-if="order" class="order-details">
        <h2>{{ isCN ? '订单' : 'Order' }} #{{ order.orderNumber }}</h2>
        
        <div class="status-timeline">
          <div class="status-item" :class="{ active: ['SUBMITTED','QUOTED','PAID','PROCESSING','COMPLETED','MAILED'].includes(order.status) }">
            <div class="status-dot">1</div>
            <span>{{ isCN ? '已提交' : 'Submitted' }}</span>
          </div>
          <div class="status-item" :class="{ active: ['QUOTED','PAID','PROCESSING','COMPLETED','MAILED'].includes(order.status) }">
            <div class="status-dot">2</div>
            <span>{{ isCN ? '已报价' : 'Quoted' }}</span>
          </div>
          <div class="status-item" :class="{ active: ['PAID','PROCESSING','COMPLETED','MAILED'].includes(order.status) }">
            <div class="status-dot">3</div>
            <span>{{ isCN ? '已付款' : 'Paid' }}</span>
          </div>
          <div class="status-item" :class="{ active: ['PROCESSING','COMPLETED','MAILED'].includes(order.status) }">
            <div class="status-dot">4</div>
            <span>{{ isCN ? '办理中' : 'Processing' }}</span>
          </div>
          <div class="status-item" :class="{ active: ['COMPLETED','MAILED'].includes(order.status) }">
            <div class="status-dot">5</div>
            <span>{{ isCN ? '已完成' : 'Completed' }}</span>
          </div>
          <div class="status-item" :class="{ active: order.status === 'MAILED' }">
            <div class="status-dot">6</div>
            <span>{{ isCN ? '已邮寄' : 'Mailed' }}</span>
          </div>
        </div>

        <el-descriptions :column="2" border>
          <el-descriptions-item :label="isCN ? '服务' : 'Service'">{{ order.serviceType }}</el-descriptions-item>
          <el-descriptions-item :label="isCN ? '状态' : 'Status'">{{ order.status }}</el-descriptions-item>
          <el-descriptions-item :label="isCN ? '价格' : 'Price'" v-if="order.quotedPrice">${{ order.quotedPrice }}</el-descriptions-item>
          <el-descriptions-item :label="isCN ? '支付' : 'Payment'">{{ order.paymentStatus }}</el-descriptions-item>
          <el-descriptions-item :label="isCN ? '交付方式' : 'Delivery'" v-if="order.deliveryMethod">{{ order.deliveryMethod === 'ELECTRONIC' ? (isCN ? '电子版' : 'Electronic') : (isCN ? '邮寄' : 'Mail') }}</el-descriptions-item>
          <el-descriptions-item :label="isCN ? '快递单号' : 'Tracking'" v-if="order.trackingNumber">{{ order.trackingNumber }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue'

const isCN = inject('isCN')
const API = '/api'
const orderNumber = ref('')
const order = ref(null)
const loading = ref(false)

const trackOrder = async () => {
  if (!orderNumber.value) return
  loading.value = true
  try {
    const res = await fetch(`${API}/orders/number/${orderNumber.value}`)
    if (res.ok) {
      order.value = await res.json()
    }
  } catch (e) {}
  loading.value = false
}
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; padding: 20px 50px; background: #1a1a2e; color: white; flex-wrap: wrap; }
.logo { color: white; font-size: 20px; font-weight: bold; text-decoration: none; }
.logo .cn { font-size: 12px; opacity: 0.8; font-weight: normal; }
.nav a { color: white; margin-left: 20px; text-decoration: none; }
.track-content { padding: 60px 20px; max-width: 700px; margin: 0 auto; text-align: center; }
.track-content h1 { color: #1a1a2e; }
.track-form { display: flex; gap: 10px; justify-content: center; margin: 30px 0; flex-wrap: wrap; }
.order-details { background: white; padding: 30px; border-radius: 12px; margin-top: 30px; text-align: left; }
.status-timeline { display: flex; justify-content: space-between; margin: 30px 0; position: relative; flex-wrap: wrap; gap: 10px; }
.status-timeline::before { content: ''; position: absolute; top: 15px; left: 30px; right: 30px; height: 2px; background: #eee; }
.status-item { display: flex; flex-direction: column; align-items: center; position: relative; z-index: 1; }
.status-dot { width: 32px; height: 32px; border-radius: 50%; background: #eee; display: flex; align-items: center; justify-content: center; margin-bottom: 8px; }
.status-item.active .status-dot { background: #ffd700; }

@media (max-width: 768px) {
  .header { padding: 15px 20px; }
  .track-content { padding: 40px 15px; }
}
</style>
