<template>
  <div id="todoinput">
    <input type="text" v-model="content" @keyup.enter="addToDo" />
    <button type="button" @click="addToDo">등록</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      content: ''
    }
  },
  methods: {
    addToDo() {
      alert('등록')
      this.$emit('add-to-do', this.content)
      this.content = ''
    }
  }
}
</script>

<style></style>