import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import { quasar, transformAssetUrls } from "@quasar/vite-plugin";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue({
      template: { transformAssetUrls }
    }),
    AutoImport({
      imports: ['vue', 'vue-router']
    })],
  server: {
    proxy: {
      "/api": "http://localhost:8081"
    }
  }
})
