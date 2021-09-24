import requests
from bs4 import BeautifulSoup


def get_html(url):
    headers = {
        'User-Agent': 'Mozilla / 5.0(Windows NT 10.0;Win64;x64) AppleWebKit / 537.36(KHTML, likeGecko) Chrome / 79.0.3945.130 Safari / 537.36'
    }
    response = requests.get(url=url, headers=headers)
    return response.text


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
    for i in range(1, 10):
        part = get_img('https://www.bing.com/images/search?q=%s&first=%s&tsc=ImageBasicHover' % (keywords, str(i)),
                       "mimg")
        images += part
    return images
