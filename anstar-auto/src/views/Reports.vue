<template>
  <div class="reports-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>📊 Financial Reports</span>
          <el-button type="primary" @click="exportPDF">Export PDF</el-button>
        </div>
      </template>
      
      <!-- Date Range Filter -->
      <el-row :gutter="20" style="margin-bottom: 20px">
        <el-col :span="6">
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="to"
            start-placeholder="Start Date"
            end-placeholder="End Date"
            style="width: 100%"
            @change="loadData"
          />
        </el-col>
        <el-col :span="4">
          <el-select v-model="reportType" @change="loadData" style="width: 100%">
            <el-option label="All Rentals" value="all" />
            <el-option label="Completed" value="completed" />
            <el-option label="In Progress" value="in_progress" />
          </el-select>
        </el-col>
      </el-row>

      <!-- Summary Cards -->
      <el-row :gutter="20" style="margin-bottom: 20px">
        <el-col :span="6">
          <div class="summary-card">
            <div class="summary-label">Total Revenue</div>
            <div class="summary-value">${{ summary.totalRevenue.toLocaleString() }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="summary-card">
            <div class="summary-label">Total Rentals</div>
            <div class="summary-value">{{ summary.totalRentals }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="summary-card">
            <div class="summary-label">Average Rental</div>
            <div class="summary-value">${{ summary.avgRental.toLocaleString() }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="summary-card">
            <div class="summary-label">Total Deposits</div>
            <div class="summary-value">${{ summary.totalDeposits.toLocaleString() }}</div>
          </div>
        </el-col>
      </el-row>

      <!-- Revenue Chart -->
      <el-card style="margin-bottom: 20px">
        <template #header>Revenue Trend</template>
        <div ref="revenueChart" style="height: 300px"></div>
      </el-card>

      <!-- Detailed Table -->
      <el-card>
        <template #header>
          <span>Rental Details</span>
        </template>
        <el-table :data="filteredRentals" stripe>
          <el-table-column prop="rentalId" label="ID" width="60" />
          <el-table-column prop="plateNumber" label="Vehicle" width="120" />
          <el-table-column prop="customerId" label="Customer ID" width="100" />
          <el-table-column label="Period" width="180">
            <template #default="scope">{{ scope.row.startDate }} ~ {{ scope.row.endDate }}</template>
          </el-table-column>
          <el-table-column prop="rentPrice" label="Amount" width="100">
            <template #default="scope">${{ scope.row.rentPrice }}</template>
          </el-table-column>
          <el-table-column prop="deposit" label="Deposit" width="100">
            <template #default="scope">${{ scope.row.deposit || 0 }}</template>
          </el-table-column>
          <el-table-column prop="paymentMethod" label="Payment" width="100" />
          <el-table-column prop="status" label="Status" width="100">
            <template #default="scope">
              <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import * as echarts from 'echarts'

const API_BASE = '/api'
const rentals = ref([])
const dateRange = ref([])
const reportType = ref('all')
const revenueChart = ref(null)

const summary = ref({
  totalRevenue: 0,
  totalRentals: 0,
  avgRental: 0,
  totalDeposits: 0
})

const filteredRentals = computed(() => {
  let result = rentals.value
  if (reportType.value === 'completed') {
    result = result.filter(r => r.status === 'COMPLETED')
  } else if (reportType.value === 'in_progress') {
    result = result.filter(r => r.status === 'IN_PROGRESS' || r.status === 'CONFIRMED')
  }
  return result
})

const loadData = async () => {
  try {
    rentals.value = await fetch(`${API_BASE}/rentals`).then(r => r.json())
    
    // Calculate summary
    const completed = rentals.value.filter(r => r.status === 'COMPLETED' || r.status === 'IN_PROGRESS')
    summary.value.totalRentals = completed.length
    summary.value.totalRevenue = completed.reduce((sum, r) => sum + (r.rentPrice || 0), 0)
    summary.value.totalDeposits = completed.reduce((sum, r) => sum + (r.deposit || 0), 0)
    summary.value.avgRental = completed.length ? Math.round(summary.value.totalRevenue / completed.length) : 0

    // Render chart
    setTimeout(() => {
      const chart = echarts.init(revenueChart.value)
      chart.setOption({
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'] },
        yAxis: { type: 'value' },
        series: [{
          data: [12000, 15000, 18000, 14000, 20000, 25000, 22000, 28000, 30000, 25000, 20000, 35000],
          type: 'bar',
          itemStyle: { color: '#409EFF' }
        }]
      })
    }, 100)

  } catch (e) {
    console.error('Error:', e)
  }
}

const getStatusType = (status) => {
  const types = { 'COMPLETED': 'success', 'IN_PROGRESS': 'warning', 'CONFIRMED': 'primary', 'PENDING': 'info', 'CANCELLED': 'danger' }
  return types[status] || 'info'
}

const exportPDF = () => {
  window.print()
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.reports-page { padding: 20px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.summary-card { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 12px; padding: 20px; color: white; text-align: center; }
.summary-label { font-size: 14px; opacity: 0.9; }
.summary-value { font-size: 28px; font-weight: bold; margin-top: 10px; }
</style>
