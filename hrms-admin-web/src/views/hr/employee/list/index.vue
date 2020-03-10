<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form inline>
        <el-form-item label="部门">
          <el-select
            v-model="listQuery.dept"
            clearable
            style="width: 140px"
            @change="onDeptFilterChange"
          >
            <el-option
              v-for="item in filterDeptOptions"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="岗位">
          <el-select
            v-model="listQuery.job"
            clearable
            style="width: 140px"
            @change="onJobFilterChange"
          >
            <el-option
              v-for="item in filterJobOptions"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="在职状态">
          <el-select
            v-model="listQuery.workingStatus"
            clearable
            style="width: 140px"
            @change="onWorkingStatusFilterChange"
          >
            <el-option label="试用期" value="PROBATION"/>
            <el-option label="在职" value="ONLINE"/>
            <el-option label="出差" value="BUSINESS_TRIP"/>
            <el-option label="休假" value="VACATION"/>
            <el-option label="离职" value="OFFLINE"/>
            <el-option label="退休" value="RETIRED"/>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="search" placeholder="输入姓名查找"/>
        </el-form-item>
      </el-form>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="员工编号">
              <span>{{ props.row.id }}</span>
            </el-form-item>
            <el-form-item label="部门">
              <span>{{ props.row.deptName }}</span>
            </el-form-item>
            <el-form-item label="岗位">
              <span>{{ props.row.jobName }}</span>
            </el-form-item>
            <el-form-item label="姓名">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label="年龄">
              <span>{{ props.row.age }}岁</span>
            </el-form-item>
            <el-form-item label="性别">
              <span>{{ props.row.gender }}</span>
            </el-form-item>
            <el-form-item label="联系电话">
              <span>{{ props.row.tel }}</span>
            </el-form-item>
            <el-form-item label="邮箱">
              <span>{{ props.row.email }}</span>
            </el-form-item>
            <el-form-item label="学历">
              <span>{{ props.row.education }}</span>
            </el-form-item>
            <el-form-item label="入职时间">
              <span>{{ props.row.entryDate }}</span>
            </el-form-item>
            <el-form-item label="基础工资">
              <span>{{ props.row.salary }}</span>
            </el-form-item>
            <el-form-item label="在职状态">
              <span>{{ props.row.workingStatus }}</span>
            </el-form-item>
            <el-form-item label="面试结果">
              <el-button type="text" @click="onInterviewResultClick(props.row)">查看</el-button>
              <el-dialog
                :visible.sync="dialogInterviewVisible"
                :append-to-body="true"
                title="面试结果"
                width="40%"
              >
                <el-form label-width="80px">
                  <el-form-item label="分数">
                    <el-col :span="12">
                      <el-input v-model.number="props.row.mark" :readonly="true"/>
                    </el-col>
                  </el-form-item>
                  <el-form-item label="评价">
                    <el-input
                      :readonly="true"
                      v-model="props.row.appraisal"
                      type="textarea"
                      rows="4"
                    />
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button type="primary" @click="dialogInterviewVisible = false">确 定</el-button>
                </div>
              </el-dialog>
            </el-form-item>
            <el-form-item label="简历">
              <el-button type="text" @click="onResumeClick(props.row)">查看</el-button>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="员工编号" prop="id"/>
      <el-table-column label="部门" prop="deptName"/>
      <el-table-column label="岗位" prop="jobName"/>
      <el-table-column label="姓名" prop="name"/>
      <el-table-column label="年龄" prop="age"/>
      <el-table-column label="性别" prop="gender"/>
      <el-table-column label="联系电话" prop="tel"/>
      <el-table-column label="学历" prop="education"/>
      <el-table-column label="入职时间" prop="entryDate"/>
      <el-table-column label="在职状态">
        <template slot-scope="scope">
          <el-select
            v-model="scope.row.workingStatus"
            placeholder="请选择工作情况"
            @change="onWorkingStatusChange(scope.row)"
          >
            <el-option label="试用期" value="试用期"/>
            <el-option label="在职" value="在职"/>
            <el-option label="出差" value="出差"/>
            <el-option label="休假" value="休假"/>
            <el-option label="离职" value="离职"/>
            <el-option label="退休" value="退休"/>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="onModifyEmployeeClick(scope.row)">修改</el-button>
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
    <el-dialog :visible.sync="dialogModifyEmployeeVisible" title="修改员工信息">
      <el-container>
        <el-main>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form ref="form" :model="form" :rules="rules" status-icon label-width="80px">
                <el-col :span="12">
                  <el-form-item label="员工编号">
                    <el-input v-model="form.id" :readonly="true"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="部门" prop="deptId">
                    <el-select v-model="form.deptId" placeholder="请选择部门" @change="onDeptChange">
                      <el-option
                        v-for="item in deptOptions"
                        :key="item.value"
                        :label="item.name"
                        :value="item.id"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="岗位" prop="jobId">
                    <el-select v-model="form.jobId" placeholder="请选择岗位">
                      <el-option
                        v-for="item in jobOptions"
                        :key="item.value"
                        :label="item.name"
                        :value="item.id"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="电话" prop="tel">
                    <el-input v-model.number="form.tel"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="年龄" prop="age">
                    <el-input v-model.number="form.age">
                      <template slot="append">岁</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="性别" prop="gender">
                    <el-switch
                      v-model="form.gender"
                      inactive-color="#F4ADE2"
                      active-color="#409EFF"
                      inactive-text="女"
                      active-text="男"
                      inactive-value="0"
                      active-value="1"
                    />
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
                    <el-select v-model="form.workingStatus" placeholder="请选择工作情况">
                      <el-option label="试用期" value="0"/>
                      <el-option label="正职" value="1"/>
                      <el-option label="出差" value="2"/>
                      <el-option label="休假" value="3"/>
                      <el-option label="离职" value="4"/>
                      <el-option label="退休" value="5"/>
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
    <el-dialog :visible.sync="dialogResumeVisible" title="简历">
      <object :data="resume" width="100%" height="600px" type="application/pdf">
        <p>
          抱歉，您的浏览器不支持PDF预览，请点击链接下载PDF文件
          <a :href="resume"/>
        </p>
      </object>
    </el-dialog>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import { listEmployeesByPage, listEmployeesByFilter, updateEmployee } from '@/api/employee'
