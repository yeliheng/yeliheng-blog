<template>
  <div id="nav">
    <router-link to="/"></router-link>
    
  </div>
  <router-view/>
</template>

<style lang="scss">

  * {
    padding: 0;
    margin: 0;
    //box-sizing: border-box;
}

  html {
    font-size: 14px;
    font-family: Avenir, Helvetica, Arial, sans-serif, 'Microsoft YaHei';
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }
  
  body{
      background: #F7F8FC;
      
  }

  @media screen and (min-width: 1450px) {
    html {
        font-size: 75%;
    }
}
</style>
