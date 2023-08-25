<template>
  <div id="todolist">
    <div id="container" v-for="data in todos" :key="data.id">
      <li id="text">{{ data }}</li>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  todos: []
})
</script>

<style></style>