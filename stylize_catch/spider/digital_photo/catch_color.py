import cv2
import numpy as np

# set the lower range of the blue
lower_blue = np.array([100, 110, 110])
# set the height range of the blue
height_blue = np.array([130, 255, 255])

# set the lower range of the red
lower_red = np.array([0, 110, 110])
# set the height range of the red
height_red = np.array([10, 255, 255])


def catch_photo_color(path):
    frame = cv2.imread(path)
    # turn into hsv model
    hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
    # inRange if the color is in lower to height ,it will be white
    mask = cv2.inRange(hsv, lower_blue, height_blue)
    # only save the part which color is blue
    res = cv2.bitwise_and(frame, frame, mask=mask)
    return mask, res


def turn_grey(path):
    # read the image
    # imread的第二个参数表示的是选择读取的信息，0是灰度图，1是彩色图，-1是包含透明通道的彩色图
    img = cv2.imread('learn.png', 0)
    return img
