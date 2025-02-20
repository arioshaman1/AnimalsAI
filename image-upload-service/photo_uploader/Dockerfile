# Используем официальный образ MinIO
FROM minio/minio:latest

# Указываем рабочую директорию для MinIO
WORKDIR /data

# Устанавливаем переменные окружения для MinIO
ENV MINIO_ROOT_USER=root
ENV MINIO_ROOT_PASSWORD=12345678


EXPOSE 9001

# Запускаем MinIO с командой сервера
ENTRYPOINT ["minio", "server", "/data"]