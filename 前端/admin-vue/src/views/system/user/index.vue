<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-table :data="userList" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column prop="id" label="用户编号" align="center" />
        <el-table-column prop="userName" label="用户名称" align="center" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template v-if="scope.row.id !== 1" slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
            >修改
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <br>
    <el-pagination
      :page-size.sync="queryParams.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      :page-sizes="[10, 20, 30, 40]"
      :current-page.sync="queryParams.pageNum"
      @current-change="getList"
      @size-change="getList"
    />
    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item
              label="用户名称"
              prop="userName"
            >
              <el-input
                v-model="form.userName"
                placeholder="请输入用户名称"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item
              label="用户密码"
              prop="password"
            >
              <el-input
                v-model="form.password"
                placeholder="请输入用户密码"
                type="password"
                maxlength="20"
                show-password
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色">
              <el-select v-model="form.roleIds" multiple placeholder="请选择">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.id"
                  :label="item.roleName"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col></el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// import { getToken } from '@/utils/auth'
import {
  listUser,
  getUser,
  addUser,
  updateUser,
} from '@/api/system/user'
import {
  listAllRole
}

from '@/api/system/role'
export default {
  name: 'User',
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
      },
      title: '',
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {
        userName: [
          { required: true, message: '用户名称不能为空', trigger: 'blur' },
          {
            min: 2,
            max: 20,
            message: '用户名称长度必须介于 2 和 20 之间',
            trigger: 'blur'
          }
        ],
        password: [
          { required: true, message: '用户密码不能为空', trigger: 'blur' },
          {
            min: 5,
            max: 20,
            message: '用户密码长度必须介于 5 和 20 之间',
            trigger: 'blur'
          }
        ]
      },
      // 角色选项
      roleOptions: [],
      // 显示搜索条件
      showSearch: true,
      // 遮罩层
      loading: true,
      // 用户表格数据
      userList: null,
      // 总条数
      total: 0,
      // 选中数组
      ids: [],
      // 表单参数
      form: {}
    }
  },
  watch: {},
  created() {
    this.getList()
  },
  methods: {
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true
      listUser(this.queryParams).then((response) => {
        this.userList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 为myDirective.vue的directives属性增加focus属性，
       实现聚焦功能。并展示结果。
     */
    directives: {
      focus: {
        inserted: (el, binding) => {
          console.log(binding)
          el.focus()
        }
      }
    },


    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getUser(id).then((response) => {
        this.form = response.user
        this.roleOptions = response.roles
        this.form.roleIds = response.roleIds
        this.open = true
        this.title = '修改用户'
        this.form.password = response.password
      })
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        userName: undefined,
        password: undefined,
        roleIds: []
      }
      this.resetForm('form')
    },
    /** 新增用户 */
    handleAdd() {
      this.reset()
      listAllRole().then((response) => {
        this.roleOptions = response
        this.open = true
        this.title = '添加用户'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id !== undefined) {
            updateUser(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addUser(this.form).then((response) => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    }
  }
}
</script>

