<template>
  <div class="dashboard">
    <!-- Stats Cards -->
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6">
        <div class="stat-card stat-blue">
          <div class="stat-icon">🚗</div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.totalVehicles }}</div>
            <div class="stat-label">Total Vehicles</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-green">
          <div class="stat-icon">✅</div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.availableVehicles }}</div>
            <div class="stat-label">Available</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-orange">
          <div class="stat-icon">📋</div>
          <div class="stat-content">
            <div class="stat-value">{{ stats.activeRentals }}</div>
            <div class="stat-label">Active Rentals</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat-card stat-purple">
          <div class="stat-icon">💰</div>
          <div class="stat-content">
            <div class="stat-value">${{ stats.monthlyRevenue }}</div>
            <div class="stat-label">Monthly Revenue</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- Charts Row -->
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <span>Rental Trend (Last 7 Days)</span>
          </template>
          <div ref="rentalChart" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <span>Vehicle Status</span>
          </template>
          <div ref="vehicleChart" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- Second Row -->
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <span>Revenue by Month</span>
          </template>
          <div ref="revenueChart" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <span>Upcoming Maintenance</span>
          </template>
          <el-table :data="upcomingMaintenance" stripe style="width: 100%">
            <el-table-column prop="plateNumber" label="Vehicle" width="120" />
            <el-table-column prop="maintenanceType" label="Type" />
            <el-table-column prop="maintenanceDate" label="Date" width="120" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <!-- Quick Actions -->
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="24">
        <el-card class="action-card">
          <template #header>
            <span>Quick Actions</span>
          </template>
          <el-space wrap>
            <el-button type="primary" @click="$router.push('/vehicles')">➕ Add Vehicle</el-button>
            <el-button type="success" @click="$router.push('/rentals')">➕ New Rental</el-button>
            <el-button type="warning" @click="$router.push('/maintenance')">🔧 Maintenance</el-button>
            <el-button type="info" @click="$router.push('/reports')">📊 Reports</el-button>
          </el-space>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const API_BASE = '/api'
const stats = ref({
  totalVehicles: 0,
  availableVehicles: 0,
  activeRentals: 0,
  monthlyRevenue: 0
})

const upcomingMaintenance = ref([])
const rentalChart = ref(null)
const vehicleChart = ref(null)
const revenueChart = ref(null)

const loadData = async () => {
  try {
    const [vehicles, rentals, maintenance] = await Promise.all([
      fetch(`${API_BASE}/vehicles`).then(r => r.json()),
      fetch(`${API_BASE}/rentals`).then(r => r.json()),
      fetch(`${API_BASE}/maintenance`).then(r => r.json())
    ])

    // Stats
    stats.value.totalVehicles = vehicles.length
    stats.value.availableVehicles = vehicles.filter(v => v.status === 'AVAILABLE').length
    stats.value.activeRentals = rentals.filter(r => r.status === 'IN_PROGRESS' || r.status === 'CONFIRMED').length
    stats.value.monthlyRevenue = rentals
      .filter(r => r.status === 'IN_PROGRESS' || r.status === 'COMPLETED')
      .reduce((sum, r) => sum + (r.rentPrice || 0), 0)

    // Upcoming maintenance
    upcomingMaintenance.value = maintenance
      .filter(m => new Date(m.maintenanceDate) >= new Date())
      .sort((a, b) => new Date(a.maintenanceDate) - new Date(b.maintenanceDate))
      .slice(0, 5)

    // Rental Trend Chart
    const rentalTrendChart = echarts.init(rentalChart.value)
    rentalTrendChart.setOption({
      tooltip: { trigger: 'axis' },
      xAxis: { type: 'category', data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] },
      yAxis: { type: 'value' },
      series: [{
        data: [12, 15, 18, 14, 20, 25, 22],
        type: 'line',
        smooth: true,
        areaStyle: { opacity: 0.3 },
        itemStyle: { color: '#409EFF' }
      }]
    })

    // Vehicle Status Pie Chart
    const statusCount = {
      AVAILABLE: vehicles.filter(v => v.status === 'AVAILABLE').length,
      RENTED: vehicles.filter(v => v.status === 'RENTED').length,
      MAINTENANCE: vehicles.filter(v => v.status === 'MAINTENANCE').length
    }
    const vehiclePieChart = echarts.init(vehicleChart.value)
    vehiclePieChart.setOption({
      tooltip: { trigger: 'item' },
      series: [{
        type: 'pie',
        radius: ['40%', '70%'],
        data: [
          { value: statusCount.AVAILABLE, name: 'Available', itemStyle: { color: '#67C23A' } },
          { value: statusCount.RENTED, name: 'Rented', itemStyle: { color: '#E6A23C' } },
          { value: statusCount.MAINTENANCE, name: 'Maintenance', itemStyle: { color: '#F56C6C' } }
        ]
      }]
    })

    // Revenue Bar Chart
    const revenueBarChart = echarts.init(revenueChart.value)
    revenueBarChart.setOption({
      tooltip: { trigger: 'axis' },
      xAxis: { type: 'category', data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'] },
      yAxis: { type: 'value' },
      series: [{
        data: [12000, 15000, 18000, 14000, 20000, 25000],
        type: 'bar',
        itemStyle: { color: '#909399' }
      }]
    })

  } catch (e) {
    console.error('Error loading dashboard:', e)
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.dashboard { padding: 20px; }
.stat-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
}
.stat-icon { font-size: 36px; margin-right: 15px; }
.stat-value { font-size: 28px; font-weight: bold; color: #333; }
.stat-label { font-size: 14px; color: #666; }
.stat-blue { border-left: 4px solid #409EFF; }
.stat-green { border-left: 4px solid #67C23A; }
.stat-orange { border-left: 4px solid #E6A23C; }
.stat-purple { border-left: 4px solid #909399; }
.chart-card { height: 360px; }
.action-card { padding: 10px; }
</style>
