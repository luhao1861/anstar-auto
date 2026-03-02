<template>
  <div id="app">
    <!-- Language Toggle -->
    <div class="lang-toggle" @click="toggleLang">
      {{ isCN ? 'EN' : '中文' }}
    </div>
    <router-view />
  </div>
</template>

<script setup>
import { ref, provide } from 'vue'

const isCN = ref(false)

const toggleLang = () => {
  isCN.value = !isCN.value
}

provide('isCN', isCN)
provide('toggleLang', toggleLang)
</script>

<style>
* { margin: 0; padding: 0; box-sizing: border-box; }
body { font-family: 'Helvetica Neue', Arial, sans-serif; background: #f8f9fa; color: #333; }
.lang-toggle {
  position: fixed; top: 15px; right: 15px; z-index: 9999;
  background: #ffd700; color: #1a1a2e; padding: 8px 15px;
  border-radius: 20px; cursor: pointer; font-weight: bold;
}
</style>
