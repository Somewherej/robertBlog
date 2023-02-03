import Vue from 'vue'
import Vuex from 'vuex'
// import * as getters from './getters.js'

Vue.use(Vuex)

/** 状态定义 */
export const state = {
  loading: false,
  themeObj: 0,//主题
  baseURL:'http://124.220.178.250:7777/'
}

export default new Vuex.Store({
    state,
})
