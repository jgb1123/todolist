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
              <q-input square filled v-model="email" type="email" label="email" />
              <q-input square filled v-model="password" type="password" label="password" />
              <q-input square filled v-model="name" label="name" />
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

<script setup>
import {reactive, ref} from "vue";
const email = ref("");
const password = ref("");
const name = ref("");
</script>

<style>
.q-card {
  width: 400px;
}
</style>