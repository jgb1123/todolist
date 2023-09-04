<template>
  <div class="bg-white shadow rounded px-3 pt-3 pb-5 border border-white">
    <div class="flex justify-between">
      <p class="text-gray-700 font-semibold font-sans tracking-wide text-sm">{{ props.item.title }}</p>
    </div>
    <div class="flex mt-4 justify-between items-center">
      <span class="text-sm text-gray-600">{{ dayjs(props.item.targetTime).format('YYYY-MM-DD HH:mm') }}</span>
    </div>
  </div>
</template>

<script setup>
import dayjs from "dayjs";

const props = defineProps(['item']);
</script>

<style scoped>
</style>