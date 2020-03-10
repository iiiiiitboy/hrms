import request from '@/utils/request'

export function createRecruitment(data) {
  return request({
    url: '/api/recruitments',
    method: 'post',
    data
  })
}

export function listRecruitmentsByPage(size, current) {
  return request({
    url: '/api/recruitments/page',
    method: 'get',
    params: { size, current }
  })
}

export function listRecruitmentsByFilter(size, current, dept, job, reviewed, finished) {
  return request({
    url: '/api/recruitments/filter',
    method: 'get',
    params: { size, current, dept, job, reviewed, finished }
  })
}

/* export function hadApplicant(id) {
  return request({
    url: '/api/recruitments/' + id + '/hadApplicant',
    method: 'get'
  })
} */

export function updateRecruitment(data) {
  return request({
    url: '/api/recruitments',
    method: 'put',
    data
  })
}

export function deleteRecruitmentById(id) {
  return request({
    url: '/api/recruitments',
    method: 'delete',
    params: { id }
  })
}

export function lookApplicants(size, current) {
  return request({
    url: '/api/applicants/applying',
    method: 'get',
    params: { size, current }
  })
}

export function lookApplicantsByFilter(size, current, dept, job, interviewed, employed) {
  return request({
    url: '/api/applicants/filter',
    method: 'get',
    params: { size, current, dept, job, interviewed, employed }
  })
}

export function updateApplicant(data) {
  return request({
    url: '/api/applicants',
    method: 'put',
    data
  })
}

export function downloadResume(applicantId) {
  return request({
    url: 'applicants/' + applicantId + '/resume',
    method: 'get'
  })
}

export function getInterviewResult(applicantId, recruitmentId) {
  return request({
    url: '/api/interview-results/by',
    method: 'get',
    params: { applicantId, recruitmentId }
  })
}

export function saveInterviewResult(data) {
  return request({
    url: '/api/interview-results',
    method: 'post',
    data
  })
}

export function updateInterviewResult(data) {
  return request({
    url: '/api/interview-results',
    method: 'put',
    data
  })
}
