import sys
from threading import Thread, Timer

from PyQt5.QtGui import *
from PyQt5.QtWidgets import *
from PyQt5.QtCore import *
from PyQt5.uic import loadUiType

from connector import connector

MainUI, _ = loadUiType("doctor/ui/RecordsList.ui")


class Main(QMainWindow, MainUI):
    def __init__(self, id, code, parent=None):
        super(Main, self).__init__(parent)
        self.database = Database(id)
        self.code = code
        QMainWindow.__init__(self)
        self.setupUi(self)
        self.setupThread()

    def setupThread(self):
        timer = Timer(1, self.List)
        timer.start()

    def List(self):
        arr = self.database.getList()
        if not self.database.isClosed(self.code):
            for i in arr:
                self.list.clear()
                item = QListWidgetItem()
                item.setText(self.database.getStudentName(i[2]))

                self.list.addItem(item)
            timer = Timer(3, self.List)
            timer.start()


class Database:
    def __init__(self, id):
        self.id = id
        self.connect = connector.connectorClass()

    def getList(self):
        if self.connect.isConnected():
            self.da = self.connect.execute(f"SELECT * FROM `absent` WHERE `subjectId`={self.id} AND `done`=1")
            return self.da.fetchall()
        else:
            print("not connected")

    def getStudentName(self, id):
        if self.connect.isConnected():
            cursor = self.connect.execute(f"SELECT * FROM `students` WHERE `userID`={id}")
            return cursor.fetchone()[2]

    def isClosed(self, code):
        if self.connect.isConnected():
            cursor = self.connect.execute(f"SELECT * FROM `codes` WHERE `code`='{code}'")
            fectch=cursor.fetchone()
            if fectch is not None:
                if len(fectch) == 0:
                    return True
