<!-- 文章详情 -->
<template>
    <div>
        <rb-header></rb-header>
      <body>
        <div  class="container" id="detail">
          <el-row  :gutter="30">
            <el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
              <rb-articleDetail></rb-articleDetail>
            </el-col>
            <el-col :sm="20"  :md="8" >
              <rb-rightList></rb-rightList>
            </el-col>
          </el-row>
        </div>
      </body>
      <rb-bottom></rb-bottom>
    </div>
</template>

<script>
import header from '../components/header.vue'
import rightList from '../components/rightlist.vue'
import articleDetail from '../components/articleDetail.vue'
import bottom from '../components/bottom.vue'
    export default {
        name:'DetailShare',
        data() { //选项 / 数据
            return {

            }
        },
        methods: { //事件处理器

        },
        components: { //定义组件
            'rb-header':header,
            'rb-articleDetail':articleDetail,
            'rb-rightList':rightList,
            'rb-bottom':bottom,
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
