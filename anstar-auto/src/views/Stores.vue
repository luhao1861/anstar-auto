<template>
  <div class="stores-page">
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>门店列表</span>
          <el-button type="primary" @click="handleAdd">添加门店</el-button>
        </div>
      </template>
      
      <!-- 搜索 -->
      <el-row :gutter="20" style="margin-bottom: 20px">
        <el-col :span="6">
          <el-input v-model="searchForm.storeName" placeholder="搜索门店名称" clearable />
        </el-col>
        <el-col :span="6">
          <el-input v-model="searchForm.phone" placeholder="搜索电话" clearable />
        </el-col>
        <el-col :span="4">
          <el-button @click="resetSearch">重置</el-button>
        </el-col>
      </el-row>
      
      <el-table :data="paginatedList" stripe>
        <el-table-column prop="storeId" label="ID" width="60" />
        <el-table-column prop="storeName" label="门店名称" width="150" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="phone" label="电话" width="130" />
        <el-table-column prop="businessHours" label="营业时间" width="120" />
        <el-table-column prop="isActive" label="状态" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.isActive ? 'success' : 'danger'">
              {{ scope.row.isActive ? '营业中' : '已停业' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页 -->
      <el-pagination
        style="margin-top: 20px; text-align: right"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="filteredList.length"
        layout="total, sizes, prev, pager, next, jumper"
      />
    </el-card>
    
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑门店' : '添加门店'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.storeName" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="营业时间"><el-input v-model="form.businessHours" placeholder="例如: 9:00-18:00" /></el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.isActive" active-text="营业中" inactive-text="已停业" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const list = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const searchForm = ref({ storeName: '', phone: '' })
const form = ref({ storeId: null, storeName: '', address: '', phone: '', businessHours: '', isActive: true })

const API_BASE = '/api'

const loadData = async () => { try { const res = await fetch(`${API_BASE}/stores`); list.value = await res.json() } catch (e) { ElMessage.error('加载失败') } }

const filteredList = computed(() => {
  return list.value.filter(s => {
    const matchName = !searchForm.value.storeName || s.storeName?.toLowerCase().includes(searchForm.value.storeName.toLowerCase())
    const matchPhone = !searchForm.value.phone || s.phone?.includes(searchForm.value.phone)
    return matchName && matchPhone
  })
})

const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  return filteredList.value.slice(start, start + pageSize.value)
})

const handleAdd = () => { isEdit.value = false; form.value = { storeId: null, storeName: '', address: '', phone: '', businessHours: '', isActive: true }; dialogVisible.value = true }
const handleEdit = (row) => { isEdit.value = true; form.value = { ...row }; dialogVisible.value = true }
const handleSubmit = async () => {
  try {
    const res = await fetch(`${API_BASE}/stores`, { method: isEdit.value ? 'PUT' : 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(form.value) })
    if (res.ok) { ElMessage.success(isEdit.value ? '更新成功' : '添加成功'); dialogVisible.value = false; loadData() }
    else ElMessage.error('操作失败')
  } catch (e) { ElMessage.error('操作失败') }
}
const handleDelete = async (row) => {
  try { await ElMessageBox.confirm('确定要删除这个门店吗？', '提示', { type: 'warning' }); await fetch(`${API_BASE}/stores/${row.storeId}`, { method: 'DELETE' }); ElMessage.success('删除成功'); loadData() }
  catch (e) { if (e !== 'cancel') ElMessage.error('删除失败') }
}
const resetSearch = () => { searchForm.value = { storeName: '', phone: '' }; currentPage.value = 1 }

onMounted(() => { loadData() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
