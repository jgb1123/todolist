<template>
  <div id="todoinput">
    <input type="text" v-model="content" @keyup.enter="addToDo" />
    <button type="button" @click="addToDo">등록</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const content = ref('');
const emit = defineEmits(['add-to-do']);

const addToDo = () => {
  alert('등록')
  emit('add-to-do', content.value)
  content.value = '';
}
</script>

<style scoped>

</style>