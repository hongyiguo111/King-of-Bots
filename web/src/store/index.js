import { createStore } from 'vuex'
import Moduleuser from './user.js'
import ModulePk from './pk.js'

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: Moduleuser,
    pk: ModulePk
  }
})
