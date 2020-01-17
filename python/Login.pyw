from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import QMessageBox, QStyleFactory

import connector.connector
import connector.ServerConnection

from doctor.MainWindow import Ui_MainWindow

import users.studentaffiar
from users import studentaffiar


class Login(object):
    def __init__(self):

        self.database = database()

    def setupUi(self, MainWindow):
        self.window = MainWindow
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(642, 480)
        self.centralwidget = QtWidgets.QWidget(self.window)
        self.centralwidget.setObjectName("centralwidget")
        self.centralwidget.setAcceptDrops(True)
        self.label = QtWidgets.QLabel(self.centralwidget)
        self.label.setGeometry(QtCore.QRect(40, 30, 511, 61))
        font = QtGui.QFont()
        font.setPointSize(16)
        font.setBold(True)
        font.setWeight(75)
        self.label.setFont(font)
        self.label.setStyleSheet("color:rgb(255, 0, 0)")
        self.label.setAlignment(QtCore.Qt.AlignCenter)
        self.label.setObjectName("label")
        self.label_2 = QtWidgets.QLabel(self.centralwidget)
        self.label_2.setGeometry(QtCore.QRect(20, 140, 111, 61))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label_2.setFont(font)
        self.label_2.setObjectName("label_2")
        self.label_3 = QtWidgets.QLabel(self.centralwidget)
        self.label_3.setGeometry(QtCore.QRect(20, 230, 111, 61))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label_3.setFont(font)
        self.label_3.setObjectName("label_3")
        self.label_4 = QtWidgets.QLabel(self.centralwidget)
        self.label_4.setGeometry(QtCore.QRect(430, 130, 111, 61))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label_4.setFont(font)
        self.label_4.setObjectName("label_4")
        self.groupBox = QtWidgets.QGroupBox(self.centralwidget)
        self.groupBox.setAcceptDrops(True)
        self.groupBox.setGeometry(QtCore.QRect(390, 190, 241, 80))
        self.groupBox.setObjectName("groupBox")
        self.doctor = QtWidgets.QRadioButton(self.groupBox)
        self.doctor.setGeometry(QtCore.QRect(10, 20, 82, 21))
        font = QtGui.QFont()
        font.setPointSize(10)
        self.doctor.setFont(font)
        self.doctor.setObjectName("doctor")
        self.affair = QtWidgets.QRadioButton(self.groupBox)
        self.affair.setGeometry(QtCore.QRect(130, 20, 82, 21))
        font = QtGui.QFont()
        font.setPointSize(10)
        self.affair.setFont(font)
        self.affair.setObjectName("affair")
        self.admin = QtWidgets.QRadioButton(self.groupBox)
        self.admin.setGeometry(QtCore.QRect(75, 50, 82, 21))
        font = QtGui.QFont()
        font.setPointSize(10)
        self.admin.setFont(font)
        self.admin.setObjectName("admin")
        self.loginButton = QtWidgets.QPushButton(self.centralwidget)
        self.loginButton.setGeometry(QtCore.QRect(230, 410, 181, 61))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.loginButton.setFont(font)
        self.loginButton.setObjectName("loginButton")
        self.userID = QtWidgets.QLineEdit(self.centralwidget)
        self.userID.setGeometry(QtCore.QRect(110, 150, 161, 51))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.userID.setFont(font)
        self.userID.setAlignment(QtCore.Qt.AlignCenter)
        self.userID.setObjectName("userID")
        self.password = QtWidgets.QLineEdit(self.centralwidget)
        self.password.setGeometry(QtCore.QRect(110, 220, 161, 51))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.password.setFont(font)
        self.password.setEchoMode(QtWidgets.QLineEdit.Password)
        self.password.setAlignment(QtCore.Qt.AlignCenter)
        self.password.setObjectName("password")
        MainWindow.setCentralWidget(self.centralwidget)
        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)
        self.loginButton.clicked.connect(self.login)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.label.setText(_translate("MainWindow", "Login Window"))
        self.label_2.setText(_translate("MainWindow", "UserID"))
        self.label_3.setText(_translate("MainWindow", "Password"))
        self.label_4.setText(_translate("MainWindow", "Login As:"))
        self.groupBox.setTitle(_translate("MainWindow", "Login As:"))
        self.doctor.setText(_translate("MainWindow", "doctor"))
        self.affair.setText(_translate("MainWindow", "affair"))

        self.admin.setText(_translate("MainWindow", "admin"))
        self.loginButton.setText(_translate("MainWindow", "Login"))

    def login(self):

        if self.userID.text() == "" or self.password.text() == "":
            QMessageBox.warning(self.window, "Failed Required", "you must fill all fields")
            return
        if not self.userID.text().isnumeric():
            QMessageBox.warning(self.window, "Failed Required", "userID must be numbers only")
            return
        if self.doctor.isChecked():
            self.login = self.database.doctor(self.userID.text(), self.password.text())
            if self.login == "no":
                QtWidgets.QMessageBox.warning(self.window, "Error", "Can't  connect to database")
            elif self.login and self.login is not None:
                print(self.login)
                self.ui = Ui_MainWindow(self.login)
                self.ui.setupUi(self.window)
            else:
                QtWidgets.QMessageBox.warning(self.window, "Error", "Incorrect UserId or password")
        elif self.affair.isChecked():
            self.login = self.database.user(self.userID.text(), self.password.text())
            if self.login == "no":
                QtWidgets.QMessageBox.warning(self.window, "Error", "Can't  connect to database")
            elif self.login and self.login is not None:
                self.window.hide()
                self.ui = studentaffiar.Main(self.login)
                self.ui.show()
            else:
                QtWidgets.QMessageBox.warning(self.window, "Error", "Incorrect UserId or password")
        elif self.admin.isChecked():
            pass
        else:
            QMessageBox.warning(self.window, "Failed Required", "you must fill all fields")
            return


class database:

    def doctor(self, id, password):
        self.connect = connector.connector.connectorClass()
        if self.connect.isConnected():
            cursor = self.connect.execute(f"SELECT * FROM `doctors` WHERE `doctorID`={id}  ")
            arr = cursor.fetchone()
            if arr is not None and arr[4] == password:
                connector.ServerConnection.DownloadImage(arr[-1])
                return arr
            else:
                return False
        else:
            return "no"

    def user(self, id, password):
        self.connect = connector.connector.connectorClass()
        if self.connect.isConnected():
            cursor = self.connect.execute(f"SELECT * FROM `users` WHERE `userID`={id}  ")
            arr = cursor.fetchone()
            if arr is not None and arr[2] == password:
                return arr
            else:
                return False
        else:
            return "no"


if __name__ == "__main__":
    import sys
    import os

    app = QtWidgets.QApplication(sys.argv)
    app.setStyle(QStyleFactory.create(QStyleFactory.keys()[2]))
    app.setPalette(app.style().standardPalette())
    MainWindow = QtWidgets.QMainWindow()
    ui = Login()
    ui.setupUi(MainWindow)
    MainWindow.show()
    app.exec_()
    try:
        os.remove("files/icon.jpg")
    except FileNotFoundError:
        pass
