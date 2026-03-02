<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script setup>
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

#app {
  font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  min-height: 100vh;
  background-color: #f0f2f5;
}

body {
  margin: 0;
}

.table-card {
  border-radius: 16px !important;
  border: none !important;
  box-shadow: 0 4px 20px rgba(0,0,0,0.06) !important;
}

.table-card :deep(.el-card__header) {
  border-bottom: 1px solid #f0f0f0;
  padding: 18px 24px;
}

.table-card :deep(.el-card__body) {
  padding: 24px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
  font-weight: 600;
  color: #1a1a2e;
}

:deep(.el-table) {
  font-size: 14px;
}

:deep(.el-table th) {
  background: #f8f9fc !important;
  font-weight: 600;
  color: #1a1a2e;
}

:deep(.el-table td) {
  padding: 14px 0;
}

:deep(.el-pagination) {
  padding: 16px 0;
}

:deep(.el-button--primary) {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
}

:deep(.el-button--primary:hover) {
  background: linear-gradient(135deg, #5a6fd6 0%, #6a4190 100%);
}

:deep(.el-input__wrapper) {
  border-radius: 10px;
}

:deep(.el-select .el-input__wrapper) {
  border-radius: 10px;
}

:deep(.el-dialog) {
  border-radius: 16px;
}

:deep(.el-dialog__header) {
  border-bottom: 1px solid #f0f0f0;
  padding: 20px 24px;
}

:deep(.el-dialog__title) {
  font-weight: 600;
  color: #1a1a2e;
}
</style>
