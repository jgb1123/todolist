<template>
  <q-page class="bg-light-blue window-height window-width row justify-center items-center">
    <div class="column">
      <div class="row">
        <h5 class="text-h5 text-white q-my-md">회원가입</h5>
      </div>
      <div class="row">
        <q-card square bordered class="q-pa-lg shadow-1">
          <q-card-section>
            <q-form class="q-gutter-md">
              <q-input square filled v-model="state.email" type="email" label="email" />
              <q-input square filled v-model="state.password" type="password" label="password" />
<!--              <q-input square filled v-model="state.passwordConfirm" type="password" label="password confirm" />-->
              <q-input square filled v-model="state.name" label="name" />
            </q-form>
          </q-card-section>
          <q-card-actions class="q-px-md">
            <q-btn unelevated color="light-blue-7" size="lg" class="full-width" label="register" />
          </q-card-actions>
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script setup lang="ts">
import {reactive} from "vue";

const state = reactive({
  email: "" as string,
  password: "" as string,
  // passwordConfirm: "" as string,
  name: "" as string

})
</script>

<style>
q-card {
  width: 400px;
}
</style>