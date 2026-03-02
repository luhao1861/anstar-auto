<template>
  <div class="rentals-page">
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>租赁记录</span>
          <el-button type="primary" @click="handleAdd">添加租赁</el-button>
        </div>
      </template>
      
      <el-row :gutter="20" style="margin-bottom: 20px">
        <el-col :span="3">
          <el-input v-model="searchForm.plateNumber" placeholder="车牌号" clearable @input="handleSearch" />
        </el-col>
        <el-col :span="3">
          <el-input v-model="searchForm.customerName" placeholder="客户姓名" clearable @input="handleSearch" />
        </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="searchForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            style="width: 100%"
            @change="handleSearch"
          />
        </el-col>
        <el-col :span="3">
          <el-select v-model="searchForm.status" placeholder="状态" clearable @change="handleSearch">
            <el-option label="待确认" value="PENDING" />
            <el-option label="已确认" value="CONFIRMED" />
            <el-option label="进行中" value="IN_PROGRESS" />
            <el-option label="已完成" value="COMPLETED" />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-button @click="resetSearch">重置</el-button>
          <el-button type="success" @click="exportData">导出</el-button>
        </el-col>
      </el-row>
      
      <el-table :data="paginatedList" stripe>
        <el-table-column prop="rentalId" label="ID" width="60" />
        <el-table-column label="车牌号" width="120">
          <template #default="scope">
            <el-link type="primary" @click="showVehicleDetail(scope.row.plateNumber)">{{ scope.row.plateNumber }}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="客户" width="120">
          <template #default="scope">
            <el-link type="success" @click="showCustomerDetail(scope.row.customerId)">{{ getCustomerName(scope.row.customerId) }}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="租赁Date" width="180">
          <template #default="scope">{{ scope.row.startDate }} ~ {{ scope.row.endDate }}</template>
        </el-table-column>
        <el-table-column prop="rentPrice" label="租金" width="80"><template #default="scope">${{ scope.row.rentPrice }}</template></el-table-column>
        <el-table-column label="应收" width="80"><template #default="scope">${{ scope.row.shouldReceive || '-' }}</template></el-table-column>
        <el-table-column label="实收" width="80"><template #default="scope">${{ scope.row.actualReceived || '-' }}</template></el-table-column>
        <el-table-column label="欠款" width="80">
          <template #default="scope">
            <el-tag v-if="scope.row.debt > 0" type="danger">${{ scope.row.debt }}</el-tag>
            <el-tag v-else type="success">已付</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="90">
          <template #default="scope"><el-tag :type="getStatusType(scope.row.status)">{{ getStatusText(scope.row.status) }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="450">
          <template #default="scope">
            <el-button size="small" @click="viewContract(scope.row)">📄 合同</el-button>
            <el-button size="small" type="success" @click="generateContract(scope.row.rentalId)" v-if="!scope.row.hasContract">生成合同</el-button>
            <el-button size="small" type="warning" @click="openExtendDialog(scope.row)" v-if="scope.row.status === 'IN_PROGRESS'">⏰ 延期</el-button>
            <el-button size="small" type="info" @click="openEarlyReturnDialog(scope.row)" v-if="scope.row.status === 'IN_PROGRESS'">🔙 还车</el-button>
            <el-button size="small" type="primary" @click="openPaymentDialog(scope.row)">💰 收款</el-button>
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination style="margin-top: 20px; text-align: right" v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[10, 20, 50, 100]" :total="filteredList.length" layout="total, sizes, prev, pager, next, jumper" />
    </el-card>
    
    <!-- 合同预览对话框 -->
    <el-dialog v-model="contractDialogVisible" title="Vehicle Rental Agreement" width="850px">
      <div class="contract-content" id="contract-print" v-if="selectedContract">
        <div class="contract-header"><h2>🚗 安星汽车Vehicle Rental Agreement</h2><p>Vehicle Borrow Agreement</p></div>
        <div class="contract-section"><h4>A. A. Lender (Anstar Auto)</h4><p><strong>安星汽车 / Anstar Auto</strong></p><p>Address: 123 Main St, Los Angeles, CA 90001</p></div>
        <div class="contract-section"><h4>B. B. Borrower</h4><div class="info-grid"><p>Name: <strong>{{ selectedContract.customerName }}</strong></p><p>Phone: <strong>{{ selectedContract.customerPhone }}</strong></p><p>WeChat: <strong>{{ selectedContract.customerWechat || 'N/A' }}</strong></p><p>Email: <strong>{{ selectedContract.customerEmail || 'N/A' }}</strong></p><p>ID: <strong>{{ selectedContract.customerIdNumber }}</strong></p><p>Address: <strong>{{ selectedContract.customerAddress || 'N/A' }}</strong></p></div></div>
        <div class="contract-section"><h4>I. 车辆信息</h4><div class="info-grid"><p>Vehicle: <strong>{{ selectedContract.vehicleInfo }}</strong></p><p>Plate: <strong>{{ selectedContract.plateNumber }}</strong></p><p>Odometer: <strong>{{ selectedContract.odometerStart }} miles</strong></p><p>Fuel: <strong>{{ selectedContract.fuelLevel }}</strong></p></div></div>
        <div class="contract-section"><h4>II. 租赁详情</h4><div class="info-grid"><p>Period: <strong>{{ selectedContract.startDate }} ~ {{ selectedContract.endDate }}</strong></p><p>Price: <strong>${{ selectedContract.monthlyPrice }}</strong></p><p>Deposit: <strong>${{ selectedContract.deposit }}</strong></p></div></div>
        <div class="contract-section"><h4>III. 条款</h4><ol><li>里程限制: 每月限2000英里，超出$20/100英里</li><li>燃油政策: 还车油量少于取车油量收$20服务费</li><li>燃油类型: 日系车用87号汽油，勿用柴油</li><li>自动续约: 到期未还车自动续约</li><li>违章处理: 租客负责所有罚单费用</li><li>车钥匙: 勿放车内</li><li>车灯: 离开需关闭所有车灯</li><li>还车清洁: 脏车收$30清洁费</li><li>行驶区域: 仅限洛杉矶地区</li><li>车辆保养: 每月检查机油和轮胎</li><li>驾驶行为: 温柔驾驶</li></ol></div>
        <div class="contract-section"><h4>IV. 道路救援</h4><p>服务时间: 早9点-晚8点</p></div>
        <div class="contract-section"><h4>V. 责任与保险</h4><ol><li>租客需自行购买车辆保险</li><li>租赁期间租客对车辆所有责任负责</li><li>租客签字即表示完全理解并接受所有条款</li></ol></div>
        <div class="contract-signatures"><div class="signature-box"><p>Lender Signature:</p><div class="signature-line">{{ selectedContract.lenderSignature || '________________' }}</div><p class="signature-date">Date: {{ selectedContract.signDate }}</p></div><div class="signature-box"><p>Borrower Signature:</p><div class="signature-line" v-if="selectedContract.borrowerSignature"><img :src="selectedContract.borrowerSignature" style="height:50px;object-fit:contain;" /></div><div class="signature-line" v-else>________________</div><p class="signature-date">Date: {{ selectedContract.signDate }}</p></div></div>
      </div>
      <template #footer><el-button @click="contractDialogVisible = false">关闭</el-button><el-button type="primary" @click="exportPDF">导出PDF</el-button></template>
    </el-dialog>
    
    <!-- 客户详情 -->
    <el-dialog v-model="customerDialogVisible" title="客户详情" width="500px">
      <el-descriptions :column="2" border v-if="selectedCustomer">
        <el-descriptions-item label="ID">{{ selectedCustomer.customerId }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ selectedCustomer.name }}</el-descriptions-item>
        <el-descriptions-item label="电话">{{ selectedCustomer.phone }}</el-descriptions-item>
        <el-descriptions-item label="微信">{{ selectedCustomer.wechatId }}</el-descriptions-item>
        <el-descriptions-item label="邮箱" :span="2">{{ selectedCustomer.email }}</el-descriptions-item>
        <el-descriptions-item label="驾驶证">{{ selectedCustomer.driverLicense }}</el-descriptions-item>
        <el-descriptions-item label="会员等级"><el-tag :type="selectedCustomer.membershipLevel === 2 ? 'warning' : 'info'">VIP {{ selectedCustomer.membershipLevel }}</el-tag></el-descriptions-item>
      </el-descriptions>
      <template #footer><el-button @click="customerDialogVisible = false">关闭</el-button></template>
    </el-dialog>
    
    <!-- 车辆详情 -->
    <el-dialog v-model="vehicleDialogVisible" title="车辆详情" width="500px">
      <el-descriptions :column="2" border v-if="selectedVehicle">
        <el-descriptions-item label="车牌号">{{ selectedVehicle.plateNumber }}</el-descriptions-item>
        <el-descriptions-item label="品牌">{{ selectedVehicle.brand }}</el-descriptions-item>
        <el-descriptions-item label="车型">{{ selectedVehicle.model }}</el-descriptions-item>
        <el-descriptions-item label="年份">{{ selectedVehicle.year }}</el-descriptions-item>
        <el-descriptions-item label="颜色">{{ selectedVehicle.color }}</el-descriptions-item>
        <el-descriptions-item label="里程">{{ selectedVehicle.mileage?.toLocaleString() }} km</el-descriptions-item>
        <el-descriptions-item label="日租金">${{ selectedVehicle.dailyPrice }}</el-descriptions-item>
        <el-descriptions-item label="状态"><el-tag :type="selectedVehicle.status === 'AVAILABLE' ? 'success' : 'warning'">{{ selectedVehicle.status }}</el-tag></el-descriptions-item>
      </el-descriptions>
      <template #footer><el-button @click="vehicleDialogVisible = false">关闭</el-button></template>
    </el-dialog>
    
    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑租赁' : '添加租赁'" width="500px">
      <el-form label-width="100px">
        <el-form-item label="选择车辆">
          <el-select v-model="form.plateNumber" filterable placeholder="搜索车牌号/车型" style="width: 100%">
            <el-option v-for="v in vehicles" :key="v.plateNumber" :label="v.plateNumber + ' - ' + v.brand + ' ' + v.model" :value="v.plateNumber" />
          </el-select>
        </el-form-item>
        <el-form-item label="选择客户">
          <el-select v-model="form.customerId" filterable placeholder="搜索姓名/电话/微信" style="width: 100%">
            <el-option v-for="c in customers" :key="c.customerId" :label="c.name + ' - ' + c.phone" :value="c.customerId" />
          </el-select>
        </el-form-item>
        <el-form-item label="门店">
          <el-input-number v-model="form.storeId" :min="1" />
        </el-form-item>
        <el-form-item label="开始Date">
          <el-date-picker v-model="form.startDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结束Date">
          <el-date-picker v-model="form.endDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="租金">
          <el-input-number v-model="form.rentPrice" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="押金">
          <el-input-number v-model="form.deposit" :min="0" :precision="2" />
        </el-form-item>
        <el-form-item label="支付方式">
          <el-select v-model="form.paymentMethod" style="width: 100%">
            <el-option label="现金" value="Cash" /><el-option label="微信" value="WeChat" /><el-option label="支付宝" value="Alipay" /><el-option label="信用卡" value="CreditCard" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width: 100%">
            <el-option label="待确认" value="PENDING" /><el-option label="已确认" value="CONFIRMED" /><el-option label="进行中" value="IN_PROGRESS" /><el-option label="已完成" value="COMPLETED" /><el-option label="已取消" value="CANCELLED" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.notes" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <!-- 延期对话框 -->
    <el-dialog v-model="extendDialogVisible" title="延长租车 Extend Rental" width="450px">
      <el-form label-width="120px">
        <el-form-item label="原结束日期">
          <el-input :value="extendForm.originalEndDate" disabled />
        </el-form-item>
        <el-form-item label="新结束日期">
          <el-date-picker v-model="extendForm.newEndDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="延期费用">
          <el-input-number v-model="extendForm.additionalFee" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="本次实收">
          <el-input-number v-model="extendForm.actualPaid" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="extendForm.notes" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="extendDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleExtend">确认延期</el-button>
      </template>
    </el-dialog>

    <!-- 提前还车对话框 -->
    <el-dialog v-model="earlyReturnDialogVisible" title="提前还车 Early Return" width="450px">
      <el-form label-width="120px">
        <el-form-item label="原结束日期">
          <el-input :value="earlyReturnForm.originalEndDate" disabled />
        </el-form-item>
        <el-form-item label="实际还车日期">
          <el-date-picker v-model="earlyReturnForm.actualReturnDate" type="date" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
        <el-form-item label="应退金额">
          <el-input-number v-model="earlyReturnForm.refundAmount" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="earlyReturnForm.notes" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="earlyReturnDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleEarlyReturn">确认还车</el-button>
      </template>
    </el-dialog>

    <!-- 收款对话框 -->
    <el-dialog v-model="paymentDialogVisible" title="💰 收款" width="400px">
      <el-form label-width="100px">
        <el-form-item label="订单号">
          <el-input :value="paymentForm.rentalId" disabled />
        </el-form-item>
        <el-form-item label="应收金额">
          <el-input-number v-model="paymentForm.shouldReceive" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="实收金额">
          <el-input-number v-model="paymentForm.actualReceived" :min="0" :precision="2" style="width: 100%" />
        </el-form-item>
        <el-form-item label="支付方式">
          <el-select v-model="paymentForm.paymentMethod" style="width: 100%">
            <el-option label="现金 Cash" value="Cash" />
            <el-option label="微信支付 WeChat" value="WeChat" />
            <el-option label="支付宝 Alipay" value="Alipay" />
            <el-option label="银行转账 Bank" value="Bank" />
            <el-option label="其他 Other" value="Other" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="paymentForm.notes" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="paymentDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handlePayment">确认收款</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const rentalList = ref([])
