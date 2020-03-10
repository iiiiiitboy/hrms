import request from '@/utils/request'

export function listApplicantsByPage(size, current) {
  return request({
    url: '/api/applicants/page',
    method: 'get',
    params: { size, current }
  })
}

export function register(data) {
  return request({
    url: '/api/employees',
    method: 'post',
    data
  })
}

export function listEmployeesByPage(size, current) {
  return request({
    url: '/api/employees/page',
    method: 'get',
    params: { size, current }
  })
}

export function listEmployeesByFilter(size, current, dept, job, workingStatusId) {
  return request({
    url: '/api/employees/filter',
    method: 'get',
    params: { size, current, dept, job, workingStatusId }
  })
}

export function updateEmployee(data) {
  return request({
    url: '/api/employees',
    method: 'put',
    data
  })
}

export function listEmployeeNamesAndIds() {
  return request({
    url: '/api/employees/namesAndIds',
    method: 'get'
  })
}

export function listEmployeeIdsAndNames() {
  return request({
    url: '/api/employees/idsAndNames',
    method: 'get'
  })
}
