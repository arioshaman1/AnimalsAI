<template>
  <div class="photo-upload">
    <h2>Upload Photo</h2>
    <!-- Форма для загрузки файла -->
    <form @submit.prevent="uploadPhoto">
      <div>
        <label for="file">Choose a photo:</label>
        <input type="file" id="file" ref="fileInput" accept="image/*" required />
      </div>
      <button type="submit" class="upload-button">Upload</button>
    </form>

    <!-- Сообщение об ошибке -->
    <div v-if="errorMessage" class="message error">
      {{ errorMessage }}
    </div>

    <!-- Результат -->
    <div v-if="result" class="result">
      <h3>Result:</h3>
      <p><strong>Class:</strong> {{ result.label }}</p>
      <p><strong>Probability:</strong> {{ result.probability }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      errorMessage: '', // Сообщение об ошибке
      result: null, // Результат от сервера
    };
  },
  methods: {
    async uploadPhoto() {
      const file = this.$refs.fileInput.files[0]; // Получаем выбранный файл

      if (!file) {
        this.errorMessage = 'Please select a file.';
        return;
      }

      // Создаём FormData для отправки файла
      const formData = new FormData();
      formData.append('file', file);

      try {
        console.log('Sending file to server...');
        const response = await axios.post('http://localhost:8080/api/files/upload', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
          withCredentials: true, // Включаем отправку куки
        });

        console.log('Server response:', response.data); // Логируем ответ сервера

        // Обработка успешного ответа
        this.result = response.data;
        this.errorMessage = '';
      } catch (error) {
        console.error('Error uploading file:', error); // Логируем ошибку
        this.errorMessage = error.response?.data?.error || 'Failed to upload photo. Please try again.';
        this.result = null;
      }
    },
  },
};
</script>

<style scoped>
.photo-upload {
  max-width: 500px;
  margin: 50px auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 2rem;
  color: #333;
  margin-bottom: 20px;
}

label {
  font-size: 1.1rem;
  color: #555;
}

input[type="file"] {
  margin-top: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
}

.upload-button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 1rem;
  color: #fff;
  background-color: #28a745;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.upload-button:hover {
  background-color: #218838;
}

.message {
  padding: 10px;
  margin-top: 20px;
  border-radius: 5px;
}

.message.error {
  background-color: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}

.result {
  margin-top: 20px;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 5px;
}

.result h3 {
  margin-top: 0;
  font-size: 1.5rem;
  color: #333;
}

.result p {
  font-size: 1.1rem;
  color: #555;
}
</style>