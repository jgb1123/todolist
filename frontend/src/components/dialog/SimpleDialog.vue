<template>
  <DialogLayout ref="dialogRef">
    <template #default>
      <q-form class="q-gutter-md">
        <div> {{ props.message }} </div>
      </q-form>
    </template>
    <template v-if="props.cancelBtn" #bottom-buttons>
      <q-btn color="primary" label="OK" @click="dialogRef.onDialogOK" />
      <q-btn color="primary" label="Cancel" @click="dialogRef.onDialogCancel" />
    </template>
  </DialogLayout>
</template>

<script setup>
import DialogLayout from "./DialogLayout.vue";
const props = defineProps(['message', 'cancelBtn'])
const dialogRef = ref();

</script>

<style scoped>

</style>