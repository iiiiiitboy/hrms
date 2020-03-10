import request from '@/utils/request'

export function listMyMonthlySalaryByPage(size, current) {
  return request({
    url: '/api/monthly-salary/my',
    method: 'get',
    params: { size, current }
  })
}

export function listNoReviewedMonthlySalaryByPage(size, current) {
  return request({
    url: '/api/monthly-salary/no-reviewed',
    method: 'get',
    params: { size, current }
  })
}

export function listAllMonthlySalaryByPage(size, current) {
  return request({
    url: '/api/monthly-salary/all',
    method: 'get',
    params: { size, current }
  })
}

export function updateMonthlySalary(data) {
  return request({
    url: '/api/monthly-salary',
    method: 'put',
    data
  })
}

export function reviewMonthlySalaryByBatch(data) {
  return request({
    url: '/api/monthly-salary/review',
    method: 'put',
    data
  })
}
