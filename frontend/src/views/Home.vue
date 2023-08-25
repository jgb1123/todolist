<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-toolbar-title>
          <q-avatar>
            <img src="../assets/vue.svg">
          </q-avatar>
          To Do
        </q-toolbar-title>
      </q-toolbar>
      <q-tabs align="left">
        <q-route-tab to="/page1" label="Page One" />
        <q-route-tab to="/page2" label="Page Two" />
      </q-tabs>
    </q-header>

    <q-drawer show-if-above v-model="leftDrawerOpen" side="left" bordered>
      <!-- drawer content -->
    </q-drawer>

    <q-page-container>
<!--      <router-view />-->
      <to-do-input @add-to-do="addToDo" />
      <to-do-list :todos="state.todos" />
    </q-page-container>

  </q-layout>
</template>

<script setup>
import ToDoInput from '../components/ToDoInput.vue'
import ToDoList from '../components/ToDoList.vue'
import {reactive} from "vue";

const state = reactive({
  todos: ['공부']
})

const addToDo = (content) => {
  this.todos.push(content)
}
// composition api로 변경해야함

</script>

<style>
</style>
