<template>
  <q-dialog>
    <q-card square bordered class="q-pa-lg shadow-1 bg-light-blue-1" style="width: 340px; max-width: 90vw;">
      <q-card-section horizontal>
        <q-card-section style="width: 310px">
          <h4>Status 등록</h4>
          <q-input class="q-pa-sm" square filled v-model="data.statusName" type="email" label="Status Name" style="width: 260px" />

          <q-card-actions class="q-px-md">
            <q-btn unelevated color="light-blue-7" size="lg" class="full-width" label="추가" @click="add" />
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
  statusName: ""
})
const router = useRouter();
const emit = defineEmits(['change-status-pop-up', 'refresh-status-list'])


const add = async () => {
  try{
    const res = await axios.post('/status/create', {
      statusName: data.value.statusName
    })
    if(res.status === 201) {
      alertCreate()
      emit('change-status-pop-up')
      emit('refresh-status-list')
      data.value.statusName ="";
    }
  } catch (e) {
    alertDenied()
  }
}

const alertCreate = () => {
  $q.dialog({
    message: 'Status가 등록되었습니다.'
  })
}

const alertDenied = () => {
  $q.dialog({
    message: '이미 같은 이름의 Status가 존재합니다.'
  })
}
</script>

<style scoped>
.q-card {
  width: 400px;
}
</style>