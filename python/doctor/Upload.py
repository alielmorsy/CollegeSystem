from threading import Thread

import requests
from PyQt5 import QtCore, QtGui, QtWidgets

import Util
from connector import upload, connector
from doctor import SubjectListUi


class Upload(object):
    def __init__(self, name, data):
        self.name = name
        self.data = data
        self.files = []
        self.count = 0
        self.database = Database(name=name, data=data)

    def setupUi(self, MainWindow):
        self.window = MainWindow
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(691, 498)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.back = QtWidgets.QPushButton(self.centralwidget)
        self.back.setGeometry(QtCore.QRect(10, 10, 141, 81))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.back.setFont(font)
        self.back.setObjectName("back")
        self.label = QtWidgets.QLabel(self.centralwidget)
        self.label.setGeometry(QtCore.QRect(170, 10, 451, 91))
        font = QtGui.QFont()
        font.setPointSize(16)
        font.setBold(True)
        font.setWeight(75)
        self.label.setFont(font)
        self.label.setStyleSheet("color:rgb(255, 0, 0)")
        self.label.setAlignment(QtCore.Qt.AlignCenter)
        self.label.setObjectName("label")
        self.pushButton = QtWidgets.QPushButton(self.centralwidget)
        self.pushButton.setGeometry(QtCore.QRect(10, 170, 141, 81))

        font = QtGui.QFont()
        font.setPointSize(12)
        self.pushButton.setFont(font)
        self.pushButton.setObjectName("pushButton")
        self.pushButton_2 = QtWidgets.QPushButton(self.centralwidget)
        self.pushButton_2.setGeometry(QtCore.QRect(10, 290, 141, 81))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.pushButton_2.setFont(font)
        self.pushButton_2.setObjectName("pushButton_2")
        self.tableWidget = QtWidgets.QTableWidget(self.centralwidget)
        self.tableWidget.setGeometry(QtCore.QRect(310, 100, 381, 261))
        self.tableWidget.setObjectName("tableWidget")
        self.tableWidget.setColumnCount(4)
        self.tableWidget.setRowCount(0)
        item = QtWidgets.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(0, item)
        item = QtWidgets.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(1, item)
        item = QtWidgets.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(2, item)
        item = QtWidgets.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(3, item)
        self.item = QtWidgets.QTableWidgetItem()
        self.progressBar = QtWidgets.QProgressBar(self.centralwidget)
        self.progressBar.setGeometry(QtCore.QRect(7, 440, 661, 23))
        self.progressBar.setProperty("value", 0)
        self.progressBar.setObjectName("progressBar")
        self.label_2 = QtWidgets.QLabel(self.centralwidget)
        self.label_2.setGeometry(QtCore.QRect(10, 110, 161, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        font.setBold(True)
        font.setWeight(75)
        self.label_2.setFont(font)
        self.label_2.setObjectName("label_2")
        MainWindow.setCentralWidget(self.centralwidget)
        self.back.clicked.connect(self.BACK)
        self.pushButton.clicked.connect(self.chooseFiles)
        self.pushButton_2.clicked.connect(self.upload)
        self.tableWidget.setRowCount(10)
        self.tableWidget.setEditTriggers(QtWidgets.QTableWidget.NoEditTriggers)
        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)
        self.pushButton_2.setEnabled(False)
        Thread(target=self.setupList).start()

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.back.setText(_translate("MainWindow", "Return"))
        self.label.setText(_translate("MainWindow", f"Upload File to {self.name} students"))
        self.pushButton.setText(_translate("MainWindow", "Choose Files"))
        self.pushButton_2.setText(_translate("MainWindow", "Upload"))
        item = self.tableWidget.horizontalHeaderItem(0)
        item.setText(_translate("MainWindow", "Name"))
        item = self.tableWidget.horizontalHeaderItem(1)
        item.setText(_translate("MainWindow", "Size"))
        item = self.tableWidget.horizontalHeaderItem(2)
        item.setText(_translate("MainWindow", "Type"))
        item = self.tableWidget.horizontalHeaderItem(3)
        item.setText(_translate("MainWindow", "time Date"))
        self.label_2.setText(_translate("MainWindow", "Maximum files is 10"))

    def setupList(self):

        self.list = self.database.getFilesList()
        if self.list == "not":

            QtWidgets.QMessageBox.warning(self.window, "Server Error", "Can't connect to server")
        else:
            count = 0
            if self.list is not None:
                self.tableWidget.setRowCount(len(self.list) + 10)

                for i in self.list:
                    item = QtWidgets.QTableWidgetItem()

                    item.setText(str(i[1]))

                    self.tableWidget.setItem(count, 0, item)

                    item = QtWidgets.QTableWidgetItem()
                    item.setText(str(i[2]))
                    self.tableWidget.setItem(count, 1, item)
                    item = QtWidgets.QTableWidgetItem()
                    item.setText(str(i[6]))
                    self.tableWidget.setItem(count, 2, item)
                    item = QtWidgets.QTableWidgetItem()
                    item.setText(str(i[-1]))
                    self.tableWidget.setItem(count, 3, item)

                    count += 1

    def BACK(self):
        self.Window = QtWidgets.QMainWindow()
        self.ui = SubjectListUi.SubjectListUi(self.data)
        self.ui.setupUi(self.Window)
        self.Window.show()
        self.window.hide()

    def chooseFiles(self):

        files = QtWidgets.QFileDialog.getOpenFileName(self.window, "Choose Your Files")
        if self.count != 10:
            name = files[0].split('/')[-1]
            type = Util.checkFileType(name)
            fileSize = QtCore.QFile(files[0]).size()

            if fileSize > Util.gb:
                QtWidgets.QMessageBox.information(self.window, "", "Can't choose this file ")
                self.chooseFiles()
                return

            for i in self.list:
                if i[1] == name:
                    QtWidgets.QMessageBox.information(self.window, "", "This File already exists ")
                    self.chooseFiles()
                    return
            item = QtWidgets.QTableWidgetItem()
            item.setText(name)
            self.tableWidget.setItem(self.count, 0, item)
            item = QtWidgets.QTableWidgetItem()
            item.setText(Util.getSize(fileSize))
            self.tableWidget.setItem(self.count, 1, item)

            item = QtWidgets.QTableWidgetItem()
            item.setText(type)
            self.tableWidget.setItem(self.count, 2, item)
            self.files.append(files[0])
            self.count += 1
        if self.count > 0:
            self.pushButton_2.setEnabled(True)

    def upload(self):
        Thread(target=self.thread).start()
        self.pushButton.setEnabled(False)
        self.pushButton_2.setEnabled(False)

        self.pushButton.setEnabled(True)
        self.pushButton_2.setEnabled(True)

    def thread(self):
        c = 0
        for i in self.files:
            try:
                upload.upload(i)
                c += 1
                self.database.insert(i)
                self.progressBar.setValue((c * 100) // self.count)
            except FileNotFoundError:
                QtWidgets.QMessageBox.warning(self.window, "File Error", "Can't find specific file")
            except requests.RequestException:
                QtWidgets.QMessageBox.warning(self.window, "Server Error", "Can't connect to server")


class Database:
    def __init__(self, data, name):
        self.data = data
        self.connect = connector.connectorClass()
        self.name = name
        self.id = self.getCourseID()

    def insert(self, fileName: str):
        name = fileName.split('/')[-1]
        type = Util.checkFileType(name)

        fileSize = Util.getSize(QtCore.QFile(fileName).size())

        self.connect.execute(
            f"INSERT INTO `files` (`id`,`fileName`,`fileSize`,`doctor_id`,`course_id`,`filePath`,type,time) VALUES(NULL,'{name}','{fileSize}',{self.data[0]},{self.id},'{name}','{type}',current_timestamp())")

    def getCourseID(self):
        c = self.connect.execute(f"SELECT `id` FROM `courses` WHERE `name` ='{self.name}'")
        return c.fetchone()[0]

    def getFilesList(self):
        if self.connect.isConnected():
            print(self.getCourseID())
            c = self.connect.execute(f"SELECT * FROM `files` WHERE `course_id`={self.id}")
            return c.fetchall()
        else:
            return "not"
