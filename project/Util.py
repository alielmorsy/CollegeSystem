def checkFileType(fileName):
    ext = fileName.split('.')[-1]
    if ext == "pdf":
        return "books"
    elif ext == "pptx" or ext == "ppt":
        return "slides"
    elif ext == "zip" or ext == "rar":
        return "compresseds"
    elif ext == "txt":
        return "documents"
    else:
        return ext + " files"


def getSize(size):
    if size < kb:
        return str(size) + "byte"
    elif size < mb or size == kb:
        return str(size / kb) + "kb"
    elif size < gb or size == mb:
        return str(size / mb) + "mb"


kb = 1024
mb = kb * 1024
gb = mb * 1024
