import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      // Vue에서 /api로 시작하는 요청을 스프링 부트 서버로 프록시
      '/api/barcode': {
        target: 'http://localhost:9876', // 백엔드 서버 주소
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '/api') // prefix 유지 (필요 시 조정)
      }
    }
  }
})

