from PyQt5.QtGui import *
from PyQt5.QtWidgets import *
from PyQt5.QtCore import *
from PyQt5.uic import loadUiType

from connector import connector
from users import AddStudent

MainUI, _ = loadUiType("users/AddStudent.ui")


class Main(QMainWindow, MainUI):
    def __init__(self, parent=None):
        super(Main, self).__init__(parent)
        self.database = Database()
        QMainWindow.__init__(self)
        self.setupUi(self)

        self.buttons()

    def buttons(self):
        self.add.clicked.connect(self.adds)

    def adds(self):
        self.database = Database()
        self.database.createResult()
        self.database.insert(self.userId.text(), self.name.text(), self.password.text(), self.level.text())
        QMessageBox.information(self, "DOne", "User add Sucess")


class Database:
    def __init__(self):
        self.connect = connector.connectorClass()
        self.cursot = self.connect.cursor

    def createResult(self):
        self.cursot.execute(f"INSERT into `result` (`id`,`subject1`) VALUES (NULL,5)")

    def insert(self, userID, name, password, level):
        self.cursot.execute(
            f"INSERT into `students` (`id`,`userID`,`name`,`age`,`gpa`,`resultID`,`level`,`password`,`imageName`) VALUES (NULL,{userID},'{name}',18,0,1,{level},'{password}','asdw')")



