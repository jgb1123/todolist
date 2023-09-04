<template>
  <q-dialog>
    <q-card square bordered class="q-pa-lg shadow-1 bg-light-blue-1" style="width: 340px; max-width: 90vw;">
      <q-card-section horizontal>
        <q-card-section style="width: 310px">
          <h4>Status 삭제</h4>
          <q-select class="q-pa-sm" square filled v-model="data.statusName" :options="statuses" label="status" style="width: 260px" />

          <q-card-actions class="q-px-md">
            <q-btn unelevated color="light-blue-7" size="lg" class="full-width" label="삭제" @click="remove" />
          </q-card-actions>
        </q-card-section>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script setup>
import axios from "../utils/axios.js";

const $q = useQuasar()
const data = ref({
  statusId: "",
  statusName: ""
})
const router = useRouter();
const emit = defineEmits(['change-delete-status-pop-up'])
const statuses = ref([]);

const remove = async () => {
  const res = await axios.post('/status/delete', {
    statusName: data.value.statusName
  })

  if(res.status === 204) {
    alertRemove()
    await emit('change-delete-status-pop-up')
    data.value.statusName ="";
  }
}

const getStatuses = async () => {
  const res = await axios.get('/status/find')
  if(res.status === 200) {
    statuses.value = res.data.data.map(status => status.statusName);
  }
}
onMounted(() => {
  getStatuses()
})

const alertRemove = () => {
  $q.dialog({
    message: 'Status가 삭제되었습니다.'
  })
}
</script>

<style scoped>
.q-card {
  width: 400px;
}
</style>