from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
import numpy as np

# Загрузка модели
model = load_model('model.h5')


# Функция для предобработки изображения
def preprocess_image(img_path, target_size=(128, 128)):  # Меняем размер на 128x128
    img = image.load_img(img_path, target_size=target_size)
    img_array = image.img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)
    img_array /= 255.0  # Нормализация
    return img_array

# Функция для предсказания
def predict_image(img_path):
    img_array = preprocess_image(img_path)
    prediction = model.predict(img_array)
    print("Prediction:", prediction)  # Для диагностики

    # Определяем класс с максимальной вероятностью
    predicted_class = np.argmax(prediction, axis=-1)
    class_names = ["Cat", "Dog"]  # Замени на свои классы
    predicted_label = class_names[predicted_class[0]]

    # Выводим вероятность
    predicted_prob = np.max(prediction)  # Максимальная вероятность
    return f"The image is a: {predicted_label} with probability {predicted_prob:.2f}"

# Пример использования
result = predict_image('cat.jpg')
print(result)
# Пример использования
result = predict_image('cat.jpg')
print(f"The image is a: {result}")
# Пример использования
print(f"The image is a: {result}")