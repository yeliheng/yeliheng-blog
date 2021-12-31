<template>
  <div class="markdown-container">
      <div style="text-align: center; font-size: 1.5rem; font-weight: bold;">关于我</div>
       <markdown 
        class="markdown-body"
        style="text-align: start;margin-top: 0.5rem;background-color: #121212;" 
        :source="about"
        />
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
    setup() {
        const about = ref();
        const readFile = (filePath) => {
            // 创建一个新的xhr对象
            let xhr = null;
            if (window.XMLHttpRequest) {
            xhr = new XMLHttpRequest();
            } else {
            // eslint-disable-next-line
            xhr = new ActiveXObject('Microsoft.XMLHTTP');
            }
            const okStatus = document.location.protocol === 'file' ? 0 : 200;
            xhr.open('GET', filePath, false);
            xhr.overrideMimeType('text/html;charset=utf-8');
            xhr.send(null);
            return xhr.status === okStatus ? xhr.responseText : null;
        }
        about.value = readFile('./about.md');
        return {
            about,
        }

        
    }
}
</script>

<style lang="scss" scoped>
    .markdown-container{
        margin: 2rem;
    }
</style>