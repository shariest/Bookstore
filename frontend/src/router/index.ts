// Composables
import { createRouter, createWebHistory } from 'vue-router/auto'

import List from '@/components/books/List.vue';
import Form from '@/components/books/Form.vue';
import Detail from '@/components/books/Detail.vue';

const routes = [
  { path: '/', redirect: '/books' },
  { path: '/books', name: 'List', component: List },
  { path: '/books/new', name: 'Create', component: Form },
  { path: '/books/:bid/update', name: 'Update', component: Form, props: true },
  { path: '/books/:bid', name: 'Detail', component: Detail, props: true },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// Workaround for https://github.com/vitejs/vite/issues/11804
router.onError((err, to) => {
  if (err?.message?.includes?.('Failed to fetch dynamically imported module')) {
    if (!localStorage.getItem('vuetify:dynamic-reload')) {
      console.log('Reloading page to fix dynamic import error')
      localStorage.setItem('vuetify:dynamic-reload', 'true')
      location.assign(to.fullPath)
    } else {
      console.error('Dynamic import error, reloading page did not fix it', err)
    }
  } else {
    console.error(err)
  }
})

router.isReady().then(() => {
  localStorage.removeItem('vuetify:dynamic-reload')
})

export default router
