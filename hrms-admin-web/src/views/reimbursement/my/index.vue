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
              <el-table-column type="expand">
                <template slot-scope="props">
                  <el-form label-position="left" inline class="demo-table-expand">
                    <el-form-item label="报销描述">
                      <span>{{ props.row.description }}</span>
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column label="报销编号" prop="id" align="center"/>
              <el-table-column label="员工编号" prop="employeeId" align="center"/>
              <el-table-column label="员工姓名" prop="employeeName" align="center"/>
              <el-table-column label="借款（元）" prop="loan" align="center"/>
              <el-table-column label="花费（元）" prop="expenses" align="center"/>
              <el-table-column label="应还款（元）" prop="debt" align="center"/>
              <el-table-column label="应报销（元）" prop="reimbursements" align="center"/>
              <el-table-column label="申请时间" prop="createDate" align="center"/>
              <el-table-column label="状态" align="center">
                <template slot-scope="scope">
                  <el-tag v-if="!scope.row.reviewed" type="info">领导审核中</el-tag>
                  <el-tag v-else-if="scope.row.reviewed && !scope.row.verified" type="warning">财务复核中</el-tag>
                  <el-tag
                    v-else-if="(scope.row.verified && !scope.row.finished) && scope.row.debt >= 0"
                  >财务处理中</el-tag>
                  <el-tag
                    v-else-if="(scope.row.verified && !scope.row.finished) && scope.row.reimbursements > 0"
                  >待收款</el-tag>
                  <el-tag v-else type="success">已完成</el-tag>
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
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { listMyReimbursements } from '@/api/reimbursement'

export default {
  name: 'MyReimbursement',
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
      listMyReimbursements(this.size, this.current).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleCurrentChange(val) {
      this.current = val
      this.fetchData()
    },
    handleSizeChange(val) {
      this.size = val
      this.current = 1
      this.fetchData()
    }
  }
}
</script>
<style>
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
  width: 70%;
}
</style>
