<template>
  <v-container>
    <!-- Header -->
    <v-sheet class="mb-5">
        <v-btn color="secondary" @click="router.go(-1)"><</v-btn>
        <h1>{{ book.bid ? 'Edit Book' : 'Add New Book' }}</h1>
    </v-sheet>

    <!-- Contents -->
    <v-sheet class="mb-5 mt-5">
        <v-form>
          <v-row class="border" v-if="book.bid">
            <v-col cols="3" class="text-center font-weight-bold border">ID</v-col>
            <v-col cols="9" class="border">
              {{ book.bid }}
            </v-col>
          </v-row>
          <v-row class="">
            <v-col cols="3" class="text-center font-weight-bold border">책 제목</v-col>
            <v-col cols="9" class="border">
              <v-text-field v-model="book.title"
                            :error-messages="v$.title.$errors.map(e => e.$message as string)"
                            required />
            </v-col>
          </v-row>
          <v-row class="">
            <v-col cols="3" class="text-center font-weight-bold border">저자</v-col>
            <v-col cols="9" class="border">
              <v-text-field v-model="book.author"
                            :error-messages="v$.author.$errors.map(e => e.$message as string)"
                            required />
            </v-col>
          </v-row>
          <v-row class="">
            <v-col cols="3" class="text-center font-weight-bold border">가격</v-col>
            <v-col cols="9" class="border">
              <v-text-field v-model="book.price"
                            type="number"
                            required />
            </v-col>
          </v-row>
          <v-row class="">
            <v-col cols="3" class="text-center font-weight-bold border">재고</v-col>
            <v-col cols="9" class="border">
              <v-text-field v-model="book.cnt"
                            type="number"
                            required />
            </v-col>
          </v-row>
        </v-form>
    </v-sheet>

    <!-- Buttons -->
    <v-sheet class="mt-5 d-flex justify-end">
      <v-btn color="primary" @click="saveBook">{{ book.bid ? 'Update' : 'Create' }}</v-btn>
    </v-sheet>
  </v-container>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { getBook, createBook, updateBook } from '@/api/books';
import type { Books } from '@/types/books';
import useVuelidate from '@vuelidate/core'
import { required } from '@vuelidate/validators'

const route = useRoute();
const router = useRouter();

const book = reactive<Books>({
  bid: 0,
  title: '',
  author: '',
  price: 0,
  cnt: 0
});

const rules = computed(() => ({
    title: { required, $message: '필수 입력 항목입니다.' },
    author: { required, $message: '필수 입력 항목입니다.' }
}))

const v$ = useVuelidate(rules, book)

const loadData = async () => {
    const bid = Number(route.params.bid);

    if (bid) {
     try {
       const response = await getBook(Number(bid));
       Object.assign(book, response);
     } catch (error) {
       console.error('Failed to fetch book details:', error);
     }
    }
}

const saveBook = async () => {
  v$.value.$validate()
  if (v$.value.$error) {
    return
  }

  try {
    if (confirm(`저장하시겠습니까?`)) {
      if (book.bid) {
        await updateBook(book.bid, book);
        alert('Book updated successfully!');
      } else {
        const response = await createBook(book);
        book.bid = response.bid;
        alert('Book created successfully!');
      }

      router.replace(`/books/${book.bid}`)
    }
  } catch (error) {
    console.error('Failed to save book:', error);
    alert('Failed to save book.');
  }
};

onMounted(loadData);
</script>
