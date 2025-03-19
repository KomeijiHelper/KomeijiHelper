<template>
  <div class="select-consultant">
    <h2>选择咨询师</h2>
    <div class="consultant-list">
      <div v-for="consultant in consultants" 
           :key="consultant.id" 
           class="consultant-card"
           @click="selectConsultant(consultant.id)">
        <h3>咨询师 {{ consultant.id }}</h3>
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
      consultants: []
    }
  },
  async created() {
    try {
      const response = await userApi.getUsersByUserClass(1)
      this.consultants = response.data
    } catch (error) {
      console.error('获取咨询师列表失败:', error)
      alert('获取咨询师列表失败')
    }
  },
  methods: {
    async selectConsultant(consultantId) {
      try {
        await userApi.selectConsultant(consultantId)
        alert('选择咨询师成功')
        // 选择成功后跳转到其他页面
        this.$router.push('/dashboard')
      } catch (error) {
        console.error('选择咨询师失败:', error)
        alert('选择咨询师失败')
      }
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
</style> 