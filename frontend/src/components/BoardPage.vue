<template>
  <div id="kanban">
    <div class="flex justify-center">

      <div class="min-h-screen flex overflow-x-scroll py-12">
        <div
            v-for="(status, i) in statusStore.$state.statuses"
            :key="i"
            class="bg-light-blue-2 rounded-lg px-3 py-3 column-width rounded mr-4"
        >
          <p class="text-gray-700 font-semi-bold font-sans tracking-wide text-sm">{{ status.statusName }}</p>
            <task-card
                v-for="(item, i) in status.items"
                :key="i"
                :item="item"
                class="mt-3 cursor-move"
            ></task-card>
        </div>

        <div class="col-3">
          <h3>Draggable 1</h3>
          <draggable
              class="list-group"
              :list="statusStore.$state.statuses"
              group="people"
              @change="log"
              itemKey="statusId"
          >
            <template #item="{ element, index }">
              <div>
                {{element}}
                <q-btn class="list-group-item">{{ element.statusName }} {{ index }}</q-btn>
              </div>
            </template>
          </draggable>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import TaskCard from "./ItemCard.vue";
import {useStatusStore} from "../store/StatusStore.js";
import draggable from "vuedraggable"

const statusStore = useStatusStore();

const log = (event) => {
  console.log(event)
}

// const statuses = ref([])
// const getStatuses = async () => {
//   const res = await axios.get('/status/find')
//   if(res.status === 200 ) {
//     statuses.value = res.data.data;
//   }
// }
//
// onMounted(() => {
//   getStatuses()
// })

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