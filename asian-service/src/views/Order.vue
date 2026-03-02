<template>
  <div class="order-page">
    <header class="header">
      <router-link to="/" class="logo">Asian Service Inc<br><span class="cn">华人服务中心</span></router-link>
      <nav class="nav">
        <router-link to="/">{{ isCN ? '首页' : 'Home' }}</router-link>
        <router-link to="/services">{{ isCN ? '服务' : 'Services' }}</router-link>
      </nav>
    </header>

    <div class="order-content">
      <h1>{{ isCN ? '上传文件获取报价' : 'Upload Documents for Quote' }}</h1>
      <p class="subtitle">{{ isCN ? '上传文件 → 获取报价 → 支付 → 办理 → 交付' : 'Upload → Quote → Pay → Process → Deliver' }}</p>

      <el-steps :active="step" finish-status="success" align-center>
        <el-step :title="isCN ? '上传' : 'Upload'" />
        <el-step :title="isCN ? '报价' : 'Quote'" />
        <el-step :title="isCN ? '支付' : 'Pay'" />
        <el-step :title="isCN ? '完成' : 'Done'" />
      </el-steps>

      <!-- Step 1: Upload -->
      <div v-if="step === 0" class="step-content">
        <el-form :model="form" label-width="140px" class="order-form">
          <el-form-item :label="isCN ? '服务类型' : 'Service Type'">
            <el-select v-model="form.serviceType" :placeholder="isCN ? '选择服务' : 'Select service'">
              <el-option :label="isCN ? '公证' : 'Notary' + ' 公证'" value="NOTARY" />
              <el-option :label="isCN ? '翻译' : 'Translation' + ' 翻译'" value="TRANSLATION" />
              <el-option :label="isCN ? '海牙认证' : 'Apostille' + ' 海牙认证'" value="APOSTILLE" />
              <el-option :label="isCN ? '移民文件' : 'Immigration' + ' 移民文件'" value="IMMIGRATION" />
              <el-option :label="isCN ? '保险' : 'Insurance' + ' 保险'" value="INSURANCE" />
            </el-select>
          </el-form-item>

          <el-form-item :label="isCN ? '描述' : 'Description'">
            <el-input v-model="form.description" type="textarea" rows="3" :placeholder="isCN ? '描述您的文件...' : 'Describe your documents...'" />
          </el-form-item>

          <el-form-item :label="isCN ? '上传文件' : 'Upload Files'">
            <el-upload v-model:file-list="fileList" :auto-upload="false" :limit="10" accept=".pdf,.jpg,.jpeg,.png" list-type="picture">
              <el-button>{{ isCN ? '选择文件 (PDF/JPG/PNG)' : 'Select Files (PDF/JPG/PNG)' }}</el-button>
              <template #tip>
                <div class="el-upload__tip">{{ isCN ? '最多10个文件' : 'Max 10 files' }}</div>
              </template>
            </el-upload>
          </el-form-item>

          <el-form-item :label="isCN ? '姓名' : 'Name'" required>
            <el-input v-model="form.name" :placeholder="isCN ? '您的姓名' : 'Your name'" />
          </el-form-item>

          <el-form-item :label="isCN ? '电话' : 'Phone'" required>
            <el-input v-model="form.phone" :placeholder="isCN ? '您的电话' : 'Your phone number'" />
          </el-form-item>

          <el-form-item :label="isCN ? '邮箱' : 'Email'">
            <el-input v-model="form.email" :placeholder="isCN ? '您的邮箱' : 'Your email'" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitOrder" :loading="loading">
              {{ isCN ? '提交获取报价' : 'Submit for Quote' }}
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- Step 2: Quote & Payment -->
      <div v-if="step === 2" class="step-content">
        <el-alert v-if="quoteInfo" type="success" :closable="false" style="margin-bottom: 20px">
          <h3>{{ isCN ? '收到报价！' : 'Quote Received!' }}</h3>
          <p>{{ isCN ? '服务' : 'Service' }}: {{ quoteInfo.serviceType }}</p>
          <p class="price">{{ isCN ? '价格' : 'Price' }}: ${{ quoteInfo.quotedPrice }}</p>
          <p v-if="quoteInfo.mailFee > 0">{{ isCN ? '邮寄费' : 'Mail Fee' }}: +${{ quoteInfo.mailFee }}</p>
          <p class="total">{{ isCN ? '总计' : 'Total' }}: ${{ quoteInfo.quotedPrice + quoteInfo.mailFee }}</p>
        </el-alert>

        <el-form label-width="140px" class="order-form">
          <el-form-item :label="isCN ? '交付方式' : 'Delivery'">
            <el-radio-group v-model="form.deliveryMethod" @change="updateMailFee">
              <el-radio value="ELECTRONIC">📧 {{ isCN ? '电子版（免费）' : 'Electronic (Free)' }}</el-radio>
              <el-radio value="MAIL">📮 {{ isCN ? '邮寄（+$30）' : 'Mail (+$30)' }}</el-radio>
            </el-radio-group>
          </el-form-item>

          <div v-if="form.deliveryMethod === 'MAIL'" class="mail-info">
            <el-form-item :label="isCN ? '收件人' : 'Recipient'" required>
              <el-input v-model="form.recipientName" :placeholder="isCN ? '收件人姓名' : 'Recipient name'" />
            </el-form-item>
            <el-form-item :label="isCN ? '地址' : 'Address'" required>
              <el-input v-model="form.recipientAddress" :placeholder="isCN ? '完整地址' : 'Full address'" />
            </el-form-item>
            <el-form-item :label="isCN ? '电话' : 'Phone'" required>
              <el-input v-model="form.recipientPhone" :placeholder="isCN ? '联系电话' : 'Contact phone'" />
            </el-form-item>
          </div>

          <el-form-item :label="isCN ? '支付方式' : 'Payment Method'">
            <el-radio-group v-model="form.paymentMethod">
              <el-radio value="STRIPE">💳 {{ isCN ? '银行卡' : 'Card (Stripe)' }}</el-radio>
              <el-radio value="ZELLE">🏦 Zelle</el-radio>
            </el-radio-group>
          </el-form-item>

          <div v-if="form.paymentMethod === 'ZELLE'" class="zelle-info">
            <el-alert type="info" :closable="false">
              <p><strong>Zelle {{ isCN ? '信息' : 'Info' }}:</strong></p>
              <p>{{ isCN ? '电话' : 'Phone' }}: 626-866-3328</p>
              <p>{{ isCN ? '姓名' : 'Name' }}: CY Property Inc</p>
              <p>{{ isCN ? '请转账后上传截图' : 'Please send payment and upload screenshot.' }}</p>
            </el-alert>
            <el-form-item :label="isCN ? '上传截图' : 'Screenshot'" style="margin-top: 15px">
              <el-upload v-model:file-list="paymentProofList" :auto-upload="false" accept=".jpg,.jpeg,.png">
                <el-button>{{ isCN ? '上传支付截图' : 'Upload Payment Screenshot' }}</el-button>
              </el-upload>
            </el-form-item>
          </div>

          <el-form-item>
            <el-button type="primary" @click="processPayment" :loading="loading">
              {{ form.paymentMethod === 'ZELLE' ? (isCN ? '确认支付' : 'Confirm Payment') : (isCN ? '立即支付' : 'Pay Now') }}
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- Step 3: Done -->
      <div v-if="step === 3" class="step-content done">
        <el-result icon="success" :title="isCN ? '订单已提交！' : 'Order Submitted!'">
          <template #sub-title>
            <p>{{ isCN ? '订单号' : 'Order #' }}: {{ orderNumber }}</p>
            <p>{{ isCN ? '我们将审核您的文件并尽快发送报价。' : "We'll review your documents and send quote soon." }}</p>
            <p>{{ isCN ? '电话' : 'Phone' }}: 626-866-3328 | WeChat: AsianService</p>
          </template>
        </el-result>
        
        <div class="register-prompt">
          <el-alert type="info" :closable="false">
            <p>{{ isCN ? '创建账户以便查看订单状态和下载文件' : 'Create an account to track order status and download files' }}</p>
          </el-alert>
          <div class="prompt-btns">
            <router-link to="/register" class="btn">{{ isCN ? '创建账户' : 'Create Account' }}</router-link>
            <router-link to="/track" class="btn btn-outline">{{ isCN ? '追踪订单' : 'Track Order' }}</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const API = '/api'
