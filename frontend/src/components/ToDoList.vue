<template>
  <div id="todolist">
    <q-card v-for="(item, i) in items" :key="i">
      <div class="q-pa-sm"> {{ item.title }} </div>
    </q-card>
  </div>
</template>

<script setup>
import axios from '../utils/axios.js';
const page = ref(1);
const records = ref(0);
const items = ref([]);
const router = useRouter();
const getItem = () => {
  axios.get('/item',{
  })
  .then((res) => {
    if(res.status === 200) {
      records.value = res.headers['x-total-count'] || 0;
      items.value = res.data.data;
      console.log(items.value)
    } else {
      console.log('router')
      router.push('/login')
    }
  })
}
onMounted(getItem)
</script>

<style></style>