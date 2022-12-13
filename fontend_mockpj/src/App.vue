<template>
  <div id="app">
    <!-- <Schedule/> -->
    <ScheduleErr/>
  </div>
</template>

<script>
import Schedule from './components/Schedule.vue'
// import Schedule2 from './views/Schedule/Schedule.vue'
import ScheduleErr from './components/ScheduleErr.vue';

export default {
  name: 'App',
  components: {
    //Schedule,
    ScheduleErr
}
}
</script>

<style>

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
