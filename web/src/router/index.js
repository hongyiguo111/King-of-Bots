import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/pk/PkIndexView'
import RecordIndexView from '../views/record/RecordIndexView'
import RanklistIndexView from '../views/ranklist/RanklistIndexView'
import UserBotIndexView from '../views/user/bot/UserBotIndexView'
import NotFound from '../views/error/NotFound'
import UserAccountLoginView from '@/views/user/account/UserAccountLoginView.vue'
import UserAccountRegisterView from '@/views/user/account/UserAccountRegisterView.vue'
import store from '@/store/index.js'

const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/pk/",
    meta: { requiresAuth: true }, // 需要登录
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PkIndexView,
    meta: { requiresAuth: true },
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndexView,
    meta: { requiresAuth: true },
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RanklistIndexView,
    meta: { requiresAuth: true },
  },
  {
    path: "/user/bot/",
    name: "user_bot_index",
    component: UserBotIndexView,
    meta: { requiresAuth: true },
  },
  {
    path: "/user/account/login/",
    name: "user_account_login",
    component: UserAccountLoginView,
    meta: { requiresAuth: false },
  },
  {
    path: "/user/account/register/",
    name: "user_account_register",
    component: UserAccountRegisterView,
    meta: { requiresAuth: false },
  },
  {
    path: "/404/",
    name: "404",
    component: NotFound,
    meta: { requiresAuth: false },
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/",
  }
]


const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => { // 在router进入之前进行判断，to表示要进入的路由，from表示从哪个路由跳转而来，next是一个函数，表示放行
  if (to.meta.requiresAuth && !store.state.user.is_login) {
    next({ name: "user_account_login" }); // 跳转到登录页面
  } else {
    next(); // 放行
  }
})

export default router
