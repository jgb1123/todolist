import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { quasar, transformAssetUrls } from "@quasar/vite-plugin";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
      vue({
        template: { transformAssetUrls }
      })],
  build: {
    outDir: "../backend/src/main/resources/static"
  },
  server: {
    proxy: {
      "/api": "http://localhost:8081"
    }
  }
})
