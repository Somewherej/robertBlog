<!-- 首页 -->
<template>
  <div>
        <div class="container">
          <rb-header></rb-header>
          <body>
            <el-row  :gutter="40">
              <el-col :sm="27" :md="17" style="transition:all .5s ease-out;margin-bottom:30px;">
                <rb-articleList></rb-articleList>
              </el-col>
              <el-col :sm="27"  :md="7" >
                <rb-rightList></rb-rightList>
              </el-col>
            </el-row>
          </body>
          <rb-bottom></rb-bottom>
        </div>
  </div>



</template>

<script>
import header from '../components/header.vue'
import articlelist from '../components/articlelist.vue'
import rightlist from '../components/rightlist.vue'
import bottom from '../components/bottom.vue'
    export default {
        name:'Home',
        data() { //选项 / 数据
            return {

            }
        },
        methods: { //事件处理器

        },
        components: { //定义组件
            'rb-header':header,
            'rb-articleList':articlelist,
            'rb-rightList':rightlist,
            'rb-bottom':bottom
        },
        created() { //生命周期函数

        }
    }
</script>

<style>
body{
  background: url("../../static/img/background.png");
}
</style>
