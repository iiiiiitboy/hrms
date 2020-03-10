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
              <el-table-column label="出差编号" prop="id" align="center"/>
              <el-table-column label="员工编号" prop="employeeId" align="center"/>
              <el-table-column label="员工姓名" prop="employeeName" align="center"/>
              <el-table-column label="开始时间" prop="startDate" align="center"/>
              <el-table-column label="结束时间" prop="endDate" align="center"/>
              <el-table-column label="借款" prop="expenses" align="center"/>
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
              <el-table-column fixed="right" label="操作" align="center" width="200">
                <template slot-scope="scope">
                  <el-button
                    :disabled="confirmDisabled || !scope.row.reviewed || scope.row.finished"
                    type="text"
                    size="small"
                    @click="onConfirmClick(scope.row)"
                  >确认出差</el-button>
                  <el-button
                    :disabled="scope.row.holding || scope.row.finished"
                    type="text"
                    size="small"
                    @click="onExtensionClick(scope.row)"
                  >延期</el-button>
                  <el-button
                    :disabled="scope.row.holding || scope.row.finished"
                    type="text"
                    size="small"
                    @click="onCheckInClick(scope.row)"
                  >报道</el-button>
                  <el-button
                    :disabled="!scope.row.finished || scope.row.reimbursed"
                    type="text"
                    size="small"
                    @click="onReimburseClick(scope.row)"
                  >报销</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
    <el-dialog :visible.sync="dialogExtensionFormVisible" title="出差延期" width="30%">
      <el-form
        ref="extensionForm"
        :model="extensionForm"
        :rules="extensionFormRules"
        label-width="100px"
      >
        <el-form-item label="结束时间" prop="endDate">
          <el-date-picker
            v-model="extensionForm.endDate"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="请选择新的结束时间"
            default-time="8:00:00"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogExtensionFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onExtensionSubmitClick('extensionForm')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogReimburseFormVisible" title="申请报销" width="40%">
      <el-form
        ref="reimburseForm"
        :model="reimburseForm"
        :rules="reimburseFormRules"
        label-width="100px"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="借款">
              <el-input v-model.number="reimburseForm.loan" readonly>
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="reimburseForm.diffLabel">
              <el-input v-model.number="reimburseForm.diffShow" readonly>
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="实际用款" prop="expenses">
              <el-input v-model.number="reimburseForm.expenses" @change="countDiff">
                <template slot="append">元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="22">
            <el-form-item label="详细描述" prop="description">
              <el-input
                v-model="reimburseForm.description"
                type="textarea"
                rows="6"
                placeholder="可参考：
1.如图1从*到*的车票200元
2.如图2住宿1晚100元"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="凭证照片" prop="fileList">
              <el-col :span="24">
                <el-upload
                  ref="upload"
                  :multiple="true"
                  :auto-upload="false"
                  :file-list="reimburseForm.fileList"
                  :on-change="handleChange"
                  :on-remove="handleRemove"
                  class="upload-demo"
                  accept=".png, .jpg"
                  list-type="picture"
                  action="https://jsonplaceholder.typicode.com/posts/"
                >
                  <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                  <div slot="tip" class="el-upload__tip">只能上传JPG/PNG文件</div>
                </el-upload>
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogReimburseFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onReimburseSubmitClick('reimburseForm')">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import { listMyBusinessTrips, confirmBusinessTrip, checkInBusinessTrip, updateBusinessTrip } from '@/api/business-trip'
import { reimburseForTrip } from '@/api/reimbursement'

