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
      <el-table-column label="请假编号" prop="id" align="center"/>
      <el-table-column label="员工编号" prop="employeeId" align="center"/>
      <el-table-column label="员工姓名" prop="employeeName" align="center"/>
      <el-table-column label="开始时间" prop="dateFrom" align="center"/>
      <el-table-column label="结束时间" prop="dateTo" align="center"/>
      <el-table-column label="合计工时" prop="hoursOfLeave" align="center"/>
      <el-table-column label="请假类型" prop="typeOfLeave" align="center"/>
      <el-table-column label="请假事由" prop="reason" align="center"/>
      <el-table-column label="提交时间" prop="createDate" align="center"/>
      <el-table-column label="审核状态" align="center">
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
import { listLeaves, updateLeave } from '@/api/commute'

export default {
  name: 'LeaveList',
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
      listLeaves(this.size, this.current).then(response => {
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
    onReviewedSwitchChange(row) {
      updateLeave({ id: row.id, reviewed: row.reviewed }).then(response => {
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
