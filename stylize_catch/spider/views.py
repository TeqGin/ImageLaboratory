from django.http import JsonResponse
from django.shortcuts import render


# Create your views here.


def index(requests):
    if requests.method == 'GET':
        res = {"code": 0, "aa": 1, "doc": requests.GET['doc']}
        # 若要返回json数据，需要用JsonResponse进行封装
        return JsonResponse(res)
