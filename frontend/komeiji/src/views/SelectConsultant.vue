<template>
  <div class="select-consultant">
    <h2>选择咨询师</h2>
    <div v-if="waitingForConfirm" class="waiting-message">
      <div class="spinner"></div>
      <p>等待咨询师确认...</p>
      <button @click="cancelRequest" class="cancel-btn">取消请求</button>
    </div>
    <div v-else class="consultant-list">
      <div v-for="consultant in consultants" 
           :key="consultant"
           class="consultant-card"
           @click="selectConsultant(consultant)">
        <h3>咨询师 {{ consultant }}</h3>
      </div>
    </div>
  </div>
</template>

<script>
import userApi from '@/api/userApi.js'

export default {
  name: 'SelectConsultant',
  data() {
    return {
      consultants: [],
      waitingForConfirm: false,
      currentConsultantId: null,
      ws: null
    }
  },
  async created() {
    try {
      const response = await userApi.getUsersByUserClass(1)
      this.consultants = response.data.data
    } catch (error) {
      console.error('获取咨询师列表失败:', error)
      alert('获取咨询师列表失败')
    }
  },
  methods: {
    async selectConsultant(consultantId) {
      try {
        this.waitingForConfirm = true
        this.currentConsultantId = consultantId
        await userApi.selectConsultant(consultantId)
        this.setupWebSocket()
      } catch (error) {
        console.error('选择咨询师失败:', error)
        alert('选择咨询师失败')
        this.waitingForConfirm = false
      }
    },
    setupWebSocket() {
      const id = localStorage.getItem('userName');
      this.ws = new WebSocket('ws://127.0.0.1:54950/ws?uid='+id)
      
      this.ws.onopen = () => {
        console.log('WebSocket连接已建立')
        // 发送咨询师ID
        this.ws.send(JSON.stringify({
          type: 'CONSULTANT_REQUEST',
          consultantId: this.currentConsultantId
        }))
      }

      this.ws.onmessage = (event) => {
        const data = JSON.parse(event.data)
        if (data.type === 'CONSULTANT_ACCEPTED') {
          alert('咨询师已接受请求')
          this.$router.push('/chat')
        } else if (data.type === 'CONSULTANT_REJECTED') {
          alert('咨询师已拒绝请求')
          this.waitingForConfirm = false
        }
      }

      this.ws.onerror = (error) => {
        console.error('WebSocket错误:', error)
        alert('连接错误')
        this.waitingForConfirm = false
      }

      this.ws.onclose = () => {
        console.log('WebSocket连接已关闭')
        this.waitingForConfirm = false
      }
    },
    cancelRequest() {
      if (this.ws) {
        this.ws.close()
      }
      this.waitingForConfirm = false
      this.currentConsultantId = null
    }
  },
  beforeUnmount() {
    if (this.ws) {
      this.ws.close()
    }
  }
}
</script>

<style scoped>
.select-consultant {
  padding: 20px;
}

.consultant-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.consultant-card {
  background-color: #f5f5f5;
  border-radius: 8px;
  padding: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.consultant-card:hover {
  background-color: #e0e0e0;
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  color: #333;
  margin-bottom: 20px;
}

h3 {
  margin: 0;
  color: #666;
}

.waiting-message {
  text-align: center;
  padding: 40px;
}

.spinner {
  width: 40px;
  height: 40px;
  margin: 0 auto 20px;
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.cancel-btn {
  margin-top: 20px;
  padding: 8px 16px;
  background-color: #ff4444;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.cancel-btn:hover {
  background-color: #cc0000;
}
</style> 