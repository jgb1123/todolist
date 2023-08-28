<template>
  <div id="todolist">
    <q-card v-for="(item, i) in items" :key="i">
      <div class="q-pa-sm"> {{ item.title }} </div>
    </q-card>
  </div>
</template>

<script setup>
import axios from 'axios';
import {useCookies} from "vue3-cookies";
const { cookies } = useCookies();
import { ref, onMounted } from 'vue';

const page = ref(1);
const records = ref(0);
const items = ref([]);
const accessToken = cookies.get('accessToken')
const getItem = () => {
  axios.get('http://localhost:5173/api/v1/item',{
    headers: {
      'Authorization': `Bearer ${accessToken}`
    }
  })
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

<style></style>