import request from '@/utils/request'

// 查询所有角色
export function listAllRole() {
  return request({
    url: '/system/role/listAllRole',
    method: 'get'
  })
}
// 查询角色列表
export function listRole(query) {
  return request({
    url: '/system/role/list',
    method: 'get',
    params: query
  })
}
// 查询角色详细
export function getRole(roleId) {
  return request({
    url: '/system/role/' + roleId,
    method: 'get'
  })
}
// 修改角色
export function updateRole(data) {
  return request({
    url: '/system/role',
    method: 'put',
    data: data
  })
}
