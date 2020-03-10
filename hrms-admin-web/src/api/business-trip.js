import request from '@/utils/request'

export function apply(data) {
  return request({
    url: '/api/business-trips',
    method: 'post',
    data
  })
}

export function listMyBusinessTrips() {
  return request({
    url: '/api/business-trips/my',
    method: 'get'
  })
}

export function confirmBusinessTrip(data) {
  return request({
    url: '/api/business-trips/confirm',
    method: 'put',
    data
  })
}

export function checkInBusinessTrip(data) {
  return request({
    url: '/api/business-trips/check-in',
    method: 'put',
    data
  })
}

export function updateBusinessTrip(data) {
  return request({
    url: '/api/business-trips',
    method: 'put',
    data
  })
}

export function arrangingBusinessTrip(data) {
  return request({
    url: '/api/business-trips/arranging',
    method: 'post',
    data
  })
}

export function listBusinessTripsByPage(size, current) {
  return request({
    url: '/api/business-trips/page',
    method: 'get',
    params: { size, current }
  })
}
