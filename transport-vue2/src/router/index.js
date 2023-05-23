import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const router = new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import('../views/AboutView.vue')
        },
        {
            path: '/login',
            name: 'login',
            //meta: {layout: 'empty'},
            component: () => import('../views/LoginView.vue')
        },
        {
            path: '/categories',
            name: 'categories',
            //meta: {layout: 'main'},
            component: () => import('../views/CategoriesView.vue')
        },
        {
            path: '/register',
            name: 'register',
            //meta: {layout: 'empty'},
            component: () => import('../views/RegisterView.vue')
        },
        {
            path: '/profile',
            name: 'profile',
            //meta: {layout: 'main'},
            component: () => import('../views/ProfileView.vue')
        },
        {
            path: '/drivers',
            name: 'drivers',
            //meta: {layout: 'main'},
            component: () => import('../views/DriversView.vue')
        },
        {
            path: '/buses',
            name: 'buses',
            //meta: {layout: 'main'},
            component: () => import('../views/BusesView.vue')
        },
        {
            path: '/schedule',
            name: 'schedule',
            //meta: {layout: 'main'},
            component: () => import('../views/ScheduleView.vue')
        }
    ]
})
export default router
