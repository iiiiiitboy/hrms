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
              v-for="item in filterDeptOptions"
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
              v-for="item in filterJobOptions"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="listQuery.employeeName" placeholder="输入姓名查找"/>
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="onSearchClick">搜索</el-button>
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
import { listHistoryCommutesByPage, listHistoryCommutesByFilter } from '@/api/commute'
import { listDeptOptions } from '@/api/dept'
import { listJobOptions } from '@/api/job'

export default {
  name: 'HistoryCommute',
  data() {
    return {
      // filter
      filterDeptOptions: [],
      filterJobOptions: [],
      listQuery: {
        dept: '',
        job: '',
        employeeName: null
      },
      // table
      search: '',
      list: [],
      listLoading: true,
      total: 0,
      size: 10,
      current: 1
    }
  },
  created() {
    this.fetchData()
    listDeptOptions().then(response => {
      this.filterDeptOptions = response.data
    })
  },
  methods: {
    fetchData() {
      this.listLoading = true
      listHistoryCommutesByPage(this.size, this.current).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    onDeptFilterChange() {
      this.listQuery.job = ''
      // this.handleFilter()
      listJobOptions(this.listQuery.dept).then(response => {
        this.filterJobOptions = response.data
      })
    },
    onJobFilterChange() {
      // this.handleFilter()
    },
    onSearchClick() {
      listHistoryCommutesByFilter(this.size, 1, this.listQuery.dept, this.listQuery.job, this.listQuery.employeeName).then(response => {
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
      listHistoryCommutesByFilter(this.size, this.current, this.listQuery.dept, this.listQuery.job, this.listQuery.employeeName).then(response => {
        this.list = response.data.records
        this.total = response.data.total
      })
    }
  }
}
</script>
