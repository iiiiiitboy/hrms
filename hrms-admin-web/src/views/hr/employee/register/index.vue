<template >
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="应聘编号" prop="id"/>
      <el-table-column label="部门" prop="deptName"/>
      <el-table-column label="岗位" prop="jobName"/>
      <el-table-column label="姓名" prop="name"/>
      <el-table-column label="年龄" prop="age"/>
      <el-table-column label="性别" prop="gender"/>
      <el-table-column label="联系电话" prop="tel"/>
      <el-table-column label="邮箱" prop="email"/>
      <el-table-column label="申请时间" prop="dateOfApplication"/>
      <el-table-column label="备注" prop="remarks"/>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="onRegisterClick(scope.row)">办理入职</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="current"
      :page-sizes="[5, 10, 30, 50]"
      :page-size="size"
      :total="total"
      background
      class="pagination"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <el-dialog :visible.sync="dialogEmployeeVisible" title="办理入职">
      <el-container>
        <el-main>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form ref="form" :model="form" :rules="rules" status-icon label-width="80px">
                <el-col :span="12">
                  <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="电话" prop="tel">
                    <el-input v-model.number="form.tel"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="年龄" prop="age">
                    <el-input v-model.number="form.age"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="学历" prop="education">
                    <el-select v-model="form.education" placeholder="请选择学历">
                      <el-option label="大专" value="0"/>
                      <el-option label="本科" value="1"/>
                      <el-option label="硕士" value="2"/>
                      <el-option label="博士" value="3"/>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="薪资" prop="salary">
                    <el-input
                      v-model.number="form.salary"
                      placeholder="请填写薪资"
                      prefix-icon="el-icon-star-off"
                    >
                      <template slot="append">元</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="入职时间" prop="entryDate">
                    <el-date-picker
                      v-model="form.entryDate"
                      type="date"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      placeholder="选择日期"
                      style="width: 100%;"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="在职状态" prop="workingStatus">
                    <el-select v-model="form.workingStatus" placeholder="请选择在职状态">
                      <el-option label="试用期" value="0"/>
                      <el-option label="正职" value="1"/>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-form>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onCancelSubmit">取 消</el-button>
        <el-button type="primary" @click="onSubmitEmployeeForm('form')">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import { listApplicantsByPage, register } from '@/api/employee'

export default {
  name: 'Register',
  data() {
    return {
      list: null,
      listLoading: true,
      total: 0,
      size: 10,
      current: 1,
      dialogEmployeeVisible: false,
      form: {
        name: '',
        tel: '',
        age: '',
        gender: '',
        email: '',
        education: '',
        deptId: '',
        jobId: '',
        applicantId: '',
        salary: '',
        entryDate: '',
        workingStatus: ''
      },
      rules: {
        name: [{ required: true, message: '请填写员工姓名', trigger: 'blur' }],
        tel: [
          { required: true, message: '请填写员工联系电话', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请填写年龄', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        education: [
          { required: true, message: '请选择员工学历', trigger: 'change' }
        ],
        salary: [
          { required: true, message: '请填写员工薪资', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        entryDate: [
          { required: true, message: '请选择入职时间', trigger: 'change' }
        ],
        workingStatus: [
          { required: true, message: '请选择在职状态', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      listApplicantsByPage(this.size, this.current).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleCurrentChange(val) {
      this.current = val
      this.fetchData()
    },
    handleSizeChange(val) {
      this.size = val
      this.current = 1
      this.fetchData()
    },
    onRegisterClick(data) {
      // 获取表格信息
      this.form.name = data.name
      this.form.tel = Number(data.tel)
      this.form.age = data.age
      this.form.gender = 0
      if (data.gender === '男') {
        this.form.gender = 1
      }
      this.form.email = data.email
      this.form.deptId = data.deptId
      this.form.jobId = data.jobId
      this.form.applicantId = data.id
      this.form.salary = data.salary
      this.dialogEmployeeVisible = true
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    onCancelSubmit() {
      this.dialogEmployeeVisible = false
    },
    onSubmitEmployeeForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // 提交
          register(this.form).then(response => {
            Message({
              showClose: true,
              message: response.message,
              type: 'success',
              duration: 1 * 1000
            })
            this.fetchData()
            this.dialogEmployeeVisible = false
          }).catch(function error() {
            Message({
              showClose: true,
              message: '提交失败，请重试或联系系统管理员',
              type: 'error',
              duration: 2 * 1000
            })
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>
