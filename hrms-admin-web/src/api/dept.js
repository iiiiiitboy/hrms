import request from '@/utils/request'

export function listDeptOptions() {
  return request({
    url: '/api/depts/options',
    method: 'get'
  })
}

export function listDeptByPage(size, current) {
  return request({
    url: '/api/depts/page',
    method: 'get',
    params: { size, current }
  })
}
