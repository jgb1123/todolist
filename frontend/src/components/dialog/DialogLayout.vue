<template>
  <q-dialog ref="dialogRef" @hide="onDialogHide">
    <q-card class="q-dialog-plugin">
      <q-card-section>
        <slot>
        </slot>
      </q-card-section>
      <q-card-actions align="right">
        <slot name="bottom-buttons">
          <q-btn color="primary" label="OK" @click="onDialogOK()"/>
        </slot>
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup>
import { useDialogPluginComponent } from 'quasar'
const { dialogRef, onDialogHide, onDialogOK, onDialogCancel } = useDialogPluginComponent()

defineExpose({onDialogOK, onDialogCancel})
</script>

<style scoped>

</style>