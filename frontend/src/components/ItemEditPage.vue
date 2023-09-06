<template>
  <q-dialog>
    <q-card square bordered class="my-card q-pa-lg shadow-1" style="width: 340px; max-width: 90vw;">
      <q-card-section horizontal>
        <q-card-section style="width: 310px">
          <h4>일정 변경</h4>
          <q-input class="q-pa-sm" square filled v-model="data.title" type="email" label="title" style="width: 260px" />
          <q-input class="q-pa-sm" square filled v-model="data.content" type="textarea" label="content" style="width: 260px" />
          <q-select class="q-pa-sm" square filled v-model="data.statusName" :options="statusStore.$state.statusNames" label="status" style="width: 260px" />

          <div class="q-pa-md" style="max-width: 300px">
            <q-input filled v-model="data.targetTime">
              <template v-slot:prepend>
                <q-icon name="event" class="cursor-pointer">
                  <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                    <q-date v-model="data.targetTime" mask="YYYY-MM-DDTHH:mm">
                      <div class="row items-center justify-end">
                        <q-btn v-close-popup label="Close" color="primary" flat />
                      </div>
                    </q-date>
                  </q-popup-proxy>
                </q-icon>
              </template>

              <template v-slot:append>
                <q-icon name="access_time" class="cursor-pointer">
                  <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                    <q-time v-model="data.targetTime" mask="YYYY-MM-DDTHH:mm" format24h>
                      <div class="row items-center justify-end">
                        <q-btn v-close-popup label="Close" color="primary" flat />
                      </div>
                    </q-time>
                  </q-popup-proxy>
                </q-icon>
              </template>
            </q-input>
          </div>

          <q-card-actions class="q-px-md">
            <q-btn unelevated color="light-blue-7" size="lg" class="full-width" label="수정" @click="edit" />
          </q-card-actions>
        </q-card-section>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script setup>
import axios from "../utils/axios.js";
import {useStatusStore} from "../store/StatusStore.js";

const statusStore = useStatusStore();

const $q = useQuasar()
const props = defineProps(['nowItem'])
const emit = defineEmits(['refresh-todo-list', 'change-edit-pop-up'])
const data = ref({
  title: "",
  content: "",
  targetTime: "",
  statusName: ""
})

const edit = async () => {
  const res = await axios.post(`/item/update/${props.nowItem.itemId}`, {
    title: data.value.title,
    content: data.value.content,
    targetTime: data.value.targetTime,
    statusName: data.value.statusName
  })
  if(res.status === 200) {
    await alertChange()
    emit('change-edit-pop-up', 0)
    emit('refresh-todo-list')
    resetData();
  }
}

const resetData = () => {
  data.value.title = "";
  data.value.content = "";
  data.value.targetTime = "";
  data.value.statusName = "";
}

const getNowItems = (item) => {
  data.value.title = item.title;
  data.value.content = item.content;
  data.value.targetTime = item.targetTime;
  data.value.statusName = item.statusName;
}

const alertChange = () => {
  $q.dialog({
    message: '일정이 변경되었습니다.'
  })
}
defineExpose({getNowItems})

</script>