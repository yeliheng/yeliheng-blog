<template>
  <iframe :src="druidSrc"
          width="100%"
          height="100%"
          style="border: 0;"
  />
</template>

<script>
export default {
  name: "Druid",
  setup() {

    const druidSrc = process.env.VUE_APP_BASE_API + '/druid';
    return {
      druidSrc,
    }
  }
}
</script>

<style scoped>

</style>