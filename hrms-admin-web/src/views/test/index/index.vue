<template>
  <div style="padding:30px;">
    <el-button @click="onClick">测试</el-button>
  </div>
</template>
<script>
import { test } from '@/api/test'

export default {
  name: 'TestIndex',
  data() {
    return {
    }
  },
  methods: {
    onClick() {
      test().then(response => {
        console.log(response)
      })
    }
  }
}
</script>

