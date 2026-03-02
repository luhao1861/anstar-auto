<template>
  <div class="contracts-page">
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>合同列表</span>
          <el-button type="primary" @click="generateAllContracts">批量生成合同</el-button>
        </div>
      </template>
      
      <el-row :gutter="20" style="margin-bottom: 20px">
        <el-col :span="5">
          <el-input v-model="searchForm.customerName" placeholder="搜索客户姓名" clearable />
        </el-col>
        <el-col :span="5">
          <el-input v-model="searchForm.plateNumber" placeholder="搜索车牌号" clearable />
        </el-col>
        <el-col :span="4">
          <el-select v-model="searchForm.status" placeholder="状态" clearable>
            <el-option label="草稿" value="DRAFT" />
            <el-option label="已签署" value="SIGNED" />
            <el-option label="已完成" value="COMPLETED" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-button @click="resetSearch">重置</el-button>
        </el-col>
      </el-row>
      
      <el-table :data="paginatedList" stripe>
        <el-table-column prop="contractId" label="ID" width="60" />
        <el-table-column prop="rentalId" label="租赁ID" width="80" />
        <el-table-column prop="customerName" label="客户姓名" width="120" />
        <el-table-column prop="plateNumber" label="车牌号" width="100" />
        <el-table-column prop="vehicleInfo" label="车辆信息" width="180" />
        <el-table-column label="租赁日期" width="180">
          <template #default="scope">{{ scope.row.startDate }} ~ {{ scope.row.endDate }}</template>
        </el-table-column>
        <el-table-column prop="monthlyPrice" label="租金" width="80">
          <template #default="scope">${{ scope.row.monthlyPrice }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="90">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ getStatusText(scope.row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template #default="scope">
            <el-button size="small" @click="viewContract(scope.row)">查看</el-button>
            <el-button size="small" type="success" @click="openSignDialog(scope.row)" v-if="scope.row.status === 'DRAFT'">签署</el-button>
            <el-button size="small" type="primary" @click="printContract(scope.row)">导出</el-button>
            <el-button size="small" type="danger" @click="deleteContract(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination style="margin-top: 20px; text-align: right" v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[10, 20, 50, 100]" :total="filteredList.length" layout="total, sizes, prev, pager, next, jumper" />
    </el-card>
    
    <el-dialog v-model="contractDialogVisible" title="租车协议" width="850px">
      <div class="contract-content" id="contract-print" v-if="selectedContract">
        <div class="contract-header"><h2>🚗 安星汽车租车协议</h2><p>Vehicle Borrow Agreement</p></div>
        <div class="contract-section"><h4>A. 出租方 (Lender)</h4><p><strong>安星汽车 / Anstar Auto</strong></p><p>Address: 123 Main St, Los Angeles, CA 90001</p></div>
        <div class="contract-section">
          <h4>B. 承租方 (Borrower)</h4>
          <div class="info-grid">
            <p>Name: <strong>{{ selectedContract.customerName }}</strong></p>
            <p>Phone: <strong>{{ selectedContract.customerPhone }}</strong></p>
            <p>WeChat: <strong>{{ selectedContract.customerWechat || 'N/A' }}</strong></p>
            <p>Email: <strong>{{ selectedContract.customerEmail || 'N/A' }}</strong></p>
            <p>ID: <strong>{{ selectedContract.customerIdNumber }}</strong></p>
            <p>Address: <strong>{{ selectedContract.customerAddress || 'N/A' }}</strong></p>
          </div>
        </div>
        <div class="contract-section">
          <h4>I. 车辆信息</h4>
          <div class="info-grid">
            <p>Vehicle: <strong>{{ selectedContract.vehicleInfo }}</strong></p>
            <p>Plate: <strong>{{ selectedContract.plateNumber }}</strong></p>
            <p>Odometer: <strong>{{ selectedContract.odometerStart }} miles</strong></p>
            <p>Fuel: <strong>{{ selectedContract.fuelLevel }}</strong></p>
          </div>
        </div>
        <div class="contract-section">
          <h4>II. 租赁详情</h4>
          <div class="info-grid">
            <p>Period: <strong>{{ selectedContract.startDate }} ~ {{ selectedContract.endDate }}</strong></p>
            <p>Price: <strong>${{ selectedContract.monthlyPrice }}</strong></p>
            <p>Deposit: <strong>${{ selectedContract.deposit }}</strong></p>
          </div>
        </div>
        <div class="contract-section">
          <h4>III. 条款</h4>
          <ol>
            <li>里程限制: 每月限2000英里，超出$20/100英里</li>
            <li>燃油政策: 还车油量少于取车油量收$20服务费</li>
            <li>燃油类型: 日系车用87号汽油，勿用柴油</li>
            <li>自动续约: 到期未还车自动续约</li>
            <li>违章处理: 租客负责所有罚单费用</li>
            <li>车钥匙: 勿放车内</li>
            <li>车灯: 离开需关闭所有车灯</li>
            <li>还车清洁: 脏车收$30清洁费</li>
            <li>行驶区域: 仅限洛杉矶地区</li>
            <li>车辆保养: 每月检查机油和轮胎</li>
          </ol>
        </div>
        <div class="contract-section"><h4>IV. 道路救援</h4><p>服务时间: 早9点-晚8点</p></div>
        <div class="contract-section"><h4>V. 责任与保险</h4><ol><li>租客需自行购买车辆保险</li><li>租赁期间租客对车辆所有责任负责</li><li>租客签字即表示完全理解并接受所有条款</li></ol></div>
        <div class="contract-signatures">
          <div class="signature-box"><p>出租方签字:</p><div class="signature-line">{{ selectedContract.lenderSignature || '________________' }}</div><p class="signature-date">日期: {{ selectedContract.signDate }}</p></div>
          <div class="signature-box"><p>承租方签字:</p><div class="signature-line" v-if="selectedContract.borrowerSignature"><img :src="selectedContract.borrowerSignature" style="height:50px;object-fit:contain;" /></div><div class="signature-line" v-else>________________</div><p class="signature-date">日期: {{ selectedContract.signDate }}</p></div>
        </div>
      </div>
      <template #footer><el-button @click="contractDialogVisible = false">关闭</el-button><el-button type="primary" @click="exportPDF">导出PDF</el-button></template>
    </el-dialog>
    
    <el-dialog v-model="signDialogVisible" title="签署合同" width="600px">
      <div class="sign-dialog-content"><p>请在下方的签名区签写您的姓名：</p>
        <div class="signature-pad-container"><canvas ref="signatureCanvas" width="300" height="100"></canvas></div>
        <div style="margin-top:15px;text-align:center;"><el-button @click="clearSignature">清除</el-button><el-button type="primary" @click="confirmSign">确认签署</el-button></div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import SignaturePad from 'signature_pad'

