<template>
  <div class="app-container">
    <br>
    <el-form ref="form" :model="form" label-width="90px">
      <el-row :gutter="20">
        <el-col :span="14">
          <el-form-item label="标题" prop="title">
            <el-input
              v-model="form.title"
              placeholder="请输入文章标题"
              maxlength="30"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="分类">
            <el-select v-model="form.categoryId" placeholder="请选择">
              <el-option
                v-for="category in categoryList"
                :key="category.id"
                :label="category.name"
                :value="category.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <br>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="置顶">
            <el-radio-group v-model="form.isTop">
              <el-radio :key="'0'" :label="'0'">是</el-radio>
              <el-radio :key="'1'" :label="'1'">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="发布">
            <el-radio-group v-model="form.status">
              <el-radio :key="'0'" :label="'0'">是</el-radio>
              <el-radio :key="'1'" :label="'1'">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <br>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item>
            <el-button type="primary" size="medium" @click="handleSubmit">{{ aId ? "更新":"发布" }}</el-button>
          </el-form-item>
          <el-form-item>
            <el-button v-if="!aId" type="info" @click="handleSave">保存到草稿箱</el-button>
          </el-form-item>

        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-form-item label="内容">
          <mavon-editor ref="md" v-model="form.content"/>
        </el-form-item>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { listAllCategory } from '@/api/content/category'
import { addArticle, getArticle, updateArticle } from '@/api/content/article'
export default {
  name: 'Write',
  data() {
    return {
      form: {
        title: '',
        isTop: '1',
        content: '',
        status: '1'
      },
      categoryList: [],
      aId: 0
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.aId = route.query && route.query.id
        console.log(this.aId)
      },
      immediate: true
    }
  },
  created() {
    this.getCategoryAndTag()
    if (this.aId) {
      this.getArticle()
    }
  },
  methods: {
    getArticle() {
      getArticle(this.aId).then(response => {
        this.form = response
      })
    },
    // 函数说明: 保存草稿
    // 状态（0已发布，1草稿）
    handleSave() {
      this.aId = 1
      addArticle(this.form).then(response => {
        this.$modal.msgSuccess('保存草稿成功')
      })
    },
    // 函数说明: 提交操作
    handleSubmit() {
      if (!this.aId) {
        this.form.status = '0'
        addArticle(this.form).then(response => {
          this.$modal.msgSuccess('博客发布成功')
          this.$router.push({ path: '/content/article' })
        })
      } else {
        // 更新博客信息
        updateArticle(this.form).then(response => {
          this.$modal.msgSuccess('博客更新成功')
          this.$router.push({ path: '/content/article' })
        })
      }
    },
    getCategoryAndTag() {
      listAllCategory().then((response) => {
        this.categoryList = response
      })
    }
  }
}
</script>
<style scoped>
div .upload-demo {
    /* padding-left: 80px; */
}
.el-col .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;

    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
