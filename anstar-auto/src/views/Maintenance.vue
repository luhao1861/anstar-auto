<template>
  <div class="maintenance-page">
    <!-- Reminder Cards -->
    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :span="8">
        <el-card class="reminder-card overdue">
          <template #header>
            <span>⚠️ Overdue Maintenance</span>
          </template>
          <el-alert v-if="overdueCount > 0" :title="overdueCount + ' vehicles need immediate attention'" type="error" :closable="false" />
          <span v-else>No overdue maintenance</span>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="reminder-card upcoming">
          <template #header>
            <span>📅 This Week</span>
          </template>
          <el-alert v-if="thisWeekCount > 0" :title="thisWeekCount + ' vehicles due this week'" type="warning" :closable="false" />
          <span v-else>No maintenance due this week</span>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="reminder-card next">
          <template #header>
            <span>📆 Next 30 Days</span>
          </template>
          <el-alert v-if="nextMonthCount > 0" :title="nextMonthCount + ' vehicles due next month'" type="info" :closable="false" />
          <span v-else>No maintenance scheduled</span>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>维修保养记录 Maintenance Records</span>
          <el-button type="primary" @click="handleAdd">➕ 添加记录</el-button>
        </div>
      </template>
      
      <!-- 搜索 -->
      <el-row :gutter="20" style="margin-bottom: 20px">
        <el-col :span="5">
          <el-input v-model="searchForm.plateNumber" placeholder="搜索车牌号" clearable />
        </el-col>
        <el-col :span="5">
          <el-select v-model="searchForm.maintenanceType" placeholder="保养类型" clearable>
            <el-option label="换机油" value="Oil Change" />
            <el-option label="刹车保养" value="Brake Service" />
            <el-option label="轮胎保养" value="Tire Service" />
          </el-select>
        </el-col>
        <el-col :span="5">
          <el-select v-model="searchForm.status" placeholder="状态" clearable>
            <el-option label="计划中" value="PLANNED" />
            <el-option label="进行中" value="IN_PROGRESS" />
            <el-option label="已完成" value="COMPLETED" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-button @click="resetSearch">重置</el-button>
        </el-col>
      </el-row>
      
      <el-table :data="paginatedList" stripe>
        <el-table-column prop="maintenanceId" label="ID" width="60" />
        <el-table-column prop="plateNumber" label="车牌号" width="100" />
        <el-table-column prop="maintenanceType" label="保养类型" width="120" />
        <el-table-column prop="maintenanceDate" label="保养日期" width="120" />
        <el-table-column prop="cost" label="费用" width="80"><template #default="scope">${{ scope.row.cost }}</template></el-table-column>
        <el-table-column prop="vendor" label="维修商" width="150" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="scope"><el-tag :type="getStatusType(scope.row.status)">{{ getStatusText(scope.row.status) }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="120">
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
    
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑记录' : '添加记录'" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="车牌号"><el-input v-model="form.plateNumber" /></el-form-item>
        <el-form-item label="保养类型">
          <el-select v-model="form.maintenanceType">
            <el-option label="换机油" value="Oil Change" />
            <el-option label="刹车保养" value="Brake Service" />
            <el-option label="轮胎保养" value="Tire Service" />
            <el-option label="常规保养" value="General Maintenance" />
            <el-option label="其他" value="Other" />
          </el-select>
        </el-form-item>
        <el-form-item label="保养日期"><el-date-picker v-model="form.maintenanceDate" type="date" value-format="YYYY-MM-DD" /></el-form-item>
        <el-form-item label="费用"><el-input-number v-model="form.cost" :min="0" :precision="2" /></el-form-item>
        <el-form-item label="维修商"><el-input v-model="form.vendor" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status">
            <el-option label="计划中" value="PLANNED" />
            <el-option label="进行中" value="IN_PROGRESS" />
            <el-option label="已完成" value="COMPLETED" />
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

const list = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const searchForm = ref({ plateNumber: '', maintenanceType: '', status: '' })
const form = ref({ maintenanceId: null, plateNumber: '', maintenanceType: 'Oil Change', maintenanceDate: '', cost: 0, vendor: '', description: '', status: 'PLANNED' })

const API_BASE = '/api'

const loadData = async () => { try { const res = await fetch(`${API_BASE}/maintenance`); list.value = await res.json() } catch (e) { ElMessage.error('加载失败') } }

const filteredList = computed(() => {
  return list.value.filter(m => {
    const matchPlate = !searchForm.value.plateNumber || m.plateNumber?.toLowerCase().includes(searchForm.value.plateNumber.toLowerCase())
    const matchType = !searchForm.value.maintenanceType || m.maintenanceType === searchForm.value.maintenanceType
    const matchStatus = !searchForm.value.status || m.status === searchForm.value.status
    return matchPlate && matchType && matchStatus
  })
})

// Reminder counts
const today = new Date()
const weekFromNow = new Date(today.getTime() + 7 * 24 * 60 * 60 * 1000)

const overdueCount = computed(() => list.value.filter(m => {
  const d = new Date(m.maintenanceDate)
  return d < today && m.status !== 'COMPLETED'
}).length)

const thisWeekCount = computed(() => {
  const weekFromNow = new Date(today.getTime() + 7 * 24 * 60 * 60 * 1000)
  return list.value.filter(m => {
    const d = new Date(m.maintenanceDate)
    return d >= today && d <= weekFromNow && m.status !== 'COMPLETED'
  }).length
})

const nextMonthCount = computed(() => {
  const monthFromNow = new Date(today.getTime() + 30 * 24 * 60 * 60 * 1000)
  return list.value.filter(m => {
    const d = new Date(m.maintenanceDate)
    return d > weekFromNow && d <= monthFromNow && m.status !== 'COMPLETED'
  }).length
})

const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  return filteredList.value.slice(start, start + pageSize.value)
})

