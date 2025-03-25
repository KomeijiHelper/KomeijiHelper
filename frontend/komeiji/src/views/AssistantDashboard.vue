<template>
  <div class="assistant-dashboard">
    <h2>咨询师工作台</h2>
    <div v-if="currentRequest" class="request-panel">
      <h3>新的咨询请求</h3>
      <p>用户ID: {{ currentRequest.userId }}</p>
      <div class="button-group">
        <button @click="handleRequest(true)" class="accept-btn">接受</button>
        <button @click="handleRequest(false)" class="reject-btn">拒绝</button>
      </div>
    </div>
    <div v-else class="no-request">
      <p>等待新的咨询请求...</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AssistantDashboard',
  data() {
    return {
      ws: null,
      currentRequest: null
    }
  },
  created() {
    this.setupWebSocket()
  },
  beforeUnmount() {
    if (this.ws) {
      this.ws.close()
    }
  },
  methods: {
    setupWebSocket() {
      const id = localStorage.getItem('userName');
      this.ws = new WebSocket('ws://127.0.0.1:54950/ws?id='+id)
      
      this.ws.onopen = () => {
        console.log('WebSocket连接已建立')
        // 发送咨询师身份信息
        this.ws.send(JSON.stringify({
          type: 'assistant_identity',
          content: localStorage.getItem('userId') // 假设用户ID存储在localStorage中
        }))
      }

      this.ws.onmessage = (event) => {
        const data = JSON.parse(event.data)
        if (data.type === 'assistant_request') {
          this.currentRequest = {
            userId: data.content
          }
        }
      }

      this.ws.onerror = (error) => {
        console.error('WebSocket错误:', error)
        alert('连接错误')
      }

      this.ws.onclose = () => {
        console.log('WebSocket连接已关闭')
      }
    },
    handleRequest(accept) {
      if (!this.currentRequest) return

      const messageType = accept ? 'assistant_accepted' : 'assistant_rejected'
      this.ws.send(JSON.stringify({
        type: messageType,
        content: this.currentRequest.userId
      }))

      if (accept) {
        // 如果接受请求，跳转到聊天页面
        this.$router.push({
          path: '/chat',
          query: {
            userId: this.currentRequest.userId
          }
        })
      }

      this.currentRequest = null
    }
  }
}
</script>

<style scoped>
.assistant-dashboard {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.request-panel {
  background-color: #f5f5f5;
  border-radius: 8px;
  padding: 20px;
  margin-top: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.no-request {
  text-align: center;
  padding: 40px;
  background-color: #f5f5f5;
  border-radius: 8px;
  margin-top: 20px;
}

.button-group {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.accept-btn, .reject-btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.accept-btn {
  background-color: #4CAF50;
  color: white;
}

.accept-btn:hover {
  background-color: #45a049;
}

.reject-btn {
  background-color: #f44336;
  color: white;
}

.reject-btn:hover {
  background-color: #da190b;
}

h2 {
  color: #333;
  margin-bottom: 20px;
}

h3 {
  color: #666;
  margin-bottom: 15px;
}

p {
  margin: 10px 0;
  color: #666;
}
</style> 