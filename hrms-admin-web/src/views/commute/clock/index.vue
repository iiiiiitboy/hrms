<template>
  <div>
    <el-row style="margin:20px 0;">
      <el-col :span="4" :offset="4">
        <el-row style="margin:20px;">
          <div style="padding:0 0 25px 0;">
            <label>上班：</label>
            <el-button :disabled="toWorkDisabled" type="primary" @click="onToWork">{{ toWorkName }}</el-button>
          </div>
          <div>
            <label>下班：</label>
            <el-button
              :disabled="offWorkDisabled"
              type="primary"
              @click="onOffWork"
            >{{ offWorkName }}</el-button>
          </div>
        </el-row>
      </el-col>
      <el-col :span="4" :offset="8">
        <clock :time="time"/>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="16" :offset="4">
        <el-table
          v-loading="listLoading"
          :data="list"
          element-loading-text="Loading"
          border
          fit
          highlight-current-row
        >
          <el-table-column label="姓名" prop="employeeName" align="center"/>
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
      </el-col>
    </el-row>
  </div>
</template>
<script>
import Clock from 'vue-clock2'
import DigitalClock from 'vue-digital-clock'
import { Message } from 'element-ui'
import { isClock, toWork, offWork } from '@/api/commute'

export default {
  components: { Clock, DigitalClock },
  data() {
    return {
      date: new Date(),
      time: this.date,
      toWorkDisabled: false,
      offWorkDisabled: false,
      toWorkName: '打卡',
      offWorkName: '打卡',
      list: [],
      listLoading: true
    }
  },
  created() {
    const _this = this // 声明一个变量指向Vue实例this，保证作用域一致
    this.timer = setInterval(() => {
      _this.date = new Date() // 修改数据date
    }, 1000)
    this.fetchData()
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer) // 在Vue实例销毁前，清除我们的定时器
    }
  },
  methods: {
    fetchData() {
      isClock().then(response => {
        if (response.data != null) {
          this.toWorkDisabled = response.data.isToWork
          this.offWorkDisabled = response.data.isOffWork
          if (response.data.isToWork) {
            this.toWorkName = '已打卡'
          }
          if (response.data.isOffWork) {
            this.offWorkName = '已打卡'
          }
          this.list = response.data.record
          this.listLoading = false
        }
      })
    },
    onToWork() {
      toWork().then(response => {
        this.toWorkName = '已打卡'
        this.toWorkDisabled = true
        Message({
          showClose: true,
          message: response.message,
          type: 'success',
          duration: 1 * 1000
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
    onOffWork() {
      offWork().then(response => {
        this.offWorkName = '已打卡'
        this.offWorkDisabled = true
        Message({
          showClose: true,
          message: response.message,
          type: 'success',
          duration: 1 * 1000
        })
      }).catch(error => {
        Message({
          showClose: true,
          message: error.message,
          type: 'error',
          duration: 2 * 1000
        })
      })
    }
  }
}
</script>
<style>
.clock2 {
  background-color: #263238;
  color: #eceff1;
  padding: 0.3rem 0.6rem;
  font-size: 3rem;
  font-family: "Menlo", monospace;
}

.clock-block {
  padding: 20px;
}

.clokck-card {
  background-color: rgb(208, 208, 208);
  margin: 30px 30px;
  height: 150px;
  border-radius: 5px;
}
</style>