const contractList = ref([])
const customerMap = ref({})
const customers = ref([])
const vehicles = ref([])
const dialogVisible = ref(false)
const contractDialogVisible = ref(false)
const customerDialogVisible = ref(false)
const vehicleDialogVisible = ref(false)
const selectedCustomer = ref(null)
const selectedVehicle = ref(null)
const selectedContract = ref(null)
const isEdit = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const searchForm = ref({ plateNumber: '', customerName: '', status: '', dateRange: null })
const form = ref({ rentalId: null, plateNumber: '', customerId: null, storeId: 1, startDate: '', endDate: '', rentPrice: 0, deposit: 0, paymentMethod: 'Cash', status: 'PENDING', notes: '' })

// Extension and Early Return dialogs
const extendDialogVisible = ref(false)
const earlyReturnDialogVisible = ref(false)
const extendForm = ref({ rentalId: null, originalEndDate: '', newEndDate: '', additionalFee: 0, actualPaid: 0, notes: '' })
const earlyReturnForm = ref({ rentalId: null, originalEndDate: '', actualReturnDate: '', refundAmount: 0, notes: '' })

// Payment dialog
const paymentDialogVisible = ref(false)
const paymentForm = ref({ rentalId: null, shouldReceive: 0, actualReceived: 0, paymentMethod: 'Cash', notes: '' })

