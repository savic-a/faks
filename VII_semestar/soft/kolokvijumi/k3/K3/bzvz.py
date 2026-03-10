import sys
import os

import numpy as np
import cv2
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans

# keras
import tensorflow as tf
from tensorflow import keras
from keras.models import Sequential
from keras.layers import Dense, Activation

from tensorflow.keras.optimizers import SGD



def load_image(path):
    return cv2.cvtColor(cv2.imread(path), cv2.COLOR_BGR2RGB)


def crop(image):
    return image[170:250, 250:830]


def image_gray(image):
    return cv2.cvtColor(image, cv2.COLOR_RGB2GRAY)


def image_bin(image_gs):
    height, width = image_gs.shape[0:2]
    image_binary = np.ndarray((height, width), dtype=np.uint8)
    ret, image_bin = cv2.threshold(image_gs, 127, 255, cv2.THRESH_BINARY)
    return image_bin


def scale_to_range(image):
    return image/255


def display_image(image, color=False):
    if color:
        plt.imshow(image)
    else:
        plt.imshow(image, 'gray')


def resize_region(region):
    return cv2.resize(region, (28, 28), interpolation=cv2.INTER_NEAREST)


def matrix_to_vector(image):
    return image.flatten()


def winner(output):
    return max(enumerate(output), key=lambda x: x[1])[0]


def extract_folders():
    image_path, counts_file = '', ''

    main_folder_path = sys.argv[1]

    for sub_folder_name in os.listdir(main_folder_path):
        if 'pictures' in sub_folder_name:
            image_path = os.path.join(main_folder_path, sub_folder_name)
        else:
            counts_file = os.path.join(main_folder_path, sub_folder_name)

    return image_path, counts_file


