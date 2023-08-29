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

    <q-drawer show-if-above v-model="leftDrawerOpen" side="left" bordered>
      <!-- drawer content -->
    </q-drawer>

    <q-page-container>
<!--      <router-view />-->
      <item-input-page :item-add-pop-up="itemAddPopUp" />
      <to-do-input @click-add-pop-up="clickAddPopUp" />
      <to-do-list />
    </q-page-container>
  </q-layout>
</template>

<script setup>
import ToDoInput from '../components/ToDoInput.vue'
import ToDoList from '../components/ToDoList.vue'
import ItemInputPage from '../components/ItemInputPage.vue'

const itemAddPopUp = ref(false);

const leftDrawerOpen = ref(false)

const toggleLeftDrawer = () => {
  leftDrawerOpen.value = !leftDrawerOpen.value
}

const clickAddPopUp = () => {
  itemAddPopUp.value = !itemAddPopUp.value;
}

</script>

<style>
</style>
