<template>
  <div class="container">
    <h2>Загрузка файлов</h2>

    <!-- Форма загрузки файлов -->
    <input type="file" @change="handleFileUpload" />
    <button @click="uploadFile" :disabled="!selectedFile">Загрузить</button>

    <h2>Список файлов</h2>
    <ul>
      <li v-for="file in files" :key="file">
        {{ file }}
        <button @click="downloadFile(file)">Скачать</button>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      selectedFile: null,
      files: [],
    };
  },
  methods: {
    // Обработчик выбора файла
    handleFileUpload(event) {
      this.selectedFile = event.target.files[0];
    },

    // Отправка файла на сервер
    async uploadFile() {
      if (!this.selectedFile) return;

      let formData = new FormData();
      formData.append("file", this.selectedFile);

      try {
        await axios.post("http://localhost:8080/api/files/upload", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });
        alert("Файл загружен!");
        this.fetchFiles();
      } catch (error) {
        console.error("Ошибка загрузки файла", error);
      }
    },

    // Запрос списка файлов
    async fetchFiles() {
      try {
        const response = await axios.get("http://localhost:8080/api/files");
        this.files = response.data; // Ожидается массив имен файлов
      } catch (error) {
        console.error("Ошибка загрузки списка файлов", error);
      }
    },

    // Скачивание файла
    async downloadFile(fileName) {
      try {
        const response = await axios.get(`http://localhost:8080/api/files/download/${fileName}`, {
          responseType: "blob",
        });

        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", fileName);
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      } catch (error) {
        console.error("Ошибка скачивания файла", error);
      }
    },
  },
  mounted() {
    this.fetchFiles();
  },
};
</script>

<style>
.container {
  max-width: 500px;
  margin: 20px auto;
  text-align: center;
}
button {
  margin: 5px;
  padding: 10px;
  cursor: pointer;
}
</style>