def select_roi_with_distances(image_orig, image_bin):
    contours, hierarchy = cv2.findContours(image_bin.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
    regions_array = []
    for contour in contours:
        x, y, w, h = cv2.boundingRect(contour)
        region = image_bin[y:y+h+1, x:x+w+1] 
        region_length = len(region)
        # skip all image and region inner letter
        if region_length < 13 or region_length > 59:
            continue
        regions_array.append([resize_region(region), (x, y, w, h)])
    
    regions_array = sorted(regions_array, key=lambda x: x[1][0])
    
    return merge_regions(image_orig, regions_array, image_bin)
    

def merge_regions(image_orig, regions_array, image_bin, first=True):
    sorted_regions = [region[0] for region in regions_array]
    sorted_rectangles = [region[1] for region in regions_array]

    region_distances = []

    merge_regions_rectangles = []
    new_regions_array = []
    new_rectangles = []

    for index in range(0, len(sorted_rectangles) - 1):
        current = sorted_rectangles[index]
        next_rect = sorted_rectangles[index + 1]

        distance = next_rect[0] - (current[0] + current[2]) # x_next - (x_current + w_current)
        
        if distance < -10:
            merge_regions_rectangles.append(current)
            merge_regions_rectangles.append(next_rect)
        else:
            if current not in merge_regions_rectangles:
                new_regions_array.append(regions_array[index])
                new_rectangles.append(current)

    # last element
    if sorted_rectangles[-1] not in merge_regions_rectangles:
        new_regions_array.append(regions_array[-1])
        new_rectangles.append(sorted_rectangles[-1])

    for i in range(0, len(merge_regions_rectangles)-1, 2):
        x1, y1, w1, h1 = merge_regions_rectangles[i]
        x2, y2, w2, h2 = merge_regions_rectangles[i+1]

        if first:
            x = x1 if x1 <= x2 else x2
            y = y1 if y1 <= y2 else y2
            w = w1 if w1 >= w2 else w2
            h = h1+h2 if y2 <= y1 else h1
        else:
            x, y, w, h = (195, 11, 44, 54)

        new_rectangles.append((x, y, w, h))

        region = image_bin[y:y+h+1, x:x+w+1]
        new_regions_array.append([resize_region(region), (x, y, w, h)])

    new_regions_array = sorted(new_regions_array, key=lambda x: x[1][0])
    sorted_regions = [region[0] for region in new_regions_array]
    sorted_rectangles = [region[1] for region in new_regions_array]

    # image_orig = np.ones_like(image_orig) * 255
    # for i in range(0, len(sorted_rectangles)):
    #     x, y, w, h = sorted_rectangles[i]
    #     cv2.rectangle(image_orig, (x, y), (x+w, y+h), (0, 255, 0), 2)

    for index in range(0, len(sorted_rectangles) - 1):
        current = sorted_rectangles[index]
        next_rect = sorted_rectangles[index + 1]
        distance = next_rect[0] - (current[0] + current[2]) # x_next - (x_current + w_current) 
        region_distances.append(distance)

    if any(broj < -5 for broj in region_distances):
        return merge_regions(image_orig, new_regions_array, image_bin, False)
    else:
        return image_orig, sorted_regions, region_distances


def display_result(outputs, alphabet, k_means, distances):
    # odredjivanje indeksa grupe koja odgovara rastojanju izmedju reci
    w_space_group = max(enumerate(k_means.cluster_centers_), key=lambda x: x[1])[0]
    result = alphabet[winner(outputs[0])]
    # iterativno dodavanje prepoznatih elemenata
    # dodavanje space karaktera ako je rastojanje izmedju dva slova odgovara rastojanju izmedju reci
    for idx, output in enumerate(outputs[1:, :]):
        # if k_means.labels_[idx] == w_space_group:
        #     result += ' '
        result += alphabet[winner(output)]

    index = 0
    for i in range(0, len(distances)):
        if distances[i] > 24:
            result = result[:index+1] + " " + result[index+1:]
            index += 1
        index += 1
    return result


def process_image(image_path):
    all_letters = []
    i = 0
    words = []

    for img_name in os.listdir(image_path):
        img_path = os.path.join(image_path, img_name)
        img_color = load_image(img_path)
        crop_img = crop(img_color)
        img = image_bin(image_gray(crop_img))
        selected_regions, letters, region_distances = select_roi_with_distances(crop_img.copy(), img)

        # for letter in letters:
        #     # if not any(np.array_equal(letter, ul) for ul in all_letters):
        #         all_letters.append(letter)

        print("Broj prepoznatih regiona: ", len(letters))

        ann, num = train(letters, i)
        word = test(region_distances, letters, ann, i)
        words.append(word)
        i = num

    # for i in all_letters:
    #     uniqe_letters.append()

    return words
        # display_image(selected_regions)
        # plt.show()
        # break


def prepare_for_ann(regions):
    ready_for_ann = []
    for region in regions:
        scale = scale_to_range(region)
        ready_for_ann.append(matrix_to_vector(scale))
    return ready_for_ann


def convert_output(alphabet):
    nn_outputs = []
    for index in range(len(alphabet)):
        output = np.zeros(len(alphabet))
        output[index] = 1
        nn_outputs.append(output)
    return np.array(nn_outputs)


def create_ann(output_size):
    ann = Sequential()
    ann.add(Dense(128, input_dim=784, activation='sigmoid')) 
    ann.add(Dense(output_size, activation='sigmoid'))
    return ann

# @tf.function(autograph=False, experimental_relax_shapes=True, experimental_relax_types=True, experimental_reduce_retracing=True)
def train_ann(ann, X_train, y_train, epochs):
    X_train = np.array(X_train, np.float32) # dati ulaz
    y_train = np.array(y_train, np.float32) # zeljeni izlazi na date ulaze
    
    print("\nTraining started...")
    sgd = SGD(learning_rate=0.001, momentum=0.9)  #todo vidi ovo da se promeni
    ann.compile(loss='mean_squared_error', optimizer=sgd)
    ann.fit(X_train, y_train, epochs=epochs, batch_size=1, verbose=0, shuffle=False)
    print("\nTraining completed...")
    return ann


def train(letters, num):
    # alphabet = ['a','á','b','c','d','e','é','g','j','k','r','m','n','ó','p','t','ú','y','r','s','u','j','i','v','z']
    rec1 = ['v','i','s','z','o','n','t','l','á','t','á','s','r','a',
            'e','g','é','s','z','s','é','g','e','s',
            'á','u','t','o','b','a','l','e','s','e','t',
            'b','o','l','d','o','g','v','a','g','y','o','k',
            'e','z','e','g','y','k','u','t','y','a',
            'k','i','s','m','a','c','s','k','a',
            'c','s','o','d','á','l','a','t','o','s',
            'f','a','n','t','a','s','z','t','i','k','u','s',
            'j','ó','n','y','ú','l']
    # print(len(rec1))
    # print(len(letters))
    inputs = prepare_for_ann(letters)  
    outputs = convert_output(rec1[num:num+len(letters)])
    ann = create_ann(output_size=len(letters))
    ann = train_ann(ann, inputs, outputs, epochs=2000)
    return ann, num+len(letters)

# todo prosledi letters i distance
def test(distances, letters, ann, num):
    # alphabet = ['a','á','b','c','d','e','é','g','j','k','r','m','n','ó','p','t','ú','y','r','s','u','j','i','v','z']
    rec1 = ['v', 'i', 's', 'z', 'o', 'n', 't', 'l', 'á', 't', 'á', 's', 'r', 'a',
            'e','g','é','s','z','s','é','g','e','s',
            'á','u','t','o','b','a','l','e','s','e','t',
            'b','o','l','d','o','g','v','a','g','y','o','k',
            'e','z','e','g','y','k','u','t','y','a',
            'k','i','s','m','a','c','s','k','a',
            'c','s','o','d','á','l','a','t','o','s',
            'f','a','n','t','a','s','z','t','i','k','u','s',
            'j','ó','n','y','ú','l']
    # todo ovo ne treba opet da racunam kad sam vec izracunala
    # for img_name in os.listdir(image_path):
    #     img_path = os.path.join(image_path, img_name)
    #     img_color = load_image(img_path)
    #     crop_img = crop(img_color)
    #     img = image_bin(image_gray(crop_img))
    # selected_regions, letters, distances = select_roi_with_distances(crop_img.copy(), img)
    print("Broj prepoznatih regiona: ", len(letters))
    # display_image(selected_regions)
    # plt.show()

    print("ovo su distance")
    print(distances)

    # neophodno je da u K-Means algoritam bude prosledjena matrica u kojoj vrste odredjuju elemente
    distances = np.array(distances).reshape(len(distances), 1)

    k_means = KMeans(n_clusters=2, n_init='auto')
    k_means.fit(distances)

    inputs = prepare_for_ann(letters)
    results = ann.predict(np.array(inputs, np.float32))
    return display_result(results, rec1[num:len(letters)+num], k_means, distances)


def calculate_error(words, counts_file):
    index = 0
    error = 0
    with open(counts_file, 'r', encoding='utf-8') as f:
        next(f)
        for line in f:
            param = line.strip().split(',')
            correct = param[1]
            my = words[index]
            # image_name-correct-my
            print(param[0] + '-' + correct + '-' + my)
            error += calculate_hamming_distance(correct, my)
            index += 1

    print(error)


def calculate_hamming_distance(s1, s2):
    len_s1 = len(s1)
    len_s2 = len(s2)

    # if length is equal
    if len_s1 == len_s2:
        return sum(1 for i in range(len_s1) if s1[i] != s2[i])

    # if length not equal
    min_len = min(len_s1, len_s2)
    return sum(1 for i in range(min_len) if s1[i] != s2[i]) + abs(len_s1 - len_s2)

def func():
    image_path, counts_file = extract_folders()
    words = process_image(image_path)
    calculate_error(words, counts_file)
    # ann = train(letters)
    # test(image_path, ann)


if __name__ == '__main__':
    func()