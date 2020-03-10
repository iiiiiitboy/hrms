import request from '@/utils/request'

export function reimburseForTrip(data) {
  return request({
    url: '/api/reimbursements/business-trip',
    method: 'post',
    data
  })
}

export function apply(data) {
  return request({
    url: '/api/reimbursements/apply',
    method: 'post',
    data
  })
}

export function listMyReimbursements(size, current) {
  return request({
    url: '/api/reimbursements/my',
    method: 'get',
    params: { size, current }
  })
}
