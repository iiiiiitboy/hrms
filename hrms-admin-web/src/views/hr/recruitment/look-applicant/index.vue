<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form inline>
        <el-form-item label="部门">
          <el-select v-model="listQuery.dept" clearable style="width: 140px" @change="onDeptChange">
            <el-option
              v-for="item in deptOptions"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="岗位">
          <el-select v-model="listQuery.job" clearable style="width: 140px" @change="onJobChange">
            <el-option
              v-for="item in jobOptions"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="面试状态">
          <el-select
            v-model="listQuery.interviewed"
            clearable
            style="width: 140px"
            @change="handleFilter"
          >
            <el-option label="可面试" value="0"/>
            <el-option label="已面试" value="1"/>
          </el-select>
        </el-form-item>
        <el-form-item label="聘用状态">
          <el-select
            v-model="listQuery.employed"
            clearable
            style="width: 140px"
            @change="handleFilter"
          >
            <el-option label="可聘用" value="0"/>
            <el-option label="已聘用" value="1"/>
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
            <el-form-item label="应聘编号">
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
            <el-form-item label="申请时间">
              <span>{{ props.row.dateOfApplication }}</span>
            </el-form-item>
            <el-form-item label="面试状态">
              <span v-if="props.row.interviewed">已完成面试</span>
              <span v-else-if="props.row.interviewing">可面试，但未面试</span>
              <span v-else>未通过网申要求</span>
            </el-form-item>
            <el-form-item label="聘用状态">
              <span v-if="props.row.employed">已入职</span>
              <span v-else-if="props.row.passed">已通过面试，但未入职</span>
              <span v-else>未通过面试</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="编号" prop="id"/>
      <el-table-column label="部门" prop="deptName"/>
      <el-table-column label="岗位" prop="jobName"/>
      <el-table-column label="姓名" prop="name"/>
      <el-table-column label="年龄" prop="age"/>
      <el-table-column label="性别" prop="gender"/>
      <el-table-column label="联系电话" prop="tel"/>
      <el-table-column label="邮箱" prop="email"/>
      <el-table-column label="申请时间" prop="dateOfApplication"/>
      <el-table-column label="可面试">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.interviewing"
            @change="onChangeInterviewingSwitch(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="已面试">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.interviewed"
            @change="onChangeInterviewedSwitch(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="可聘用">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.passed"
            active-color="#13ce66"
            @change="onChangePassedSwitch(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="已聘用">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.employed"
            active-color="#13ce66"
            @change="onChangeEmployedSwitch(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="120px">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="onResumeClick(scope.row)">简历</el-button>
          <el-button
            v-show="scope.row.interviewed"
            type="text"
            size="small"
            @click="onInterviewResultClick(scope.row)"
          >面试结果</el-button>
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
    <el-dialog :visible.sync="dialogResumeVisible" title="简历">
      <object :data="resume" width="100%" height="600px" type="application/pdf">
        <p>
          抱歉，您的浏览器不支持PDF预览，请点击链接下载PDF文件
          <a :href="resume"/>
        </p>
      </object>
    </el-dialog>
    <el-dialog :visible.sync="dialogInterviewResultVisible" title="面试结果" width="40%">
      <el-form ref="form" :model="form" :rules="rules" status-icon label-width="80px">
        <el-form-item label="分数" prop="mark">
          <el-col :span="12">
            <el-input v-model.number="form.mark"/>
          </el-col>
        </el-form-item>
        <el-form-item label="评价" prop="appraisal">
          <el-input v-model="form.appraisal" type="textarea" rows="4"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onCancelInterviewResultClick">取 消</el-button>
        <el-button type="primary" @click="onSubmitInterviewResultForm('form')">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import { listDeptOptions } from '@/api/dept'
import { listJobOptions } from '@/api/job'
import { lookApplicants, lookApplicantsByFilter, updateApplicant, getInterviewResult, saveInterviewResult, updateInterviewResult } from '@/api/recruitment'

