<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn class="menu-button" @click="toggleLeftDrawer">
          <q-icon name="mdi-menu" size="20px" />
        </q-btn>
        <q-toolbar-title>
          <q-avatar>
            <img src="../assets/vue.svg">
          </q-avatar>
          To Do
        </q-toolbar-title>
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
      <to-do-list ref="todoRef"/>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import ToDoList from '../components/ToDoList.vue'
import ItemInputPage from '../components/ItemInputPage.vue'

const addPopUpOpen = ref(false);

const leftDrawerOpen = ref(false);

const todoRef = ref();

const toggleLeftDrawer = () => {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

const changeAddPopUp = () => {
  addPopUpOpen.value = !addPopUpOpen.value;
}

const refreshTodoList = () => {
  todoRef.value.getItem();
}

</script>

<style>
</style>
