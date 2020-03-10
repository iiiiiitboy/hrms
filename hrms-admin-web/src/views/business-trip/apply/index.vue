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
                  <el-form-item label="借款" prop="expenses">
                    <el-col :span="18">
                      <el-input v-model.number="form.expenses">
                        <template slot="append">元</template>
                      </el-input>
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
import { apply } from '@/api/business-trip'

export default {
  name: 'ApplyBusinessTrip',
  data() {
    return {
      list: null,
      listLoading: true,
      form: {
        startDate: '',
        endDate: '',
        expenses: '',
        reason: ''
      },
      rules: {
        startDate: [{ required: true, message: '请选择出差开始时间', trigger: 'change' }],
        endDate: [{ required: true, message: '请选择出差结束时间', trigger: 'change' }],
        expenses: [
          { required: true, message: '请填写预算', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        reason: [{ required: true, message: '请填写出差原由', trigger: 'blur' }]
      }
    }
  },
  methods: {
    onSubmitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          apply(this.form).then(response => {
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
