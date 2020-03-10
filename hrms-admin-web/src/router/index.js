import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in subMenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    meta: {
      title: '控制面板',
      icon: 'dashboard'
    },
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/hr',
    component: Layout,
    redirect: '/hr/recruitment',
    name: 'HR',
    meta: {
      title: '人事管理',
      icon: 'nested'
    },
    children: [
      {
        path: 'recruitment',
        component: () => import('@/views/hr/recruitment/index'), // Parent router-view
        name: 'Recruitment',
        meta: {
          title: '招聘模块',
          icon: 'form'
        },
        children: [
          {
            path: 'create',
            component: () => import('@/views/hr/recruitment/create'),
            name: 'CreateRecruitment',
            meta: { title: '创建招聘计划' }
          },
          {
            path: 'list',
            component: () => import('@/views/hr/recruitment/list'),
            name: 'RecruitmentList',
            meta: { title: '查看招聘计划' }
          },
          {
            path: 'applicant',
            component: () => import('@/views/hr/recruitment/look-applicant'),
            name: 'Applicant',
            meta: { title: '查看应聘者' }
          }
        ]
      },
      {
        path: 'employee',
        component: () => import('@/views/hr/employee/index'),
        meta: {
          title: '员工模块',
          icon: 'peoples'
        },
        children: [
          {
            path: 'register',
            component: () => import('@/views/hr/employee/register'),
            name: 'Register',
            meta: { title: '办理入职' }
          },
          {
            path: 'list',
            component: () => import('@/views/hr/employee/list'),
            name: 'EmployeeList',
            meta: { title: '查看员工列表' }
          }
        ]
      }
    ]
  },

  {
    path: '/commute',
    component: Layout,
    redirect: '/commute/clock',
    name: 'Commute',
    meta: {
      title: '通勤管理',
      icon: 'table'
    },
    children: [
      {
        path: 'clock',
        component: () => import('@/views/commute/clock/index'),
        name: 'Clock',
        meta: { title: '打卡' }
      },
      {
        path: 'quick-commute',
        component: () => import('@/views/commute/quick-commute/index'),
        name: 'QuickCommute',
        meta: { title: '快速签卡' }
      },
      {
        path: 'exception',
        component: () => import('@/views/commute/exception/index'),
        name: 'CommuteException',
        meta: { title: '通勤异常列表' }
      },
      {
        path: 'today',
        component: () => import('@/views/commute/today/index'),
        name: 'TodayCommute',
        meta: { title: '今日通勤列表' }
      },
      {
        path: 'history',
        component: () => import('@/views/commute/history/index'),
        name: 'HistoryCommute',
        meta: { title: '历史通勤列表' }
      },
      {
        path: 'leave/create',
        component: () => import('@/views/commute/create-leave/index'),
        name: 'CreateLeave',
        meta: { title: '请假' }
      },
      {
        path: 'leave',
        component: () => import('@/views/commute/leave/index'),
        name: 'Leave',
        meta: { title: '我的请假记录' }
      },
      {
        path: 'leave-list',
        component: () => import('@/views/commute/leave-list/index'),
        name: 'LeaveList',
        meta: { title: '请假审核' }
      },
      {
        path: 'statistics',
        component: () => import('@/views/commute/statistics/index'),
        name: 'Statistics',
        hidden: true,
        meta: { title: '数据统计' }
      }
    ]
  },

  {
    path: '/business-trip',
    component: Layout,
    redirect: '/business-trip/apply',
    name: 'BusinessTrip',
    meta: {
      title: '出差管理',
      icon: 'guide'
    },
    children: [
      {
        path: 'apply',
        component: () => import('@/views/business-trip/apply/index'),
        name: 'ApplyBusinessTrip',
        meta: { title: '申请出差' }
      },
      {
        path: 'my-business-trip',
        component: () => import('@/views/business-trip/my/index'),
        name: 'MyBusinessTrip',
        meta: { title: '我的出差记录' }
      },
      {
        path: 'arranging',
        component: () => import('@/views/business-trip/arranging/index'),
        name: 'ArrangingBusinessTrip',
        meta: { title: '安排出差' }
      },
      {
        path: 'list',
        component: () => import('@/views/business-trip/list/index'),
        name: 'BusinessTripList',
        meta: { title: '查看出差列表' }
      }
    ]
  },

  {
    path: '/reimbursement',
    component: Layout,
    redirect: '/reimbursement/apply',
    name: 'Reimbursement',
    meta: {
      title: '报销管理',
      icon: 'star'
    },
    children: [
      {
        path: 'apply',
        component: () => import('@/views/reimbursement/apply/index'),
        name: 'ApplyReimbursement',
        meta: { title: '申请报销' }
      },
      {
        path: 'my',
        component: () => import('@/views/reimbursement/my/index'),
        name: 'MyReimbursement',
        meta: { title: '我的报销' }
      },
      {
        path: 'list',
        component: () => import('@/views/reimbursement/list/index'),
        name: 'ReimbursementList',
        meta: { title: '查看报销申请' }
      }
    ]
  },

  {
    path: '/salary',
    component: Layout,
    redirect: '/salary/my',
    name: 'Salary',
    meta: {
      title: '薪资管理',
      icon: 'money'
    },
    children: [
      {
        path: 'my',
        component: () => import('@/views/salary/my/index'),
        name: 'MySalary',
        meta: { title: '我的薪资' }
      },
      {
        path: 'review',
        component: () => import('@/views/salary/review/index'),
        name: 'ReviewSalary',
        meta: { title: '薪资审核' }
      },
      {
        path: 'list',
        component: () => import('@/views/salary/list/index'),
        name: 'SalaryList',
        meta: { title: '查看薪资列表' }
      }
    ]
  },

  {
    path: '/org',
    component: Layout,
    redirect: '/org/dept',
    name: 'Organization',
    meta: {
      title: '组织架构',
      icon: 'tree'
    },
    children: [
      {
        path: 'dept',
        component: () => import('@/views/org/dept/index'),
        name: 'Dept',
        meta: { title: '部门管理' }
      },
      {
        path: 'job',
        component: () => import('@/views/org/job/index'),
        name: 'Job',
        meta: { title: '岗位管理' }
      }
    ]
  }
]

export const asyncRouterMap = [
  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
