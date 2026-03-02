<template>
  <div class="seo-page">
    <header class="header">
      <router-link to="/" class="logo">Asian Service Inc<br><span class="cn">华人服务中心</span></router-link>
      <nav class="nav">
        <router-link to="/">Home</router-link>
        <router-link to="/order">Get Quote</router-link>
        <router-link to="/templates">Templates</router-link>
      </nav>
    </header>

    <div class="seo-content">
      <title>Chinese Notary Public in Los Angeles | Asian Service</title>
      <meta name="description" content="Professional Chinese Notary Public service in Los Angeles. We provide Chinese language notary for contracts, power of attorney, and all document types. Serving Monterey Park and San Gabriel Valley.">

      <section class="hero">
        <h1>Chinese Notary Public in Los Angeles</h1>
        <p class="subtitle">专业华人公证服务 | Chinese Notary Service in Los Angeles, California</p>
        <div class="hero-btns">
          <router-link to="/order" class="btn btn-primary">📤 Upload Documents for Notary Quote</router-link>
          <a href="tel:6268663328" class="btn btn-outline">📞 Call Us</a>
        </div>
      </section>

      <section class="local-info">
        <div class="info-card">
          <h3>📍 Service Area</h3>
          <p>Los Angeles, Monterey Park, San Gabriel, Alhambra, and all of California</p>
        </div>
        <div class="info-card">
          <h3>📞</h3>
          <p>626-866-3328</p>
        </div>
        <div class="info-card">
          <h3>🌐 Languages</h3>
          <p>Chinese (中文) & English</p>
        </div>
      </section>

      <section class="service-detail">
        <h2>Our Notary Services</h2>
        
        <div class="service-item">
          <h3>📝 Power of Attorney (委托书)</h3>
          <p>Professional POA notarization for real estate, financial matters, immigration, and more. Chinese and English supported.</p>
        </div>

        <div class="service-item">
          <h3>🏠 Real Estate Documents (房产文件)</h3>
          <p>Notarization for property deeds, titles, contracts, and real estate transactions.</p>
        </div>

        <div class="service-item">
          <h3>📄 Contract Notarization (合同公证)</h3>
          <p>Business contracts, personal agreements, and legal documents notarization.</p>
        </div>

        <div class="service-item">
          <h3>🏢 On-Site Notary (上门公证)</h3>
          <p>We can come to your office, home, or any location in Los Angeles area for notarization.</p>
        </div>

        <div class="service-item">
          <h3>✍️ Signature Verification (签名公证)</h3>
          <p>Verify signatures on documents for various purposes including immigration and legal matters.</p>
        </div>
      </section>

      <section class="process">
        <h2>Notary Service Process</h2>
        <div class="steps">
          <div class="step"><span class="step-num">1</span><h4>Contact Us</h4><p>Call or upload documents</p></div>
          <div class="step"><span class="step-num">2</span><h4>Review</h4><p>We check your documents</p></div>
          <div class="step"><span class="step-num">3</span><h4>Notarize</h4><p>Sign and notarize</p></div>
          <div class="step"><span class="step-num">4</span><h4>Deliver</h4><p>Get certified documents</p></div>
        </div>
      </section>

      <section class="faq">
        <h2>Frequently Asked Questions</h2>
        
        <div class="faq-item">
          <h3>❓ Do you provide Chinese notary service in Los Angeles?</h3>
          <p>Yes! We specialize in Chinese-language notary services. Our staff speaks both Chinese and English.</p>
        </div>

        <div class="faq-item">
          <h3>❓ What documents can be notarized?</h3>
          <p>We notarize powers of attorney, contracts, real estate documents, affidavits, and more.</p>
        </div>

        <div class="faq-item">
          <h3>❓ Can you do on-site notarization?</h3>
          <p>Yes, we offer on-site notary services in Los Angeles, Monterey Park, and San Gabriel Valley areas.</p>
        </div>

        <div class="faq-item">
          <h3>❓ How much does notary cost in California?</h3>
          <p>Notary fees vary by document type. Contact us for a quote.</p>
        </div>

        <div class="faq-item">
          <h3>❓ What do I need for notarization?</h3>
          <p>Valid ID and the document(s) to be signed. For Chinese documents, bring your passport or Chinese ID.</p>
        </div>
      </section>

      <section class="cta-section">
        <h2>需要华人公证服务？</h2>
        <p>Contact us today for professional Chinese notary service in Los Angeles.</p>
        <router-link to="/order" class="btn btn-primary btn-large">📤 Get Notary Quote</router-link>
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
      "name": "Asian Service Inc - Chinese Notary",
      "description": "Chinese Notary Public service in Los Angeles",
      "telephone": "+1-626-866-3328",
      "areaServed": ["Los Angeles", "Monterey Park", "San Gabriel"],
      "serviceType": ["Chinese Notary", "Notary Public", "POA Notarization"]
    }
    </script>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue'
const isCN = ref(false)
const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
</script>

<style scoped>
.seo-page { min-height: 100vh; }
.header { display: flex; justify-content: space-between; align-items: center; padding: 15px 30px; background: #1a1a2e; color: white; }
.logo { color: white; font-size: 18px; font-weight: bold; text-decoration: none; }
.logo .cn { font-size: 12px; opacity: 0.8; }
.nav a { color: white; margin-left: 15px; text-decoration: none; }
.seo-content { max-width: 900px; margin: 0 auto; padding: 40px 20px; }
.hero { text-align: center; padding: 60px 20px; background: linear-gradient(135deg, #1a1a2e, #16213e); color: white; border-radius: 12px; margin-bottom: 40px; }
.hero h1 { font-size: 36px; margin-bottom: 15px; }
.hero .subtitle { font-size: 18px; opacity: 0.9; margin-bottom: 30px; }
.hero-btns { display: flex; gap: 15px; justify-content: center; flex-wrap: wrap; }
.btn { padding: 15px 30px; border-radius: 8px; text-decoration: none; font-weight: bold; }
.btn-primary { background: #ffd700; color: #1a1a2e; }
.btn-outline { border: 2px solid white; color: white; }
.btn-large { font-size: 18px; }
.local-info { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 40px; }
.info-card { background: white; padding: 20px; border-radius: 8px; text-align: center; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.service-detail h2, .process h2, .faq h2 { text-align: center; color: #1a1a2e; margin: 40px 0 30px; font-size: 28px; }
.service-item { background: white; padding: 25px; margin-bottom: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.service-item h3 { color: #1a1a2e; margin-bottom: 10px; }
.service-item p { color: #666; line-height: 1.6; }
.process { background: #f8f9fa; padding: 40px; border-radius: 12px; margin-bottom: 40px; }
.steps { display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 20px; }
.step { text-align: center; }
.step-num { display: inline-block; width: 40px; height: 40px; background: #ffd700; color: #1a1a2e; border-radius: 50%; font-weight: bold; line-height: 40px; margin-bottom: 10px; }
.faq-item { background: white; padding: 20px; margin-bottom: 15px; border-radius: 8px; border-left: 4px solid #ffd700; }
.faq-item h3 { color: #1a1a2e; font-size: 16px; margin-bottom: 10px; }
.faq-item p { color: #666; }
.cta-section { text-align: center; padding: 50px 20px; background: #1a1a2e; color: white; border-radius: 12px; margin-bottom: 40px; }
.contact-section { text-align: center; padding: 40px; background: white; border-radius: 12px; margin-bottom: 30px; }
.disclaimer { padding: 20px; background: #f5f5f5; border-radius: 8px; text-align: center; font-size: 13px; color: #666; }
</style>
