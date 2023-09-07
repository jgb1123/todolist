<template>
  <div id="kanban">
    <div class="justify-center">
      <div class="flex">
        <div class="flex-container py-12 px-12" @drag="ondrop($event)" @dragenter.prevent @dragover.prevent>
          <div
              v-for="(status, i) in statusStore.$state.statuses"
              :key="i"
              class="bg-light-blue-2 rounded-lg px-3 py-3 column-width mr-4 mt-3 cursor-move"
              draggable="true"
              @dragstart="startDrag($event, status.statusId)"
              @drop="dragDrop($event, status.statusId)"
          >
            <p class="text-gray-700 font-semi-bold font-sans tracking-wide text-sm">{{ status.statusName }}</p>
            <item-card
                v-for="(item, i) in status.items"
                :key="i"
                :item="item"
                class="mt-3"
            ></item-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import ItemCard from "./ItemCard.vue";
import {useStatusStore} from "../store/StatusStore.js";
import axios from "../utils/axios.js";

const statusStore = useStatusStore();

const ondrop = (event) => {
  // console.log(event)
}

const startDrag = (event, startStatusId) => {
  event.dataTransfer.setData("startStatusId", startStatusId)
  event.dataTransfer.dropEffect = 'move'
  event.dataTransfer.effectAllowed = 'move'
}

const dragDrop = async (event, endStatusId) => {
  const startStatusId = event.dataTransfer.getData("startStatusId")
  if(endStatusId != startStatusId) {
    const res = await axios.post(`/status/swap/${startStatusId}/${endStatusId}`)
    if (res.status === 200) {
      await statusStore.getStatuses()
    }
  }
}
</script>

<style scoped>
.flex-container {
  display: flex;
  overflow-x: scroll;
  width: 100%

}
.column-width {
  min-width: 320px;
  width: 320px;
}
</style>