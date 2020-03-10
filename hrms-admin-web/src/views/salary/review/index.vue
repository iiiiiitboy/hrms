<template>
  <div style="padding:30px;">
    <el-button
      type="primary"
      icon="el-icon-circle-check-outline"
      style="margin-bottom:20px"
      @click="onReviewClick"
    >审核选中行</el-button>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      >
      <el-table-column type="selection" width="55"/>
      <el-table-column label="编号" prop="id" align="center"/>
      <el-table-column label="部门" prop="deptName" align="center"/>
      <el-table-column label="岗位" prop="jobName" align="center"/>
      <el-table-column label="员工编号" prop="employeeId" align="center"/>
      <el-table-column label="员工姓名" prop="employeeName" align="center"/>
      <el-table-column label="月度" prop="month" align="center"/>
      <el-table-column label="应出勤天数" prop="daysOfWorking" align="center"/>
      <el-table-column label="应出勤工时" prop="hoursOfWorking" align="center"/>
      <el-table-column label="实际出勤天数" prop="daysOfAttendance" align="center"/>
      <el-table-column label="缺勤天数" prop="daysOfAbsence" align="center"/>
      <el-table-column label="实际出勤工时" prop="hoursOfWorked" align="center"/>
      <el-table-column label="加班工时" prop="hoursOfOvertime" align="center"/>
      <el-table-column label="基础工资" prop="basicSalary" align="center"/>
      <el-table-column label="实发工资" prop="salary" align="center"/>
      <el-table-column fixed="right" label="审核状态" align="center">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.reviewed" @change="onReviewedSwitchChange(scope.row)"/>
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
  </div>
</template>
<script>
import { Message } from 'element-ui'
import { listNoReviewedMonthlySalaryByPage, reviewMonthlySalaryByBatch, updateMonthlySalary } from '@/api/salary'

export default {
  name: 'ReviewSalary',
  data() {
    return {
      list: null,
      listLoading: true,
      total: 0,
      size: 10,
      current: 1,
      multipleSelection: [],
      sendingIds: []
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      listNoReviewedMonthlySalaryByPage(this.size, this.current).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleCurrentChange(data) {
      this.current = data
      this.fetchData()
    },
    handleSizeChange(data) {
      this.size = data
      this.current = 1
      this.fetchData()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    onReviewClick() {
      this.multipleSelection.forEach(row => {
        this.sendingIds.push(row.id)
      })
      if (this.sendingIds.length !== 0) {
        reviewMonthlySalaryByBatch(this.sendingIds).then(response => {
          Message({
            showClose: true,
            message: '审核成功',
            type: 'success',
            duration: 2 * 1000
          })
          this.fetchData()
        }).catch(function error() {
          Message({
            showClose: true,
            message: '提交失败，请重试或联系系统管理员',
            type: 'error',
            duration: 2 * 1000
          })
        })
      }
    },
    onReviewedSwitchChange(row) {
      updateMonthlySalary({ id: row.id, reviewed: row.reviewed }).then(response => {
        Message({
          showClose: true,
          message: response.message,
          type: 'success',
          duration: 2 * 1000
        })
      })
    }
  }
}
</script>
