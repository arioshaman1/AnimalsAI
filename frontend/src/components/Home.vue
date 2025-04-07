<template>
    <div class="home">
      <h1>Welcome, {{ username }}</h1>
      <div v-if="photos.length > 0">
        <h2>Your Photos</h2>
        <div class="photo-grid">
          <div v-for="(photo, index) in photos" :key="index" class="photo-item">
            <p><strong>File Name:</strong> {{ photo.fileName }}</p>
            <p><strong>Original File Name:</strong> {{ photo.originalFileName }}</p>
            <p><strong>File Size:</strong> {{ photo.fileSize }} bytes</p>
            <p><strong>File Type:</strong> {{ photo.fileType }}</p>
            <p><strong>Upload Time:</strong> {{ photo.uploadTime }}</p>
          </div>
        </div>
      </div>
      <div v-else>
        <p>No photos uploaded yet.</p>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        username: '',
        photos: [],
      };
    },
    async created() {
      await this.fetchUserPhotos();
    },
    methods: {
        async fetchUserPhotos() {
    try {
        const response = await axios.get('http://localhost:8080/api/files/user-photos', {
            withCredentials: true, 
        });

        if (response.data) {
            this.username = response.data.username;
            this.photos = response.data.photos;
            console.log("User photos:", this.photos); // Логируем список фотографий
        }
    } catch (error) {
        console.error('Error fetching user photos:', error);
    }
},
    },
  };
  </script>
  
  <style scoped>
  .home {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    text-align: center;
  }
  
  .photo-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 15px;
    margin-top: 20px;
  }
  
  .photo-item {
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 10px;
    background-color: #f9f9f9;
  }
  </style>