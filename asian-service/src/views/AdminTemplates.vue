<template>
  <div class="admin-page">
    <header class="header">
      <router-link to="/" class="logo">Asian Service Admin</router-link>
      <nav class="nav">
        <router-link to="/admin/orders">Orders</router-link>
        <router-link to="/admin/templates">Templates</router-link>
        <span @click="logout">Logout</span>
      </nav>
    </header>

    <div class="admin-content">
      <h1>Template Management</h1>
      
      <el-button type="primary" @click="openDialog()" style="margin-bottom: 20px">Add Template</el-button>

      <el-table :data="templates" style="width: 100%">
        <el-table-column prop="name" label="Name" />
        <el-table-column prop="category" label="Category" width="120" />
        <el-table-column prop="description" label="Description" />
        <el-table-column prop="downloadCount" label="Downloads" width="100" />
        <el-table-column label="Actions" width="150">
          <template #default="scope">
            <el-button size="small" @click="openDialog(scope.row)">Edit</el-button>
            <el-button size="small" type="danger" @click="deleteT(scope.row)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>

      <h2 style="margin-top: 40px">Download Records</h2>
      <el-table :data="downloads" style="width: 100%">
        <el-table-column prop="name" label="Name" />
        <el-table-column prop="phone" label="Phone" />
        <el-table-column prop="wechat" label="WeChat" />
        <el-table-column prop="filePurpose" label="Purpose" />
        <el-table-column prop="downloadedAt" label="Date" />
      </el-table>
    </div>

    <el-dialog v-model="dialogVisible" :title="isEdit ? 'Edit Template' : 'Add Template'" width="500px">
      <el-form :model="form">
        <el-form-item label="Name"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="Category">
          <el-select v-model="form.category" style="width: 100%">
            <el-option label="Notary" value="NOTARY" />
            <el-option label="Translation" value="TRANSLATION" />
            <el-option label="Apostille" value="APOSTILLE" />
            <el-option label="Immigration" value="IMMIGRATION" />
          </el-select>
        </el-form-item>
        <el-form-item label="Description"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="File URL"><el-input v-model="form.fileUrl" placeholder="https://..." /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Save</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const API = 'http://localhost:8081/api'
const templates = ref([])
const downloads = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = ref({ id: null, name: '', category: '', description: '', fileUrl: '' })

onMounted(() => {
  loadData()
})

const loadData = async () => {
  templates.value = await fetch(`${API}/templates`).then(r => r.json())
  downloads.value = await fetch(`${API}/templates/downloads`).then(r => r.json())
}

const openDialog = (t = null) => {
  if (t) { form.value = { ...t }; isEdit.value = true }
  else { form.value = { name: '', category: '', description: '', fileUrl: '' }; isEdit.value = false }
  dialogVisible.value = true
}

const save = async () => {
  if (isEdit.value) {
    await fetch(`${API}/templates/${form.value.id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(form.value) })
  } else {
    await fetch(`${API}/templates`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(form.value) })
  }
  dialogVisible.value = false
  loadData()
}

const deleteT = async (t) => {
  if (confirm('Delete?')) {
    await fetch(`${API}/templates/${t.id}`, { method: 'DELETE' })
    loadData()
  }
}

const logout = () => { localStorage.removeItem('user'); router.push('/') }
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; padding: 20px 50px; background: #1a1a2e; color: white; }
.logo { color: white; font-size: 20px; font-weight: bold; text-decoration: none; }
.nav a, .nav span { color: white; margin-left: 20px; text-decoration: none; cursor: pointer; }
.admin-content { padding: 40px 50px; }
</style>
