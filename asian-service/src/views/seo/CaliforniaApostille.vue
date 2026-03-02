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
      <title>California Apostille Service | Fast & Reliable | Asian Service</title>
      <meta name="description" content="California Apostille service for all document types. Fast 3-5 day turnaround. We handle state documents, federal documents, Chinese documents. Get free quote today.">

      <section class="hero">
        <h1>California Apostille Service</h1>
        <p class="subtitle">Fast, Reliable, Affordable Apostille Authentication in California</p>
        <div class="hero-btns">
          <router-link to="/order" class="btn btn-primary">📤 Get Free Quote</router-link>
          <a href="tel:6268663328" class="btn btn-outline">📞 Call Now</a>
        </div>
      </section>

      <section class="local-info">
        <div class="info-card"><h3>📍 California Wide</h3><p>Los Angeles, SF, San Diego, Sacramento</p></div>
        <div class="info-card"><h3>⚡ Fast</h3><p>3-5 days standard, rush available</p></div>
        <div class="info-card"><h3>💰 Affordable</h3><p>Competitive pricing</p></div>
      </section>

      <section class="service-detail">
        <h2>California Apostille Services</h2>
        
        <div class="service-item">
          <h3>🏛️ California Secretary of State Documents</h3>
          <p>Birth certificates, marriage certificates, death certificates, corporation documents, and more.</p>
        </div>

        <div class="service-item">
          <h3>🇫 Federal Documents</h3>
          <p>FBI background checks, federal court documents, HHS documents.</p>
        </div>

        <div class="service-item">
          <h3>🇨🇳 Chinese Documents</h3>
          <p>Authentication of Chinese documents for use in the US and vice versa.</p>
        </div>

        <div class="service-item">
          <h3>🔄 Rush Apostille</h3>
          <p>1-2 business day rush service available for urgent needs.</p>
        </div>
      </section>

      <section class="faq">
        <h2>California Apostille FAQ</h2>
        
        <div class="faq-item">
          <h3>❓ How long does California Apostille take?</h3>
          <p>Standard: 3-5 business days. Rush: 1-2 business days.</p>
        </div>

        <div class="faq-item">
          <h3>❓ What documents need Apostille in California?</h3>
          <p>Documents to be used in Hague Convention countries need Apostille authentication.</p>
        </div>

        <div class="faq-item">
          <h3>❓ Can I get Apostille for documents from China?</h3>
          <p>Yes, we handle Chinese document authentication for use in the US.</p>
        </div>

        <div class="faq-item">
          <h3>❓ What is the difference between Apostille and Notary?</h3>
          <p>Notary is for US documents. Apostille is international certification for Hague Convention countries.</p>
        </div>
      </section>

      <section class="cta-section">
        <h2>Get California Apostille Quote</h2>
        <p>Upload your documents for a free quote within 24 hours.</p>
        <router-link to="/order" class="btn btn-primary btn-large">📤 Upload Documents</router-link>
      </section>

      <section class="contact-section">
        <h2>Contact Us</h2>
        <p>📞 626-866-3328</p>
        <p>📧 info@asianservice.com</p>
        <p>📍 California</p>
      </section>

      <section class="disclaimer">
        <p><strong>Disclaimer:</strong> We provide administrative document services only.</p>
      </section>
    </div>
  </div>
</template>

<script setup>
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
.btn-large { font-size: 18px; }
.local-info { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-bottom: 40px; }
.info-card { background: white; padding: 20px; border-radius: 8px; text-align: center; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.service-detail h2, .faq h2 { text-align: center; color: #1a1a2e; margin: 40px 0 30px; }
.service-item { background: white; padding: 25px; margin-bottom: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
.service-item h3 { color: #1a1a2e; margin-bottom: 10px; }
.service-item p { color: #666; }
.faq-item { background: white; padding: 20px; margin-bottom: 15px; border-radius: 8px; border-left: 4px solid #ffd700; }
.faq-item h3 { color: #1a1a2e; font-size: 16px; margin-bottom: 10px; }
.faq-item p { color: #666; }
.cta-section { text-align: center; padding: 50px 20px; background: #1a1a2e; color: white; border-radius: 12px; margin-bottom: 40px; }
.contact-section { text-align: center; padding: 40px; background: white; border-radius: 12px; margin-bottom: 30px; }
.disclaimer { padding: 20px; background: #f5f5f5; border-radius: 8px; text-align: center; font-size: 13px; color: #666; }
@media (max-width: 768px) { .local-info { grid-template-columns: 1fr; } }
</style>
