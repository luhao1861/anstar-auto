<template>
  <div class="templates-page">
    <header class="header">
      <router-link to="/" class="logo">Asian Service Inc<br><span class="cn">华人服务中心</span></router-link>
      <nav class="nav">
        <router-link to="/">{{ isCN ? '首页' : 'Home' }}</router-link>
        <router-link to="/services">{{ isCN ? '服务' : 'Services' }}</router-link>
      </nav>
    </header>

    <div class="templates-content">
      <h1>{{ isCN ? '文档模板' : 'Document Templates' }}</h1>
      <p class="subtitle">{{ isCN ? '下载模板。留下联系方式以便更好服务您。' : 'Download templates. Leave your contact to help us serve you better.' }}</p>
      
      <!-- Disclaimer -->
      <div class="main-disclaimer">
        <p><strong>⚠️ {{ isCN ? '免责声明' : 'Disclaimer' }}:</strong></p>
        <p>{{ isCN ? '所有模板仅供参考使用。' : 'All templates are for reference only.' }}</p>
        <p>{{ isCN ? '请在使用前向相关受理部门确认内容是否符合要求。' : 'Please confirm with the relevant department before use.' }}</p>
        <p>{{ isCN ? '本公司不对客户自行填写错误或因模板不符合特定机构要求所产生的后果承担责任。' : 'We are not responsible for any errors or issues arising from template use.' }}</p>
      </div>

      <div class="template-list">
        <div v-for="t in templates" :key="t.id" class="template-card">
          <h3>{{ t.name }}</h3>
          <p>{{ t.description }}</p>
          <el-button type="primary" @click="downloadTemplate(t)">{{ isCN ? '下载' : 'Download' }}</el-button>
          <p class="downloads">{{ t.downloadCount }} {{ isCN ? '次下载' : 'downloads' }}</p>
        </div>
      </div>

      <div class="cta">
        <router-link to="/order" class="btn">{{ isCN ? '上传文件获取报价' : 'Upload Documents for Quote' }}</router-link>
      </div>
    </div>

    <!-- Download Dialog -->
    <el-dialog v-model="dialogVisible" :title="isCN ? '留下联系方式' : 'Leave Your Contact'" width="400px">
      <el-form :model="downloadForm">
        <el-form-item :label="isCN ? '姓名' : 'Name'" required>
          <el-input v-model="downloadForm.name" />
        </el-form-item>
        <el-form-item :label="isCN ? '电话' : 'Phone'" required>
          <el-input v-model="downloadForm.phone" />
        </el-form-item>
        <el-form-item :label="isCN ? '微信 (可选)' : 'WeChat (Optional)'">
          <el-input v-model="downloadForm.wechat" />
        </el-form-item>
        <el-form-item :label="isCN ? '用途' : 'Purpose'">
          <el-input v-model="downloadForm.purpose" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">{{ isCN ? '取消' : 'Cancel' }}</el-button>
        <el-button type="primary" @click="confirmDownload">{{ isCN ? '下载' : 'Download' }}</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue'

const isCN = inject('isCN')
const API = '/api'
const templates = ref([])
const dialogVisible = ref(false)
const selectedTemplate = ref(null)
const downloadForm = ref({ name: '', phone: '', wechat: '', purpose: '' })

onMounted(async () => {
  const res = await fetch(`${API}/templates`)
  templates.value = await res.json()
})

const downloadTemplate = (t) => {
  selectedTemplate.value = t
  downloadForm.value = { name: '', phone: '', wechat: '', purpose: '' }
  dialogVisible.value = true
}

const confirmDownload = async () => {
  if (!downloadForm.value.name || !downloadForm.value.phone) {
    alert(isCN.value ? '请填写姓名和电话' : 'Please fill name and phone')
    return
  }
  await fetch(`${API}/templates/${selectedTemplate.value.id}/download`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(downloadForm.value)
  })
  dialogVisible.value = false
  window.open(selectedTemplate.value.fileUrl, '_blank')
}
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; padding: 20px 50px; background: #1a1a2e; color: white; flex-wrap: wrap; }
.logo { color: white; font-size: 20px; font-weight: bold; text-decoration: none; }
.logo .cn { font-size: 12px; opacity: 0.8; font-weight: normal; }
.nav a { color: white; margin-left: 20px; text-decoration: none; }
.templates-content { padding: 60px 20px; max-width: 900px; margin: 0 auto; }
.templates-content h1 { text-align: center; color: #1a1a2e; }
.subtitle { text-align: center; color: #666; }
.main-disclaimer { margin: 20px auto; padding: 20px; background: #fff3cd; border-radius: 8px; border-left: 4px solid #ffc107; max-width: 800px; text-align: left; }
.main-disclaimer p { margin: 8px 0; font-size: 14px; color: #856404; }
.disclaimer { text-align: center; color: #999; font-size: 12px; margin: 20px 0; }
.template-list { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px; margin-top: 30px; }
.template-card { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.template-card h3 { color: #1a1a2e; margin-bottom: 10px; }
.downloads { color: #999; font-size: 12px; margin-top: 10px; }
.cta { text-align: center; margin-top: 40px; }
.btn { background: #ffd700; color: #1a1a2e; padding: 15px 40px; border-radius: 8px; text-decoration: none; font-weight: bold; display: inline-block; }

@media (max-width: 768px) {
  .header { padding: 15px 20px; }
  .templates-content { padding: 40px 15px; }
}
</style>
