<template>
  <div class="home">
    <!-- Header -->
    <header class="header">
      <div class="logo">Asian Service Inc<br><span class="cn">华人服务中心</span></div>
      <nav class="nav">
        <router-link to="/">{{ isCN ? '首页' : 'Home' }}</router-link>
        <router-link to="/services">{{ isCN ? '服务' : 'Services' }}</router-link>
        <router-link to="/order">{{ isCN ? '上传报价' : 'Get Quote' }}</router-link>
        <router-link to="/templates">{{ isCN ? '模板' : 'Templates' }}</router-link>
        <template v-if="!user">
          <router-link to="/login" class="auth-link">{{ isCN ? '登录' : 'Login' }}</router-link>
          <router-link to="/register" class="auth-link register">{{ isCN ? '注册' : 'Register' }}</router-link>
        </template>
        <template v-else>
          <router-link to="/dashboard" class="auth-link">{{ isCN ? '我的订单' : 'My Orders' }}</router-link>
          <span @click="logout" class="auth-link">{{ isCN ? '退出' : 'Logout' }}</span>
        </template>
      </nav>
    </header>

    <!-- Banner Carousel -->
    <section class="carousel">
      <div class="carousel-slides" :style="{ transform: `translateX(-${currentSlide * 100}%)` }">
        <div class="carousel-slide slide-1">
          <div class="slide-content">
            <h1>{{ isCN ? '专业华人公证服务' : 'Professional Chinese Notary' }}</h1>
            <p>{{ isCN ? '洛杉矶·蒙特利公园·圣盖博' : 'Los Angeles · Monterey Park · San Gabriel' }}</p>
            <div class="hero-btns">
              <router-link to="/order" class="btn btn-primary">{{ isCN ? '立即预约' : 'Book Now' }}</router-link>
            </div>
          </div>
        </div>
        <div class="carousel-slide slide-2">
          <div class="slide-content">
            <h1>{{ isCN ? '海牙认证服务' : 'Apostille Services' }}</h1>
            <p>{{ isCN ? '加州文件·联邦文件·一站式办理' : 'California · Federal · One-Stop Service' }}</p>
            <div class="hero-btns">
              <router-link to="/apostille" class="btn btn-primary">{{ isCN ? '了解更多' : 'Learn More' }}</router-link>
            </div>
          </div>
        </div>
        <div class="carousel-slide slide-3">
          <div class="slide-content">
            <h1>{{ isCN ? '专业翻译服务' : 'Professional Translation' }}</h1>
            <p>{{ isCN ? 'USCIS认证·出生证明·结婚证·学历' : 'USCIS Certified · Birth · Marriage · Degree' }}</p>
            <div class="hero-btns">
              <router-link to="/translation" class="btn btn-primary">{{ isCN ? '获取报价' : 'Get Quote' }}</router-link>
            </div>
          </div>
        </div>
      </div>
      <div class="carousel-dots">
        <span v-for="i in 3" :key="i" :class="{ active: currentSlide === i-1 }" @click="currentSlide = i-1"></span>
      </div>
    </section>

    <!-- Services -->
    <section class="services">
      <h2>{{ isCN ? '我们提供的核心服务' : 'Our Core Services' }}</h2>
      
      <div class="service-block">
        <div class="service-icon">📝</div>
        <div class="service-info">
          <h3>{{ isCN ? '公证 Notary' : 'Notary 公证' }}</h3>
          <ul>
            <li>{{ isCN ? '办公室公证' : 'Office Notarization' }}</li>
            <li>{{ isCN ? '上门公证' : 'On-site Notarization' }}</li>
            <li>{{ isCN ? '各类委托书公证' : 'Power of Attorney & More' }}</li>
          </ul>
        </div>
        <div class="service-links">
          <router-link to="/chinese-notary-los-angeles" class="seo-link">Learn More</router-link>
          <router-link to="/order" class="service-btn">👉 {{ isCN ? '预约公证' : 'Book Notary' }}</router-link>
        </div>
      </div>

      <div class="service-block">
        <div class="service-icon">📄</div>
        <div class="service-info">
          <h3>{{ isCN ? '翻译 Translation' : 'Translation 翻译' }}</h3>
          <ul>
            <li>{{ isCN ? 'USCIS 文件翻译' : 'USCIS Document Translation' }}</li>
            <li>{{ isCN ? '法院 / 学校 / 银行文件' : 'Court / School / Bank Documents' }}</li>
            <li>{{ isCN ? '附翻译认证声明' : 'Certified Translation Statement' }}</li>
          </ul>
        </div>
        <div class="service-links">
          <router-link to="/chinese-translation-service" class="seo-link">Learn More</router-link>
          <router-link to="/order" class="service-btn">👉 {{ isCN ? '上传文件获取报价' : 'Upload & Quote' }}</router-link>
        </div>
      </div>

      <div class="service-block">
        <div class="service-icon">🏛️</div>
        <div class="service-info">
          <h3>{{ isCN ? '海牙认证 Apostille' : 'Apostille 海牙认证' }}</h3>
          <ul>
            <li>{{ isCN ? '加州文件' : 'California Documents' }}</li>
            <li>{{ isCN ? '联邦文件' : 'Federal Documents' }}</li>
            <li>{{ isCN ? '翻译 + 公证 + 海牙一站式' : 'Translation + Notary + Apostille' }}</li>
          </ul>
        </div>
        <div class="service-links">
          <router-link to="/apostille-los-angeles" class="seo-link">Learn More</router-link>
          <router-link to="/order" class="service-btn">👉 {{ isCN ? '上传文件办理' : 'Upload Documents' }}</router-link>
        </div>
      </div>

      <div class="service-block">
        <div class="service-icon">🛂</div>
        <div class="service-info">
          <h3>{{ isCN ? '移民文件协助' : 'Immigration Assistance' }}</h3>
          <ul>
            <li>I-130 / I-485 / I-765 / I-131 / I-589</li>
            <li>{{ isCN ? '行政文件准备与整理' : 'Document Preparation & Organization' }}</li>
          </ul>
        </div>
        <router-link to="/order" class="service-btn">👉 {{ isCN ? '提交材料评估' : 'Submit for Review' }}</router-link>
      </div>

      <div class="service-block">
        <div class="service-icon">🛡️</div>
        <div class="service-info">
          <h3>{{ isCN ? '保险服务' : 'Insurance Services' }}</h3>
          <ul>
            <li>{{ isCN ? '汽车保险' : 'Auto Insurance' }}</li>
            <li>{{ isCN ? '健康保险' : 'Health Insurance' }}</li>
            <li>{{ isCN ? '商业保险' : 'Commercial Insurance' }}</li>
          </ul>
        </div>
        <router-link to="/order" class="service-btn">👉 {{ isCN ? '获取保险报价' : 'Get Quote' }}</router-link>
      </div>
    </section>

    <!-- Process -->
    <section class="process">
      <h2>{{ isCN ? '办理流程 (5步完成)' : 'Process (5 Steps)' }}</h2>
      <div class="process-steps">
        <div class="step">
          <div class="step-num">1️⃣</div>
          <p>{{ isCN ? '上传文件或预约' : 'Upload or Book' }}</p>
        </div>
        <div class="step">
          <div class="step-num">2️⃣</div>
          <p>{{ isCN ? '专业审核并报价' : 'Review & Quote' }}</p>
        </div>
        <div class="step">
          <div class="step-num">3️⃣</div>
          <p>{{ isCN ? '在线支付' : 'Online Payment' }}</p>
        </div>
        <div class="step">
          <div class="step-num">4️⃣</div>
          <p>{{ isCN ? '正式办理' : 'Processing' }}</p>
        </div>
        <div class="step">
          <div class="step-num">5️⃣</div>
          <p>{{ isCN ? '电子交付或邮寄' : 'Delivery' }}</p>
        </div>
      </div>
    </section>

    <!-- Why Choose Us -->
    <section class="why-us">
      <h2>{{ isCN ? '为什么选择我们？' : 'Why Choose Us?' }}</h2>
      <div class="features">
        <div class="feature">
          <div class="feature-icon">✓</div>
          <h3>{{ isCN ? '专业行政服务经验' : 'Professional Experience' }}</h3>
        </div>
        <div class="feature">
          <div class="feature-icon">✓</div>
          <h3>{{ isCN ? '熟悉政府办理流程' : 'Familiar with Government Process' }}</h3>
        </div>
        <div class="feature">
          <div class="feature-icon">✓</div>
          <h3>{{ isCN ? '支持远程办理' : 'Remote Service Available' }}</h3>
        </div>
        <div class="feature">
          <div class="feature-icon">✓</div>
          <h3>{{ isCN ? '全程订单可追踪' : 'Order Tracking' }}</h3>
        </div>
        <div class="feature">
          <div class="feature-icon">✓</div>
          <h3>{{ isCN ? '严格保护客户隐私' : 'Privacy Protection' }}</h3>
        </div>
      </div>
    </section>

    <!-- Template Download -->
    <section class="templates-section">
      <h2>{{ isCN ? '常用委托书与声明模板下载' : 'Template Download' }}</h2>
      <p class="templates-desc">{{ isCN ? '填写姓名与电话即可下载模板。使用前请向相关受理部门确认是否符合要求。' : 'Fill in name and phone to download. Confirm with relevant department before use.' }}</p>
      <router-link to="/templates" class="btn btn-primary">
        📥 {{ isCN ? '下载模板' : 'Download Templates' }}
      </router-link>
    </section>

    <!-- Contact -->
    <section class="contact">
      <h2>{{ isCN ? '联系我们' : 'Contact Us' }}</h2>
      <p>📞 626-866-3328</p>
      <p>📧 info@asianservice.com</p>
      <p>WeChat: AsianService</p>
    </section>

    <!-- Disclaimer -->
    <section class="disclaimer">
      <p><strong>Disclaimer:</strong></p>
      <ul>
        <li>{{ isCN ? '我们不是律师事务所，不提供法律意见。' : 'We are not a law firm. No legal advice offered.' }}</li>
        <li>{{ isCN ? '公证仅核验签名真实性。' : 'Notary only verifies signature authenticity.' }}</li>
        <li>{{ isCN ? '服务价格根据文件类型确定。' : 'Service price depends on document type.' }}</li>
        <li>{{ isCN ? '客户信息严格保密。' : 'Client information is strictly confidential.' }}</li>
      </ul>
    </section>

    <!-- Footer -->
    <footer class="footer">
      <p>© 2026 Asian Service Inc. All rights reserved.</p>
    </footer>

    <!-- Fixed CTA -->
    <div class="fixed-cta">
      <router-link to="/order">📤 {{ isCN ? '上传文件获取报价' : 'Upload & Get Quote' }}</router-link>
    </div>

    <!-- Chat -->
    <div class="chat-btn" @click="showChat = true">💬</div>
    <div class="chat-modal" v-if="showChat" @click.self="showChat = false">
      <div class="chat-content">
        <div class="chat-header">
          <span>{{ isCN ? '联系我们' : 'Contact Us' }}</span>
          <span @click="showChat = false">✕</span>
        </div>
        <div class="chat-body">
          <p><strong>📞 {{ isCN ? '电话' : 'Phone' }}:</strong> 626-866-3328</p>
          <p><strong>WeChat:</strong> AsianService</p>
          <p><strong>📧 {{ isCN ? '邮箱' : 'Email' }}:</strong> info@asianservice.com</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject, onMounted, onUnmounted } from 'vue'

