<!-- 头部公用 -->
<template>
<div class="">
	<div class="headBack">
		<el-row class="container">
			<el-col :span="27">
				<!-- pc端导航 -->
				<div class="headBox">
					<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" :router="true">
						<el-menu-item index="/Home"><i class="el-icon-s-home"></i> 首页</el-menu-item>
						<el-submenu index="/Share">
							<template slot="title"><i class="el-icon-s-fold"></i> 分类</template>
							<el-menu-item v-for="(item,index) in classListObj" :key="'class1'+index" :index="'/Share?classId='+item.id">{{item.name}}</el-menu-item>
						</el-submenu>
					</el-menu>
				</div>
			</el-col>
		</el-row>
	</div>

</div>
</template>
<script>

import {getCategoryList} from '../api/category'
import header from "./header";
import articleDetail from "./articleDetail";
import rightList from "./rightlist";
import bottom from "./bottom";
export default {
	data() { //选项 / 数据
		return {
			classListObj: '', //文章分类
			activeIndex: '/', //当前选择的路由模块
			state: '', //icon点击状态
			pMenu: true, //手机端菜单打开
			headBg: 'url(static/img/small_black.jpg)', //头部背景图
			headTou: '', //头像
		}
	},

	methods: { //事件处理器
		getCategoryList(){
			getCategoryList().then((response)=>{
				this.classListObj = response
			})
		},
		handleSelect(key, keyPath) { //pc菜单选择
			//    console.log(key, keyPath);
		},
		routeChange: function() {
			var that = this;
			that.pMenu = true
			this.activeIndex = this.$route.path == '/' ? '/Home' : this.$route.path;
			this.getCategoryList()
		}
	},
	components: { //定义组件
    'rb-header':header,
    'rb-articleDetail':articleDetail,
    'rb-rightList':rightList,
    'rb-bottom':bottom,
	},
	watch: {
		// 如果路由有变化，会再次执行该方法
		'$route': 'routeChange'
	},
	created() { //生命周期函数
		var that = this;
    that.routeChange();
	},
}
</script>

<style>
/*********头部导航栏********/

/*头部导航栏盒子*/

.headBack {
  width: 100%;
  background: #1b1f23;
  margin-bottom: 30px;

  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, .12), 0 0 6px 0 rgba(0, 0, 0, .04);
  left: 0;
  top: 0;
  right: 0;
  z-index: 100;
}

.headBox li.is-active {
  /*background: #48456C;*/
  background: rgba(73, 69, 107, 0.7);
}

.el-menu--horizontal>.el-submenu.is-active .el-submenu__title {
  border-bottom: none!important;
}

.headBox .el-menu {
  background: transparent;
  border-bottom: none!important;
}


.headBox .el-menu-demo li.el-menu-item,
.headBox .el-menu--horizontal .el-submenu .el-submenu__title {
  height: 38px;
  line-height: 38px;
  border-bottom: none!important;

}

.headBox .el-submenu li.el-menu-item {
  height: 38px;
  line-height: 38px;
}

.headBox li .fa-wa {
  vertical-align: baseline;
}

.headBox ul li.el-menu-item,
.headBox ul li.el-menu-item.is-active{
  background-color: #1b1f23 !important;
  color: #fff
}



.headBox ul li.el-menu-item:hover,
.headBox .el-submenu div.el-submenu__title,
.headBox .el-submenu__title i.el-submenu__icon-arrow {
  color: #fff;
}

.headBox .el-menu--horizontal .el-submenu>.el-menu {
  top: 38px;
  border: none;
  padding: 0;
}

.headBox>ul li.el-menu-item:hover,
.headBox>ul li.el-submenu:hover .el-submenu__title {
  background: #1b1f23;
  border-bottom: none;
}


.headBox>ul .el-submenu .el-menu,
.headBox>ul .el-submenu .el-menu .el-menu-item {
  background: #48456C;
}

.headBox>ul .el-submenu .el-menu .el-menu-item {
  min-width: 0;
}

.headBox>ul .el-submenu .el-menu .el-menu-item:hover {
  background: #64609E;
}

.headBox .el-input__inner {
  height: 30px;
  border: none;
  background: #fff;
  /*border: 1px solid #333;*/
  border-radius: 2px;
  padding-right: 10px;
}

.headBox .userInfo {
  height: 100%;
  line-height: 38px;
  position: absolute;
  right: 30px;
  top: 0;
  color: #fff;
}

/*******移动端*******/

.mobileBox {
  position: relative;
  height: 38px;
  line-height: 38px;
  color: #fff;
}

.hideMenu {
  position: relative;
  width: 100%;
  height: 100%;
  line-height: 38px;
}

.hideMenu ul.mlistmenu {
  width: 100%;
  position: absolute;
  left: 0;
  top: 100%;
  box-sizing: border-box;
  z-index: 999;
  box-shadow: 0 2px 6px 0 rgba(0, 0, 0, .12), 0 0 8px 0 rgba(0, 0, 0, .04);
  background: #48456C;
  color: #fff;
  border-right: none;
}

.hideMenu .el-submenu .el-menu {
  background: #000000;
}

.hideMenu .el-menu-item,
.hideMenu .el-submenu__title {
  color: #fff;
}

.hideMenu>i {
  position: absolute;
  left: 10px;
  top: 12px;
  width: 30px;
  height: 30px;
  font-size: 16px;
  color: #fff;
  cursor: pointer;
}

.hideMenu .el-menu-item,
.el-submenu__title {
  height: 40px;
  line-height: 40px;
}

.mobileBox .searchBox {
  padding-left: 40px;
  width: 100%;
  box-sizing: border-box;
}

.mobileBox .searchBox .el-input__inner {
  display: block;
  border-radius: 2px;
  border: none;
  height: 25px;
}

.hideMenu ul.mlistmenu.pshow {
  display: block;
}

.hideMenu ul.mlistmenu .el-submenu__icon-arrow,
.mobileBox li.el-menu-item a {
  color: #fff;
}

.hideMenu>ul li.el-menu-item:hover,
.hideMenu>ul li.el-menu-item.is-active {
  background: #48576a;
}



</style>

