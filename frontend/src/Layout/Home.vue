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
        <q-toggle
            v-model="value"
            checked-icon="list"
            color="gray"
            unchecked-icon="view_column"
            size="80px"
        />
      </div>
      <item-input-pop-up ref="statusRef" v-model="addPopUpOpen" @change-add-pop-up="changeAddPopUp" @refresh-todo-list="refreshTodoList"/>
      <item-edit-pop-up ref="editRef" v-model="editPopUpOpen" @change-edit-pop-up="changeEditPopUp" @refresh-todo-list="refreshTodoList" :nowItem="nowItem"/>
      <status-input-pop-up v-model="statusPopUpOpen" @change-status-pop-up="changeStatusPopUp"/>
      <status-delete-pop-up v-model="statusDeletePopUpOpen" @change-delete-status-pop-up="changeDeleteStatusPopUp" />
      <to-do-list v-if="value === true" ref="todoRef" @refresh-todo-list="refreshTodoList" @change-edit-pop-up="changeEditPopUp"/>
      <board v-if="value === false" />
    </q-page-container>
  </q-layout>
</template>

<script setup>
import ToDoList from '../components/ToDoList.vue'
import ItemInputPopUp from '../components/PopUp/ItemInputPopUp.vue'
import ItemEditPopUp from "../components/PopUp/ItemEditPopUp.vue";
import StatusInputPopUp from "../components/PopUp/StatusInputPopUp.vue";
import StatusDeletePopUp from "../components/PopUp/StatusDeletePopUp.vue";
import Board from "../components/Board.vue";
import {useCookies} from "vue3-cookies";
import router from "../router/index.js";
import axios from "../utils/axios.js";
import {useItemStore} from "../store/ItemStore.js";

const itemStore = useItemStore();

const {cookies} = useCookies();

const $q = useQuasar();

const addPopUpOpen = ref(false);
const editPopUpOpen = ref(false);
const statusPopUpOpen = ref(false);
const statusDeletePopUpOpen = ref(false);
const leftDrawerOpen = ref(false);

let nowItem = ref();

const todoRef = ref();
const statusRef = ref();
const editRef = ref();

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
  editRef.value.getNowItems(item)
}

const changeStatusPopUp = () => {
  statusPopUpOpen.value = !statusPopUpOpen.value;
}

const changeDeleteStatusPopUp = () => {
  statusDeletePopUpOpen.value = !statusDeletePopUpOpen.value;
}

const refreshTodoList = () => {
  getItem();
}

const getItem = async () => {
  const res = await axios.get('/item/find')

  if(res.status === 200) {
    itemStore.setItems(res.data.data);
  }
}

onMounted( ()=> {
  getItem();
})

const logout = async () => {
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
