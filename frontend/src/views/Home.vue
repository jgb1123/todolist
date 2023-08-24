<template>
  <h1>11</h1>
  <to-do-title name="TO-DO" />
  <to-do-input @add-to-do="addToDo" />
  <to-do-list :todos="todos" />
</template>

<script>
import ToDoTitle from '../components/ToDoTitle.vue'
import ToDoInput from '../components/ToDoInput.vue'
import ToDoList from '../components/ToDoList.vue'
export default {
  name: 'Home',
  components: {
    ToDoTitle,
    ToDoInput,
    ToDoList
  },
  data() {
    return {
      todos: ['공부']
    }
  },
  methods: {
    addToDo(content) {
      this.todos.push(content)
    }
  }
}
</script>

<style>

</style>
