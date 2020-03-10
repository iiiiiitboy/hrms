<template>
  <div style="padding:30px">
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="12" :offset="6">
            <el-form ref="form" :model="form" :rules="rules" status-icon label-width="80px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="请假时间" prop="dateOfLeave">
                    <el-date-picker
                      v-model="form.dateOfLeave"
                      :default-time="['08:00:00', '17:00:00']"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      type="datetimerange"
                      align="right"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="请假类型" prop="typeOfLeave">
                    <el-select v-model="form.typeOfLeave" placeholder="请选择请假类型">
                      <el-option label="事假" value="0"/>
                      <el-option label="病假" value="1"/>
                      <el-option label="生理假" value="2"/>
                      <el-option label="年假" value="5"/>
                      <el-option label="婚假" value="3"/>
                      <el-option label="产假" value="4"/>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="合计工时" prop="hoursOfLeave">
                    <el-input v-model.number="form.hoursOfLeave">
                      <template slot="append">小时</template>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-col :span="24">
                <el-form-item label="请假事由" prop="reason">
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
import { createLeave } from '@/api/commute'

export default {
  name: 'CreateLeave',
  data() {
    return {
      form: {
        dateOfLeave: '',
        typeOfLeave: '',
        hoursOfLeave: '',
        reason: ''
      },
      rules: {
        dateOfLeave: [{ required: true, message: '请选择请假时间', trigger: 'change' }],
        typeOfLeave: [{ required: true, message: '请选择请假类型', trigger: 'change' }],
        hoursOfLeave: [
          { required: true, message: '请填写合计工时，一天8个工时', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        reason: [{ required: true, message: '请填写请假事由', trigger: 'blur' }]
      }
    }
  },
  methods: {
    onSubmitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          var data = { dateFrom: this.form.dateOfLeave[0], dateTo: this.form.dateOfLeave[1], hoursOfLeave: this.form.hoursOfLeave, typeOfLeave: this.form.typeOfLeave, reason: this.form.reason }
          createLeave(data).then(response => {
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
