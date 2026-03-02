<template>
  <div class="seo-page">
    <header class="header">
      <router-link to="/" class="logo">Asian Service Inc<br><span class="cn">华人服务中心</span></router-link>
      <nav class="nav">
        <router-link to="/">{{ isCN ? '首页' : 'Home' }}</router-link>
        <router-link to="/order">{{ isCN ? '上传报价' : 'Get Quote' }}</router-link>
        <router-link to="/templates">{{ isCN ? '模板' : 'Templates' }}</router-link>
        <template v-if="!user">
          <router-link to="/login">{{ isCN ? '登录' : 'Login' }}</router-link>
        </template>
        <template v-else>
          <router-link to="/dashboard">{{ isCN ? '我的订单' : 'My Orders' }}</router-link>
        </template>
      </nav>
    </header>

    <div class="seo-content">
      <!-- SEO Meta -->
      <div class="seo-meta" style="display:none">
        <title>Los Angeles Apostille Service | Asian Service Center</title>
        <meta name="description" content="Professional Apostille service in Los Angeles and California. We provide fast, reliable Apostille authentication for Chinese documents. Serving all of California.">
      </div>

      <!-- Hero -->
      <section class="hero">
        <h1>Los Angeles Apostille Service | California Apostille</h1>
        <p class="subtitle">Professional Apostille Authentication Service in Los Angeles, California. Fast turnaround, reliable, and affordable.</p>
        <div class="hero-btns">
          <router-link to="/order" class="btn btn-primary">📤 Upload Documents for Quote</router-link>
          <a href="tel:6268663328" class="btn btn-outline">📞 Call Us</a>
        </div>
      </section>

      <!-- Local SEO Info -->
      <section class="local-info">
        <div class="info-card">
          <h3>📍 Service Area</h3>
          <p>Los Angeles, Monterey Park, San Gabriel Valley, and all of California</p>
        </div>
        <div class="info-card">
          <h3>📞 Contact</h3>
          <p>626-866-3328</p>
        </div>
        <div class="info-card">
          <h3>⏰ Turnaround</h3>
          <p>3-5 business days standard, rush available</p>
        </div>
      </section>

      <!-- Services -->
      <section class="service-detail">
        <h2>Our Apostille Services</h2>
        
        <div class="service-item">
          <h3>🏛️ California State Documents Apostille</h3>
          <p>We authenticate California-issued documents including birth certificates, marriage certificates, death certificates, and corporate documents for use abroad.</p>
        </div>

        <div class="service-item">
          <h3>🇨🇳 Chinese Document Authentication</h3>
          <p>Specialized in authenticating Chinese documents for use in the US and vice versa. We handle documents from China, Hong Kong, and Taiwan.</p>
        </div>

        <div class="service-item">
          <h3>📄 Federal Documents Apostille</h3>
          <p>FBI background checks, federal court documents, and other federal-level authentication.</p>
        </div>

        <div class="service-item">
          <h3>🔄 Translation + Notary + Apostille Package</h3>
          <p>One-stop service: We translate your documents, notarize them, and obtain Apostille certification. Save time and money!</p>
        </div>
      </section>

      <!-- Process -->
      <section class="process">
        <h2>How to Get Apostille in California</h2>
        <div class="steps">
          <div class="step">
            <span class="step-num">1</span>
            <h4>Upload Documents</h4>
            <p>Send us your documents via our secure upload system</p>
          </div>
          <div class="step">
            <span class="step-num">2</span>
            <h4>We Review</h4>
            <p>Our team reviews and prepares documents</p>
          </div>
          <div class="step">
            <span class="step-num">3</span>
            <h4>Process</h4>
            <p>We submit to California Secretary of State</p>
          </div>
          <div class="step">
            <span class="step-num">4</span>
            <h4>Delivery</h4>
            <p>Receive authenticated documents by email or mail</p>
          </div>
        </div>
      </section>

      <!-- FAQ -->
      <section class="faq">
        <h2>Frequently Asked Questions</h2>
        
        <div class="faq-item">
          <h3>❓ How long does Apostille take in California?</h3>
          <p>Standard processing takes 3-5 business days. Rush service is available for 1-2 business days.</p>
        </div>

        <div class="faq-item">
          <h3>❓ Can I get Apostille for Chinese documents in Los Angeles?</h3>
          <p>Yes! We specialize in Chinese document authentication. We work with documents from China, Hong Kong, and Taiwan.</p>
        </div>

        <div class="faq-item">
          <h3>❓ What is the difference between Apostille and Notary?</h3>
          <p>Notary verifies signature authenticity within the US. Apostille is an international certification for documents used in countries that are members of the Hague Convention.</p>
        </div>

        <div class="faq-item">
          <h3>❓ Do you provide Chinese notary service in Los Angeles?</h3>
          <p>Yes, we provide Chinese-language notary services in Los Angeles and the San Gabriel Valley area.</p>
        </div>

        <div class="faq-item">
          <h3>❓ How much does Apostille cost?</h3>
          <p>Cost varies by document type and turnaround time. Upload your documents for a free quote.</p>
        </div>
      </section>

      <!-- CTA -->
      <section class="cta-section">
        <h2>Ready to Get Started?</h2>
        <p>Upload your documents today and get a free quote within 24 hours.</p>
        <router-link to="/order" class="btn btn-primary btn-large">📤 Upload Documents for Quote</router-link>
      </section>

      <!-- Contact -->
      <section class="contact-section">
        <h2>Contact Us</h2>
        <p>📞 626-866-3328</p>
        <p>📧 info@asianservice.com</p>
        <p>WeChat: AsianService</p>
        <p>📍 Serving Los Angeles and all of California</p>
      </section>

      <!-- Disclaimer -->
      <section class="disclaimer">
        <p><strong>Disclaimer:</strong> We are not a law firm. We provide administrative document preparation assistance only. No legal advice is offered or implied.</p>
      </section>
    </div>

    <!-- JSON-LD Schema -->
    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "LocalBusiness",
      "name": "Asian Service Inc",
      "description": "Professional document services including Apostille, Notary, Translation in Los Angeles",
      "telephone": "+1-626-866-3328",
      "email": "info@asianservice.com",
      "address": {
        "@type": "PostalAddress",
        "addressLocality": "Los Angeles",
        "addressRegion": "CA",
        "addressCountry": "US"
      },
      "areaServed": ["Los Angeles", "California", "Monterey Park", "San Gabriel Valley"],
      "serviceType": ["Apostille", "Notary", "Translation", "Immigration Services"]
    }
    </script>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue'

