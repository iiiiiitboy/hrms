import request from '@/utils/request'

export function listJobOptions(deptId) {
  return request({
    url: '/api/jobs/options',
    method: 'get',
    params: { deptId }
  })
}

export function listJobByPage(size, current) {
  return request({
    url: '/api/jobs/page',
    method: 'get',
    params: { size, current }
  })
}
