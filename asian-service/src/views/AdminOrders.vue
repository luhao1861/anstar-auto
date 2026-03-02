<template>
  <div class="admin-page">
    <header class="header">
      <router-link to="/" class="logo">Asian Service Admin<br><span class="cn">后台管理</span></router-link>
      <nav class="nav">
        <router-link to="/admin/orders">{{ isCN ? '订单' : 'Orders' }}</router-link>
        <router-link to="/admin/templates">{{ isCN ? '模板' : 'Templates' }}</router-link>
        <span @click="logout">{{ isCN ? '退出' : 'Logout' }}</span>
      </nav>
    </header>

    <div class="admin-content">
      <h1>{{ isCN ? '订单管理' : 'Order Management' }}</h1>
      
      <el-table :data="orders" style="width: 100%" @expand-change="expandRow">
        <el-table-column type="expand">
          <template #default="props">
            <div class="order-expand">
              <el-descriptions :column="2" border>
                <el-descriptions-item :label="isCN ? '客户姓名' : 'Customer Name'">{{ props.row.customerName || '-' }}</el-descriptions-item>
                <el-descriptions-item :label="isCN ? '电话' : 'Phone'">{{ props.row.customerPhone || '-' }}</el-descriptions-item>
                <el-descriptions-item :label="isCN ? '邮箱' : 'Email'">{{ props.row.customerEmail || '-' }}</el-descriptions-item>
                <el-descriptions-item :label="isCN ? '支付方式' : 'Payment Method'">{{ props.row.paymentMethod || '-' }}</el-descriptions-item>
                <el-descriptions-item :label="isCN ? '交付方式' : 'Delivery'">
                  {{ props.row.deliveryMethod === 'ELECTRONIC' ? (isCN ? '电子版' : 'Electronic') : (isCN ? '邮寄' : 'Mail') }}
                </el-descriptions-item>
                <el-descriptions-item :label="isCN ? '邮寄地址' : 'Mail Address'" v-if="props.row.deliveryMethod === 'MAIL'">
                  {{ props.row.recipientName }}, {{ props.row.recipientAddress }}, {{ props.row.recipientPhone }}
                </el-descriptions-item>
                <el-descriptions-item :label="isCN ? '描述' : 'Description'" :span="2">{{ props.row.description || '-' }}</el-descriptions-item>
                <el-descriptions-item :label="isCN ? '管理员备注' : 'Admin Notes'" :span="2">
                  <el-input v-model="props.row.adminNotes" type="textarea" rows="2" @blur="saveNotes(props.row)" :placeholder="isCN ? '添加备注...' : 'Add notes...'" />
                </el-descriptions-item>
              </el-descriptions>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="orderNumber" :label="isCN ? '订单号' : 'Order #'" width="150" />
        <el-table-column :label="isCN ? '服务' : 'Service'" width="100">
          <template #default="scope">{{ getServiceName(scope.row.serviceType) }}</template>
        </el-table-column>
        <el-table-column prop="customerName" :label="isCN ? '客户' : 'Customer'" />
        <el-table-column :label="isCN ? '状态' : 'Status'" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ getStatusText(scope.row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="isCN ? '金额' : 'Amount'" width="80">
          <template #default="scope">${{ (scope.row.quotedPrice || 0) + (scope.row.mailFee || 0) }}</template>
        </el-table-column>
        <el-table-column :label="isCN ? '支付' : 'Pay'" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.paymentStatus === 'PAID' ? 'success' : 'warning'" size="small">
              {{ scope.row.paymentStatus === 'PAID' ? (isCN ? '已付' : 'Paid') : (isCN ? '未付' : 'Unpaid') }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="isCN ? '操作' : 'Actions'" width="280" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="openQuote(scope.row)">{{ isCN ? '报价' : 'Quote' }}</el-button>
            <el-button size="small" type="success" @click="markPaid(scope.row)" v-if="scope.row.status === 'QUOTED'">{{ isCN ? '已付' : 'Paid' }}</el-button>
            <el-button size="small" type="primary" @click="startProcessing(scope.row)" v-if="scope.row.status === 'PAID'">{{ isCN ? '办理' : 'Process' }}</el-button>
            <el-button size="small" type="success" @click="completeOrder(scope.row)" v-if="scope.row.status === 'PROCESSING'">{{ isCN ? '完成' : 'Complete' }}</el-button>
            <el-button size="small" @click="openMail(scope.row)" v-if="scope.row.status === 'COMPLETED' && scope.row.deliveryMethod === 'MAIL'">{{ isCN ? '邮寄' : 'Mail' }}</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- Quote Dialog -->
    <el-dialog v-model="quoteDialog" :title="isCN ? '设置报价' : 'Set Quote'" width="400px">
      <el-form :model="quoteForm">
        <el-form-item :label="isCN ? '服务价格 ($)' : 'Service Price ($)'">
          <el-input-number v-model="quoteForm.price" :min="0" style="width: 100%" />
        </el-form-item>
        <el-form-item :label="isCN ? '邮寄费 ($)' : 'Mail Fee ($)'">
          <el-input-number v-model="quoteForm.mailFee" :min="0" :max="100" style="width: 100%" />
        </el-form-item>
        <el-form-item :label="isCN ? '备注' : 'Notes'">
          <el-input v-model="quoteForm.notes" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="quoteDialog = false">{{ isCN ? '取消' : 'Cancel' }}</el-button>
        <el-button type="primary" @click="submitQuote">{{ isCN ? '发送报价' : 'Send Quote' }}</el-button>
      </template>
    </el-dialog>

    <!-- Mail Dialog -->
    <el-dialog v-model="mailDialog" :title="isCN ? '填写快递单号' : 'Add Tracking Number'" width="400px">
      <el-form :model="mailForm">
        <el-form-item :label="isCN ? '快递单号' : 'Tracking #'">
          <el-input v-model="mailForm.tracking" :placeholder="isCN ? '输入快递单号' : 'Enter tracking number'" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="mailDialog = false">{{ isCN ? '取消' : 'Cancel' }}</el-button>
        <el-button type="primary" @click="submitMail">{{ isCN ? '确认' : 'Confirm' }}</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const isCN = inject('isCN')
const router = useRouter()
const orders = ref([])
const API = '/api'

const quoteDialog = ref(false)
const mailDialog = ref(false)
const quoteForm = ref({ price: 0, mailFee: 0, notes: '', orderId: null })
const mailForm = ref({ tracking: '', orderId: null })

onMounted(async () => {
  loadOrders()
})

const loadOrders = async () => {
  const res = await fetch(`${API}/orders`)
  orders.value = await res.json()
}

const getStatusType = (status) => {
  const map = { 'SUBMITTED': 'info', 'QUOTED': 'warning', 'PAID': 'success', 'PROCESSING': 'primary', 'COMPLETED': 'success', 'MAILED': 'success', 'CANCELLED': 'danger' }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = {
    'SUBMITTED': isCN.value ? '已提交' : 'Submitted',
    'QUOTED': isCN.value ? '待付款' : 'Quoted',
    'PAID': isCN.value ? '已付款' : 'Paid',
    'PROCESSING': isCN.value ? '办理中' : 'Processing',
    'COMPLETED': isCN.value ? '已完成' : 'Completed',
    'MAILED': isCN.value ? '已邮寄' : 'Mailed'
  }
  return map[status] || status
}

const getServiceName = (type) => {
  const map = { 'NOTARY': '公证', 'TRANSLATION': '翻译', 'APOSTILLE': '海牙', 'IMMIGRATION': '移民', 'INSURANCE': '保险' }
  return isCN ? (map[type] || type) : type
}

const expandRow = (row) => {}

const openQuote = (order) => {
  quoteForm.value = { price: order.quotedPrice || 0, mailFee: order.deliveryMethod === 'MAIL' ? 30 : 0, notes: '', orderId: order.id }
  quoteDialog.value = true
}

const submitQuote = async () => {
  await fetch(`${API}/orders/${quoteForm.value.orderId}/quote`, { 
    method: 'PUT', 
    headers: { 'Content-Type': 'application/json' }, 
    body: JSON.stringify({ price: quoteForm.value.price, mailFee: quoteForm.value.mailFee, notes: quoteForm.value.notes }) 
  })
  quoteDialog.value = false
  loadOrders()
  ElMessage.success(isCN.value ? '报价已发送' : 'Quote sent')
}

const markPaid = async (order) => {
  await fetch(`${API}/orders/${order.id}/pay`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ method: 'MANUAL' }) })
  loadOrders()
  ElMessage.success(isCN.value ? '已标记为已付款' : 'Marked as paid')
}