const isCN = inject('isCN')
const route = useRoute()
const router = useRouter()

const step = ref(0)
const loading = ref(false)
const orderNumber = ref('')
const quoteInfo = ref(null)

const form = ref({
  serviceType: '',
  description: '',
  name: '',
  phone: '',
  email: '',
  deliveryMethod: 'ELECTRONIC',
  paymentMethod: 'STRIPE',
  recipientName: '',
  recipientAddress: '',
  recipientPhone: ''
})

const fileList = ref([])
const paymentProofList = ref([])

onMounted(() => {
  const saved = localStorage.getItem('pendingOrder')
  if (saved) {
    const data = JSON.parse(saved)
    orderNumber.value = data.orderNumber
    step.value = 2
    quoteInfo.value = data
  }
})

const submitOrder = async () => {
  if (!form.value.serviceType || !form.value.name || !form.value.phone) {
    ElMessage.warning(isCN.value ? '请填写必填项' : 'Please fill in required fields')
    return
  }
  
  loading.value = true
  try {
    const res = await fetch(`${API}/orders`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        userId: null,
        serviceType: form.value.serviceType,
        description: form.value.description,
        status: 'SUBMITTED',
        deliveryMethod: form.value.deliveryMethod,
        recipientName: form.value.recipientName,
        recipientAddress: form.value.recipientAddress,
        recipientPhone: form.value.recipientPhone,
        customerName: form.value.name,
        customerPhone: form.value.phone,
        customerEmail: form.value.email
      })
    })
    const order = await res.json()
    orderNumber.value = order.orderNumber
    ElMessage.success(isCN.value ? '订单已提交！' + order.orderNumber : 'Order submitted! ' + order.orderNumber)
    step.value = 3
  } catch (e) {
    ElMessage.error(isCN.value ? '提交失败' : 'Failed to submit')
  }
  loading.value = false
}