const isCN = inject('isCN')
const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
</script>

<style scoped>
.seo-page { min-height: 100vh; }
.header { display: flex; justify-content: space-between; align-items: center; padding: 15px 30px; background: #1a1a2e; color: white; flex-wrap: wrap; }
.logo { color: white; font-size: 18px; font-weight: bold; text-decoration: none; }
.logo .cn { font-size: 12px; opacity: 0.8; }
.nav a { color: white; margin-left: 15px; text-decoration: none; font-size: 14px; }

.seo-content { max-width: 900px; margin: 0 auto; padding: 40px 20px; }

.hero { text-align: center; padding: 60px 20px; background: linear-gradient(135deg, #1a1a2e, #16213e); color: white; border-radius: 12px; margin-bottom: 40px; }
.hero h1 { font-size: 36px; margin-bottom: 15px; }
.hero .subtitle { font-size: 18px; opacity: 0.9; margin-bottom: 30px; }
.hero-btns { display: flex; gap: 15px; justify-content: center; flex-wrap: wrap; }
.btn { padding: 15px 30px; border-radius: 8px; text-decoration: none; font-weight: bold; }
.btn-primary { background: #ffd700; color: #1a1a2e; }
.btn-outline { border: 2px solid white; color: white; }
.btn-large { font-size: 18px; padding: 18px 40px; }

.local-info { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 40px; }
.info-card { background: white; padding: 20px; border-radius: 8px; text-align: center; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.info-card h3 { color: #1a1a2e; margin-bottom: 10px; }

.service-detail { margin-bottom: 40px; }
.service-detail h2 { text-align: center; color: #1a1a2e; margin-bottom: 30px; font-size: 28px; }
.service-item { background: white; padding: 25px; margin-bottom: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.service-item h3 { color: #1a1a2e; margin-bottom: 10px; }
.service-item p { color: #666; line-height: 1.6; }

.process { background: #f8f9fa; padding: 40px; border-radius: 12px; margin-bottom: 40px; }
.process h2 { text-align: center; color: #1a1a2e; margin-bottom: 30px; }
.steps { display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 20px; }
.step { text-align: center; }
.step-num { display: inline-block; width: 40px; height: 40px; background: #ffd700; color: #1a1a2e; border-radius: 50%; font-weight: bold; line-height: 40px; margin-bottom: 10px; }
.step h4 { color: #1a1a2e; margin-bottom: 5px; }
.step p { font-size: 14px; color: #666; }

.faq { margin-bottom: 40px; }
.faq h2 { text-align: center; color: #1a1a2e; margin-bottom: 30px; font-size: 28px; }
.faq-item { background: white; padding: 20px; margin-bottom: 15px; border-radius: 8px; border-left: 4px solid #ffd700; }
.faq-item h3 { color: #1a1a2e; font-size: 16px; margin-bottom: 10px; }
.faq-item p { color: #666; line-height: 1.6; }

.cta-section { text-align: center; padding: 50px 20px; background: #1a1a2e; color: white; border-radius: 12px; margin-bottom: 40px; }
.cta-section h2 { margin-bottom: 15px; }
.cta-section p { margin-bottom: 25px; opacity: 0.9; }

.contact-section { text-align: center; padding: 40px; background: white; border-radius: 12px; margin-bottom: 30px; }
.contact-section h2 { color: #1a1a2e; margin-bottom: 20px; }
.contact-section p { margin: 10px 0; color: #666; }

.disclaimer { padding: 20px; background: #f5f5f5; border-radius: 8px; text-align: center; font-size: 13px; color: #666; }

@media (max-width: 768px) {
  .hero h1 { font-size: 24px; }
  .steps { grid-template-columns: repeat(2, 1fr); }
}
</style>
