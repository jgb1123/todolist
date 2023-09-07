<template>
  <q-page class="bg-light-blue window-height window-width row justify-center items-center">
    <div class="column">
      <div class="row">
        <h5 class="text-h5 text-white q-my-md">회원가입</h5>
      </div>
      <div class="row">
        <q-card square bordered class="q-pa-lg shadow-1">
          <q-card-section>
            <q-form ref="registerForm" class="q-gutter-md" @submit="register" >
              <q-input :rules="[email_rules]"
                       square filled v-model="data.email" type="email" label="email" />
              <q-input :rules="[password_rules]"
                       square filled v-model="data.password" type="password" label="password" />
              <q-input :rules="[password_confirm_rules]"
                       square filled v-model="data.passwordConfirm" type="password" label="password confirm" />
              <q-input :rules="[name_rules]"
                       square filled v-model="data.name" label="name" />
              <q-card-actions class="q-px-md">
                <q-btn unelevated color="light-blue-7" type="submit" size="lg" class="full-width" label="register" />
              </q-card-actions>
            </q-form>
          </q-card-section>
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script setup>
import axios from "../utils/axios.js";

const $q = useQuasar();
const data = ref({
  email: "",
  password: "",
  passwordConfirm: "",
  name: ""
})
const router = useRouter();

const register = async () => {
  try {
    const res = await axios.post('/member/create', {
      email: data.value.email,
      password: data.value.password,
      name: data.value.name
    });

    if(res.status === 201) {
      await router.push({name: 'login'})
      await alertComplete()
    }
  } catch (e) {
    alertDenied()
  }
}

const alertComplete = () => {
  $q.dialog({
    message: '회원가입이 완료되었습니다.'
  })
}
const alertDenied = () => {
  $q.dialog({
    message: '중복된 이메일입니다.'
  })
}

const email_rules = (val) => {
  if(!val) {
    return '이메일을 입력해주세요.'
  }
  const form = val.match(/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[a-zA-Z0-9]+$/);
  if(!form) {
    return '알맞은 이메일 형식을 입력해주세요.'
  }
}

const password_rules = (val) => {
  const len = val.length
  if(len < 8 || len > 20) {
    return '비밀번호는 8자이상 20자 이하여야 합니다.'
  }
  const a = val.match(/^[a-zA-z0-9]+$/);
  if(!a) {
    return '영문, 숫자 조합이어야 합니다.'
  }
}

const password_confirm_rules = (val) => {
  const len = val.length
  if(len < 8 || len > 20) {
    return '비밀번호는 8자이상 20자 이하여야 합니다.'
  }
  const a = val.match(/^[a-zA-z0-9]+$/);
  if(!a) {
    return '영문, 숫자 조합이어야 합니다.'
  }
  if(val !== data.value.password) {
    return '비밀번호를 확인해주세요'
  }
}

const name_rules = (val) => {
  const kor = val.match(/[가-힣]+$/g);
  if(!kor) {
    return '이름은 한글로만 입력되어야 합니다.'
  }
}

</script>

<style scoped>
.q-card {
  width: 400px;
}
</style>