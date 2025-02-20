<script setup>
import { ref } from 'vue';
import axios from 'axios';
import FileUploader from "./components/FileUploader.vue";


const file = ref(null);
const previewUrl = ref(null);
const uploadStatus = ref('');

const handleFileChange = (event) => {
  const selectedFile = event.target.files[0];
  if (selectedFile) {
    file.value = selectedFile;
    previewUrl.value = URL.createObjectURL(selectedFile);
  }
};

const uploadFile = async () => {
  if (!file.value) {
    uploadStatus.value = 'Выберите файл!';
    return;
  }

  const formData = new FormData();
  formData.append('file', file.value);

  try {
    uploadStatus.value = 'Загрузка...';
    await axios.post('http://localhost:8080/api/files/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    uploadStatus.value = 'Файл успешно загружен!';
  } catch (error) {
    uploadStatus.value = 'Ошибка загрузки!';
  }
};
</script>

<template>
  <div class="container">
    <h1>Загрузка изображения</h1>
    <input type="file" @change="handleFileChange" accept="image/*" />

    <div v-if="previewUrl" class="preview">
      <img :src="previewUrl" alt="Preview" />
    </div>

    <button @click="uploadFile">Загрузить</button>
    <p>{{ uploadStatus }}</p>
  </div>
  <FileUploader />
</template>

<style scoped>
.container {
  text-align: center;
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
}

.preview img {
  max-width: 100%;
  height: auto;
  margin-top: 10px;
}

button {
  margin-top: 10px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}

button:hover {
  background-color: #0056b3;
}
</style>