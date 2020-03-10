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
      <el-table-column label="出差编号" prop="id" align="center"/>
      <el-table-column label="员工编号" prop="employeeId" align="center"/>
      <el-table-column label="员工姓名" prop="employeeName" align="center"/>
      <el-table-column label="开始时间" prop="startDate" align="center"/>
      <el-table-column label="结束时间" prop="endDate" align="center"/>
      <el-table-column label="预算" prop="expenses" align="center"/>
      <el-table-column label="出差原由" prop="reason" align="center"/>
      <el-table-column label="申请时间" prop="createDate" align="center"/>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="!scope.row.reviewed" type="info">未审核</el-tag>
          <el-tag v-else-if="scope.row.reviewed && scope.row.holding" type="warning">待出差</el-tag>
          <el-tag v-else-if="!scope.row.holding && !scope.row.finished">出差中</el-tag>
          <el-tag v-else type="success">已完成</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.reviewed"
            :disabled="!scope.row.holding || scope.row.finished"
            @change="onReviewedSwitchChange(scope.row)"
          />
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
import { listBusinessTripsByPage, updateBusinessTrip } from '@/api/business-trip'

export default {
  name: 'BusinessTripList',
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
      listBusinessTripsByPage(this.size, this.current).then(response => {
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
      updateBusinessTrip({ id: row.id, reviewed: row.reviewed }).then(response => {
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