export default {
  name: 'ListApplicant',
  data() {
    return {
      // filter
      deptOptions: [],
      jobOptions: [],
      listQuery: {
        dept: '',
        job: '',
        interviewed: '',
        employed: ''
      },
      // table
      search: '',
      list: [],
      listLoading: true,
      total: 0,
      size: 10,
      current: 1,
      resume: null,
      dialogResumeVisible: false,
      dialogInterviewResultVisible: false,
      hadInterviewRecoded: false,
      form: {
        id: '',
        recruitmentId: '',
        applicantId: '',
        mark: '',
        appraisal: '',
        interviewer: ''
      },
      rules: {
        mark: [
          { required: true, message: '请填写分数', trigger: 'sumbit' },
          { type: 'number', message: '请填写数字值', trigger: 'sumbit' },
          {
            type: 'number',
            min: 0,
            max: 100,
            message: '分数请填写0-100',
            trigger: 'sumbit'
          }
        ],
        appraisal: [
          { required: true, message: '请认真填写评价', trigger: 'sumbit' }
        ]
      }
    }
  },
  created() {
    this.fetchData()
    this.getDeptOptions()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      lookApplicants(this.size, this.current).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    getDeptOptions() {
      listDeptOptions().then(response => {
        this.deptOptions = response.data
      })
    },
    getJobOptions(deptId) {
      // 级联读取岗位列表
      this.jobOptions = []
      listJobOptions(deptId).then(response => {
        this.jobOptions = response.data
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
    onDeptChange() {
      this.listQuery.job = ''
      this.handleFilter()
      this.getJobOptions(this.listQuery.dept)
    },
    onJobChange() {
      this.handleFilter()
    },
    handleFilter() {
      lookApplicantsByFilter(this.size, this.current, this.listQuery.dept, this.listQuery.job, this.listQuery.interviewed, this.listQuery.employed).then(response => {
        this.list = response.data.records
        this.total = response.data.total
      })
    },
    onChangeInterviewingSwitch(data) {
      // 已面试的不能取消可面试
      if (data.interviewed) {
        data.interviewing = !data.interviewing
        Message({
          showClose: true,
          message: '已面试的应聘者不能取消\"可面试\"状态，请先取消\"已面试\"状态后重试',
          type: 'error',
          duration: 2 * 1000
        })
        return
      }

      // 根据Alibaba规范，只传要修改的数据
      data = { id: data.id, interviewing: data.interviewing }
      updateApplicant(data).then(response => {
        Message({
          showClose: true,
          message: '修改成功',
          type: 'success',
          duration: 1 * 1000
        })
      }).catch(function error() {
        // 请求失败，服务器错误
        data.interviewing = !data.interviewing
        Message({
          showClose: true,
          message: '服务器开了点小差，请重试',
          type: 'error',
          duration: 2 * 1000
        })
      })
    },
    onChangeInterviewedSwitch(data) {
      // 可聘用的不能取消已面试，不可面试的不能已面试

      // 不可面试的不能已面试
      if (!data.interviewing) {
        data.interviewed = !data.interviewed
        Message({
          showClose: true,
          message: '不可面试的应聘者不能转为\"已面试\"状态，请先确认\"可面试\"状态后重试',
          type: 'error',
          duration: 2 * 1000
        })
        return
      }

      // 可聘用的不能取消已面试
      if (data.passed) {
        data.interviewed = !data.interviewed
        Message({
          showClose: true,
          message: '可聘用的应聘者不能取消\"已面试\"状态，请先取消\"可聘用\"状态后重试',
          type: 'error',
          duration: 2 * 1000
        })
        return
      }

      // 根据Alibaba规范，只传要修改的数据
      data = { id: data.id, interviewed: data.interviewed }
      updateApplicant(data).then(response => {
        Message({
          showClose: true,
          message: '修改成功',
          type: 'success',
          duration: 1 * 1000
        })
      }).catch(function error() {
        // 请求失败，服务器错误
        data.interviewed = !data.interviewed
        Message({
          showClose: true,
          message: '服务器开了点小差，请重试',
          type: 'error',
          duration: 2 * 1000
        })
      })
    },
    onChangePassedSwitch(data) {
      // 已雇佣的不能取消可聘用，未已面试的不可聘用

      // 未已面试的不可聘用
      if (!data.interviewed) {
        data.passed = !data.passed
        Message({
          showClose: true,
          message: '未面试的应聘者不能转为\"可聘用\"状态，请先确认\"已面试\"状态后重试',
          type: 'error',
          duration: 2 * 1000
        })
        return
      }

      // 已雇佣的不能取消可聘用
      if (data.employed) {
        data.passed = !data.passed
        Message({
          showClose: true,
          message: '已聘用的应聘者不能取消\"可聘用\"状态，请先取消\"已聘用\"状态后重试',
          type: 'error',
          duration: 2 * 1000
        })
        return
      }

      // 根据Alibaba规范，只传要修改的数据
      data = { id: data.id, passed: data.passed }
      updateApplicant(data).then(response => {
        Message({
          showClose: true,
          message: '修改成功',
          type: 'success',
          duration: 1 * 1000
        })
      }).catch(function error() {
        // 请求失败，服务器错误
        data.passed = !data.passed
        Message({
          showClose: true,
          message: '服务器开了点小差，请重试',
          type: 'error',
          duration: 2 * 1000
        })
      })
    },
    onChangeEmployedSwitch(data) {
      // 不可聘用的不能改为已聘用状态
      if (!data.passed) {
        data.employed = !data.employed
        Message({
          showClose: true,
          message: '请先确认应聘者是否可聘用',
          type: 'error',
          duration: 2 * 1000
        })
        return
      }

      // 根据Alibaba规范，只传要修改的数据
      data = { id: data.id, employed: data.employed }
      updateApplicant(data).then(response => {
        Message({
          showClose: true,
          message: '修改成功',
          type: 'success',
          duration: 1 * 1000
        })
      }).catch(function error() {
        // 请求失败，服务器错误
        data.interviewing = !data.interviewing
        Message({
          showClose: true,
          message: '服务器开了点小差，请重试',
          type: 'error',
          duration: 2 * 1000
        })
      })
    },
    onResumeClick(data) {
      // 请求简历
      this.resume = 'http://localhost:10001/applicants/' + data.id + '/resume'
      this.dialogResumeVisible = true
    },
    onInterviewResultClick(data) {
      this.form.applicantId = data.id
      this.form.recruitmentId = data.recruitmentId
      getInterviewResult(data.id, data.recruitmentId).then(response => {
        // 有面试结果，则显示数据
        if (response.data != null) {
          this.hadInterviewRecoded = true
          this.form.id = response.data.id
          this.form.mark = response.data.mark
          this.form.appraisal = response.data.appraisal
          this.form.interviewer = response.data.interviewer
        } else {
          // 无面试结果
          this.form.mark = ''
          this.form.appraisal = ''
          this.hadInterviewRecoded = false
        }
        this.dialogInterviewResultVisible = true
        this.$nextTick(() => {
          this.$refs['form'].clearValidate()
        })
      }).catch(function error() {
        this.hadInterviewRecoded = false
        Message({
          showClose: true,
          message: '服务器开了点小差，请重试',
          type: 'error',
          duration: 2 * 1000
        })
      })
    },
    onCancelInterviewResultClick() {
      this.dialogInterviewResultVisible = false
      this.form.mark = ''
      this.form.appraisal = ''
    },
    onSubmitInterviewResultForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.hadInterviewRecoded) {
            // 如果有面试结果记录则更新
            updateInterviewResult(this.form).then(response => {
              this.dialogInterviewResultVisible = false
              Message({
                showClose: true,
                message: '提交成功',
                type: 'success',
                duration: 1 * 1000
              })
            }).catch(function error() {
              Message({
                showClose: true,
                message: '提交失败，请重试或联系系统管理员',
                type: 'error',
                duration: 2 * 1000
              })
            })
          } else {
            // 没有面试结果记录则新建
            saveInterviewResult(this.form).then(response => {
              this.dialogInterviewResultVisible = false
              this.$refs[formName].resetFields()
              Message({
                showClose: true,
                message: '提交成功',
                type: 'success',
                duration: 1 * 1000
              })
            }).catch(function error() {
              Message({
                showClose: true,
                message: '提交失败，请重试或联系系统管理员',
                type: 'error',
                duration: 2 * 1000
              })
            })
          }
        } else {
          return false
        }
      })
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
