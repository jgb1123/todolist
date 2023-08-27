<template>
  <div id="todolist">
    <q-card v-for="(todos, i) in todos" :key="i">
      <div class="q-pa-sm">{{ todos }}</div>
    </q-card>
  </div>
</template>

<script setup>
const props = defineProps({
  todos: []
})
</script>

<style></style>