const contractList = ref([])
const contractDialogVisible = ref(false)
const signDialogVisible = ref(false)
const selectedContract = ref(null)
const signingContractId = ref(null)
const signatureCanvas = ref(null)
let signaturePad = null
const currentPage = ref(1)
const pageSize = ref(10)
const searchForm = ref({ customerName: '', plateNumber: '', status: '' })

const API_BASE = '/api'

const loadContracts = async () => { try { const res = await fetch(`${API_BASE}/contracts`); contractList.value = await res.json() } catch (e) { ElMessage.error('加载失败') } }

const filteredList = computed(() => {
  return contractList.value.filter(c => {
    const matchName = !searchForm.value.customerName || c.customerName?.toLowerCase().includes(searchForm.value.customerName.toLowerCase())
    const matchPlate = !searchForm.value.plateNumber || c.plateNumber?.toLowerCase().includes(searchForm.value.plateNumber.toLowerCase())
    const matchStatus = !searchForm.value.status || c.status === searchForm.value.status
    return matchName && matchPlate && matchStatus
  })
})

const paginatedList = computed(() => { const start = (currentPage.value - 1) * pageSize.value; return filteredList.value.slice(start, start + pageSize.value) })
const getStatusType = (status) => ({ 'DRAFT': 'info', 'SIGNED': 'success', 'COMPLETED': 'primary' }[status] || 'info')
const getStatusText = (status) => ({ 'DRAFT': '草稿', 'SIGNED': '已签署', 'COMPLETED': '已完成' }[status] || status)

const generateAllContracts = async () => { try { const res = await fetch(`${API_BASE}/contracts/generate-all`, { method: 'POST' }); const text = await res.text(); ElMessage.success(text); loadContracts() } catch (e) { ElMessage.error('生成失败') } }
const viewContract = (row) => { selectedContract.value = row; contractDialogVisible.value = true }
const openSignDialog = (row) => { signingContractId.value = row.contractId; signDialogVisible.value = true; nextTick(() => { if (signatureCanvas.value) { signaturePad = new SignaturePad(signatureCanvas.value, { backgroundColor: 'rgb(255,255,255)', penColor: 'rgb(0,0,0)' }) } }) }
const clearSignature = () => { if (signaturePad) signaturePad.clear() }
const confirmSign = async () => { if (!signaturePad || signaturePad.isEmpty()) { ElMessage.warning('请先签名'); return } const signatureData = signaturePad.toDataURL('image/jpeg', 0.5); try { const res = await fetch(`${API_BASE}/contracts/${signingContractId.value}/sign`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ signature: signatureData }) }); if (res.ok) { ElMessage.success('签署成功'); signDialogVisible.value = false; loadContracts() } else { ElMessage.error('签署失败') } } catch (e) { ElMessage.error('签署失败: ' + e.message) } }
const printContract = (row) => { selectedContract.value = row; contractDialogVisible.value = true }
const exportPDF = () => { const content = document.getElementById('contract-print'); if (!content) return; const printWindow = window.open('', '_blank'); printWindow.document.write(`<html><head><title>租车协议</title><style>body{font-family:Arial;padding:20px;font-size:12px}.contract-section{margin-bottom:15px;padding:10px;border:1px solid #ddd}.info-grid{display:grid;grid-template-columns:1fr 1fr;gap:5px}@media print{body{font-size:10px}}</style></head><body>${content.innerHTML}</body></html>`); printWindow.document.close(); printWindow.print() }
const resetSearch = () => { searchForm.value = { customerName: '', plateNumber: '', status: '' }; currentPage.value = 1 }
const deleteContract = async (row) => { try { await ElMessageBox.confirm('确定要删除这份合同吗？', '提示', { type: 'warning' }); await fetch(`${API_BASE}/contracts/${row.contractId}`, { method: 'DELETE' }); ElMessage.success('删除成功'); loadContracts() } catch (e) { if (e !== 'cancel') ElMessage.error('删除失败') } }

onMounted(() => { loadContracts() })
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
.sign-dialog-content { padding: 10px; }
.signature-pad-container { border: 2px dashed #ccc; border-radius: 8px; overflow: hidden; background: white; }
.signature-pad-container canvas { display: block; width: 100%; }
</style>
