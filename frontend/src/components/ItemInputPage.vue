<template>
    <q-dialog :model-value="itemAddPopUp">
      <q-card square bordered class="q-pa-lg shadow-1">
        <q-card-section class="row items-center q-pb-none">
          <q-space />
          <q-btn icon="close" flat round dense v-close-popup />
        </q-card-section>
        <q-card-section>
          <q-form class="q-gutter-md">
            <q-input square filled v-model="title" type="email" label="title" />
            <q-input square filled v-model="targetTime" type="email" label="yyyy-MM-ddTHH:mm:ss" />
            <q-input square filled v-model="content" type="textarea" label="content" />
          </q-form>
        </q-card-section>
        <q-card-actions class="q-px-md">
          <q-btn unelevated color="light-blue-7" size="lg" class="full-width" label="추가" @click="add" />
        </q-card-actions>
      </q-card>
    </q-dialog>
</template>

<script setup lang="ts">
import {ref, toRefs} from "vue"
import axios from "axios";
import {useCookies} from "vue3-cookies";
const { cookies } = useCookies();
const title =  ref("");
const content = ref("");
const targetTime = ref("");

// const props = defineProps<{
//   itemAddPopUp: boolean
// }>();
const props = withDefaults(defineProps<{
  itemAddPopUp?: boolean
}>(), {
  itemAddPopUp: false
})
const { itemAddPopUp } = toRefs(props)
const accessToken = cookies.get('accessToken')
const add = () => {
  axios
      .post('http://localhost:5173/api/v1/item', {
        targetTime: targetTime.value,
        title: title.value,
        content: content.value
      }, {
        headers: {
          'Authorization': `Bearer ${accessToken}`
        }
      })
      .then((res) => {
        if(res.status === 200) {
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