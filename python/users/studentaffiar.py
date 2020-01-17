from PyQt5.QtGui import *
from PyQt5.QtWidgets import *
from PyQt5.QtCore import *
from PyQt5.uic import loadUiType

from connector import connector
from users import AddStudent

MainUI, _ = loadUiType("users/student.ui")


class Main(QMainWindow, MainUI):
    def __init__(self, data, parent=None):
        super(Main, self).__init__(parent)
        self.database = Database(id)
        self.data = data
        QMainWindow.__init__(self)
        self.setupUi(self)
        self.student.clicked.connect(self.tudents)

    def tudents(self):
        print("clicked")
        self.aadd = AddStudent.Main()
        self.aadd.show()


class Database:
    def __init__(self, id):
        self.id = id