const updateMailFee = () => {}

const processPayment = async () => {
  ElMessage.info(isCN.value ? '支付功能即将上线' : 'Payment integration coming soon!')
}
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; padding: 20px 50px; background: #1a1a2e; color: white; flex-wrap: wrap; }
.logo { color: white; font-size: 20px; font-weight: bold; text-decoration: none; }
.logo .cn { font-size: 12px; opacity: 0.8; font-weight: normal; }
.nav a { color: white; margin-left: 20px; text-decoration: none; }
.order-content { padding: 40px 20px; max-width: 700px; margin: 0 auto; }
.order-content h1 { text-align: center; color: #1a1a2e; font-size: 24px; }
.subtitle { text-align: center; color: #666; margin-bottom: 30px; }
.step-content { margin-top: 30px; }
.order-form { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.price { font-size: 20px; font-weight: bold; color: #ffd700; }
.total { font-size: 24px; font-weight: bold; color: #1a1a2e; }
.mail-info, .zelle-info { margin: 20px 0; padding: 15px; background: #f5f5f5; border-radius: 8px; }
.done { text-align: center; }
.register-prompt { margin-top: 20px; text-align: center; }
.prompt-btns { display: flex; gap: 15px; justify-content: center; margin-top: 15px; flex-wrap: wrap; }
.btn-outline { background: transparent; border: 2px solid #1a1a2e; color: #1a1a2e; }
.btn { display: inline-block; background: #ffd700; color: #1a1a2e; padding: 12px 30px; border-radius: 8px; text-decoration: none; font-weight: bold; margin-top: 20px; }

@media (max-width: 768px) {
  .header { padding: 15px 20px; }
  .order-content { padding: 20px 15px; }
  .order-form { padding: 20px; }
}
</style>
