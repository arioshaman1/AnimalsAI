from flask import Flask, request, jsonify
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
import numpy as np
import os

# Загрузка модели
model = load_model('model.h5')

# Инициализация Flask приложения
app = Flask(__name__)

def preprocess_image(img_path, target_size=(128, 128)):
    img = image.load_img(img_path, target_size=target_size)
    img_array = image.img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)
    img_array /= 255.0  # Нормализация
    return img_array

def predict_image(img_path):
    img_array = preprocess_image(img_path)
    prediction = model.predict(img_array)
    predicted_class = np.argmax(prediction, axis=-1)
    class_names = ["Cat", "Dog"]  # Замените на свои классы
    predicted_label = class_names[predicted_class[0]]
    predicted_prob = np.max(prediction)  # Максимальная вероятность
    return predicted_label, predicted_prob


@app.route('/predict', methods=['POST'])
def predict():
    if 'file' not in request.files:
        return jsonify({'error': 'No file uploaded'}), 400

    file = request.files['file']
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    # Сохраняем файл временно
    file_path = os.path.join('uploads', file.filename)
    file.save(file_path)

    # Делаем предсказание
    try:
        label, probability = predict_image(file_path)
        response = {
            'label': label,
            'probability': float(probability)
        }
        return jsonify(response)
    except Exception as e:
        return jsonify({'error': str(e)}), 500
    finally:
        # Удаляем временный файл
        if os.path.exists(file_path):
            os.remove(file_path)

# Запуск Flask приложения
if __name__ == '__main__':
    # Создаем папку для загрузок, если её нет
    os.makedirs('uploads', exist_ok=True)
    app.run(debug=True)