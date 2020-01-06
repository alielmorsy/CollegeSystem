import requests


def upload(filePath: str):

    url = "http://localhost/EductionSystem/upload.php"
    files = {'file': open(filePath, 'rb')}
    payload = {'filedata': 'foo', 'filename': 'bar', 'systemid': 'fooe', 'createNew': 'false'}
    r = requests.post(url, files=files, data=payload)
