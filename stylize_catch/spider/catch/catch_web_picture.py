import time

import requests
from bs4 import BeautifulSoup


def get_html(url):
    headers = {
        'User-Agent': 'Mozilla / 5.0(Windows NT 10.0;Win64;x64) AppleWebKit / 537.36(KHTML, likeGecko) Chrome / 79.0.3945.130 Safari / 537.36',
        'Connection': 'close'
    }
    response = requests.get(url=url, headers=headers,stream=True,verify=False)
    text = response.text
    response.close()
    return text


def get_img(url, clazz):
    html = get_html(url)
    # 把html网页源代码转成soup对象
    soup = BeautifulSoup(html, 'html.parser')
    # 所有的p标签  用soup.title.string可输出title中的内容，所以直接遍历strings并调用p标签中的string
    res = soup.select('.mimg')
    part_images = []
    for r in res:
        try:
            src = r['src']
            if 'data:image/gif' not in src:
                part_images.append(src)
        except:
            try:
                src = r['data-src']
                if 'data:image/gif' not in src:
                    part_images.append(src)
            except:
                print("no src")
    return part_images


def grab_img_form_bing(keywords):
    images = []
    for i in range(1, 11):
        try:
            part = get_img('https://www.bing.com/images/search?q=%s&first=%s&tsc=ImageBasicHover' % (keywords, str(i)),
                           ".mimg")
            images += part
        except:
            print("被迫关闭")
            time.sleep(5)
        finally:
            print("抓取到 %d张图片" % (len(images)))
    return images
