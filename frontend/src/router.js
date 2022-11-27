
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import VacationManager from "./components/listers/VacationCards"
import VacationDetail from "./components/listers/VacationDetail"
import VacationDaysLeftManager from "./components/listers/VacationDaysLeftCards"
import VacationDaysLeftDetail from "./components/listers/VacationDaysLeftDetail"

import VacationStatusView from "./components/VacationStatusView"
import VacationStatusViewDetail from "./components/VacationStatusViewDetail"
import ScheduleView from "./components/ScheduleView"
import ScheduleViewDetail from "./components/ScheduleViewDetail"
import VacationDaysStatusView from "./components/VacationDaysStatusView"
import VacationDaysStatusViewDetail from "./components/VacationDaysStatusViewDetail"
import CalendarManager from "./components/listers/CalendarCards"
import CalendarDetail from "./components/listers/CalendarDetail"


export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/vacations',
                name: 'VacationManager',
                component: VacationManager
            },
            {
                path: '/vacations/:id',
                name: 'VacationDetail',
                component: VacationDetail
            },
            {
                path: '/vacationDaysLefts',
                name: 'VacationDaysLeftManager',
                component: VacationDaysLeftManager
            },
            {
                path: '/vacationDaysLefts/:id',
                name: 'VacationDaysLeftDetail',
                component: VacationDaysLeftDetail
            },

            {
                path: '/vacationStatuses',
                name: 'VacationStatusView',
                component: VacationStatusView
            },
            {
                path: '/vacationStatuses/:id',
                name: 'VacationStatusViewDetail',
                component: VacationStatusViewDetail
            },
            {
                path: '/schedules',
                name: 'ScheduleView',
                component: ScheduleView
            },
            {
                path: '/schedules/:id',
                name: 'ScheduleViewDetail',
                component: ScheduleViewDetail
            },
            {
                path: '/vacationDaysStatuses',
                name: 'VacationDaysStatusView',
                component: VacationDaysStatusView
            },
            {
                path: '/vacationDaysStatuses/:id',
                name: 'VacationDaysStatusViewDetail',
                component: VacationDaysStatusViewDetail
            },
            {
                path: '/calendars',
                name: 'CalendarManager',
                component: CalendarManager
            },
            {
                path: '/calendars/:id',
                name: 'CalendarDetail',
                component: CalendarDetail
            },



    ]
})
