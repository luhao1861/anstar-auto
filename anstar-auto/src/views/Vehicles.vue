<template>
  <div class="vehicles-page">
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>车辆列表</span>
          <el-button type="primary" @click="handleAdd">添加车辆</el-button>
        </div>
      </template>
      
      <!-- 搜索 -->
      <el-row :gutter="20" style="margin-bottom: 20px">
        <el-col :span="6">
          <el-input v-model="searchForm.plateNumber" placeholder="搜索车牌号" clearable>
            <template #prefix><el-icon><Search /></el-icon></template>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchForm.brand" placeholder="品牌" clearable>
            <el-option label="Toyota" value="Toyota" />
            <el-option label="Honda" value="Honda" />
            <el-option label="BMW" value="BMW" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchForm.status" placeholder="状态" clearable>
            <el-option label="可用" value="AVAILABLE" />
            <el-option label="已租" value="RENTED" />
            <el-option label="维修中" value="MAINTENANCE" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-button @click="resetSearch">重置</el-button>
        </el-col>
      </el-row>
      
      <el-table :data="paginatedList" stripe>
        <el-table-column label="照片" width="80">
          <template #default="scope">
            <el-avatar v-if="scope.row.photo" :src="scope.row.photo" shape="square" :size="50" />
            <el-avatar v-else shape="square" :size="50" style="background:#f5f7fa">🚗</el-avatar>
          </template>
        </el-table-column>
        <el-table-column prop="plateNumber" label="车牌号" width="120" />
        <el-table-column prop="vin" label="VIN" width="180" />
        <el-table-column prop="brand" label="品牌" width="100" />
        <el-table-column prop="model" label="车型" width="120" />
        <el-table-column prop="year" label="年份" width="80" />
        <el-table-column prop="color" label="颜色" width="80" />
        <el-table-column prop="mileage" label="里程" width="100">
          <template #default="scope">{{ scope.row.mileage?.toLocaleString() }} km</template>
        </el-table-column>
        <el-table-column prop="dailyPrice" label="日租金" width="100">
          <template #default="scope">${{ scope.row.dailyPrice }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ getStatusText(scope.row.status) }}</el-tag>
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
    
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑车辆' : '添加车辆'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="车牌号"><el-input v-model="form.plateNumber" :disabled="isEdit" /></el-form-item>
        <el-form-item label="VIN"><el-input v-model="form.vin" placeholder="车架号 (17位)" /></el-form-item>
        <el-form-item label="品牌"><el-input v-model="form.brand" /></el-form-item>
        <el-form-item label="车型"><el-input v-model="form.model" /></el-form-item>
        <el-form-item label="年份"><el-input-number v-model="form.year" :min="2000" :max="2030" /></el-form-item>
        <el-form-item label="颜色"><el-input v-model="form.color" /></el-form-item>
        <el-form-item label="里程"><el-input-number v-model="form.mileage" :min="0" /></el-form-item>
        <el-form-item label="日租金"><el-input-number v-model="form.dailyPrice" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="照片">
          <el-upload action="#" :auto-upload="false" :on-change="handlePhotoChange" :show-file-list="false">
            <el-button>上传照片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status">
            <el-option label="可用" value="AVAILABLE" />
            <el-option label="已租" value="RENTED" />
            <el-option label="维修中" value="MAINTENANCE" />
            <el-option label="报废" value="RETIRED" />
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

const vehicleList = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const searchForm = ref({ plateNumber: '', brand: '', status: '' })
const form = ref({ plateNumber: '', vin: '', brand: '', model: '', year: 2024, color: '', mileage: 0, dailyPrice: 0, status: 'AVAILABLE', isActive: true })

const API_BASE = '/api'

const loadVehicles = async () => {
  try {
    const res = await fetch(`${API_BASE}/vehicles`)
    vehicleList.value = await res.json()
  } catch (e) { ElMessage.error('加载失败') }
}

const filteredList = computed(() => {
  return vehicleList.value.filter(v => {
    const matchPlate = !searchForm.value.plateNumber || v.plateNumber?.toLowerCase().includes(searchForm.value.plateNumber.toLowerCase())
    const matchBrand = !searchForm.value.brand || v.brand === searchForm.value.brand
    const matchStatus = !searchForm.value.status || v.status === searchForm.value.status
    return matchPlate && matchBrand && matchStatus
  })
})

const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  return filteredList.value.slice(start, start + pageSize.value)
})

const resetSearch = () => { searchForm.value = { plateNumber: '', brand: '', status: '' }; currentPage.value = 1 }
const getStatusType = (status) => ({ 'AVAILABLE': 'success', 'RENTED': 'warning', 'MAINTENANCE': 'info', 'RETIRED': 'danger' }[status] || 'info')
const getStatusText = (status) => ({ 'AVAILABLE': '可用', 'RENTED': '已租', 'MAINTENANCE': '维修中', 'RETIRED': '报废' }[status] || status)

const handleAdd = () => { isEdit.value = false; form.value = { plateNumber: '', vin: '', brand: '', model: '', year: 2024, color: '', mileage: 0, dailyPrice: 0, status: 'AVAILABLE', isActive: true }; dialogVisible.value = true }
const handleEdit = (row) => { isEdit.value = true; form.value = { ...row }; dialogVisible.value = true }
const handlePhotoChange = (file) => {
  const reader = new FileReader()
  reader.onload = (e) => { form.value.photo = e.target.result }
  reader.readAsDataURL(file.raw)
}
const handleSubmit = async () => {
  try {
    const res = await fetch(`${API_BASE}/vehicles`, { method: isEdit.value ? 'PUT' : 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(form.value) })
    if (res.ok) { ElMessage.success(isEdit.value ? '更新成功' : '添加成功'); dialogVisible.value = false; loadVehicles() }
    else ElMessage.error('操作失败')
  } catch (e) { ElMessage.error('操作失败') }
}
const handleDelete = async (row) => {
  try { await ElMessageBox.confirm('确定要删除这辆车吗？', '提示', { type: 'warning' }); await fetch(`${API_BASE}/vehicles/${row.plateNumber}`, { method: 'DELETE' }); ElMessage.success('删除成功'); loadVehicles() }
  catch (e) { if (e !== 'cancel') ElMessage.error('删除失败') }
}

onMounted(() => { loadVehicles() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
