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
              Add ToDo
            </q-item-section>
          </q-item>
          <q-separator></q-separator>
          <q-item active clickable v-ripple @click="changeStatusPopUp" >
            <q-item-section avatar>
              <q-icon name="add_circle" />
            </q-item-section>
            <q-item-section>
              Add Status
            </q-item-section>
          </q-item>
          <q-item active clickable v-ripple @click="changeDeleteStatusPopUp" >
            <q-item-section avatar>
              <q-icon name="delete" />
            </q-item-section>
            <q-item-section>
              Delete Status
            </q-item-section>
          </q-item>
        </q-list>
      </q-scroll-area>
    </q-drawer>

    <q-page-container>
      <div class="q-pa-md">
        <span>Board</span>
        <q-toggle v-model="value" />
        <span>Table</span>
      </div>
      <item-input-page v-model="addPopUpOpen" @change-add-pop-up="changeAddPopUp" @refresh-todo-list="refreshTodoList"/>
      <item-edit-page v-model="editPopUpOpen" @change-edit-pop-up="changeEditPopUp" @refresh-todo-list="refreshTodoList" :nowItem="nowItem"/>
      <status-input-page v-model="statusPopUpOpen" @change-status-pop-up="changeStatusPopUp"/>
      <status-delete-page v-model="statusDeletePopUpOpen" @change-delete-status-pop-up="changeDeleteStatusPopUp" />
      <to-do-list v-if="value === true" ref="todoRef" @change-edit-pop-up="changeEditPopUp"/>
      <board-page v-if="value === false"/>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import ToDoList from '../components/ToDoList.vue'
import ItemInputPage from '../components/ItemInputPage.vue'
import StatusInputPage from "../components/StatusInputPage.vue";
import {useCookies} from "vue3-cookies";
import router from "../router/index.js";
import ItemEditPage from "../components/ItemEditPage.vue";
import BoardPage from "../components/BoardPage.vue";
import StatusDeletePage from "../components/StatusDeletePage.vue";

const {cookies} = useCookies();

const $q = useQuasar();

const addPopUpOpen = ref(false);

const editPopUpOpen = ref(false);

const statusPopUpOpen = ref(false);

const statusDeletePopUpOpen = ref(false);

const leftDrawerOpen = ref(false);

let nowItem = ref();

const todoRef = ref();

const value = ref(true);

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

const changeStatusPopUp = () => {
  statusPopUpOpen.value = !statusPopUpOpen.value;
}

const changeDeleteStatusPopUp = () => {
  statusDeletePopUpOpen.value = !statusDeletePopUpOpen.value;
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