export default {
  name: 'MyBusinessTrip',
  data() {
    var validateEndDate = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请选择新的出差结束时间'))
      } else if (new Date(this.extensionForm.rawEndDate) - new Date(value) > 0) {
        callback(new Error('新的时间必须比原来的时间晚'))
      } else {
        callback()
      }
    }
    return {
      list: null,
      listLoading: true,
      confirmDisabled: true,
      dialogExtensionFormVisible: false,
      dialogReimburseFormVisible: false,
      extensionForm: {
        id: '',
        rawEndDate: '',
        endDate: ''
      },
      extensionFormRules: {
        endDate: [
          { required: true, message: '请选择新的出差结束时间', trigger: 'change' },
          { validator: validateEndDate, trigger: 'change' }
        ]
      },
      reimburseForm: {
        id: '',
        loan: '',
        expenses: '',
        diff: '',
        diffShow: '',
        diffLabel: '报销金额',
        description: '',
        fileList: []
      },
      reimburseFormRules: {
        expenses: [
          { required: true, message: '请填写实际用款', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        description: [{ required: true, message: '请填写报销详细描述', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      listMyBusinessTrips().then(response => {
        this.list = response.data
        var temp = true
        for (let index = 0; index < this.list.length; index++) {
          // 只要有一个出差中的记录就将所有确认出差按钮禁用
          temp = temp && !(!this.list[index].holding && !this.list[index].finished)
        }
        this.confirmDisabled = !temp
        this.listLoading = false
      })
    },
    // 确认出差
    onConfirmClick(row) {
      confirmBusinessTrip({ id: row.id, employeeId: row.employeeId }).then(response => {
        this.fetchData()
        Message({
          showClose: true,
          message: response.message,
          type: 'success',
          duration: 2 * 1000
        })
      }).catch(error => {
        Message({
          showClose: true,
          message: error.message,
          type: 'error',
          duration: 2 * 1000
        })
      })
    },
    // 延期
    onExtensionClick(row) {
      this.extensionForm.id = row.id
      this.extensionForm.rawEndDate = row.endDate
      this.dialogExtensionFormVisible = true
    },
    onExtensionSubmitClick(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          updateBusinessTrip({ id: this.extensionForm.id, endDate: this.extensionForm.endDate }).then(response => {
            this.fetchData()
            this.dialogExtensionFormVisible = false
            this.$refs[formName].resetFields()
            Message({
              showClose: true,
              message: response.message,
              type: 'success',
              duration: 2 * 1000
            })
          }).catch(error => {
            Message({
              showClose: true,
              message: error.message,
              type: 'error',
              duration: 2 * 1000
            })
          })
        } else {
          return false
        }
      })
    },
    // 报道
    onCheckInClick(row) {
      checkInBusinessTrip({ id: row.id, employeeId: row.employeeId }).then(response => {
        this.fetchData()
        Message({
          showClose: true,
          message: response.message,
          type: 'success',
          duration: 2 * 1000
        })
      }).catch(error => {
        Message({
          showClose: true,
          message: error.message,
          type: 'error',
          duration: 2 * 1000
        })
      })
    },
    // 报销
    onReimburseClick(row) {
      this.reimburseForm.id = row.id
      this.reimburseForm.loan = row.expenses
      this.dialogReimburseFormVisible = true
    },
    countDiff() {
      this.reimburseForm.diff = this.reimburseForm.loan - this.reimburseForm.expenses
      if (this.reimburseForm.diff > 0) {
        this.reimburseForm.diffLabel = '应还金额'
        this.reimburseForm.diffShow = this.reimburseForm.diff
      } else {
        this.reimburseForm.diffLabel = '报销金额'
        this.reimburseForm.diffShow = Math.abs(this.reimburseForm.diff)
      }
    },
    handleChange(file, fileList) {
      this.reimburseForm.fileList = fileList
    },
    handleRemove(file, fileList) {
      this.reimburseForm.fileList = fileList
    },
    onReimburseSubmitClick(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          const formData = new FormData()
          for (let index = 0; index < this.reimburseForm.fileList.length; index++) {
            formData.append('files', this.reimburseForm.fileList[index].raw, this.reimburseForm.fileList[index].name)
          }
          formData.append('id', this.reimburseForm.id)
          formData.append('loan', this.reimburseForm.loan)
          formData.append('expenses', this.reimburseForm.expenses)
          if (this.reimburseForm.diff > 0) {
            formData.append('debt', this.reimburseForm.diff)
          } else {
            formData.append('reimbursements', Math.abs(this.reimburseForm.diff))
          }
          formData.append('description', this.reimburseForm.description)
          reimburseForTrip(formData).then(response => {
            this.fetchData()
            this.dialogReimburseFormVisible = false
            this.reimburseForm.diffShow = ''
            this.$refs[formName].resetFields()
            Message({
              showClose: true,
              message: response.message,
              type: 'success',
              duration: 2 * 1000
            })
          }).catch(error => {
            Message({
              showClose: true,
              message: error.message,
              type: 'error',
              duration: 2 * 1000
            })
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>
