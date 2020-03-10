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
      <el-table-column label="部门" prop="deptName" align="center"/>
      <el-table-column label="岗位" prop="jobName" align="center"/>
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
import { listTodayCommutesByPage } from '@/api/commute'

export default {
  name: 'TodayCommute',
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
      listTodayCommutesByPage(this.size, this.current).then(response => {
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
