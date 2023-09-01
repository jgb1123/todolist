<template>
  <q-page class="bg-light-blue window-height window-width row justify-center items-center">
    <div class="column">
      <div class="row">
        <h5 class="text-h5 text-white q-my-md">로그인</h5>
      </div>
      <div class="row">
        <q-card square bordered class="q-pa-lg shadow-1">
          <q-card-section>
            <q-form ref="loginForm" class="q-gutter-md" @submit="login">
              <q-input :rules="[email_rules]"
                       square filled v-model="data.email" type="email" label="email" @keyup.enter="login"/>
              <q-input :rules="[password_rules]"
                       square filled v-model="data.password" type="password" label="password" @keyup.enter="login" />
              <q-card-actions class="q-px-md">
                <q-btn unelevated color="light-blue-7" type="submit" size="lg" class="full-width" label="Login"/>
              </q-card-actions>
              <q-card-section class="text-center q-pa-none">
                <p class="text-grey-6">계정이 없으신가요?</p>
                <q-btn unelevated color="light-blue-7" @click="router.push({name: 'register'})">회원가입</q-btn>
              </q-card-section>
            </q-form>
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
const data = ref({
  email: "",
  password: ""
})
const router = useRouter();
const login = async () => {
  const res = await axios.post('/auth/login', {
    email: data.value.email,
    password: data.value.password
  });

  if (res.status === 200) {
    cookies.set('accessToken', res.data.accessToken)
    cookies.set('refreshToken', res.data.refreshToken)
    await router.push({name: 'home'})
  }
}

const email_rules = (val) => {
  if(!val) {
    return '이메일을 입력해주세요.'
  }
  const kor = val.match(/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g);
  if(kor) {
    return '한글은 입력할 수 없습니다.'
  }
  const form = val.match(/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z0-9]+$/);
  if(!form) {
    return '알맞은 이메일 형식을 입력해주세요.'
  }
}

const password_rules = (val) => {
  const len = val.length
  if(len < 8 || len > 20) {
    return '알맞은 비밀번호를 입력해주세요.'
  }
  const a = val.match(/^[a-zA-Z0-9]+$/);
  if(!a) {
    return '알맞은 비밀번호를 입력해주세요.'
  }
}

</script>

<style scoped>
.q-card {
  width: 400px;
}
</style>