import request from '@/utils/request'

export function listCommuteExceptionsByPage(size, current) {
  return request({
    url: '/api/commutes/exception',
    method: 'get',
    params: { size, current }
  })
}

export function callMorningAttentionToEmployee(data) {
  return request({
    url: '/api/commutes/morningCall',
    method: 'post',
    data
  })
}

export function callAfternoonAttentionToEmployee(data) {
  return request({
    url: '/api/commutes/afternoonCall',
    method: 'post',
    data
  })
}

export function listTodayCommutesByPage(size, current) {
  return request({
    url: '/api/commutes/today',
    method: 'get',
    params: { size, current }
  })
}

export function listHistoryCommutesByPage(size, current) {
  return request({
    url: '/api/commutes/history',
    method: 'get',
    params: { size, current }
  })
}

export function listHistoryCommutesByFilter(size, current, dept, job, employeeName) {
  return request({
    url: '/api/commutes/history/filter',
    method: 'get',
    params: { size, current, dept, job, employeeName }
  })
}

export function isClock() {
  return request({
    url: '/api/commutes/clock',
    method: 'get'
  })
}

export function toWork() {
  return request({
    url: '/api/commutes/to-work',
    method: 'post'
  })
}

export function offWork() {
  return request({
    url: '/api/commutes/off-work',
    method: 'post'
  })
}

export function listCommuteExceptionsForEmployee() {
  return request({
    url: '/api/commuteExceptions/employees',
    method: 'get'
  })
}

export function updateCommuteException(data) {
  return request({
    url: '/api/commuteExceptions',
    method: 'put',
    data
  })
}

export function quickCommute(data) {
  return request({
    url: '/api/commuteExceptions/quickCommute',
    method: 'put',
    data
  })
}

export function createLeave(data) {
  return request({
    url: '/api/leaves',
    method: 'post',
    data
  })
}

export function listMyLeaves() {
  return request({
    url: '/api/leaves/my',
    method: 'get'
  })
}

export function listLeaves(size, current) {
  return request({
    url: '/api/leaves/page',
    method: 'get',
    params: { size, current }
  })
}

export function updateLeave(data) {
  return request({
    url: '/api/leaves',
    method: 'put',
    data
  })
}
