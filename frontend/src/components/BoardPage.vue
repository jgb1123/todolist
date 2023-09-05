<template>
  <div id="kanban">
    <div class="flex justify-center">

      <div class="min-h-screen flex overflow-x-scroll py-12 flex">
        <draggable
          class="list-group flex"
          :list="statusStore.$state.statuses"
          group="people"
          @change="log"
          itemKey="statusId"
        >
          <div
            v-for="(status, i) in statusStore.$state.statuses"
            :key="i"
            class="bg-light-blue-2 rounded-lg px-3 py-3 column-width rounded mr-4 mt-3 cursor-move"
          >
            <p class="text-gray-700 font-semi-bold font-sans tracking-wide text-sm">{{ status.statusName }}</p>
            <item-card
                v-for="(item, i) in status.items"
                :key="i"
                :item="item"
                class="mt-3"
            ></item-card>
          </div>
        </draggable>
      </div>
    </div>
  </div>
</template>

<script setup>
import ItemCard from "./ItemCard.vue";
import {useStatusStore} from "../store/StatusStore.js";
import { VueDraggableNext as draggable } from "vue-draggable-next";

const statusStore = useStatusStore();

const log = (event) => {
  console.log(event)
}

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