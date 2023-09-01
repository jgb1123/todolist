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

      <q-card-actions align="right">
        <q-btn icon="mode_edit" color="primary" @click="editItem(item)" />
        <q-btn icon="delete" color="primary" @click="confirm(item.itemId)" />
      </q-card-actions>
    </q-card>
  </div>
</template>

<script setup>
import axios from '../utils/axios.js';
import dayjs from "dayjs";

const router = useRouter();
const $q = useQuasar();
// const page = ref(1);
const records = ref(0);
const items = ref([]);
const emit = defineEmits(['change-edit-pop-up'])

const getItem = async () => {
  const res = await axios.get('/item/find')

  if(res.status === 200) {
    records.value = res.headers['x-total-count'] || 0;
    items.value = res.data.data;
  }
}

const confirm = (itemId) => {
  $q.dialog({
    ok: {
      color: 'negative'
    },
    cancel: true,
    message: '해당 일정을 정말 삭제하시겠습니까?',
  }).onOk(() => {
    deleteItem(itemId)
  })
}

const alertDelete = () => {
  $q.dialog({
    message: '삭제되었습니다.'
  })
}

const deleteItem = async (itemId) => {
  const res = await axios.post(`/item/delete/${itemId}`)
  if(res.status === 204){
    alertDelete()
    await getItem()
  }
}

const editItem = async (item) => {
  await emit(`change-edit-pop-up`, item)
}

defineExpose({getItem})

onMounted( ()=> {
  getItem();
})

</script>

<style scoped>
.q-card {
  width: 600px;
  height: 240px;
}
</style>