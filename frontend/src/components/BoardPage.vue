<template>
  <div id="kanban">
    <div class="flex justify-center">
      <div class="min-h-screen flex overflow-x-scroll py-12">
        <div
            v-for="(status, i) in statuses"
            :key="i"
            class="bg-light-blue-2 rounded-lg px-3 py-3 column-width rounded mr-4"
        >
          <p class="text-gray-700 font-semi-bold font-sans tracking-wide text-sm">{{ status.statusName }}</p>
          <!--           Draggable component comes from vuedraggable. It provides drag & drop functionality -->
<!--          <draggable v-model="status" :list=status.items :animation="200" ghost-class="ghost-card" group="items">-->
            <!--             Each element from here will be draggable and animated. Note :key is very important here to be unique both for draggable and animations to be smooth & consistent. -->
            <task-card
                v-for="(item, i) in status.items"
                :key="i"
                :item="item"
                class="mt-3 cursor-move"
            ></task-card>
            <!--             </transition-group> -->
<!--          </draggable>-->
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "../utils/axios.js";
import TaskCard from "./ItemCard.vue";

const statuses = ref([])
const getStatuses = async () => {
  const res = await axios.get('/status/find')
  if(res.status === 200 ) {
    statuses.value = res.data.data;
  }
}

onMounted(() => {
  getStatuses()
})

</script>

<style scoped>
.column-width {
  min-width: 320px;
  width: 320px;
}
.ghost-card {
  opacity: 0.5;
  background: #F7FAFC;
  border: 1px solid #4299e1;
}
</style>