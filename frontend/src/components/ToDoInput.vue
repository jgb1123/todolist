<template>
  <div id="todoinput">
    <q-btn color="primary" label="할일 추가" style="margin: auto" @click="clickAdd"></q-btn>
  </div>
</template>

<script setup>
const emit = defineEmits(['click-add-pop-up']);
const clickAdd = () => {
  emit('click-add-pop-up')
  console.log('클릭')
}

</script>

<style scoped>

</style>