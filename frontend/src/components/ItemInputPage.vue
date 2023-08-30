<template>
    <q-dialog>
      <q-card square bordered class="my-card q-pa-lg shadow-1" style="width: 1100px; max-width: 90vw;">
<!--        <q-card-section class="row items-center q-pb-none">-->
<!--          <q-space />-->
<!--          <q-btn icon="close" flat round dense v-close-popup />-->
<!--        </q-card-section>-->
        <q-card-section horizontal>
          <q-card-section>
            <div class="q-pa-md">
              <div class="q-gutter-md row items-start">
                <q-date v-model="targetTime" mask="YYYY-MM-DDTHH:mm" color="light-blue-7" />
              </div>
            </div>
          </q-card-section>
          <q-separator vertical />
          <q-card-section>
            <div class="q-pa-md">
              <div class="q-gutter-md row items-start">
                <q-time v-model="targetTime" mask="YYYY-MM-DDTHH:mm" color="light-blue-7" />
              </div>
            </div>
          </q-card-section>
          <q-separator vertical />
          <q-card-section style="width: 310px">
            <q-input square filled v-model="title" type="email" label="title" />
            <q-separator />
            <q-input square filled v-model="content" type="textarea" label="content" />
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
const title =  ref("");
const content = ref("");
const targetTime = ref("");
const router = useRouter();

const emit = defineEmits(['change-add-pop-up', 'refresh-todo-list'])



const add = async () => {
  const res = await axios.post('/item/create', {
    targetTime: targetTime.value,
    title: title.value,
    content: content.value
  })

  if(res.status === 201) {
    console.log('item add')
    alert('일정이 등록되었습니다.')
    await emit('change-add-pop-up')
    await emit('refresh-todo-list')
  }
}
</script>

<style scoped>
.q-card {
  width: 400px;
}
</style>