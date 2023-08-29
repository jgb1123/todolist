<template>
  <div id="todolist">
    <q-card v-for= "(item, i) in items" :key="i" class="q-mt-md my-card bg-primary text-white shadow-2">
      <q-card-section>
        <div class="text-h5">{{ dayjs(item.targetTime).format('YYYY-MM-DD HH:mm') }}</div>
        <q-separator dark/>
        <div class="text-h6">{{ item.title }}</div>
        <q-separator dark/>
        <div class="q-ma-md">
          <q-scroll-area style="height: 50px; max-width: 400px; word-break: break-all">
              <div class="q-py-xs">{{ item.content }}</div>
          </q-scroll-area>
        </div>
      </q-card-section>

      <q-separator dark/>

      <q-card-actions>
        <q-btn flat>Edit</q-btn>
        <q-btn flat>Delete</q-btn>
      </q-card-actions>
    </q-card>
  </div>
</template>

<script setup>
import axios from '../utils/axios.js';
import dayjs from "dayjs";
import { useCookies } from "vue3-cookies";

const { cookies } = useCookies();
const page = ref(1);
const records = ref(0);
const items = ref([]);

const getItem = () => {
  axios
      .get('/item')
      .then((res) => {
        if(res.status === 200) {
        records.value = res.headers['x-total-count'] || 0;
        items.value = res.data.data;
        console.log(items.value)
        }
      })
}

onMounted(getItem)
</script>

<style scoped>
.q-card {
  width: 600px;
  height: 240px;
}
</style>