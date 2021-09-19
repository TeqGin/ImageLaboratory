import base64

import cv2

import numpy as np


def base64_cv2(base64_str):
    imgString = base64.b64decode(base64_str)
    nparr = np.fromstring(imgString, np.uint8)
    image = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    return image


def cv2_base64(image):
    base64_str = cv2.imencode('.jpg', image)[1].tostring()
    base64_str = base64.b64encode(base64_str)
    base64_str_res = str(base64_str)
    base64_str_res = base64_str_res[2: len(base64_str_res) - 1]
    return base64_str_res
