import requests

link = "http://127.0.0.1/EductionSystem"


def DownloadImage(imageName):
    try:
        request = requests.get(link + f"/download.php?fileName={imageName}&place=userImages")
        with open('files/icon.jpg', "wb+") as f:
            f.write(request.content)
    except requests.exceptions.ConnectionError:
        print("can't download Image")
