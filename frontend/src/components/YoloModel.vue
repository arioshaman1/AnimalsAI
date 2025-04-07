<template>
  <div class="yolo-model">
    <h1>YOLOv8 Object Detection</h1>
    
    <div class="upload-section">
      <input 
        type="file" 
        ref="fileInput" 
        @change="handleFileUpload" 
        accept=".png,.jpg,.jpeg,.mp4,.avi,.mov"
        class="file-input"
      >
      <button @click="uploadFile" :disabled="!file || isProcessing" class="upload-btn">
        {{ isProcessing ? 'Processing...' : 'Upload & Detect' }}
      </button>
    </div>

    <div v-if="error" class="error-message">
      {{ error }}
    </div>

    <div v-if="result" class="results-section">
      <h2>Detection Results</h2>
      <p><strong>Filename:</strong> {{ result.filename }}</p>
      <p><strong>Type:</strong> {{ result.media_type }}</p>
      
      <div v-if="result.media_type === 'image'" class="image-container">
        <img :src="imagePreview" alt="Uploaded image" class="preview-image">
        <div 
          v-for="(detection, index) in result.detections" 
          :key="index"
          class="bounding-box"
          :style="{
            left: `${(detection.bbox.x1 / imageWidth) * 100}%`,
            top: `${(detection.bbox.y1 / imageHeight) * 100}%`,
            width: `${((detection.bbox.x2 - detection.bbox.x1) / imageWidth) * 100}%`,
            height: `${((detection.bbox.y2 - detection.bbox.y1) / imageHeight) * 100}%`,
          }"
        >
          <span class="label">{{ detection.class }} ({{ (detection.confidence * 100).toFixed(1) }}%)</span>
        </div>
      </div>

      <div v-else class="video-container">
        <video controls class="preview-video">
          <source :src="videoPreview" :type="videoType">
          Your browser does not support the video tag.
        </video>
      </div>

      <div class="detections-list">
        <h3>Detected Objects:</h3>
        <ul>
          <li v-for="(detection, index) in result.detections" :key="index">
            {{ detection.class }} - {{ (detection.confidence * 100).toFixed(1) }}% confidence
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'YoloModel',
  data() {
    return {
      file: null,
      isProcessing: false,
      error: null,
      result: null,
      imagePreview: null,
      videoPreview: null,
      videoType: null,
      imageWidth: 0,
      imageHeight: 0
    }
  },
  methods: {
    handleFileUpload(event) {
      this.file = event.target.files[0]
      this.error = null
      this.result = null
      
      // Create preview
      if (this.file) {
        const reader = new FileReader()
        reader.onload = (e) => {
          if (this.file.type.match('image.*')) {
            this.imagePreview = e.target.result
            const img = new Image()
            img.onload = () => {
              this.imageWidth = img.width
              this.imageHeight = img.height
            }
            img.src = e.target.result
          } else if (this.file.type.match('video.*')) {
            this.videoPreview = e.target.result
            this.videoType = this.file.type
          }
        }
        reader.readAsDataURL(this.file)
      }
    },
    async uploadFile() {
      if (!this.file) {
        this.error = 'Please select a file first'
        return
      }

      this.isProcessing = true
      this.error = null

      const formData = new FormData()
      formData.append('file', this.file)

      try {
        const response = await fetch('http://localhost:5001/predict', {
          method: 'POST',
          body: formData
        })

        if (!response.ok) {
          throw new Error(`Server returned ${response.status}`)
        }

        this.result = await response.json()
      } catch (err) {
        this.error = `Error processing file: ${err.message}`
        console.error('API Error:', err)
      } finally {
        this.isProcessing = false
      }
    }
  }
}
</script>

<style scoped>
.yolo-model {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

.upload-section {
  margin: 30px 0;
  display: flex;
  gap: 15px;
  align-items: center;
}

.file-input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  flex-grow: 1;
}

.upload-btn {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.upload-btn:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.error-message {
  color: #ff4444;
  margin: 20px 0;
  padding: 10px;
  background-color: #ffeeee;
  border-radius: 4px;
}

.results-section {
  margin-top: 30px;
  border-top: 1px solid #eee;
  padding-top: 20px;
}

.image-container {
  position: relative;
  margin: 20px 0;
}

.preview-image {
  max-width: 100%;
  display: block;
  border-radius: 4px;
}

.bounding-box {
  position: absolute;
  border: 2px solid #42b983;
  background-color: rgba(66, 185, 131, 0.2);
}

.bounding-box .label {
  position: absolute;
  top: -20px;
  left: 0;
  background-color: #42b983;
  color: white;
  padding: 2px 5px;
  font-size: 12px;
  border-radius: 3px;
}

.preview-video {
  max-width: 100%;
  border-radius: 4px;
}

.detections-list {
  margin-top: 20px;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.detections-list ul {
  list-style-type: none;
  padding: 0;
}

.detections-list li {
  padding: 5px 0;
  border-bottom: 1px solid #ddd;
}

.detections-list li:last-child {
  border-bottom: none;
}
</style>