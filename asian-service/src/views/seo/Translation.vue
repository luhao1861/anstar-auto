<template>
  <div class="seo-page">
    <header class="header">
      <router-link to="/" class="logo">Asian Service Inc</router-link>
      <nav class="nav">
        <router-link to="/">Home</router-link>
        <router-link to="/order">Get Quote</router-link>
      </nav>
    </header>

    <div class="seo-content">
      <title>Chinese Document Translation Service | USCIS Certified | Los Angeles</title>
      <meta name="description" content="Professional Chinese document translation service in Los Angeles. USCIS certified translations for immigration. Fast turnaround, accurate translations for birth certificates, marriage certificates, and more.">

      <section class="hero">
        <h1>Chinese Document Translation Service</h1>
        <p class="subtitle">专业中文翻译服务 | USCIS Certified | Los Angeles, California</p>
        <div class="hero-btns">
          <router-link to="/order" class="btn btn-primary">📤 Upload Documents for Translation Quote</router-link>
          <a href="tel:6268663328" class="btn btn-outline">📞 Call Us</a>
        </div>
      </section>

      <section class="local-info">
        <div class="info-card"><h3>📍 Location</h3><p>Los Angeles, California</p></div>
        <div class="info-card"><h3>📞 Contact</h3><p>626-866-3328</p></div>
        <div class="info-card"><h3>✅ Certified</h3><p>USCIS Certified</p></div>
      </section>

      <section class="service-detail">
        <h2>Our Translation Services</h2>
        
        <div class="service-item">
          <h3>📄 Birth Certificate Translation (出生证明翻译)</h3>
          <p>USCIS certified Chinese birth certificate translation for immigration, visa, and passport applications.</p>
        </div>

        <div class="service-item">
          <h3>💍 Marriage Certificate Translation (结婚证翻译)</h3>
          <p>Official translation of marriage certificates for immigration, wedding registration, and legal use.</p>
        </div>

        <div class="service-item">
          <h3>📑 Degree & Transcript Translation (学历翻译)</h3>
          <p>Translation of diplomas, degrees, and transcripts for H-1B, OPT, and other immigration purposes.</p>
        </div>

        <div class="service-item">
          <h3>🏛️ Court Document Translation (法院文件翻译)</h3>
          <p>Professional translation of court documents, judgments, and legal filings.</p>
        </div>

        <div class="service-item">
          <h3>🏦 Bank Document Translation (银行文件翻译)</h3>
          <p>Translation of bank statements, financial documents for immigration and business purposes.</p>
        </div>

        <div class="service-item">
          <h3>🔄 Translation + Notary + Apostille Package</h3>
          <p>Complete one-stop service: Translate, notarize, and authenticate documents for international use.</p>
        </div>
      </section>

      <section class="process">
        <h2>Translation Process</h2>
        <div class="steps">
          <div class="step"><span class="step-num">1</span><h4>Upload</h4><p>Send us your documents</p></div>
          <div class="step"><span class="step-num">2</span><h4>Translate</h4><p>Professional translation</p></div>
          <div class="step"><span class="step-num">3</span><h4>Certify</h4><p>USCIS certification</p></div>
          <div class="step"><span class="step-num">4</span><h4>Deliver</h4><p>Send by email/mail</p></div>
        </div>
      </section>

      <section class="faq">
        <h2>Frequently Asked Questions</h2>
        
        <div class="faq-item">
          <h3>❓ Do you provide USCIS certified translation?</h3>
          <p>Yes! All our translations include a certification statement required by USCIS.</p>
        </div>

        <div class="faq-item">
          <h3>❓ How long does translation take?</h3>
          <p>Standard: 2-3 business days. Rush service available.</p>
        </div>

        <div class="faq-item">
          <h3>❓ Can you translate from Chinese to English?</h3>
          <p>Yes, we provide Chinese to English and English to Chinese translation services.</p>
        </div>

        <div class="faq-item">
          <h3>❓ What documents do you translate?</h3>
          <p>Birth certificates, marriage certificates, death certificates, diplomas, transcripts, court documents, bank statements, and more.</p>
        </div>

        <div class="faq-item">
          <h3>❓ Do you offer notarization and Apostille?</h3>
          <p>Yes! We provide translation + notarization + Apostille as a complete package.</p>
        </div>
      </section>

      <section class="cta-section">
        <h2>需要中文翻译服务？</h2>
        <p>Get a free quote for Chinese document translation today.</p>
        <router-link to="/order" class="btn btn-primary btn-large">📤 Get Translation Quote</router-link>
      </section>

      <section class="contact-section">
        <h2>Contact Us</h2>
        <p>📞 626-866-3328</p>
        <p>📧 info@asianservice.com</p>
        <p>WeChat: AsianService</p>
        <p>📍 Los Angeles, California</p>
      </section>

      <section class="disclaimer">
        <p><strong>Disclaimer:</strong> We are not a law firm. We provide administrative document preparation assistance only.</p>
      </section>
    </div>

    <script type="application/ld+json">
    {
      "@context": "https://schema.org",
      "@type": "LocalBusiness",
      "name": "Asian Service - Chinese Translation",
      "description": "USCIS certified Chinese translation service in Los Angeles",
      "telephone": "+1-626-866-3328",
      "areaServed": ["Los Angeles", "California"],
      "serviceType": ["Chinese Translation", "USCIS Translation", "Document Translation"]
    }
    </script>
  </div>
