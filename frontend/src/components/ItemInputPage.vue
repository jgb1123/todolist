<template>
    <q-dialog :model-value="addPopUpOpen">
      <q-card square bordered class="q-pa-lg shadow-1">
        <q-card-section class="row items-center q-pb-none">
          <q-space />
          <q-btn icon="close" flat round dense v-close-popup />
        </q-card-section>
        <q-card-section>
          <q-form class="q-gutter-md">
            <q-input square filled v-model="title" type="email" label="title" />
            <q-input square filled v-model="content" type="textarea" label="content" />
            <div class="q-pa-md">
              <div class="q-gutter-sm">
                <q-badge color="teal">
                  {{ targetTime }}
                </q-badge>
              </div>

              <div class="q-gutter-md row items-start">
                <q-date v-model="targetTime" mask="YYYY-MM-DDTHH:mm" color="light-blue-7" />
                <q-time v-model="targetTime" mask="YYYY-MM-DDTHH:mm" color="light-blue-7" />
              </div>
            </div>
          </q-form>
        </q-card-section>
        <q-card-actions class="q-px-md">
          <q-btn unelevated color="light-blue-7" size="lg" class="full-width" label="추가" @click="add" />
        </q-card-actions>
      </q-card>
    </q-dialog>
</template>

<script setup>
import axios from "../utils/axios.js";
const title =  ref("");
const content = ref("");
const targetTime = ref("");

const props = defineProps({
  addPopUpOpen: Boolean
})
const add = () => {
  axios
      .post('/item', {
        targetTime: targetTime.value,
        title: title.value,
        content: content.value
      })
      .then((res) => {
        if(res.status === 201) {
          console.log('add')
        }
      })
}
</script>

<style scoped>
.q-card {
  width: 400px;
}
</style>