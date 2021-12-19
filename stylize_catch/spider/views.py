import json

from django.core import serializers
from django.http import JsonResponse
from django.shortcuts import render
import sys
import numpy as np
import cv2
# Create your views here.
from spider.segment.segment import Segment
from spider.utils import image_utils
from spider.catch import catch_web_picture


def index(requests):
    if requests.method == 'GET':
        res = {"code": 0, "aa": 1, "doc": requests.GET['doc']}
        # 若要返回json数据，需要用JsonResponse进行封装
        return JsonResponse(res)


def segment(requests):
    if requests.method == 'GET':
        # postbody = requests.body
        # json_param = json.loads(postbody.decode())

        path = requests.GET['path']
        image = cv2.imread(path)
        if len(sys.argv) == 3:
            seg = Segment()
            label, result = seg.kmeans(image)
        else:
            seg = Segment(4)
            label, result = seg.kmeans(image)
        # cv2.imshow("segmented", result)
        result = seg.extractComponent(image, label, 2)
        base64_str_res = image_utils.cv2_base64(result)
        res = {'image': str(base64_str_res), 'code': 0}
        return JsonResponse(res)


def grab_img(requests):
    if requests.method == 'GET':
        keywords = requests.GET['keywords']
        images = catch_web_picture.grab_img_form_bing(keywords)
        res = {'images': images, 'code': 0}
        return JsonResponse(res)


def grab_img_split(requests):
    # 获取关键字并实现对关键字的图像抓取
    if requests.method == 'GET':
        key1 = requests.GET['key1']
        key2 = requests.GET['key2']
        key3 = requests.GET['key3']
        keywords = [key1, key2, key3]
        images = catch_web_picture.grab_img_form_bing_split(keywords)
        res = {'images': images, 'code': 0}
        return JsonResponse(res)


def test(requests):
    if requests.method == 'POST':
        postbody = requests.body
        json_param = json.loads(postbody.decode())
        print(json_param['num'])
        return JsonResponse(json_param)