</template>

<script setup>
import { ref } from 'vue'
const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
</script>

<style scoped>
.seo-page { min-height: 100vh; }
.header { display: flex; justify-content: space-between; align-items: center; padding: 15px 30px; background: #1a1a2e; color: white; }
.logo { color: white; font-size: 18px; font-weight: bold; text-decoration: none; }
.nav a { color: white; margin-left: 15px; text-decoration: none; }
.seo-content { max-width: 900px; margin: 0 auto; padding: 40px 20px; }
.hero { text-align: center; padding: 60px 20px; background: linear-gradient(135deg, #1a1a2e, #16213e); color: white; border-radius: 12px; margin-bottom: 40px; }
.hero h1 { font-size: 36px; margin-bottom: 15px; }
.hero .subtitle { font-size: 18px; opacity: 0.9; margin-bottom: 30px; }
.hero-btns { display: flex; gap: 15px; justify-content: center; }
.btn { padding: 15px 30px; border-radius: 8px; text-decoration: none; font-weight: bold; }
.btn-primary { background: #ffd700; color: #1a1a2e; }
.btn-outline { border: 2px solid white; color: white; }
.btn-large { display: inline-block; margin-top: 15px; }
.local-info { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-bottom: 40px; }
.info-card { background: white; padding: 20px; border-radius: 8px; text-align: center; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.service-detail h2, .process h2, .faq h2 { text-align: center; color: #1a1a2e; margin: 40px 0 30px; }
.service-item { background: white; padding: 25px; margin-bottom: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.service-item h3 { color: #1a1a2e; margin-bottom: 10px; }
.service-item p { color: #666; }
.process { background: #f8f9fa; padding: 40px; border-radius: 12px; margin-bottom: 40px; }
.steps { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; }
.step { text-align: center; }
.step-num { display: inline-block; width: 40px; height: 40px; background: #ffd700; color: #1a1a2e; border-radius: 50%; font-weight: bold; line-height: 40px; margin-bottom: 10px; }
.faq-item { background: white; padding: 20px; margin-bottom: 15px; border-radius: 8px; border-left: 4px solid #ffd700; }
.faq-item h3 { color: #1a1a2e; font-size: 16px; margin-bottom: 10px; }
.faq-item p { color: #666; }
.cta-section { text-align: center; padding: 50px 20px; background: #1a1a2e; color: white; border-radius: 12px; margin-bottom: 40px; }
.contact-section { text-align: center; padding: 40px; background: white; border-radius: 12px; margin-bottom: 30px; }
.disclaimer { padding: 20px; background: #f5f5f5; border-radius: 8px; text-align: center; font-size: 13px; color: #666; }
@media (max-width: 768px) {
  .local-info, .steps { grid-template-columns: 1fr; }
}
</style>
