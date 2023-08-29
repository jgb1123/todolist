<template>
  <div id="todoinput">
    <button type="button" @click="clickAdd">추가</button>
  </div>
</template>

<script setup>
import axios from "../utils/axios.js";

const emit = defineEmits(['click-add-pop-up']);
const clickAdd = () => {
  emit('click-add-pop-up')
  console.log('클릭')
}

</script>

<style scoped>

</style>