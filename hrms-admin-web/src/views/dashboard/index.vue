<template>
  <div class="dashboard-container">
    <el-row :gutter="8">
      <el-col :span="8">
        <div class="dashboard-card">
          <div class="dashboard-card-data">
            上班卡：
            <el-tag v-if="isToWork" type="success">已打卡</el-tag>
            <el-tag v-else type="info">未打卡</el-tag>
          </div>
          <div class="dashboard-card-data">
            下班卡：
            <el-tag v-if="isOffWork" type="success">已打卡</el-tag>
            <el-tag v-else type="info">未打卡</el-tag>
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="dashboard-card">b</div>
      </el-col>
      <el-col :span="8">
        <div class="dashboard-card">b</div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  data() {
    return {
      isToWork: null,
      isOffWork: null
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }
}
</script>

<style>
.dashboard-container {
  background-color: rgb(240, 242, 245);
}
.dashboard-card {
  background-color: rgb(255, 255, 255);
  margin: 30px 30px;
  height: 150px;
  border-radius: 5px;
}

.dashboard-card-data {
  line-height: 18px;
  color: rgba(0, 0, 0, 0.5);
  font-size: 16px;
  font-weight: 700;
  margin: 11px 2px;
}
</style>
