<template>
  <v-container>
    <!-- Header -->
    <v-sheet class="mb-5 d-flex justify-end">
        <v-text-field
          v-model="params.keyword"
          label="검색"
          @keyup.enter="loadData"
          clearable />
        <v-btn icon color="primary" @click="createBook">
          +
        </v-btn>
    </v-sheet>

    <!-- Contents -->
    <v-sheet class="mt-5 mb-5">
      <v-data-table
        :headers="headers"
        :items="list"
        hide-default-footer
        class="mt-4"
      >
        <template #item.title="{ item }">
          <router-link :to="`/books/${item.bid}`">{{ item.title }}</router-link>
        </template>
      </v-data-table>
      <v-pagination
        v-model="page.currentPage"
        :length="page.totalPages"
        :total-visible="params.size"
        @update:modelValue="loadData"
      >
        <template v-slot:prev>
          <v-btn height="48" @click="page.currentPage = 1" :disabled="page.currentPage === 1"><<</v-btn>
          <v-btn height="48" @click="--page.currentPage" :disabled="page.currentPage === 1"><</v-btn>
        </template>
        <template v-slot:next>
          <v-btn height="48" @click="++page.currentPage" :disabled="page.currentPage === page.totalPages">></v-btn>
          <v-btn height="48" @click="page.currentPage = page.totalPages" :disabled="page.currentPage === page.totalPages">>></v-btn>
        </template>
      </v-pagination>
    </v-sheet>
  </v-container>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { getBooks } from '@/api/books';
import type { Books, Params, Page } from '@/types';

const list = ref<Books[]>([]);
const params = reactive<Params>({
  keyword: '',
  page: 0,
  size: 10,
  sort: ''
});

const page: Page = reactive({
  number: 0,
  currentPage: 1,
  totalPages: 1
});

watch(() => page.currentPage, () => {
  loadData();
});

const headers = ref([
  { title: 'ID', key: 'bid' },
  { title: '제목', key: 'title' },
  { title: '저자', key: 'author' },
  { title: '가격', key: 'price' },
  { title: '판매 수량', key: 'cnt' },
]);

const loadData = async () => {
  try {
    params.page = page.currentPage - 1

    const response = await getBooks(params);
    list.value = response.content;
    page.totalPages = response.page.totalPages || 1;
  } catch (error) {
    console.error('Failed to fetch books:', error);
  }
};

const router = useRouter();

const createBook = () => {
  router.push({ name: 'Create' });
};

onMounted(loadData);
</script>
