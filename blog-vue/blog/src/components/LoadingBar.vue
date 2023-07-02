<template>
  <div class="loading-bar-full" ref="loadingBar">
    <bounce-loader
        :loading="true"
        :color="loadingBarColor"
    />
  </div>
</template>

<script>
import {computed, ref} from "vue";
import {useStore} from "vuex";

export default {
  name: "LoadingBar",
  setup() {
    const store = useStore();
    const loadingBar = ref(null);
    const loadingBarColor = computed(() => store.getters.isDarkMode ? '#fff' : '#000');

    store.watch(
        (state) => state.loading,
        (newVal) => {
          if(newVal) {
            loadingBar.value.style.visibility = "visible";
            loadingBar.value.style.opacity = "1";
          }else {
            loadingBar.value.style.visibility = "collapse";
            loadingBar.value.style.opacity = "0";
          }
        }
    );

    store.watch(
        (state) => state.isDarkMode,
        (newVal) => {
          document.querySelector('.v-bounce2').style.backgroundColor = newVal ? 'rgb(255,255,255)' : 'rgb(0,0,0)';
          document.querySelector('.v-bounce3').style.backgroundColor = newVal ? 'rgb(255,255,255)' : 'rgb(0,0,0)';
        }
    );

    return {
      loadingBar,
      loadingBarColor,
    }
  }
}

</script>

<style lang="scss">
@use "@/theme/_handle.scss" as *;

.loading-bar-full {
  display: flex;
  @include background_color("contentBackgroudColor");
  transition: all 0.5s;
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  justify-content: center;
  align-items: center;
  visibility: collapse;
  opacity: 0;
  z-index: 99999;
}

</style>