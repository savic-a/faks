import sys
import os

import numpy as np
import cv2
import matplotlib.pyplot as plt


def load_image(path):
    return cv2.cvtColor(cv2.imread(path), cv2.COLOR_BGR2RGB)


def image_gray(image):
    return cv2.cvtColor(image, cv2.COLOR_RGB2GRAY)


def image_bin(image_gs):
    return cv2.adaptiveThreshold(image_gs, 255, cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY, 55, 10)


def crop(image):
    return image[60:990, 300:900]


def invert(image):
    return 255-image


def display_image(image, color=False):
    if color:
        plt.imshow(image)
    else:
        plt.imshow(image, 'gray')


def label2rgb(markers):
    cmap = plt.get_cmap('jet', len(np.unique(markers)))
    return cmap(markers)


def set_markers(image, unknown):
    ret, markers = cv2.connectedComponents(np.uint8(image))
    markers = markers + 1
    markers[unknown == 255] = 0
    return markers


def func():
    mean_absolute_error = []

    for filename in os.listdir(sys.argv[1]):
        file = os.path.join(sys.argv[1], filename)
        img = load_image(file)
        crop_img = crop(img)
        img_bin = image_bin(image_gray(crop_img))
        invert_img = invert(img_bin)

        bg = determine_background(invert_img)
        fg = determine_foreground(bg)
        unknown = determine_other_parts(fg, bg)

        markers = set_markers(fg, unknown)

        markers = cv2.watershed(crop_img, markers)
        crop_img[markers == -1] = [255, 0, 0]

        # rgba_img = label2rgb(markers)
        # display_image(rgba_img, color=True)
        # plt.show()

        result = count_dittos(filename, markers)
        mean_absolute_error.append(result)

    print(calculate_mae(mean_absolute_error))


def determine_background(img):
    # dilate
    kernel = np.ones((5, 5), np.uint8)
    sure_bg = cv2.dilate(img, kernel, iterations=5)
    return sure_bg


def determine_foreground(img):
    dist_transform = cv2.distanceTransform(img, cv2.DIST_L2, 5)
    ret, sure_fg = cv2.threshold(dist_transform, 0.6 * dist_transform.max(), 255, 0)
    return sure_fg


def determine_other_parts(sure_fg, sure_bg):
    sure_fg = np.uint8(sure_fg)
    unknown = cv2.subtract(sure_bg, sure_fg)
    return unknown


def count_dittos(img_name, markers):
    unique_colours = {pixel for row in markers for pixel in row}
    dittos = len(unique_colours) - 2

    with open('ditto_count.csv', 'r') as f:
        next(f)
        for line in f:
            param = line.strip().split(',')
            if img_name == param[0]:
                # image_name-correct-my
                print(img_name + '-' + param[1] + '-' + str(dittos))
                return abs(dittos - int(param[1]))


def calculate_mae(mae):
    sum_abs_value = 0

    for value in mae:
        sum_abs_value = sum_abs_value + value

    return sum_abs_value / 10


if __name__ == '__main__':
    func()