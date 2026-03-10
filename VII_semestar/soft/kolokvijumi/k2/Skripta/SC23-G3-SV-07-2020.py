import os
import sys
import numpy as np
import cv2 
from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt


def load_image(path):
    return cv2.cvtColor(cv2.imread(path), cv2.COLOR_BGR2GRAY)


def display_image(image):
    plt.imshow(image, 'gray')


def extract_folders():
    image_path, video_path, counts_file = '', '', ''

    main_folder_path = sys.argv[1]

    for sub_folder_name in os.listdir(main_folder_path):
        if 'pictures' in sub_folder_name:
            image_path = os.path.join(main_folder_path, sub_folder_name)
        elif 'videos' in sub_folder_name:
            video_path = os.path.join(main_folder_path, sub_folder_name)
        else:
            counts_file = os.path.join(main_folder_path, sub_folder_name)

    return image_path, video_path, counts_file


def load_images(image_path):
    img = ''
    pos_imgs, neg_imgs = [], []

    for img_name in os.listdir(image_path):
        img_path = os.path.join(image_path, img_name)
        img = load_image(img_path)
        if 'p_' in img_name:
            pos_imgs.append(img)
        elif 'n_' in img_name:
            neg_imgs.append(img)
            
    # print("Positive images #: ", len(pos_imgs))
    # print("Negative images #: ", len(neg_imgs))

    return pos_imgs, neg_imgs


def hog(pos_imgs, neg_imgs):
    pos_features = []
    neg_features = []
    labels = []

    img_shape = (60, 120)
    nbins = 9            # bin number
    cell_size = (8, 8)   # number of pixels per cell
    block_size = (3, 3)  # number of cells per block

    hog = cv2.HOGDescriptor(_winSize=(img_shape[1] // cell_size[1] * cell_size[1], 
                                    img_shape[0] // cell_size[0] * cell_size[0]),
                            _blockSize=(block_size[1] * cell_size[1],
                                        block_size[0] * cell_size[0]),
                            _blockStride=(cell_size[1], cell_size[0]),
                            _cellSize=(cell_size[1], cell_size[0]),
                            _nbins=nbins)
    for img in pos_imgs:
        pos_features.append(hog.compute(img))
        labels.append(1)

    for img in neg_imgs:
        neg_features.append(hog.compute(img))
        labels.append(0)

    pos_features = np.array(pos_features)
    neg_features = np.array(neg_features)
    x = np.vstack((pos_features, neg_features))
    y = np.array(labels)

    return hog, x, y


def svm(x, y):
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2, random_state=42)
    # print('Train shape: ', x_train.shape, y_train.shape)
    # print('Test shape: ', x_test.shape, y_test.shape)
    clf_svm = SVC(kernel='linear', probability=True) 
    clf_svm.fit(x_train, y_train)
    y_train_pred = clf_svm.predict(x_train)
    y_test_pred = clf_svm.predict(x_test)
    # print("Train accuracy: ", accuracy_score(y_train, y_train_pred))
    # print("Validation accuracy: ", accuracy_score(y_test, y_test_pred))

    return clf_svm


def detect_line(img):
    alpha = -0.5
    img_higher_contrast = cv2.convertScaleAbs(img, alpha=alpha)
    edges_img = cv2.Canny(img_higher_contrast, 50, 300, apertureSize=3)

    min_line_length = 150  
    
    # hough transformation
    lines = cv2.HoughLinesP(image=edges_img, rho=1, theta=np.pi/180, threshold=10, lines=np.array([]),
                            minLineLength=min_line_length, maxLineGap=30)
    
    x1 = lines[0][0][0]
    y1 = 1080 - lines[0][0][1]
    x2 = lines[0][0][2]
    y2 = 1080 - lines[0][0][3]
    
    return (x1, y1, x2, y2)


def load_videos(counts_file, video_folder_path, hog_descriptor, classifier):
    mae = []

    for video_name in os.listdir(video_folder_path):
        video_path = os.path.join(video_folder_path, video_name)
        car_num = process_video(video_path, hog_descriptor , classifier)
        result = print_result(counts_file, video_name, car_num)
        mae.append(result)

    return mae


def process_video(video_path, hog_descriptor, classifier):
    cars_num = 0
    often = 0
    
    # load video
    frame_num = 0
    cap = cv2.VideoCapture(video_path)
    cap.set(1, frame_num) # frame indexing
    
    # frame by frame video analysis
    while True:
        frame_num += 1
        grabbed, frame = cap.read()

        # if the frame is not captured
        if not grabbed:
            break
        
        # if frame_num == 1: # if it is the first frame, detect the line
        #     line_coords = detect_line(frame)  # (x1, y1, x2, y2)
            
        #     # the x coordinates are the same because the line is vertical!!!!!!!!!!   
        #     line_x = line_coords[0]


        x, y, w, h = 850, 250, 300, 100

        roi = frame[y:y+h, x:x+w]
        roi = cv2.resize(roi, (120, 60), interpolation=cv2.INTER_NEAREST)
                
        # hog
        number_features = hog_descriptor.compute(roi).reshape(1, -1)
        predicted_number = classifier.predict(number_features)[0]

        # do not count the same cars
        if predicted_number:
            often += 1
        else:
            often = 0

        if predicted_number and often == 1:
            # and (x + w) > line_x
            cars_num += 1
            # show the frame with the drawn rectangle
            # cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)      
            # frame_rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)     
            # plt.imshow(frame_rgb)
            # plt.show()

    cap.release()
    return cars_num


def print_result(counts_file, video_name, car_num):
    with open(counts_file, 'r') as f:
        next(f)
        for line in f:
            param = line.strip().split(',')
            if video_name == param[0]:
                # video_name-correct-my
                print(video_name + '-' + param[1] + '-' + str(car_num))
                return abs(car_num - int(param[1]))


def calculate_mae(abs_values):
    sum_abs_value = 0

    for value in abs_values:
        sum_abs_value = sum_abs_value + value

    return sum_abs_value / 3


def func():
    image_path, video_path, counts_file = extract_folders()
    pos_imgs, neg_imgs = load_images(image_path)
    hog_result, x, y = hog(pos_imgs, neg_imgs)
    classifier = svm(x, y)
    result = load_videos(counts_file, video_path, hog_result, classifier)
    print(calculate_mae(result))


if __name__ == '__main__':
    func() 
