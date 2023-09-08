<template>
  <q-dialog>
    <q-card square bordered class="q-pa-lg shadow-1 bg-light-blue-1" style="width: 340px; max-width: 90vw;">
      <q-card-section horizontal>
        <q-card-section style="width: 310px">
          <h4>일정 등록</h4>
          <q-form ref="itemForm" @submit="add">
            <q-input :rules="[title_rules]"
                     class="q-pa-sm" square filled v-model="data.title" label="title" style="width: 260px" />
            <q-input class="q-pa-sm" square filled v-model="data.content" type="textarea" label="content" style="width: 260px" />
            <q-select class="q-pa-sm" square filled v-model="data.statusName" :options="statusStore.$state.statusNames" label="status" style="width: 260px" />

            <div style="width: 290px; max-width: 260px">
              <q-input :rules="[date_rules]"
                       class="q-pa-sm" filled v-model="data.targetTime">
                <template v-slot:prepend>
                  <q-icon name="event" class="cursor-pointer">
                    <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                      <q-date v-model="data.targetTime" mask="YYYY-MM-DDTHH:mm:ss">
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
                      <q-time v-model="data.targetTime" mask="YYYY-MM-DDTHH:mm:ss" format24h>
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
              <q-btn unelevated color="light-blue-7" type="submit" size="lg" class="full-width" label="추가" />
            </q-card-actions>
          </q-form>

        </q-card-section>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script setup>
import axios from "../../utils/axios.js";
import {useStatusStore} from "../../store/StatusStore.js";

const statusStore = useStatusStore();

const $q = useQuasar()
const data = ref({
  title: "",
  content: "",
  targetTime: "",
  statusName: ""
})
const router = useRouter();
const emit = defineEmits(['change-add-pop-up', 'refresh-todo-list'])

const add = async () => {
  console.log(data.value.statusName)
  const res = await axios.post('/item/create', {
    targetTime: data.value.targetTime,
    title: data.value.title,
    content: data.value.content,
    statusName: data.value.statusName
  })

  if(res.status === 201) {
    console.log('item add')
    await statusStore.getStatuses()
    alertCreate()
    emit('change-add-pop-up')
    emit('refresh-todo-list')
    resetData();
  }
}

const alertCreate = () => {
  $q.dialog({
    message: '일정이 등록되었습니다.'
  })
}

const resetData = () => {
  data.value.title = "";
  data.value.content = "";
  data.value.targetTime = "";
  data.value.statusName = "";
}

const title_rules = (val) => {
  const len = val.length
  if(len < 1) {
    return "제목을 입력해주세요."
  }
}
const date_rules = (val) => {
  const a = val.match(/([0-2][0-9]{3})-([0-1][0-9])-([0-3][0-9])T([0-5][0-9]):([0-5][0-9]):([0-5][0-9])(([\-\+]([0-1][0-9])\:00))?/);
  if(!a) {
    return '올바른 날짜를 입력해주세요'
  }
}

</script>

<style scoped>
.q-card {
  width: 400px;
}
</style>