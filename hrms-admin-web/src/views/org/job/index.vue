<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary">添加岗位</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="部门编号" prop="id"/>
      <el-table-column label="部门名称" prop="deptName"/>
      <el-table-column label="岗位名称" prop="name"/>
      <el-table-column label="层级" prop="level"/>
      <el-table-column label="创建时间" prop="createDate"/>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="!scope.row.reviewed"
            type="text"
            size="small"
            @click="onModifyClick(scope.row)"
          >修改</el-button>
          <el-button type="text" size="small" @click="onDeleteClick(scope.row)">删除</el-button>
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
import { listJobByPage } from '@/api/job'

export default {
  name: 'Job',
  data() {
    return {
      list: null,
      listLoading: true,
      total: 0,
      size: 10,
      current: 1,
      dialogFormVisible: false,
      form: {
        id: '',
        deptName: '',
        jobName: '',
        typeOfWorking: '',
        yearsOfWorking: '',
        numberOf: '',
        addressOfWorking: '',
        education: '',
        requirement: ''
      },
      rules: {
        typeOfWorking: [
          { required: true, message: '请选择工作类型', trigger: 'blur' }
        ],
        yearsOfWorking: [
          { required: true, message: '请填写工作年限要求', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        numberOf: [
          { required: true, message: '请填写要招聘的人数', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        addressOfWorking: [
          { required: true, message: '请填写工作地区', trigger: 'blur' }
        ],
        education: [
          { required: true, message: '请选择学历要求', trigger: 'blur' }
        ],
        requirement: [
          { required: true, message: '请填写岗位要求', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      listJobByPage(this.size, this.current).then(response => {
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
.filter-container {
  margin-bottom: 20px;
}

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
  width: 50%;
}
</style>