const getStatusType = (status) => ({ 'PLANNED': 'info', 'IN_PROGRESS': 'warning', 'COMPLETED': 'success' }[status] || 'info')
const getStatusText = (status) => ({ 'PLANNED': '计划中', 'IN_PROGRESS': '进行中', 'COMPLETED': '已完成' }[status] || status)
const handleAdd = () => { isEdit.value = false; form.value = { maintenanceId: null, plateNumber: '', maintenanceType: 'Oil Change', maintenanceDate: '', cost: 0, vendor: '', description: '', status: 'PLANNED' }; dialogVisible.value = true }
const handleEdit = (row) => { isEdit.value = true; form.value = { ...row }; dialogVisible.value = true }
const handleSubmit = async () => {
  try {
    const res = await fetch(`${API_BASE}/maintenance`, { method: isEdit.value ? 'PUT' : 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(form.value) })
    if (res.ok) { ElMessage.success(isEdit.value ? '更新成功' : '添加成功'); dialogVisible.value = false; loadData() }
    else ElMessage.error('操作失败')
  } catch (e) { ElMessage.error('操作失败') }
}
const handleDelete = async (row) => {
  try { await ElMessageBox.confirm('确定要删除这条记录吗？', '提示', { type: 'warning' }); await fetch(`${API_BASE}/maintenance/${row.maintenanceId}`, { method: 'DELETE' }); ElMessage.success('删除成功'); loadData() }
  catch (e) { if (e !== 'cancel') ElMessage.error('删除失败') }
}
const resetSearch = () => { searchForm.value = { plateNumber: '', maintenanceType: '', status: '' }; currentPage.value = 1 }

onMounted(() => { loadData() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>

<style scoped>
.reminder-card { text-align: center; }
.reminder-card.overdue { border-left: 4px solid #F56C6C; }
.reminder-card.upcoming { border-left: 4px solid #E6A23C; }
.reminder-card.next { border-left: 4px solid #409EFF; }
</style>
