<template>
    <div class="container" :style="backgroundStyle">
      <h2>Загрузка изображения</h2>
  
      <!-- Форма для загрузки -->
      <div class="upload-section">
        <label for="file-upload" class="upload-label">
          <span v-if="!selectedFile">Выберите изображение</span>
          <img v-else :src="imagePreview" alt="Предпросмотр" class="image-preview" />
        </label>
        <input id="file-upload" type="file" @change="handleFileChange" accept="image/*" />
        <button @click="uploadFile" :disabled="!selectedFile" class="upload-button">
          {{ selectedFile ? "Загрузить и классифицировать" : "Выберите файл" }}
        </button>
      </div>
  
      <!-- Отображение результата -->
      <div v-if="result" class="result-section">
        <h3>Результат классификации</h3>
        <div class="result-card">
          <p><strong>Это:</strong> {{ result.label }}</p>
          <p><strong>С вероятностью:</strong> {{ (result.probability * 100).toFixed(2) }}%</p>
        </div>
      </div>
  
      <!-- Отображение ошибки -->
      <div v-if="error" class="error-section">
        <p class="error-message">{{ error }}</p>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    data() {
      return {
        selectedFile: null,
        imagePreview: null,
        result: null,
        error: null,
        backgroundImage: null, // Состояние для фонового изображения
      };
    },
    computed: {
      // Вычисляемое свойство для стиля фона
      backgroundStyle() {
        return {
          backgroundImage: this.backgroundImage ? `url(${this.backgroundImage})` : "none",
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
        };
      },
    },
    methods: {
      // Обрабатываем выбор файла
      handleFileChange(event) {
        const file = event.target.files[0];
        if (file) {
          this.selectedFile = file;
          this.imagePreview = URL.createObjectURL(file); // Создаем превью изображения
        }
      },
  
      // Отправляем файл на сервер
      async uploadFile() {
        if (!this.selectedFile) return;
  
        const formData = new FormData();
        formData.append("file", this.selectedFile);
  
        try {
          // Отправляем файл на Spring Boot сервер
          const response = await axios.post("http://localhost:8080/api/files/upload", formData, {
            headers: { "Content-Type": "multipart/form-data" },
          });
  
          // Парсим JSON-ответ от нейронки
          this.result = response.data;
          this.error = null;
  
          // Обновляем фон в зависимости от результата
          if (this.result.label === "Dog") {
            this.backgroundImage = require("@/assets/dog.jpg"); // Укажите правильный путь к изображению собаки
          } else if (this.result.label === "Cat") {
            this.backgroundImage = require("@/assets/cat.jpeg"); // Укажите правильный путь к изображению кота
          } else {
            this.backgroundImage = null; // Сброс фона, если результат неизвестен
          }
        } catch (err) {
          this.error = "Ошибка загрузки: " + (err.response?.data?.error || err.message);
          this.result = null;
          this.backgroundImage = null; // Сброс фона при ошибке
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .container {
    text-align: center;
    padding: 20px;
    min-height: 100vh; /* Минимальная высота для отображения фона */
    transition: background-image 0.5s ease; /* Плавное изменение фона */
    background-color: transparent; /* Убедимся, что фон прозрачный */
  }
  
  h2 {
    font-size: 24px;
    margin-bottom: 20px;
    color: #333;
  }
  
  .upload-section {
    margin-bottom: 20px;
  }
  
  .upload-label {
    display: block;
    width: 100%;
    height: 150px;
    border: 2px dashed #007bff;
    border-radius: 10px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    transition: border-color 0.3s ease;
    background: rgba(255, 255, 255, 0.9); /* Полупрозрачный белый фон для области загрузки */
  }
  
  .upload-label:hover {
    border-color: #0056b3;
  }
  
  .upload-label span {
    font-size: 16px;
    color: #007bff;
  }
  
  .image-preview {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover;
    border-radius: 10px;
  }
  
  #file-upload {
    display: none;
  }
  
  .upload-button {
    margin-top: 10px;
    padding: 10px 20px;
    background: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    transition: background 0.3s ease;
  }
  
  .upload-button:disabled {
    background: #ccc;
    cursor: not-allowed;
  }
  
  .upload-button:hover:not(:disabled) {
    background: #0056b3;
  }
  
  .result-section {
    margin-top: 20px;
  }
  
  .result-card {
    background: rgba(255, 255, 255, 0.9); /* Полупрозрачный белый фон для карточки */
    padding: 15px;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    text-align: left;
  }
  
  .result-card p {
    margin: 5px 0;
    font-size: 16px;
    color: #333;
  }
  
  .error-section {
    margin-top: 20px;
  }
  
  .error-message {
    color: #ff4d4d;
    font-size: 14px;
  }
  </style>