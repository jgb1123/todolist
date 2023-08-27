<template>
  <div id="todoinput">
    <input type="text" v-model="content" @keyup.enter="addToDo" />
    <button type="button">등록</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const content = ref('');

</script>

<style scoped>

</style>