const openPaymentDialog = (row) => {
  paymentForm.value = { 
    rentalId: row.rentalId, 
    shouldReceive: row.shouldReceive || row.rentPrice || 0, 
    actualReceived: row.actualReceived || 0, 
    paymentMethod: row.paymentMethod || 'Cash', 
    notes: '' 
  }
  paymentDialogVisible.value = true
}

const handlePayment = async () => {
  try {
    const debt = paymentForm.value.shouldReceive - paymentForm.value.actualReceived
    const updatedRental = { 
      ...form.value, 
      shouldReceive: paymentForm.value.shouldReceive,
      actualReceived: paymentForm.value.actualReceived,
      debt: debt > 0 ? debt : 0,
      paymentMethod: paymentForm.value.paymentMethod,
      notes: (form.value.notes || '') + '\n[收款]' + paymentForm.value.notes
    }
    await fetch(`${API_BASE}/rentals`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(updatedRental) })
    ElMessage.success('收款成功')
    paymentDialogVisible.value = false
    loadRentals()
  } catch (e) { ElMessage.error('收款失败') }
}

const openExtendDialog = (row) => {
  extendForm.value = { rentalId: row.rentalId, originalEndDate: row.endDate, newEndDate: '', additionalFee: row.rentPrice || 0, actualPaid: 0, notes: '' }
  extendDialogVisible.value = true
}

