<template>
  <div class="services">
    <header class="header">
      <router-link to="/" class="logo">Asian Service Inc<br><span class="cn">华人服务中心</span></router-link>
      <nav class="nav">
        <router-link to="/">{{ isCN ? '首页' : 'Home' }}</router-link>
        <router-link to="/services">{{ isCN ? '服务' : 'Services' }}</router-link>
        <router-link to="/templates">{{ isCN ? '模板' : 'Templates' }}</router-link>
        <router-link to="/track">{{ isCN ? '追踪' : 'Track' }}</router-link>
        <router-link to="/login">{{ isCN ? '登录' : 'Login' }}</router-link>
      </nav>
    </header>

    <div class="services-content">
      <h1>{{ isCN ? '我们的服务' : 'Our Services' }}</h1>
      <p class="subtitle">{{ isCN ? '价格根据文件类型、页数及复杂程度确定，请上传文件获取具体报价。' : 'Price quoted after reviewing your documents.' }}</p>

      <div class="service-list">
        <!-- Notary -->
        <div class="service-section">
          <h2>📝 {{ isCN ? '公证 Notary' : 'Notary 公证' }}</h2>
          <ul>
            <li>{{ isCN ? '办公室公证' : 'Office Notarization' }}</li>
            <li>{{ isCN ? '上门公证' : 'On-site Notarization' }}</li>
            <li>{{ isCN ? '委托书公证' : 'Power of Attorney' }}</li>
            <li>{{ isCN ? '签名公证' : 'Signature Verification' }}</li>
          </ul>
          <p class="note">* {{ isCN ? '上门需支付定金，价格根据情况确定' : 'On-site requires deposit. Price varies by document type.' }}</p>
        </div>

        <!-- Translation -->
        <div class="service-section">
          <h2>📄 {{ isCN ? '翻译 Translation' : 'Translation 翻译' }}</h2>
          <ul>
            <li>{{ isCN ? 'USCIS移民局文件翻译' : 'USCIS Documents' }}</li>
            <li>{{ isCN ? '法院文件翻译' : 'Court Documents' }}</li>
            <li>{{ isCN ? '学校文件翻译' : 'School Documents' }}</li>
            <li>{{ isCN ? '银行文件翻译' : 'Bank Documents' }}</li>
            <li>{{ isCN ? '出生/结婚/死亡证明翻译' : 'Birth/Marriage/Death Certificates' }}</li>
          </ul>
          <p class="note">* {{ isCN ? '所有翻译包含USCIS认证声明' : 'All translations include certification for USCIS.' }}</p>
        </div>

        <!-- Apostille -->
        <div class="service-section">
          <h2>🏛️ {{ isCN ? '海牙认证 Apostille' : 'Apostille 海牙认证' }}</h2>
          <ul>
            <li>
              {{ isCN ? '结婚证认证' : 'Marriage Certificate' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载委托书模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '离婚协议认证' : 'Divorce Agreement' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载委托书模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '单身证明认证' : 'Single Status Certificate' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载声明模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '出生证明认证' : 'Birth Certificate' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载授权模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '死亡证明认证' : 'Death Certificate' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载授权模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '无犯罪记录认证' : 'Police Clearance' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载授权模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '学历证书认证' : 'Education Certificate' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载授权模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '成绩单认证' : 'Transcript' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载授权模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '放弃遗产声明认证' : 'Estate Waiver' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载声明模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '房屋买卖委托书认证' : 'Property POA' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载房产委托书模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '各类授权委托书认证' : 'General POA' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载通用授权模板' : 'Download Template' }}】</router-link>
            </li>
            <li>
              {{ isCN ? '各类声明及证明文件认证' : 'General Statement' }}
              <router-link to="/templates" class="template-link">【{{ isCN ? '下载通用声明模板' : 'Download Template' }}】</router-link>
            </li>
          </ul>
          <p class="note">* {{ isCN ? '支持加急服务' : 'Rush service available.' }}</p>
          
          <!-- Disclaimer -->
          <div class="template-disclaimer">
            <p><strong>⚠️ {{ isCN ? '模板免责声明' : 'Template Disclaimer' }}:</strong></p>
            <p>{{ isCN ? '所有模板仅供参考使用。' : 'All templates are for reference only.' }}</p>
            <p>{{ isCN ? '请在使用前向相关受理部门确认内容是否符合要求。' : 'Please confirm with the relevant department before use.' }}</p>
            <p>{{ isCN ? '本公司不对客户自行填写错误或因模板不符合特定机构要求所产生的后果承担责任。' : 'We are not responsible for any errors or issues arising from template use.' }}</p>
          </div>
        </div>

        <!-- Immigration -->
        <div class="service-section">
          <h2>🛂 {{ isCN ? '移民文件 Immigration' : 'Immigration 移民文件' }}</h2>
          <ul>
            <li>I-130</li>
            <li>I-485</li>
            <li>I-765</li>
            <li>I-131</li>
            <li>I-589</li>
          </ul>
          <p class="note warning">⚠️ {{ isCN ? '我们不是律师事务所，不提供法律意见，仅提供行政文件准备协助。' : 'We are NOT a law firm. We only provide administrative document preparation assistance. No legal advice offered.' }}</p>
        </div>

        <!-- Insurance -->
        <div class="service-section">
          <h2>🛡️ {{ isCN ? '保险 Insurance' : 'Insurance 保险' }}</h2>
          <ul>
            <li>{{ isCN ? '汽车保险' : 'Auto Insurance' }}</li>
            <li>{{ isCN ? '健康保险' : 'Health Insurance' }}</li>
            <li>{{ isCN ? '商业保险' : 'Commercial Insurance' }}</li>
          </ul>
          <p class="note">* {{ isCN ? '联系获取报价' : 'Get a quote by contacting us.' }}</p>
        </div>
      </div>

      <div class="cta">
        <h3>{{ isCN ? '准备好开始了吗？' : 'Ready to get started?' }}</h3>
        <router-link to="/order" class="btn">{{ isCN ? '上传文件获取报价' : 'Upload Documents for Quote' }}</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { inject } from 'vue'

const isCN = inject('isCN')
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; padding: 20px 50px; background: #1a1a2e; color: white; flex-wrap: wrap; }
.logo { color: white; font-size: 20px; font-weight: bold; text-decoration: none; }
.logo .cn { font-size: 12px; opacity: 0.8; font-weight: normal; }
.nav a { color: white; margin-left: 20px; text-decoration: none; }
.services-content { padding: 60px 20px; max-width: 900px; margin: 0 auto; }
.services-content h1 { text-align: center; color: #1a1a2e; margin-bottom: 10px; }
.subtitle { text-align: center; color: #666; margin-bottom: 40px; }
.service-section { background: white; padding: 30px; margin-bottom: 30px; border-radius: 12px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.service-section h2 { color: #1a1a2e; margin-bottom: 20px; border-bottom: 2px solid #ffd700; padding-bottom: 10px; }
.service-section ul { list-style: none; }
.service-section li { padding: 8px 0; border-bottom: 1px solid #eee; }
.note { color: #666; font-size: 14px; margin-top: 15px; font-style: italic; }
.note.warning { color: #e6a23c; }
.template-link { color: #ffd700; text-decoration: none; font-size: 13px; margin-left: 10px; }
.template-link:hover { text-decoration: underline; }
.template-disclaimer { margin-top: 20px; padding: 15px; background: #fff3cd; border-radius: 8px; border-left: 4px solid #ffc107; }
.template-disclaimer p { margin: 5px 0; font-size: 13px; color: #856404; }
.cta { text-align: center; margin-top: 40px; }
.cta h3 { margin-bottom: 20px; color: #1a1a2e; }
.btn { background: #ffd700; color: #1a1a2e; padding: 15px 40px; border-radius: 8px; text-decoration: none; font-weight: bold; display: inline-block; }

@media (max-width: 768px) {
  .header { padding: 15px 20px; }
  .services-content { padding: 40px 15px; }
}
</style>
