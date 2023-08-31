<template>
  <q-dialog>
    <q-card square bordered class="my-card q-pa-lg shadow-1" style="width: 1100px; max-width: 90vw;">
      <q-card-section horizontal>
        <q-card-section style="width: 310px">
          <h4>일정 변경</h4>
          <q-input square filled v-model="title" type="email" label="title" style="width: 260px" />
          <q-separator />
          <q-input square filled v-model="content" type="textarea" label="content" style="width: 260px" />
          <q-card-actions class="q-px-md">
            <q-btn unelevated color="light-blue-7" size="lg" class="full-width" label="수정" @click="edit" />
          </q-card-actions>
        </q-card-section>
        <q-separator vertical />
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
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script setup>
const props = defineProps(['nowItem'])
const emit = defineEmits(['refresh-todo-list', 'change-edit-pop-up'])
const title = ref("");
const content = ref("");
const targetTime = ref("");

import axios from "../utils/axios.js";
const edit = async () => {
  const res = await axios.post(`/item/update/${props.nowItem.itemId}`, {
    targetTime: targetTime.value,
    title: title.value,
    content: content.value
  })
  if(res.status === 200) {
    await alert('일정이 변경되었습니다.')
    await emit('change-edit-pop-up', 0)
    await emit('refresh-todo-list')
  }
}
</script>