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
              v-for="item in deptOptions"
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
              v-for="item in jobOptions"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="审核状态">
          <el-select
            v-model="listQuery.reviewed"
            clearable
            style="width: 140px"
            @change="onReviewedFilterChange"
          >
            <el-option label="未审核" value="0"/>
            <el-option label="已审核" value="1"/>
          </el-select>
        </el-form-item>
        <el-form-item label="完成状态">
          <el-select
            v-model="listQuery.finished"
            clearable
            style="width: 140px"
            @change="onFinishedFilterChange"
          >
            <el-option label="未完成" value="0"/>
            <el-option label="已完成" value="1"/>
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="招聘编号">
              <span>{{ props.row.id }}</span>
            </el-form-item>
            <el-form-item label="部门">
              <span>{{ props.row.deptName }}</span>
            </el-form-item>
            <el-form-item label="岗位">
              <span>{{ props.row.jobName }}</span>
            </el-form-item>
            <el-form-item label="工作类型">
              <span>{{ props.row.typeOfWorking }}</span>
            </el-form-item>
            <el-form-item label="工作年限">
              <span v-if="props.row.yearsOfWorking==0">应届生或经验不限</span>
              <span v-if="props.row.yearsOfWorking!=0">{{ props.row.yearsOfWorking }}及年以上工作经验</span>
            </el-form-item>
            <el-form-item label="招聘人数">
              <span>{{ props.row.numberOf }}人</span>
            </el-form-item>
            <el-form-item label="工作地点">
              <span>{{ props.row.addressOfWorking }}</span>
            </el-form-item>
            <el-form-item label="学历要求">
              <span>{{ props.row.education }}</span>
            </el-form-item>
            <el-form-item label="详细要求">
              <span>{{ props.row.requirement }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="招聘编号" prop="id"/>
      <el-table-column label="部门" prop="deptName"/>
      <el-table-column label="岗位" prop="jobName"/>
      <el-table-column label="审核状态（发布状态）">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.reviewed" @change="onReviewedSwitchChange(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column label="完成状态">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.finished"
            active-color="#13ce66"
            @change="onFinishedSwitchChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="!scope.row.reviewed"
            type="text"
            size="small"
            @click="onModifyClick(scope.row)"
          >修改</el-button>
          <el-button type="text" size="small" @click="onDeleteClick(scope.row)">删除</el-button>
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
    <el-dialog :visible.sync="dialogFormVisible" title="修改招聘计划">
      <el-container>
        <el-main>
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form ref="form" :model="form" :rules="rules" status-icon label-width="80px">
                <el-col :span="12">
                  <el-form-item label="部门">
                    <el-input v-model="form.deptName" :disabled="true"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="岗位">
                    <el-input v-model="form.jobName" :disabled="true"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="工作类型" prop="typeOfWorking">
                    <el-select v-model="form.typeOfWorking" placeholder="请选择工作类型">
                      <el-option label="全职" value="0"/>
                      <el-option label="兼职" value="1"/>
                      <el-option label="其他" value="2"/>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="学历要求" prop="education">
                    <el-select v-model="form.education" placeholder="请选择学历要求">
                      <el-option label="大专" value="0"/>
                      <el-option label="本科" value="1"/>
                      <el-option label="硕士" value="2"/>
                      <el-option label="博士" value="3"/>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="工作年限" prop="yearsOfWorking">
                    <el-input v-model.number="form.yearsOfWorking">
                      <template slot="append">年以上</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="招聘人数" prop="numberOf">
                    <el-input v-model.number="form.numberOf">
                      <template slot="append">人</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="工作地区" prop="addressOfWorking">
                    <el-input v-model="form.addressOfWorking"/>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item label="要求" prop="requirement">
                    <el-input v-model="form.requirement" type="textarea" rows="4"/>
                  </el-form-item>
                </el-col>
              </el-form>
            </el-col>
          </el-row>
        </el-main>
      </el-container>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onCancelClick('form')">取 消</el-button>
        <el-button type="primary" @click="onSubmitForm('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import { listDeptOptions } from '@/api/dept'
import { listJobOptions } from '@/api/job'
import { listRecruitmentsByPage, listRecruitmentsByFilter, updateRecruitment, deleteRecruitmentById } from '@/api/recruitment'

export default {
  name: 'ListRecruitment',
  data() {
    return {
      // filter
      deptOptions: [],
      jobOptions: [],
      listQuery: {
        dept: '',
        job: '',
        reviewed: '',
        finished: ''
      },
      // table
      list: null,
      listLoading: true,
      total: 0,
      size: 10,
      current: 1,
      dialogFormVisible: false,
      form: {
        id: '',
        deptName: '',
        jobName: '',
        typeOfWorking: '',
        yearsOfWorking: '',
        numberOf: '',
        addressOfWorking: '',
        education: '',
        requirement: ''
      },
      rules: {
        typeOfWorking: [
          { required: true, message: '请选择工作类型', trigger: 'blur' }
        ],
        yearsOfWorking: [
          { required: true, message: '请填写工作年限要求', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        numberOf: [
          { required: true, message: '请填写要招聘的人数', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        addressOfWorking: [
          { required: true, message: '请填写工作地区', trigger: 'blur' }
        ],
        education: [
          { required: true, message: '请选择学历要求', trigger: 'blur' }
        ],
        requirement: [
          { required: true, message: '请填写岗位要求', trigger: 'blur' }
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
      listRecruitmentsByPage(this.size, this.current).then(response => {
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
    onDeptFilterChange() {
      this.listQuery.job = ''
      this.getJobOptions(this.listQuery.dept)
      this.handleFilterToFirstPage()
    },
    onJobFilterChange() {
      this.handleFilterToFirstPage()
    },
    onReviewedFilterChange() {
      this.handleFilterToFirstPage()
    },
    onFinishedFilterChange() {
      this.handleFilterToFirstPage()
    },
    handleFilterToFirstPage() {
      listRecruitmentsByFilter(this.size, 1, this.listQuery.dept, this.listQuery.job, this.listQuery.reviewed, this.listQuery.finished).then(response => {
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
      listRecruitmentsByFilter(this.size, this.current, this.listQuery.dept, this.listQuery.job, this.listQuery.reviewed, this.listQuery.finished).then(response => {
        this.list = response.data.records
        this.total = response.data.total
      })
    },
    onReviewedSwitchChange(data) {
      // 已完成的不能取消审核
      if (data.finished) {
        data.reviewed = !data.reviewed
        Message({
          showClose: true,
          message: '已完成的招聘计划不能取消审核状态',
          type: 'error',
          duration: 2 * 1000
        })
        return
      }
      updateRecruitment(data).then(response => {
        Message({
          showClose: true,
          message: '修改成功',
          type: 'success',
          duration: 1 * 1000
        })
      }).catch(function error() {
        // 请求失败，服务器错误
        data.reviewed = !data.reviewed
        Message({
          showClose: true,
          message: '服务器开了点小差，请重试',
          type: 'error',
          duration: 2 * 1000
        })
      })
      // 已经有申请者的招聘计划不能取消审核
      /* var hadApplicantFlag = true
      hadApplicant(data.id).then(response => {
        hadApplicantFlag = response.data
        // 请求成功
        if (hadApplicantFlag) {
          // 已经有申请者
          data.reviewed = !data.reviewed
          Message({
            showClose: true,
            message: '该招聘计划已有申请者，不可取消审核',
            type: 'warning',
            duration: 2 * 1000
          })
          return
        }
        // 没有申请者，发起请求
        updateRecruitment(data).then(response => {
          Message({
            showClose: true,
            message: '修改成功',
            type: 'success',
            duration: 1 * 1000
          })
        }).catch(function error() {
          // 请求失败，服务器错误
          data.reviewed = !data.reviewed
          Message({
            showClose: true,
            message: '服务器开了点小差，请重试',
            type: 'error',
            duration: 2 * 1000
          })
          return
        })
      }).catch(function error() {
        // 请求失败，服务器错误
        data.reviewed = !data.reviewed
        Message({
          showClose: true,
          message: '服务器开了点小差，请重试',
          type: 'error',
          duration: 2 * 1000
        })
        return
      }) */
    },
    onFinishedSwitchChange(data) {
      // 未审核的不能完成
      if (data.reviewed) {
        updateRecruitment(data).then(response => {
          Message({
            showClose: true,
            message: '修改成功',
            type: 'success',
            duration: 1 * 1000
          })
        })
      } else {
        data.finished = !data.finished
        Message({
          showClose: true,
          message: '未审核的招聘计划不能改为完成状态',
          type: 'error',
          duration: 2 * 1000
        })
      }
    },
    onModifyClick(data) {
      this.form.id = data.id
      this.form.deptName = data.deptName
      this.form.jobName = data.jobName
      this.form.typeOfWorking = data.typeOfWorking
      this.form.yearsOfWorking = data.yearsOfWorking
      this.form.numberOf = data.numberOf
      this.form.addressOfWorking = data.addressOfWorking
      this.form.education = data.education
      this.form.requirement = data.requirement
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    onCancelClick(formName) {
      this.dialogFormVisible = false
    },
    onSubmitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          updateRecruitment(this.form).then(response => {
            this.fetchData()
            this.dialogFormVisible = false
            Message({
              showClose: true,
              message: '修改成功',
              type: 'success',
              duration: 1 * 1000
            })
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
    onDeleteClick(data) {
      this.$confirm('是否确认删除该招聘计划?', '提示', {
        confirmButtonClass: 'el-button el-button--danger',
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deleteRecruitmentById(data.id).then(response => {
            this.list.splice(this.list.indexOf(data), 1)
            Message({
              showClose: true,
              message: '删除成功',
              type: 'success',
              duration: 1 * 1000
            })
          }).catch(function error() {
            // 请求失败，服务器错误
            Message({
              showClose: true,
              message: '删除失败，请联系系统管理员',
              type: 'error',
              duration: 2 * 1000
            })
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除!'
          })
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
