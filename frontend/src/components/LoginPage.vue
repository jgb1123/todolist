<template>
  <q-page class="bg-light-blue window-height window-width row justify-center items-center">
    <div class="column">
      <div class="row">
        <h5 class="text-h5 text-white q-my-md">로그인</h5>
      </div>
      <div class="row">
        <q-card square bordered class="q-pa-lg shadow-1">
          <q-card-section>
            <q-form class="q-gutter-md">
              <q-input square filled v-model="email" type="email" label="email" />
              <q-input square filled v-model="password" type="password" label="password" />
            </q-form>
          </q-card-section>
          <q-card-actions class="q-px-md">
            <q-btn unelevated color="light-blue-7" size="lg" class="full-width" label="Login" @click="login"/>
          </q-card-actions>
          <q-card-section class="text-center q-pa-none">
            <p class="text-grey-6">계정이 없으신가요?</p>
            <q-btn unelevated color="light-blue-7" @click="router.push({name: 'register'})">회원가입</q-btn>
          </q-card-section>
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script setup>
import axios from "../utils/axios.js"
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies()
const email =  ref("");
const password = ref("");
const router = useRouter();
const login = async () => {
  const res = await axios.post('/auth/login', {
    email: email.value,
    password: password.value
  });

  if (res.status === 200) {
    cookies.set('accessToken', res.data.accessToken)
    cookies.set('refreshToken', res.data.refreshToken)
    console.log(cookies.get('accessToken'))
    console.log('login')
    await router.push({name: 'home'})
  }
}
</script>

<style scoped>
.q-card {
  width: 400px;
}
</style>