const startProcessing = async (order) => {
  await fetch(`${API}/orders/${order.id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ status: 'PROCESSING' }) })
  loadOrders()
  ElMessage.success(isCN.value ? '已开始办理' : 'Processing started')
}

const completeOrder = async (order) => {
  await fetch(`${API}/orders/${order.id}/complete`, { method: 'PUT' })
  loadOrders()
  ElMessage.success(isCN.value ? '已完成' : 'Completed')
}

const openMail = (order) => {
  mailForm.value = { tracking: '', orderId: order.id }
  mailDialog.value = true
}

const submitMail = async () => {
  await fetch(`${API}/orders/${mailForm.value.orderId}/mail`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ tracking: mailForm.value.tracking }) })
  mailDialog.value = false
  loadOrders()
  ElMessage.success(isCN.value ? '已填写快递单号' : 'Tracking added')
}

const saveNotes = async (order) => {
  await fetch(`${API}/orders/${order.id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ adminNotes: order.adminNotes }) })
}

const logout = () => { localStorage.removeItem('user'); router.push('/') }
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; padding: 20px 50px; background: #1a1a2e; color: white; flex-wrap: wrap; }
.logo { color: white; font-size: 18px; font-weight: bold; text-decoration: none; }
.logo .cn { font-size: 12px; opacity: 0.8; }
.nav a, .nav span { color: white; margin-left: 20px; text-decoration: none; cursor: pointer; }
.admin-content { padding: 40px 20px; }
.order-expand { padding: 20px; background: #f5f5f5; border-radius: 8px; }

@media (max-width: 768px) {
  .header { padding: 15px 20px; }
  .admin-content { padding: 20px 15px; }
}
</style>
