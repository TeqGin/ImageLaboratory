# 开发环境

### 前端

- thymeleaf
- bootstrap
- jquery
- layui
- echarts



### java后端

- springboot
- mybatis
- mysql 8.0



### python后端

- django
- opencv





# 简介

- 该项目集成百度智能云的图像处理接口。
- 项目为用户创建专属云空间，支持用户上传图片进行存储和处理。
- 用户上传图片时系统会对图片进行分类，在推荐模块中用以计算出用户的上传偏好并进行推荐

## 登陆注册模块

### 登陆

- 登陆时添加滑动验证，防止爬虫
- 用户信息使用session进行存储和管理

### 注册

- 使用邮箱进行注册
- 密码使用md5进行加密存储



### 找回密码

- 支持使用邮箱验证找回
  - 向邮箱发送验证码



## 用户模块

### 云空间

- 为用户提供云空间，以存储用户上传的图片，目前使用服务器空间存储图片信息
- 云空间支持创建、删除、移动、重命名文件夹，支持文件夹多级路径
- 云空间支持图片的上传和下载和移动
- 在上传时系统开启分类线程对图片打上标签。



### 用户个人信息

- 支持修改密码、昵称、手机号。
- 支持查看用户登陆记录
- 支持注销账号
- 支持帮助别人申诉账号



### 数据分析

- 对用户数据进行分析，支持分析用户上传偏好、上传图片类型数量分析、上传次数分析



### 偏好推荐

- 在基于用户浏览数和上传图片时间进行内容推荐。
- 实时爬取图片对用户进行推送。



## 图像处理模块

### 图像转文字

- 支持ocr



### 图像特效

- 支持神经风格转换
- 支持背景轮廓分割
- 支持人像卡通化
- 支持黑白照片上色



### 图像增强

- 支持图像去雾
- 支持图像对比度增强
- 支持图像清晰度增强



## 爬虫模块

### 抓取图片

- 支持基于关键字在bing.com上抓取相关图片



## 轮廓分割

- 基于分类、聚合算法对背景颜色进行分类并保留明显部分。





#  项目展示



## 推荐模块

![preference](\intro\recommand\analyze.png)

![show](\intro\recommand\show.png)

![show](\intro\recommand\show2.png)

![show](\intro\recommand\show3.png)





## 图像处理模块（部分展示）

![index](\intro\image_process\index.png)

![ocr](\intro\image_process\ocr.png)

![changes](\intro\image_process\changes.png)

![black_before](\intro\image_process\black_before.png)

![black_after](\intro\image_process\black_after.png)

从云空间中选择图片

![choose_from_cloud](\intro\image_process\choose_from_cloud.png)

![nst_before](\intro\image_process\nst_before.png)

![nst_after](\intro\image_process\nst_after.png)

![catch_face](\intro\image_process\catch_face.png)

![enhance](\intro\image_process\enhance.png)

## 登陆注册模块

![image-login_index](\intro\login_index.png)



![register](\intro\register.png)



## 用户模块

![indedx](intro\user_space\index.png)

![data](intro\user_space\data.png)

![info](intro\user_space\info.png)

![login_record](intro\user_space\login_record.png)