import { listDeptOptions } from '@/api/dept'
import { listJobOptions } from '@/api/job'

export default {
  name: 'EmployeeList',
  data() {
    return {
      // filter
      filterDeptOptions: [],
      filterJobOptions: [],
      listQuery: {
        dept: '',
        job: '',
        workingStatus: ''
      },
      // table
      search: '',
      list: [],
      listLoading: true,
      total: 0,
      size: 10,
      current: 1,
      dialogFormVisible: false,
      dialogModifyEmployeeVisible: false,
      dialogInterviewVisible: false,
      resume: null,
      dialogResumeVisible: false,
      hadInterviewRecoded: false,
      deptOptions: [],
      jobOptions: [],
      form: {
        id: '',
        name: '',
        tel: '',
        age: '',
        gender: '',
        email: '',
        education: '',
        deptId: '',
        deptName: '',
        jobId: '',
        jobName: '',
        applicantId: '',
        salary: '',
        entryDate: '',
        workingStatus: '',
        resumePath: '',
        mark: '',
        appraisal: ''
      },
      rules: {
        deptId: [
          { required: true, message: '请选择部门', trigger: 'change' }
        ],
        jobId: [
          { required: true, message: '请选择岗位', trigger: 'change' }
        ],
        name: [
          { required: true, message: '请填写员工姓名', trigger: 'blur' }
        ],
        tel: [
          { required: true, message: '请填写员工电话', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请填写员工年龄', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择员工的性别', trigger: 'change' }
        ],
        education: [
          { required: true, message: '请选择学历要求', trigger: 'change' }
        ],
        salary: [
          { required: true, message: '请填写员工薪资', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        entryDate: [
          { required: true, message: '请选择入职时间', trigger: 'change' }
        ],
        workingStatus: [
          { required: true, message: '请选择工作状态', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.fetchData()
    listDeptOptions().then(response => {
      this.filterDeptOptions = response.data
      this.deptOptions = response.data
    })
  },
  methods: {
    fetchData() {
      this.listLoading = true
      listEmployeesByPage(this.size, this.current).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    onDeptFilterChange() {
      this.listQuery.job = ''
      listJobOptions(this.listQuery.dept).then(response => {
        this.filterJobOptions = response.data
      })
      this.handleFilterToFirstPage()
    },
    onJobFilterChange() {
      this.handleFilterToFirstPage()
    },
    onWorkingStatusFilterChange() {
      this.handleFilterToFirstPage()
    },
    handleFilterToFirstPage() {
      listEmployeesByFilter(this.size, 1, this.listQuery.dept, this.listQuery.job, this.listQuery.workingStatus).then(response => {
        this.list = response.data.records
        this.current = 1
        this.total = response.data.total
      })
    },
    handleCurrentChange(val) {
      this.current = val
      this.handleFilter()
    },
    handleSizeChange(val) {
      this.size = val
      this.current = 1
      this.handleFilter()
    },
    handleFilter() {
      listEmployeesByFilter(this.size, this.current, this.listQuery.dept, this.listQuery.job, this.listQuery.workingStatus).then(response => {
        this.list = response.data.records
        this.total = response.data.total
      })
    },
    onWorkingStatusChange(data) {
      data = { id: data.id, workingStatus: data.workingStatus }
      updateEmployee(data).then(response => {
        Message({
          showClose: true,
          message: response.message,
          type: 'success',
          duration: 1 * 1000
        })
      }).catch(function error() {
        Message({
          showClose: true,
          message: '修改失败，请重试或联系系统管理员',
          type: 'error',
          duration: 2 * 1000
        })
      })
    },
    onModifyEmployeeClick(data) {
      this.form.id = data.id
      this.form.name = data.name
      this.form.tel = data.tel
      this.form.age = data.age
      this.form.gender = '0'
      if (data.gender === '男') {
        this.form.gender = '1'
      }
      this.form.email = data.email
      this.form.education = data.education
      this.form.deptId = data.deptId
      listJobOptions(data.deptId).then(response => {
        this.jobOptions = response.data
      })
      this.form.jobId = data.jobId
      this.form.salary = data.salary
      this.form.entryDate = data.entryDate
      this.form.workingStatus = data.workingStatus
      this.dialogModifyEmployeeVisible = true
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    onDeptChange() {
      // 级联读取岗位列表
      this.form.jobId = null
      this.jobOptions = []
      listJobOptions(this.form.deptId).then(response => {
        this.jobOptions = response.data
      })
    },
    onCancelSubmit() {
      this.dialogModifyEmployeeVisible = false
    },
    onSubmitEmployeeForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          updateEmployee(this.form).then(response => {
            Message({
              showClose: true,
              message: '修改成功',
              type: 'success',
              duration: 2 * 1000
            })
            this.dialogModifyEmployeeVisible = false
            this.fetchData()
          }).catch(function error() {
            Message({
              showClose: true,
              message: '修改失败，请联系系统管理员',
              type: 'error',
              duration: 2 * 1000
            })
          })
        } else {
          return false
        }
      })
    },
    onInterviewResultClick() {
      this.dialogInterviewVisible = true
    },
    onResumeClick(data) {
      this.resume = 'http://localhost:10001/applicants/' + data.id + '/resume'
      this.dialogResumeVisible = true
    }
  }
}
</script>
<style>
.filter-container {
  margin-bottom: 20px;
}

.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