const isCN = inject('isCN')
const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
const showChat = ref(false)
const currentSlide = ref(0)

let slideInterval = null

onMounted(() => {
  slideInterval = setInterval(() => {
    currentSlide.value = (currentSlide.value + 1) % 3
  }, 4000)
})

onUnmounted(() => {
  if (slideInterval) clearInterval(slideInterval)
})

const logout = () => {
  localStorage.removeItem('user')
  user.value = null
}
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; padding: 15px 30px; background: #1a1a2e; color: white; position: sticky; top: 0; z-index: 100; }
.logo { font-size: 20px; font-weight: bold; }
.logo .cn { font-size: 12px; opacity: 0.8; }
.nav a, .nav span { color: white; margin-left: 15px; text-decoration: none; font-size: 14px; }
.auth-link { padding: 6px 12px; border-radius: 4px; }
.auth-link.register { background: #ffd700; color: #1a1a2e; font-weight: bold; }

.banner { background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%); color: white; padding: 80px 30px; text-align: center; }
.banner-content { max-width: 800px; margin: 0 auto; }
.banner h1 { font-size: 42px; margin-bottom: 15px; }
.banner-sub { font-size: 18px; opacity: 0.9; margin-bottom: 30px; }
.banner-btns { display: flex; gap: 20px; justify-content: center; flex-wrap: wrap; }
.btn { padding: 15px 30px; border-radius: 8px; text-decoration: none; font-weight: bold; font-size: 16px; }
.btn-primary { background: #ffd700; color: #1a1a2e; }
.btn-outline { border: 2px solid white; color: white; }

/* Carousel */
.carousel { position: relative; overflow: hidden; height: 450px; }
.carousel-slides { display: flex; transition: transform 0.5s ease; height: 100%; }
.carousel-slide { min-width: 100%; height: 100%; display: flex; align-items: center; justify-content: center; }
.slide-1 { background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%); }
.slide-2 { background: linear-gradient(135deg, #2c3e50 0%, #3498db 100%); }
.slide-3 { background: linear-gradient(135deg, #34495e 0%, #16a085 100%); }
.slide-content { text-align: center; color: white; padding: 20px; }
.slide-content h1 { font-size: 42px; margin-bottom: 15px; }
.slide-content p { font-size: 20px; opacity: 0.9; margin-bottom: 25px; }
.carousel-dots { position: absolute; bottom: 20px; left: 50%; transform: translateX(-50%); display: flex; gap: 10px; }
.carousel-dots span { width: 12px; height: 12px; border-radius: 50%; background: rgba(255,255,255,0.5); cursor: pointer; }
.carousel-dots span.active { background: #ffd700; }

.hero-btns { display: flex; gap: 15px; justify-content: center; flex-wrap: wrap; }

.services { padding: 60px 30px; background: white; max-width: 900px; margin: 0 auto; }
.services h2 { text-align: center; margin-bottom: 40px; color: #1a1a2e; font-size: 28px; }
.service-block { display: flex; align-items: center; padding: 25px; margin-bottom: 20px; border: 1px solid #eee; border-radius: 12px; transition: 0.3s; }
.service-block:hover { box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
.service-icon { font-size: 40px; margin-right: 20px; }
.service-info { flex: 1; }
.service-info h3 { color: #1a1a2e; margin-bottom: 10px; }
.service-info ul { list-style: none; }
.service-info li { color: #666; padding: 3px 0; font-size: 14px; }
.service-btn { background: #1a1a2e; color: white; padding: 12px 20px; border-radius: 6px; text-decoration: none; font-weight: bold; white-space: nowrap; }
.service-links { display: flex; flex-direction: column; gap: 10px; align-items: flex-end; }
.seo-link { color: #666; font-size: 13px; text-decoration: underline; }

.process { padding: 60px 30px; background: #f8f9fa; }
.process h2 { text-align: center; margin-bottom: 40px; color: #1a1a2e; }
.process-steps { display: flex; justify-content: center; gap: 30px; flex-wrap: wrap; }
.step { text-align: center; }
.step-num { font-size: 36px; margin-bottom: 10px; }
.step p { color: #666; font-size: 14px; }

.why-us { padding: 60px 30px; background: white; }
.why-us h2 { text-align: center; margin-bottom: 40px; color: #1a1a2e; }
.features { display: flex; justify-content: center; gap: 40px; flex-wrap: wrap; max-width: 900px; margin: 0 auto; }
.feature { text-align: center; max-width: 150px; }
.feature-icon { width: 50px; height: 50px; background: #ffd700; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 15px; font-weight: bold; color: #1a1a2e; }

.templates-section { padding: 60px 30px; background: #fff3cd; text-align: center; }
.templates-section h2 { color: #1a1a2e; margin-bottom: 15px; }
.templates-desc { color: #666; margin-bottom: 25px; max-width: 600px; margin-left: auto; margin-right: auto; }

.contact { padding: 40px 30px; background: #1a1a2e; color: white; text-align: center; }
.contact h2 { margin-bottom: 20px; }
.contact p { margin: 8px 0; }

.disclaimer { padding: 30px; background: #f5f5f5; text-align: center; font-size: 13px; color: #666; }
.disclaimer ul { list-style: none; margin-top: 15px; }
.disclaimer li { margin: 5px 0; }

.footer { background: #333; color: white; padding: 20px; text-align: center; }

.fixed-cta { position: fixed; bottom: 0; left: 0; right: 0; background: #ffd700; padding: 15px; text-align: center; z-index: 99; }
.fixed-cta a { color: #1a1a2e; font-weight: bold; font-size: 18px; text-decoration: none; }

.chat-btn { position: fixed; bottom: 80px; right: 20px; width: 60px; height: 60px; background: #ffd700; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 30px; cursor: pointer; z-index: 98; }
.chat-modal { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,0.5); display: flex; align-items: center; justify-content: center; z-index: 10000; }
.chat-content { background: white; padding: 30px; border-radius: 12px; width: 90%; max-width: 400px; }
.chat-header { display: flex; justify-content: space-between; margin-bottom: 20px; font-weight: bold; }
.chat-body p { margin: 10px 0; }

@media (max-width: 768px) {
  .banner h1 { font-size: 28px; }
  .service-block { flex-direction: column; text-align: center; }
  .service-icon { margin: 0 0 15px 0; }
  .service-btn { width: 100%; text-align: center; margin-top: 15px; }
  .process-steps { gap: 15px; }
  .features { gap: 20px; }
}
</style>
