<!-- 文章列表 -->
<template>
<div>
	<rb-nav></rb-nav>
	<div class="container">
		<el-row :gutter="30">
			<el-col :sm="24" :md="16" style="transition:all .5s ease-out;margin-bottom:30px;">
				<rb-articleList></rb-articleList>
			</el-col>
			<el-col :sm="24" :md="8">
				<rb-rightList></rb-rightList>
			</el-col>
		</el-row>
	</div>
  <rb-bottom></rb-bottom>
</div>
</template>

<script>
import header from '../components/header.vue'
import articleList from '../components/articlelist.vue'
import rightList from '../components/rightlist.vue'
import bottom from '../components/bottom.vue'
export default {
	name: "Share",
	data() { //选项 / 数据
		return {

		}
	},
	methods: { //事件处理器

	},
	components: { //定义组件
		'rb-nav': header,
		'rb-articleList': articleList,
		'rb-rightList': rightList,
    'rb-bottom':bottom
	},

	created() { //生命周期函数

	}
}
</script>

<style>

</style>
