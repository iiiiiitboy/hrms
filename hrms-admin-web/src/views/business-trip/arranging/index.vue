<template>
  <div style="padding:30px">
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="12" :offset="6">
            <el-form ref="form" :model="form" :rules="rules" status-icon label-width="80px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="开始时间" prop="startDate">
                    <el-date-picker
                      v-model="form.startDate"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      type="datetime"
                      placeholder="请选择开始时间"
                      default-time="8:00:00"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="结束时间" prop="endDate">
                    <el-date-picker
                      v-model="form.endDate"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      type="datetime"
                      placeholder="请选择结束时间"
                      default-time="8:00:00"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="出差人员" prop="employeeIds">
                    <el-select
                      v-model="form.employeeIds"
                      :remote-method="remoteMethod"
                      :loading="loading"
                      multiple
                      filterable
                      remote
                      reserve-keyword
                      placeholder="请输入员工姓名"
                    >
                      <el-option
                        v-for="item in employeeOptions"
                        :key="item.value"
                        :label="item.name"
                        :value="item.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="预算" prop="expenses">
                    <el-col :span="18">
                      <el-input v-model.number="form.expenses">
                        <template slot="append">元</template>
                      </el-input>
                    </el-col>
                    <el-col :span="2">
                      <el-tooltip class="item" effect="dark" content="若不可预计，可填 0" placement="right">
                        <i class="el-icon-question"/>
                      </el-tooltip>
                    </el-col>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-col :span="24">
                <el-form-item label="出差原由" prop="reason">
                  <el-input v-model="form.reason" type="textarea" rows="6"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item>
                  <el-button type="primary" @click="onSubmitForm('form')">立即提交</el-button>
                  <el-button @click="onResetForm('form')">重置</el-button>
                </el-form-item>
              </el-col>
            </el-form>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { Message } from 'element-ui'
import { arrangingBusinessTrip } from '@/api/business-trip'
import { listEmployeeIdsAndNames } from '@/api/employee'

export default {
  name: 'ArrangingBusinessTrip',
  data() {
    return {
      loading: false,
      list: [],
      employeeOptions: [],
      form: {
        startDate: '',
        endDate: '',
        employeeIds: [],
        expenses: '',
        reason: ''
      },
      rules: {
        startDate: [{ required: true, message: '请选择出差开始时间', trigger: 'change' }],
        endDate: [{ required: true, message: '请选择出差结束时间', trigger: 'change' }],
        employeeIds: [{ required: true, message: '请选择出差人员', trigger: 'blur' }],
        expenses: [
          { required: true, message: '请填写预算', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        reason: [{ required: true, message: '请填写出差原由', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.fetchData()
  },
  mounted() {

  },
  methods: {
    fetchData() {
      listEmployeeIdsAndNames().then(response => {
        this.list = response.data
      })
    },
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.employeeOptions = this.list.filter(item => {
            return item.name.toLowerCase()
              .indexOf(query.toLowerCase()) > -1
          })
        }, 200)
      } else {
        this.employeeOptions = []
      }
    },
    onSubmitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          arrangingBusinessTrip(this.form).then(response => {
            Message({
              showClose: true,
              message: response.message,
              type: 'success',
              duration: 1 * 1000
            })
            this.$refs[formName].resetFields()
          })
        } else {
          return false
        }
      })
    },
    onResetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
