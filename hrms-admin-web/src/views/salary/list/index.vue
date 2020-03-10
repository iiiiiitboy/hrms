<template>
  <div style="padding:30px;">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
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
      <el-table-column label="审核状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="!scope.row.reviewed" type="info">未审核</el-tag>
          <el-tag v-else type="success">已审核</el-tag>
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
import { listAllMonthlySalaryByPage } from '@/api/salary'

export default {
  name: 'SalaryList',
  data() {
    return {
      list: null,
      listLoading: true,
      total: 0,
      size: 10,
      current: 1
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      listAllMonthlySalaryByPage(this.size, this.current).then(response => {
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
    }
  }
}
</script>
