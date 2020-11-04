#!/usr/bin/env python3

import numpy as np
import argparse
import sys
import psycopg2

from tensorflow.keras.preprocessing import image
from tensorflow.keras.models import model_from_json


def createParser():
    parser = argparse.ArgumentParser()
    parser.add_argument('-tp', '--temppath')
    parser.add_argument('-wd', '--workdir')
    return parser


# pip install --upgrade --force-reinstall matplotlib==2.0.0

# Устанавливаем seed для повторяемости результатов
np.random.seed(42)

# Загружаем обученную нейронную сеть из файла
# Загружаем данные об архитектуре сети из файла json
json_file = open("/Users/a17857189/Documents/Quandoo/NeuroNet/src/main/resources/mnistreal_model.json", "r")
loaded_model_json = json_file.read()
json_file.close()

# Создаем модель на основе загруженных данных
loaded_model = model_from_json(loaded_model_json)
# Загружаем веса в модель
loaded_model.load_weights("/Users/a17857189/Documents/Quandoo/NeuroNet/src/main/resources/mnistreal_model.h5")

# Перед использованием модели, ее обязательно нужно скомпилировать:

# Компилируем модель
loaded_model.compile(loss="categorical_crossentropy", optimizer="SGD", metrics=["accuracy"])

parser = createParser()
namespace = parser.parse_args(sys.argv[1:])
work_dir = namespace.workdir

# Загружаем изображение
img_path = namespace.temppath
img = image.load_img(img_path, target_size=(28, 28), grayscale=True)

# im = plt.imread('two_1.png')
# plt.imshow(im,cmap="gray")
# plt.show()

# Преобразуем изображением в массив numpy
x = image.img_to_array(img)

# Нормализуем изображение
x_norm = np.expand_dims(x, axis=0)

prediction = loaded_model.predict(x_norm)

print("Number: ")
print(np.argmax(prediction))
print("with accuracy: ")
print(prediction[0][np.argmax(prediction)])

# /Users/a17857189/PycharmProjects/project/
path_elements = str(img_path).split("/")
file_name = path_elements[len(path_elements) - 1]
path = "{0}{1}/{2}".format(work_dir, str(np.argmax(prediction)), file_name)
image.save_img(path, x)

print("save file to path: ")
print(path)

conn = psycopg2.connect(dbname='test_db', user='test_user',
                        password='password', host='localhost', port='5442')
cursor = conn.cursor()
conn.autocommit = True
cursor.execute("INSERT INTO statistic (pic_filename, digit, accuracy) VALUES(%s, %s, %s)", (file_name, int(np.argmax(prediction)), float(prediction[0][np.argmax(prediction)])))
cursor.close()
conn.close()
