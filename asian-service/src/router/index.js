import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Services from '../views/Services.vue'
import Order from '../views/Order.vue'
import Track from '../views/Track.vue'
import Templates from '../views/Templates.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Dashboard from '../views/Dashboard.vue'
import AdminOrders from '../views/AdminOrders.vue'
import AdminTemplates from '../views/AdminTemplates.vue'
import Apostille from '../views/seo/Apostille.vue'
import Notary from '../views/seo/Notary.vue'
import Translation from '../views/seo/Translation.vue'
import CaliforniaApostille from '../views/seo/CaliforniaApostille.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/services', component: Services },
  { path: '/order', component: Order },
  { path: '/track', component: Track },
  { path: '/templates', component: Templates },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/dashboard', component: Dashboard },
  { path: '/admin/orders', component: AdminOrders },
  { path: '/admin/templates', component: AdminTemplates },
  
  // SEO Pages
  { path: '/apostille', component: Apostille },
  { path: '/apostille-los-angeles', component: Apostille },
  { path: '/california-apostille', component: CaliforniaApostille },
  { path: '/notary', component: Notary },
  { path: '/chinese-notary-los-angeles', component: Notary },
  { path: '/notary-los-angeles', component: Notary },
  { path: '/translation', component: Translation },
  { path: '/chinese-translation-service', component: Translation },
  { path: '/chinese-document-translation', component: Translation },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
