"""stylize_catch URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path

from spider import views

urlpatterns = [
    # path('admin/', admin.site.urls),
    path('index/', views.index),
    path('segment/', views.segment),
    path('test/', views.test),
    path('images', views.grab_img),
    path('images_split', views.grab_img_split),
    path('catch_color', views.catch_color),
    path('turn_grey', views.turn_grey),
]
