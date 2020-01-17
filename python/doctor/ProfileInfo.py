# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'ProfileInfo.ui'
#
# Created by: PyQt5 UI code generator 5.13.0
#
# WARNING! All changes made in this file will be lost!


from PyQt5 import QtCore, QtGui, QtWidgets

from connector import connector

import doctor.MainWindow
from doctor import MainWindow


class Ui_MainWindow(object):
    def __init__(self, data):
        self.data = data
        database=Database(data)
        self.departmentName=database.getDepartmentName()
    def setupUi(self, MainWindow):
        self.window=MainWindow
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(658, 526)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.image = QtWidgets.QLabel(self.centralwidget)
        self.image.setGeometry(QtCore.QRect(10, 30, 151, 181))
        self.image.setObjectName("image")
        self.label = QtWidgets.QLabel(self.centralwidget)
        self.label.setGeometry(QtCore.QRect(180, 100, 51, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label.setFont(font)
        self.label.setStyleSheet("color:rgb(227, 0, 0)")
        self.label.setObjectName("label")
        self.label_2 = QtWidgets.QLabel(self.centralwidget)
        self.label_2.setGeometry(QtCore.QRect(200, 20, 371, 71))
        font = QtGui.QFont()
        font.setPointSize(16)
        self.label_2.setFont(font)
        self.label_2.setAlignment(QtCore.Qt.AlignCenter)
        self.label_2.setObjectName("label_2")
        self.name = QtWidgets.QLabel(self.centralwidget)
        self.name.setGeometry(QtCore.QRect(240, 101, 301, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.name.setFont(font)
        self.name.setObjectName("name")
        self.age = QtWidgets.QLabel(self.centralwidget)
        self.age.setGeometry(QtCore.QRect(220, 160, 301, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.age.setFont(font)
        self.age.setObjectName("age")
        self.label_5 = QtWidgets.QLabel(self.centralwidget)
        self.label_5.setGeometry(QtCore.QRect(180, 160, 51, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label_5.setFont(font)
        self.label_5.setStyleSheet("color:rgb(227, 0, 0)\n"
                                   "")
        self.label_5.setObjectName("label_5")
        self.label_6 = QtWidgets.QLabel(self.centralwidget)
        self.label_6.setGeometry(QtCore.QRect(180, 209, 61, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label_6.setFont(font)
        self.label_6.setStyleSheet("color:rgb(227, 0, 0)\n"
                                   "")
        self.label_6.setObjectName("label_6")
        self.userID = QtWidgets.QLabel(self.centralwidget)
        self.userID.setGeometry(QtCore.QRect(250, 210, 301, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.userID.setFont(font)
        self.userID.setObjectName("userID")
        self.label_8 = QtWidgets.QLabel(self.centralwidget)
        self.label_8.setGeometry(QtCore.QRect(180, 389-60, 91, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label_8.setFont(font)
        self.label_8.setStyleSheet("color:rgb(227, 0, 0)\n"
                                   "")
        self.label_8.setObjectName("label_8")
        self.department = QtWidgets.QLabel(self.centralwidget)
        self.department.setGeometry(QtCore.QRect(270, 389-60, 301, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.department.setFont(font)
        self.department.setObjectName("department")
        self.label_9 = QtWidgets.QLabel(self.centralwidget)
        self.label_9.setGeometry(QtCore.QRect(180, 330-60, 71, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label_9.setFont(font)
        self.label_9.setStyleSheet("color:rgb(227, 0, 0)\n"
                                   "")
        self.label_9.setObjectName("label_9")
        self.label_10 = QtWidgets.QLabel(self.centralwidget)
        self.label_10.setGeometry(QtCore.QRect(250, 331-60, 301, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label_10.setFont(font)
        self.label_10.setObjectName("label_10")
        self.pushButton = QtWidgets.QPushButton(self.centralwidget)
        self.pushButton.setGeometry(QtCore.QRect(190, 450-60, 221, 51))
        self.pushButton.setObjectName("pushButton")
        MainWindow.setCentralWidget(self.centralwidget)
        self.pushButton.clicked.connect(self.Return)
        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)
    def Return(self):
        self.indow=MainWindow.Ui_MainWindow(self.data)
        self.indow.setupUi(self.window)


    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.image.setText(_translate("MainWindow", ""))
        self.label.setText(_translate("MainWindow", "Name:"))
        self.label_2.setText(_translate("MainWindow", "User Info"))
        self.name.setText(_translate("MainWindow", self.data[2]))
        self.age.setText(_translate("MainWindow", str(self.data[3])))
        self.label_5.setText(_translate("MainWindow", "Age:"))
        self.label_6.setText(_translate("MainWindow", "UserID:"))
        self.userID.setText(_translate("MainWindow", str(self.data[1])))
        self.label_8.setText(_translate("MainWindow", "Department:"))
        self.department.setText(_translate("MainWindow", self.departmentName))
        self.label_9.setText(_translate("MainWindow", "Graduate:"))
        self.label_10.setText(_translate("MainWindow", self.data[-2]))
        self.pushButton.setText(_translate("MainWindow", "Return"))


class Database:
    def __init__(self, data):
        self.data = data
        self.connect = connector.connectorClass()

    def getDepartmentName(self):
        if self.connect.isConnected():
            data = self.connect.execute(f"SELECT `name` FROM `department` WHERE `id`={self.data[-3]}")
            return data.fetchone()[0]
