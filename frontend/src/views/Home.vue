<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn class="menu-button" @click="toggleLeftDrawer">
          <q-icon name="mdi-menu" size="20px" />
        </q-btn>
        <q-toolbar-title>
          To Do
        </q-toolbar-title>
        <q-btn class="logout-button" @click="logout">
          <q-icon name="logout"></q-icon>
        </q-btn>
      </q-toolbar>
    </q-header>

    <q-drawer
        v-model="leftDrawerOpen"
        show-if-above
        :width="200"
        :breakpoint="500"
    >
      <q-scroll-area class="fit">
        <q-list padding class="menu-list">
          <q-item active clickable v-ripple @click="changeAddPopUp" >
            <q-item-section avatar>
              <q-icon name="add_circle" />
            </q-item-section>
            <q-item-section>
              AddToDo
            </q-item-section>
          </q-item>
        </q-list>
      </q-scroll-area>
    </q-drawer>

    <q-page-container>
      <item-input-page v-model="addPopUpOpen" @change-add-pop-up="changeAddPopUp" @refresh-todo-list="refreshTodoList"/>
      <item-edit-page v-model="editPopUpOpen" @change-edit-pop-up="changeEditPopUp" @refresh-todo-list="refreshTodoList" :nowItem="nowItem"/>
      <to-do-list ref="todoRef" @change-edit-pop-up="changeEditPopUp"/>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import ToDoList from '../components/ToDoList.vue'
import ItemInputPage from '../components/ItemInputPage.vue'
import {useCookies} from "vue3-cookies";
import router from "../router/index.js";
import ItemEditPage from "../components/ItemEditPage.vue";

const {cookies} = useCookies();

const $q = useQuasar();

const addPopUpOpen = ref(false);

const editPopUpOpen = ref(false);

const leftDrawerOpen = ref(false);

let nowItem = ref();

const todoRef = ref();

const toggleLeftDrawer = () => {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

const changeAddPopUp = () => {
  addPopUpOpen.value = !addPopUpOpen.value;
}

const changeEditPopUp = (item) => {
  editPopUpOpen.value = !editPopUpOpen.value;
  nowItem.value = item
}

const refreshTodoList = () => {
  todoRef.value.getItem();
}

const logout = async () => {
  console.log('logout')
  cookies.remove('accessToken')
  cookies.remove('refreshToken')
  await logoutAlert()
}

const logoutAlert = () => {
  $q.dialog({
    message: '로그아웃 되었습니다.'
  }).onOk(() => {
    router.push({name: 'login'})
  }).onDismiss(() => {
    router.push({name: 'login'})
  })
}
</script>

<style>
</style>