const openEarlyReturnDialog = (row) => {
  earlyReturnForm.value = { rentalId: row.rentalId, originalEndDate: row.endDate, actualReturnDate: '', refundAmount: 0, notes: '' }
  earlyReturnDialogVisible.value = true
}

const handleExtend = async () => {
  try {
    const newTotal = form.value.rentPrice + extendForm.value.additionalFee
    const newActual = (form.value.actualReceived || 0) + extendForm.value.actualPaid
    const newDebt = newTotal - newActual
    const updatedRental = { 
      ...form.value, 
      endDate: extendForm.value.newEndDate, 
      rentPrice: newTotal,
      shouldReceive: newTotal,
      actualReceived: newActual,
      debt: newDebt > 0 ? newDebt : 0,
      notes: (form.value.notes || '') + '\n[延期+' + extendForm.value.additionalFee + ']' + extendForm.value.notes 
    }
    await fetch(`${API_BASE}/rentals`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(updatedRental) })
    ElMessage.success('延期成功')
    extendDialogVisible.value = false
    loadRentals()
  } catch (e) { ElMessage.error('延期失败') }
}

const handleEarlyReturn = async () => {
  try {
    const updatedRental = { ...form.value, endDate: earlyReturnForm.value.actualReturnDate, status: 'COMPLETED', notes: (form.value.notes || '') + '\n[提前还车]' + earlyReturnForm.value.notes }
    await fetch(`${API_BASE}/rentals`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(updatedRental) })
    ElMessage.success('还车成功')
    earlyReturnDialogVisible.value = false
    loadRentals()
  } catch (e) { ElMessage.error('还车失败') }
}

