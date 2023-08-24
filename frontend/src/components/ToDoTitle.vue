<template>
  <div id="todotitle">
    <h1>{{ name }}</h1>
  </div>
</template>

<script>
export default {
  name: 'ToDoTitle',
  props: {
    name: String
  }
}
</script>

<style></style>