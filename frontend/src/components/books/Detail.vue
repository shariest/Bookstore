<template>
  <v-container>
    <!-- Header -->
    <v-sheet class="mb-5">
        <v-btn color="secondary" @click="router.go(-1)"><</v-btn>
    </v-sheet>

    <!-- Contents -->
    <v-sheet class="mb-5 mt-5">
      <v-row
        v-for="(value, key) in bookInfo"
        :key="key"
        class="border"
      >
        <v-col cols="3" class="text-center font-weight-bold border">{{ key }}</v-col>
        <v-col cols="9" class="border">{{ value }}</v-col>
      </v-row>
    </v-sheet>

    <!-- Buttons -->
    <v-sheet class="mt-5 d-flex justify-end">
      <v-btn color="primary" @click="router.push(`/books/${book.bid}/update`)" class="mr-2">수정</v-btn>
      <v-btn color="error" @click="deleteBook">삭제</v-btn>
    </v-sheet>
  </v-container>
</template>

<script setup lang="ts">
import { reactive, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import { getBook, deleteBook as deleteBookApi } from '@/api/books'
import type { Books } from '@/types/books'

const route = useRoute()
const router = useRouter()

const book = reactive<Books>({
  bid: 0,
  title: '',
  author: '',
  price: 0,
  cnt: 0
});
const bookInfo = computed(() => ({
  '아이디': book?.bid,
  '제목': book?.title,
  '저자': book?.author,
  '가격': book?.price,
  '판매 수량': book?.cnt
}))

const loadData = async () => {
  try {
    const response = await getBook(Number(route.params.bid))
    Object.assign(book, response);
  } catch (error) {
    console.error('Error fetching book:', error)
  }
}

const deleteBook = async () => {
  if (confirm('삭제하시겠습니까?')) {
    try {
      await deleteBookApi(book.bid)
      alert('삭제되었습니다.')
      router.replace('/books')
    } catch (error) {
      console.error('Error deleting book:', error)
    }
  }
}

onMounted(loadData)
</script>
