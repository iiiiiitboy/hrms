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
      <el-table-column fixed="right" label="提醒员工" align="center" width="160">
        <template slot-scope="scope">
          <el-button
            :disabled="scope.row.morningCalled"
            type="text"
            size="small"
            @click="onSendMorningExcpetionClick(scope.row)"
          >
            <span v-if="scope.row.morningCalled">已 提 醒</span>
            <span v-else>上午异常</span>
          </el-button>
          <el-button
            :disabled="scope.row.afternoonCalled"
            type="text"
            size="small"
            @click="onSendAfternoonExcpetionClick(scope.row)"
          >
            <span v-if="scope.row.afternoonCalled">已 提 醒</span>
            <span v-else>下午异常</span>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="current"
      :page-sizes="[10, 30, 50, 100]"
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
import { listCommuteExceptionsByPage, callMorningAttentionToEmployee, callAfternoonAttentionToEmployee } from '@/api/commute'

export default {
  name: 'CommuteException',
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
      listCommuteExceptionsByPage(this.size, this.current).then(response => {
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
    onSendMorningExcpetionClick(row) {
      var data = { id: row.id, employeeId: row.employeeId, today: row.today, toWorkTime: row.toWorkTime, toWork: row.toWork, offWorkTime: row.offWorkTime, offWork: row.offWork }
      callMorningAttentionToEmployee(data).then(response => {
        // 下午也提醒了，则刷新页面重新加载数据
        if (row.afternoonCalled) {
          this.fetchData()
        } else {
          row.morningCalled = true
        }
        Message({
          showClose: true,
          message: response.message,
          type: 'success',
          duration: 1 * 1000
        })
      })
    },
    onSendAfternoonExcpetionClick(row) {
      var data = { id: row.id, employeeId: row.employeeId, today: row.today, toWorkTime: row.toWorkTime, toWork: row.toWork, offWorkTime: row.offWorkTime, offWork: row.offWork }
      callAfternoonAttentionToEmployee(data).then(response => {
        // 上午也提醒了，则刷新页面重新加载数据
        if (row.morningCalled) {
          this.fetchData()
        } else {
          row.afternoonCalled = true
        }
        Message({
          showClose: true,
          message: response.message,
          type: 'success',
          duration: 1 * 1000
        })
      })
    }
  }
}
</script>
