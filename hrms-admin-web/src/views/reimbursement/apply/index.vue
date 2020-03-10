<template>
  <div style="padding:30px">
    <el-container>
      <el-main>
        <el-row>
          <el-col :span="12" :offset="6">
            <el-form ref="form" :model="form" :rules="rules" status-icon label-width="80px">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="时间" prop="time">
                    <el-date-picker
                      v-model="form.time"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      type="datetime"
                      placeholder="请选择时间"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="费用" prop="expenses">
                    <el-col :span="18">
                      <el-input v-model.number="form.expenses" placeholder="请输入费用">
                        <template slot="append">元</template>
                      </el-input>
                    </el-col>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="描述" prop="description">
                    <el-input v-model="form.description" type="textarea" rows="6"/>
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
                        :file-list="form.fileList"
                        :on-change="handleChange"
                        :on-remove="handleRemove"
                        class="upload-demo"
                        accept=".png, .jpg"
                        list-type="picture"
                        action="#"
                      >
                        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                        <div slot="tip" class="el-upload__tip">只能上传JPG/PNG文件</div>
                      </el-upload>
                    </el-col>
                  </el-form-item>
                </el-col>
              </el-row>
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
import { apply } from '@/api/reimbursement'

export default {
  name: 'ApplyReimbursement',
  data() {
    return {
      list: null,
      form: {
        time: '',
        expenses: '',
        description: '',
        fileList: []
      },
      rules: {
        time: [{ required: true, message: '请选择时间', trigger: 'change' }],
        expenses: [
          { required: true, message: '请填写预算', trigger: 'blur' },
          { type: 'number', message: '请填写数字值', trigger: 'blur' }
        ],
        description: [{ required: true, message: '请填写报销描述', trigger: 'blur' }]
      }
    }
  },
  methods: {
    handleChange(file, fileList) {
      this.form.fileList = fileList
    },
    handleRemove(file, fileList) {
      this.form.fileList = fileList
    },
    onSubmitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          const formData = new FormData()
          for (let index = 0; index < this.form.fileList.length; index++) {
            formData.append('files', this.form.fileList[index].raw, this.form.fileList[index].name)
          }
          formData.append('time', this.form.time)
          formData.append('expenses', this.form.expenses)
          formData.append('description', this.form.description)
          apply(formData).then(response => {
            this.$refs[formName].resetFields()
            Message({
              showClose: true,
              message: response.message,
              type: 'success',
              duration: 2 * 1000
            }).catch(error => {
              Message({
                showClose: true,
                message: error.message,
                type: 'error',
                duration: 2 * 1000
              })
            })
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
