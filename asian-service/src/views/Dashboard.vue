<template>
  <div class="dashboard">
    <header class="header">
      <router-link to="/" class="logo">Asian Service Inc<br><span class="cn">华人服务中心</span></router-link>
      <nav class="nav">
        <router-link to="/">{{ isCN ? '首页' : 'Home' }}</router-link>
        <router-link to="/services">{{ isCN ? '服务' : 'Services' }}</router-link>
        <router-link to="/order">{{ isCN ? '下单' : 'Order' }}</router-link>
        <span @click="logout" class="logout">{{ isCN ? '退出' : 'Logout' }}</span>
      </nav>
    </header>

    <div class="dashboard-content">
      <div class="welcome">
        <h1>{{ isCN ? '欢迎' : 'Welcome' }}, {{ user?.name }}!</h1>
        <p>{{ isCN ? '管理您的订单和历史记录' : 'Manage your orders and history' }}</p>
      </div>

      <div class="actions">
        <router-link to="/order" class="action-card">
          <h3>📝 {{ isCN ? '新订单' : 'New Order' }}</h3>
          <p>{{ isCN ? '上传文件获取报价' : 'Upload documents for quote' }}</p>
        </router-link>
        <router-link to="/templates" class="action-card">
          <h3>📄 {{ isCN ? '模板下载' : 'Templates' }}</h3>
          <p>{{ isCN ? '下载文档模板' : 'Download document templates' }}</p>
        </router-link>
      </div>

      <div class="orders-section">
        <h2>{{ isCN ? '我的订单' : 'My Orders' }}</h2>
        
        <el-table :data="orders" style="width: 100%" v-if="orders.length > 0">
          <el-table-column prop="orderNumber" :label="isCN ? '订单号' : 'Order #'" width="150" />
          <el-table-column :label="isCN ? '服务类型' : 'Service'" width="120">
            <template #default="scope">
              {{ getServiceName(scope.row.serviceType) }}
            </template>
          </el-table-column>
          <el-table-column :label="isCN ? '状态' : 'Status'" width="120">
            <template #default="scope">
              <el-tag :type="getStatusType(scope.row.status)">{{ getStatusText(scope.row.status) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column :label="isCN ? '金额' : 'Amount'" width="100">
            <template #default="scope">${{ (scope.row.quotedPrice || 0) + (scope.row.mailFee || 0) }}</template>
          </el-table-column>
          <el-table-column :label="isCN ? '支付' : 'Payment'" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.paymentStatus === 'PAID' ? 'success' : 'info'" size="small">
                {{ scope.row.paymentStatus === 'PAID' ? (isCN ? '已付' : 'Paid') : (isCN ? '未付' : 'Unpaid') }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column :label="isCN ? '日期' : 'Date'" width="120">
            <template #default="scope">{{ formatDate(scope.row.createdAt) }}</template>
          </el-table-column>
          <el-table-column :label="isCN ? '操作' : 'Actions'" width="150">
            <template #default="scope">
              <el-button size="small" @click="viewOrder(scope.row)">{{ isCN ? '查看' : 'View' }}</el-button>
              <el-button size="small" type="primary" @click="payOrder(scope.row)" v-if="scope.row.status === 'QUOTED' && scope.row.paymentStatus !== 'PAID'">
                {{ isCN ? '支付' : 'Pay' }}
              </el-button>
            </template>
          </el-table-column>
        </el-table>

        <div v-else class="no-orders">
          <p>{{ isCN ? '暂无订单' : 'No orders yet' }}</p>
          <router-link to="/order" class="btn">{{ isCN ? '立即下单' : 'Place Order' }}</router-link>
        </div>
      </div>

      <!-- Order Detail Dialog -->
      <el-dialog v-model="detailDialog" :title="isCN ? '订单详情' : 'Order Details'" width="600px">
        <div v-if="selectedOrder" class="order-detail">
          <el-descriptions :column="2" border>
            <el-descriptions-item :label="isCN ? '订单号' : 'Order #'">{{ selectedOrder.orderNumber }}</el-descriptions-item>
            <el-descriptions-item :label="isCN ? '状态' : 'Status'">
              <el-tag :type="getStatusType(selectedOrder.status)">{{ getStatusText(selectedOrder.status) }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item :label="isCN ? '服务类型' : 'Service'">{{ getServiceName(selectedOrder.serviceType) }}</el-descriptions-item>
            <el-descriptions-item :label="isCN ? '金额' : 'Amount'">${{ (selectedOrder.quotedPrice || 0) + (selectedOrder.mailFee || 0) }}</el-descriptions-item>
            <el-descriptions-item :label="isCN ? '支付方式' : 'Payment Method'">{{ selectedOrder.paymentMethod || '-' }}</el-descriptions-item>
            <el-descriptions-item :label="isCN ? '支付状态' : 'Payment Status'">
              <el-tag :type="selectedOrder.paymentStatus === 'PAID' ? 'success' : 'info'">{{ selectedOrder.paymentStatus === 'PAID' ? (isCN ? '已付' : 'Paid') : (isCN ? '未付' : 'Unpaid') }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item :label="isCN ? '交付方式' : 'Delivery'">
              {{ selectedOrder.deliveryMethod === 'ELECTRONIC' ? (isCN ? '电子版' : 'Electronic') : (isCN ? '邮寄' : 'Mail') }}
            </el-descriptions-item>
            <el-descriptions-item :label="isCN ? '快递单号' : 'Tracking'">{{ selectedOrder.trackingNumber || '-' }}</el-descriptions-item>
            <el-descriptions-item :label="isCN ? '描述' : 'Description'" :span="2">{{ selectedOrder.description || '-' }}</el-descriptions-item>
            <el-descriptions-item :label="isCN ? '创建时间' : 'Created'">{{ formatDate(selectedOrder.createdAt) }}</el-descriptions-item>
            <el-descriptions-item :label="isCN ? '更新时间' : 'Updated'">{{ formatDate(selectedOrder.updatedAt) }}</el-descriptions-item>
          </el-descriptions>
        </div>
        <template #footer>
          <el-button @click="detailDialog = false">{{ isCN ? '关闭' : 'Close' }}</el-button>
          <el-button type="primary" @click="payOrder(selectedOrder)" v-if="selectedOrder?.status === 'QUOTED' && selectedOrder?.paymentStatus !== 'PAID'">
            {{ isCN ? '立即支付' : 'Pay Now' }}
          </el-button>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue'
import { useRouter } from 'vue-router'

const isCN = inject('isCN')
const router = useRouter()
const user = ref(JSON.parse(localStorage.getItem('user') || '{}'))
const orders = ref([])
const detailDialog = ref(false)
const selectedOrder = ref(null)

const API = '/api'

onMounted(async () => {
  if (!user.value.userId) { router.push('/login'); return }
  const res = await fetch(`${API}/orders/user/${user.value.userId}`)
  orders.value = await res.json()
})

const getStatusType = (status) => {
  const map = { 
    'SUBMITTED': 'info', 
    'QUOTED': 'warning', 
    'PAID': 'success', 
    'PROCESSING': 'primary',
    'COMPLETED': 'success', 
    'MAILED': 'success',
    'CANCELLED': 'danger' 
  }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = {
    'SUBMITTED': isCN.value ? '已提交' : 'Submitted',
    'QUOTED': isCN.value ? '已报价' : 'Quoted',
    'PAID': isCN.value ? '已付款' : 'Paid',
    'PROCESSING': isCN.value ? '办理中' : 'Processing',
    'COMPLETED': isCN.value ? '已完成' : 'Completed',
    'MAILED': isCN.value ? '已邮寄' : 'Mailed',
    'CANCELLED': isCN.value ? '已取消' : 'Cancelled'
  }
  return map[status] || status
}

const getServiceName = (type) => {
  const map = {
    'NOTARY': isCN.value ? '公证' : 'Notary',
    'TRANSLATION': isCN.value ? '翻译' : 'Translation',
    'APOSTILLE': isCN.value ? '海牙认证' : 'Apostille',
    'IMMIGRATION': isCN.value ? '移民文件' : 'Immigration',
    'INSURANCE': isCN.value ? '保险' : 'Insurance'
  }
  return map[type] || type
}

const formatDate = (date) => {
  if (!date) return '-'
  return new Date(date).toLocaleDateString()
}

const viewOrder = (order) => {
  selectedOrder.value = order
  detailDialog.value = true
}

const payOrder = (order) => {
  // Payment integration placeholder
  alert(isCN.value ? '支付功能即将上线' : 'Payment integration coming soon!')
}

const logout = () => {
  localStorage.removeItem('user')
  router.push('/')
}
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; padding: 20px 50px; background: #1a1a2e; color: white; flex-wrap: wrap; }
.logo { color: white; font-size: 18px; font-weight: bold; text-decoration: none; }
.logo .cn { font-size: 12px; opacity: 0.8; font-weight: normal; }
.nav a { color: white; margin-left: 20px; text-decoration: none; }
.logout { color: #ffd700; margin-left: 20px; cursor: pointer; }
.dashboard-content { padding: 40px 20px; max-width: 1000px; margin: 0 auto; }
.welcome { text-align: center; margin-bottom: 30px; }
.welcome h1 { color: #1a1a2e; margin-bottom: 10px; }
.actions { display: flex; gap: 20px; margin: 30px 0; flex-wrap: wrap; }
.action-card { background: white; padding: 25px; border-radius: 12px; text-decoration: none; color: #1a1a2e; box-shadow: 0 2px 10px rgba(0,0,0,0.1); flex: 1; min-width: 200px; }
.action-card h3 { margin-bottom: 10px; }
.orders-section { background: white; padding: 30px; border-radius: 12px; margin-top: 30px; }
.orders-section h2 { margin-bottom: 20px; color: #1a1a2e; }
.no-orders { text-align: center; padding: 40px; }
.no-orders p { color: #666; margin-bottom: 20px; }
.btn { background: #ffd700; color: #1a1a2e; padding: 12px 30px; border-radius: 8px; text-decoration: none; font-weight: bold; display: inline-block; }
.order-detail { padding: 10px 0; }

@media (max-width: 768px) {
  .header { padding: 15px 20px; }
  .dashboard-content { padding: 20px 15px; }
}
</style>