const handleSearch = () => { currentPage.value = 1 }
const exportData = () => { const data = filteredList.value.map(r => ({ ...r, customerName: getCustomerName(r.customerId) })); const csv = Papa.parse(data); console.log('Export:', csv) }

const API_BASE = '/api'

const loadRentals = async () => {
  try {
    const [rentalRes, customerRes, vehicleRes, contractRes] = await Promise.all([
      fetch(`${API_BASE}/rentals`), fetch(`${API_BASE}/customers`), fetch(`${API_BASE}/vehicles`), fetch(`${API_BASE}/contracts`)])
    rentalList.value = await rentalRes.json()
    customers.value = await customerRes.json()
    vehicles.value = await vehicleRes.json()
    contractList.value = await contractRes.json()
    customerMap.value = {}
    customers.value.forEach(c => { customerMap.value[c.customerId] = c.name })
  } catch (e) { ElMessage.error('加载失败') }
}

const filteredList = computed(() => {
  return rentalList.value.map(r => ({
    ...r,
    hasContract: contractList.value.some(c => c.rentalId === r.rentalId)
  })).filter(r => {
    const matchPlate = !searchForm.value.plateNumber || r.plateNumber?.toLowerCase().includes(searchForm.value.plateNumber.toLowerCase())
    const matchCustomer = !searchForm.value.customerName || getCustomerName(r.customerId)?.toLowerCase().includes(searchForm.value.customerName.toLowerCase())
    const matchStatus = !searchForm.value.status || r.status === searchForm.value.status
    let matchDate = true
    if (searchForm.value.dateRange && searchForm.value.dateRange[0]) {
      const start = new Date(r.startDate)
      const rangeStart = new Date(searchForm.value.dateRange[0])
      const rangeEnd = new Date(searchForm.value.dateRange[1])
      matchDate = start >= rangeStart && start <= rangeEnd
    }
    return matchPlate && matchCustomer && matchStatus && matchDate
  })
})

const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  return filteredList.value.slice(start, start + pageSize.value)
})

const getCustomerName = (customerId) => customerMap.value[customerId] || `客户 #${customerId}`

const generateContract = async (rentalId) => {
  try {
    const res = await fetch(`${API_BASE}/contracts/generate?rentalId=${rentalId}`, { method: 'POST' })
    if (res.ok) {
      ElMessage.success('合同生成成功')
      loadRentals()
    } else {
      ElMessage.error('生成失败')
    }
  } catch (e) { ElMessage.error('生成失败') }
}

