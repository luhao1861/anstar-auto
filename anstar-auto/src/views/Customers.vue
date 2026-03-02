<template>
  <div class="customers-page">
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>客户列表</span>
          <el-button type="primary" @click="handleAdd">添加客户</el-button>
        </div>
      </template>
      
      <!-- 搜索 -->
      <el-row :gutter="20" style="margin-bottom: 20px">
        <el-col :span="5">
          <el-input v-model="searchForm.name" placeholder="搜索姓名" clearable>
            <template #prefix><el-icon><Search /></el-icon></template>
          </el-input>
        </el-col>
        <el-col :span="5">
          <el-input v-model="searchForm.phone" placeholder="搜索电话" clearable />
        </el-col>
        <el-col :span="5">
          <el-input v-model="searchForm.wechatId" placeholder="搜索微信" clearable />
        </el-col>
        <el-col :span="4">
          <el-button @click="resetSearch">重置</el-button>
        </el-col>
      </el-row>
      
      <el-table :data="paginatedList" stripe>
        <el-table-column prop="customerId" label="ID" width="60" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="phone" label="电话" width="130" />
        <el-table-column prop="wechatId" label="微信" width="130" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="driverLicense" label="驾驶证" width="120" />
        <el-table-column prop="membershipLevel" label="会员等级" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.membershipLevel === 2 ? 'warning' : 'info'">VIP {{ scope.row.membershipLevel }}</el-tag>
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
    
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑客户' : '添加客户'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="姓名"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="微信"><el-input v-model="form.wechatId" /></el-form-item>
        <el-form-item label="邮箱"><el-input v-model="form.email" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="驾驶证"><el-input v-model="form.driverLicense" /></el-form-item>
        <el-form-item label="会员等级">
          <el-select v-model="form.membershipLevel">
            <el-option label="普通会员" :value="1" />
            <el-option label="VIP" :value="2" />
            <el-option label="SVIP" :value="3" />
          </el-select>
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
import { Search } from '@element-plus/icons-vue'

const customerList = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const searchForm = ref({ name: '', phone: '', wechatId: '' })
const form = ref({ customerId: null, name: '', phone: '', wechatId: '', email: '', address: '', driverLicense: '', membershipLevel: 1, isActive: true })

const API_BASE = '/api'

const loadCustomers = async () => { try { const res = await fetch(`${API_BASE}/customers`); customerList.value = await res.json() } catch (e) { ElMessage.error('加载失败') } }

const filteredList = computed(() => {
  return customerList.value.filter(c => {
    const matchName = !searchForm.value.name || c.name?.toLowerCase().includes(searchForm.value.name.toLowerCase())
    const matchPhone = !searchForm.value.phone || c.phone?.includes(searchForm.value.phone)
    const matchWechat = !searchForm.value.wechatId || c.wechatId?.toLowerCase().includes(searchForm.value.wechatId.toLowerCase())
    return matchName && matchPhone && matchWechat
  })
})

const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  return filteredList.value.slice(start, start + pageSize.value)
})

const resetSearch = () => { searchForm.value = { name: '', phone: '', wechatId: '' }; currentPage.value = 1 }

const handleAdd = () => { isEdit.value = false; form.value = { customerId: null, name: '', phone: '', wechatId: '', email: '', address: '', driverLicense: '', membershipLevel: 1, isActive: true }; dialogVisible.value = true }
const handleEdit = (row) => { isEdit.value = true; form.value = { ...row }; dialogVisible.value = true }
const handleSubmit = async () => {
  try {
    const res = await fetch(`${API_BASE}/customers`, { method: isEdit.value ? 'PUT' : 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(form.value) })
    if (res.ok) { ElMessage.success(isEdit.value ? '更新成功' : '添加成功'); dialogVisible.value = false; loadCustomers() }
    else ElMessage.error('操作失败')
  } catch (e) { ElMessage.error('操作失败') }
}
const handleDelete = async (row) => {
  try { await ElMessageBox.confirm('确定要删除这个客户吗？', '提示', { type: 'warning' }); await fetch(`${API_BASE}/customers/${row.customerId}`, { method: 'DELETE' }); ElMessage.success('删除成功'); loadCustomers() }
  catch (e) { if (e !== 'cancel') ElMessage.error('删除失败') }
}

onMounted(() => { loadCustomers() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
