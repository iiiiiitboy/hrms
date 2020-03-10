<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="员工编号" prop="employeeId" align="center"/>
      <el-table-column label="姓名" prop="employeeName" align="center"/>
      <el-table-column label="日期" prop="today" align="center"/>
      <el-table-column label="上班已打卡" prop="toWork" align="center">
        <template slot-scope="scope">
          <i v-if="scope.row.toWork" class="el-icon-check"/>
          <i v-else class="el-icon-close"/>
        </template>
      </el-table-column>
      <el-table-column label="上班时间" prop="toWorkTime" align="center"/>
      <el-table-column label="下班已打卡" prop="offWork" align="center">
        <template slot-scope="scope">
          <i v-if="scope.row.offWork" class="el-icon-check"/>
          <i v-else class="el-icon-close"/>
        </template>
      </el-table-column>
      <el-table-column label="下班时间" prop="offWorkTime" align="center"/>
      <el-table-column fixed="right" label="操作" align="center" width="80">
        <template slot-scope="scope">
          <el-button
            :disabled="scope.row.done"
            type="text"
            size="small"
            @click="onQuickCommutClick(scope.row)"
          >快速签卡</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogFormVisible" title="快速签卡" width="40%">
      <el-form ref="form" :model="form" :rules="rules" status-icon label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="员工编号">
              <el-input v-model="form.employeeId" :readonly="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名">
              <el-input v-model="form.employeeName" :readonly="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="日期">
              <el-input v-model="form.today" :readonly="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="扣除">
              <el-input v-model="form.deductedWorkingHours" :readonly="true">
                <template slot="append">工时</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注">
          <el-input v-model="form.remarks" :readonly="true"/>
        </el-form-item>
        <el-col :span="12">
          <el-form-item label="证明人姓名">
            <el-autocomplete
              v-model="witnessName"
              :fetch-suggestions="witnessNameQuerySearchAsync"
              placeholder="请搜索员工姓名"
              @select="handleWitnessNameSelect"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="证明人编号" prop="witnessId">
            <el-input v-model="witnessId" placeholder="请填写员工编号"/>
          </el-form-item>
        </el-col>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onConfirmException(form)">确认异常</el-button>
        <el-button type="primary" @click="onSubmitForm('form')">快速签卡</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { Message } from 'element-ui'
import { listCommuteExceptionsForEmployee, updateCommuteException, quickCommute } from '@/api/commute'
import { listEmployeeNamesAndIds } from '@/api/employee'

export default {
  name: 'QuickCommute',
  data() {
    return {
      list: null,
      listLoading: true,
      dialogFormVisible: false,
      form: {
        id: null,
        employeeId: null,
        employeeName: null,
        commuteId: null,
        witnessName: null,
        witnessId: null,
        today: null,
        toWorkTime: null,
        toWork: null,
        offWorkTime: null,
        offWork: null,
        deductedWorkingHours: null,
        commuteExceptionType: null,
        remarks: null,
        done: null
      },
      rules: {
        witnessId: [{ required: true, message: '请填写员工编号', trigger: 'blur' }]
      },
      witnessName: null,
      witnessId: null,
      employeeNamesAndIds: null,
      employeeIdsAndNames: null
    }
  },
  created() {
    this.fetchData()
  },
  mounted() {
    this.fetchWitnessData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      listCommuteExceptionsForEmployee().then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    fetchWitnessData() {
      listEmployeeNamesAndIds().then(response => {
        this.employeeNamesAndIds = response.data
      })
    },
    onQuickCommutClick(row) {
      this.form.id = row.id
      this.form.employeeId = row.employeeId
      this.form.employeeName = row.employeeName
      this.form.commuteId = row.commuteId
      this.form.today = row.today
      this.form.deductedWorkingHours = row.deductedWorkingHours
      this.form.commuteExceptionType = row.commuteExceptionType
      this.form.remarks = row.remarks
      this.dialogFormVisible = true
    },

    witnessNameQuerySearchAsync(queryString, cb) {
      var employeeNamesAndIds = this.employeeNamesAndIds
      var results = queryString
        ? employeeNamesAndIds.filter(this.createWitnessNameFilter(queryString))
        : employeeNamesAndIds

      clearTimeout(this.timeout)
      this.timeout = setTimeout(() => {
        cb(results)
      }, 2000 * Math.random())
    },
    createWitnessNameFilter(queryString) {
      return state => {
        return (
          state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        )
      }
    },
    handleWitnessNameSelect(item) {
      this.witnessId = item.id
      this.form.witnessId = item.id
      console.log(item)
    },

    witnessIdQuerySearchAsync(queryString, cb) {
      var employeeIdsAndNames = this.employeeIdsAndNames
      var results = queryString
        ? employeeIdsAndNames.filter(this.createWitnessIdFilter(queryString))
        : employeeIdsAndNames

      clearTimeout(this.timeout)
      this.timeout = setTimeout(() => {
        cb(results)
      }, 2000 * Math.random())
    },
    createWitnessIdFilter(queryString) {
      return state => {
        return (
          state.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        )
      }
    },
    handleWitnessIdSelect(item) {
      this.witnessName = item.name
      this.form.witnessName = item.name
      console.log(item)
    },
    onConfirmException(data) {
      this.$confirm(
        '确认通勤异常则本月扣除 ' +
        data.deductedWorkingHours +
        ' 个工时, 是否确认?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      )
        .then(() => {
          updateCommuteException({
            id: data.id,
            done: true
          }).then(response => {
            Message({
              showClose: true,
              message: '确认成功',
              type: 'success',
              duration: 2 * 1000
            })
            this.fetchData()
            this.dialogFormVisible = false
          })
        }
        ).catch(() => { })
    },
    onSubmitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          quickCommute({
            id: this.form.id,
            commuteId: this.form.commuteId,
            witnessId: this.witnessId,
            commuteExceptionType: this.form.commuteExceptionType
          }).then(response => {
            this.fetchData()
            this.dialogFormVisible = false
            Message({
              showClose: true,
              message: response.message,
              type: 'success',
              duration: 2 * 1000
            })
          }).catch(error => {
            Message({
              showClose: true,
              message: error.message,
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