const viewContract = (row) => {
  const contract = contractList.value.find(c => c.rentalId === row.rentalId)
  if (contract) {
    selectedContract.value = contract
    contractDialogVisible.value = true
  } else {
    ElMessage.warning('请先生成合同')
  }
}

const showCustomerDetail = (customerId) => { selectedCustomer.value = customers.value.find(c => c.customerId === customerId) || null; customerDialogVisible.value = true }
const showVehicleDetail = (plateNumber) => { selectedVehicle.value = vehicles.value.find(v => v.plateNumber === plateNumber) || null; vehicleDialogVisible.value = true }
const getStatusType = (status) => ({ 'PENDING': 'info', 'CONFIRMED': 'primary', 'IN_PROGRESS': 'warning', 'COMPLETED': 'success', 'CANCELLED': 'danger' }[status] || 'info')
const getStatusText = (status) => ({ 'PENDING': '待确认', 'CONFIRMED': '已确认', 'IN_PROGRESS': '进行中', 'COMPLETED': '已完成', 'CANCELLED': '已取消' }[status] || status)
const handleAdd = () => { isEdit.value = false; form.value = { rentalId: null, plateNumber: '', customerId: null, storeId: 1, startDate: '', endDate: '', rentPrice: 0, deposit: 0, paymentMethod: 'Cash', status: 'PENDING', notes: '' }; dialogVisible.value = true }
const handleEdit = (row) => { isEdit.value = true; form.value = { ...row }; dialogVisible.value = true }
const handleSubmit = async () => {
  if (!form.value.plateNumber || !form.value.customerId) { ElMessage.warning('请选择车辆和客户'); return }
  try {
    const res = await fetch(`${API_BASE}/rentals`, { method: isEdit.value ? 'PUT' : 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(form.value) })
    if (res.ok) { ElMessage.success(isEdit.value ? '更新成功' : '添加成功'); dialogVisible.value = false; loadRentals() }
    else ElMessage.error('操作失败')
  } catch (e) { ElMessage.error('操作失败') }
}
const handleDelete = async (row) => {
  try { await ElMessageBox.confirm('确定要删除这条记录吗？', '提示', { type: 'warning' }); await fetch(`${API_BASE}/rentals/${row.rentalId}`, { method: 'DELETE' }); ElMessage.success('删除成功'); loadRentals() }
  catch (e) { if (e !== 'cancel') ElMessage.error('删除失败') }
}
const resetSearch = () => { searchForm.value = { plateNumber: '', customerName: '', status: '' }; currentPage.value = 1 }

const exportPDF = () => {
  const content = document.getElementById('contract-print')
  if (!content) return
  const printWindow = window.open('', '_blank')
  printWindow.document.write(`<html><head><title>Vehicle Rental Agreement</title><style>body{font-family:Arial;padding:20px;font-size:12px}.contract-section{margin-bottom:15px;padding:10px;border:1px solid #ddd}.info-grid{display:grid;grid-template-columns:1fr 1fr;gap:5px}@media print{body{font-size:10px}}</style></head><body>${content.innerHTML}</body></html>`)
  printWindow.document.close()
  printWindow.print()
}

onMounted(() => { loadRentals() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.contract-content { font-size: 12px; line-height: 1.6; }
.contract-header { text-align: center; margin-bottom: 20px; }
.contract-header h2 { margin: 0; font-size: 18px; }
.contract-section { margin-bottom: 15px; padding: 12px; background: #f8f9fc; border-radius: 8px; }
.contract-section h4 { margin: 0 0 10px 0; color: #1a1a2e; font-size: 13px; }
.info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 5px; font-size: 11px; }
.contract-signatures { display: flex; justify-content: space-between; margin-top: 30px; }
.signature-box { width: 45%; }
.signature-line { border-bottom: 1px solid #333; padding: 25px 0 5px 0; }
.signature-line img { max-width: 100%; height: 50px; }
.signature-date { margin-top: 5px; font-size: 10px; color: #666; }
</style>
