<template>
  <div style="padding:30px">
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="12" :offset="6">
            <el-form ref="form" :model="form" :rules="rules" status-icon label-width="80px">
              <el-col :span="12">
                <el-form-item label="部门" prop="deptId">
                  <el-select v-model="form.deptId" placeholder="请选择部门" @change="onDeptChange">
                    <el-option
                      v-for="item in deptOptions"
                      :key="item.value"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="岗位" prop="jobId">
                  <el-select v-model="form.jobId" placeholder="请选择岗位">
                    <el-option
                      v-for="item in jobOptions"
                      :key="item.value"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工作类型" prop="typeOfWorking">
                  <el-select v-model="form.typeOfWorking" placeholder="请选择工作类型">
                    <el-option label="全职" value="0"/>
                    <el-option label="兼职" value="1"/>
                    <el-option label="其他" value="2"/>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="学历要求" prop="education">
                  <el-select v-model="form.education" placeholder="请选择学历要求">
                    <el-option label="大专" value="0"/>
                    <el-option label="本科" value="1"/>
                    <el-option label="硕士" value="2"/>
                    <el-option label="博士" value="3"/>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工作年限" prop="yearsOfWorking">
                  <el-input v-model.number="form.yearsOfWorking">
                    <template slot="append">年以上</template>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="招聘人数" prop="numberOf">
                  <el-input v-model.number="form.numberOf">
                    <template slot="append">人</template>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工作地区" prop="addressOfWorking">
                  <el-input v-model="form.addressOfWorking"/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="要求" prop="requirement">
                  <el-input v-model="form.requirement" type="textarea" rows="6"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item>
                  <el-button type="primary" @click="onSubmitForm('form')">立即创建</el-button>
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
import { listDeptOptions } from '@/api/dept'
import { listJobOptions } from '@/api/job'
import { createRecruitment } from '@/api/recruitment'

export default {
  name: 'CreateRecruitment',
  data() {
    return {
      deptOptions: [],
      jobOptions: [],
      form: {
        deptId: '',
        jobId: '',
        typeOfWorking: '',
        yearsOfWorking: '',
        numberOf: '',
        addressOfWorking: '',
        education: '',
        requirement: ''
      },
      rules: {
        deptId: [{ required: true, message: '请选择部门', trigger: 'change' }],
        jobId: [{ required: true, message: '请选择岗位', trigger: 'change' }],
        typeOfWorking: [
          { required: true, message: '请选择工作类型', trigger: 'change' }
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
          { required: true, message: '请选择学历要求', trigger: 'change' }
        ],
        requirement: [
          { required: true, message: '请填写岗位要求', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getDeptOptions()
  },
  methods: {
    getDeptOptions() {
      // 加载时读取部门列表
      listDeptOptions().then(response => {
        this.deptOptions = response.data
      })
    },
    onDeptChange(deptId) {
      // 级联读取岗位列表
      this.form.jobId = ''
      this.jobOptions = []
      listJobOptions(deptId).then(response => {
        this.jobOptions = response.data
      })
    },
    onSubmitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          createRecruitment(this.form).then(response => {
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
      this.form.jobId = ''
      this.jobOptions = []
      this.$refs[formName].resetFields()
    }
  }
}
</script>
