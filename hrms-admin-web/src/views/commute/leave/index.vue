<template>
  <div style="padding:30px">
    <el-container>
      <el-main>
        <el-row>
          <el-col>
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
              <el-table-column label="审核状态" align="center">
                <template slot-scope="scope">
                  <el-tag v-if="scope.row.reviewed" type="success">已审核</el-tag>
                  <el-tag v-else type="warning">未审核</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="提交时间" prop="createDate" align="center"/>
            </el-table>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { listMyLeaves } from '@/api/commute'

export default {
  name: 'Leave',
  data() {
    return {
      list: null,
      listLoading: true
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      listMyLeaves().then(response => {
        this.list = response.data
        this.listLoading = false
      })
    }
  }
}
</script>
