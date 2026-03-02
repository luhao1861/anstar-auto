import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import Vehicles from '../views/Vehicles.vue'
import Customers from '../views/Customers.vue'
import Rentals from '../views/Rentals.vue'
import Reports from '../views/Reports.vue'
import Maintenance from '../views/Maintenance.vue'
import Stores from '../views/Stores.vue'
import Contracts from '../views/Contracts.vue'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', name: 'Login', component: Login },
  {
    path: '/',
    component: Layout,
    children: [
      { path: 'dashboard', component: Dashboard },
      { path: 'vehicles', component: Vehicles },
      { path: 'customers', component: Customers },
      { path: 'rentals', component: Rentals },
      { path: 'reports', component: Reports },
      { path: 'maintenance', component: Maintenance },
      { path: 'stores', component: Stores },
      { path: 'contracts', component: Contracts },
    ]
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
