from threading import Timer

from PyQt5 import QtGui, QtCore
from PyQt5.QtGui import *
from PyQt5.QtWidgets import *
from PyQt5.QtCore import *
from PyQt5.uic import loadUiType

from connector import connector

MainUI, _ = loadUiType("doctor/ui/StudentList.ui")


class Main(QMainWindow, MainUI):

    def __init__(self, name, data, parent=None):
        super(Main, self).__init__(parent)
        self.messaga = QMessageBox()
        self.name = name
        self.data = data
        self.database = Database(self.name)
        self.loading = False
        self.timer = Timer(1, function=self.tick)

    def tick(self):
        QTableWidget.blockSignals(self.table, False)
        self.timer = Timer(1, function=self.tick)

    def setup(self, window, object):
        self.window = window
        self.object = object
        self.setupUi(window)
        self.buttons()
        self.getList(1)

    def buttons(self):

        self.back.clicked.connect(self.Back)
        self.section.valueChanged['int'].connect(self.getList)

    def Back(self):
        self.object.setupUi(self.window)

    def getList(self, value):
        table = QTableWidget()
        self.table.blockSignals(True)
        for i in range(25):
            for j in range(5):
                try:
                    self.table.item(i, j).setText("")
                except:
                    pass

        if self.database.connect.isConnected():

            self.messaga.setText("Please Wait")
            self.messaga.addButton(self.messaga.NoButton)
            self.messaga.show()
            list = self.database.getList(value)
            count = 0
            if list is None or list == "Empty":
                QMessageBox.information(self.window, "Credit Hours", "Credit Hours Registration doesn't open yet")
                self.Back()
                self.messaga.close()
                return

            if type(list) != "<class 'list'>":

                for i in list:
                    item = self.table.item(count, 0)

                    item.setText(i[2])
                    item.setFlags(QtCore.Qt.ItemIsSelectable | QtCore.Qt.ItemIsDragEnabled | QtCore.Qt.ItemIsEnabled)
                    item = self.table.item(count, 1)
                    item.setFlags(QtCore.Qt.ItemIsSelectable | QtCore.Qt.ItemIsDragEnabled | QtCore.Qt.ItemIsEnabled)
                    item.setText(str(i[1]))
                    absent = self.database.getAbsentNo(i[-1])
                    if absent is not None:
                        item = self.table.item(count, 3)
                        item.setFlags(
                            QtCore.Qt.ItemIsSelectable | QtCore.Qt.ItemIsDragEnabled | QtCore.Qt.ItemIsEnabled)
                        item.setText(str(absent))
                    else:
                        print("error here")
                        QMessageBox.warning(self.window, "Connection Error", "Can't connect to server")
                        self.window.setEnabled(False)
                        self.getList(value)
                        return
                    if i[-2] != 0:
                        item = QTableWidgetItem()
                        item.setText(str(i[-2]))
                        self.table.setItem(count, 2, item)
                    if absent == 5:
                        self.table.item(count, 4).setCheckState(Qt.Checked)
                    count += 1
                self.messaga.close()
                self.table.itemChanged.connect(self.lo)
                self.table.blockSignals(False)
        else:
            QMessageBox.warning(self.window, "Connection Error", "Can't connect to server")
            self.database.retryConnect()

    def lo(self, item: QTableWidgetItem):

        if item.column() == 2:
            id = self.table.item(item.row(), 1).text()

            if id and item.text().isdecimal():
                QTableWidget.blockSignals(self.table, True)
                try:
                    self.timer.start()
                    if 0 <= int(item.text()) <= 15:
                        self.database.updateMidTerm(id, item.text())
                    else:
                        item.setText("15")
                        QMessageBox.information(self.window, "Warning",
                                                "Please check The MidTerm Degree ")
                except RuntimeError:
                    QTableWidget.blockSignals(self.table, True)
                    pass
            else:
                item.setText("")
                QMessageBox.information(self.window, "Warning",
                                        "Please check The MidTerm Degree or check is this field "
                                        "is not None")


class Database:
    def __init__(self, name):
        self.name = name
        self.connect = connector.connectorClass()
        if self.connect.isConnected():
            self.id = self.getCourseID(name)

    def getCourseID(self, name):
        c = self.connect.execute(f"SELECT `id` FROM `courses` WHERE `name` ='{name}'")
        return c.fetchone()[0]

    def retryConnect(self):
        self.connect = connector.connectorClass()
        if self.connect.isConnected():
            self.id = self.getCourseID(self.name)
            return True

    def getList(self, section):
        if self.connect.isConnected():
            try:
                return self.connect.execute(f"SELECT * FROM `{self.name}_table` WHERE `section`={section}").fetchall()
            except:
                return "Empty"

    def getAbsentNo(self, id):
        if self.connect.isConnected():
            return \
                self.connect.execute(
                    f"SELECT `nAbsent` FROM `absent` WHERE `id`={id} LIMIT 1").fetchone()[0]

    def getName(self, id):
        if self.connect.isConnected():
            return self.connect.execute(f"SELECT `name` FROM `students` WHERE `userID`={id}").fetchone()[0]

    def updateMidTerm(self, id, value):
        if self.connect.isConnected():
            input = f"UPDATE `{self.name}_table` SET `midTerm` ={value} WHERE `userId`={id}"
            print(input)
            self.connect.execute(input)

    # def getMidTerm(self, id):
    #     if self.connect.isConnected():
    #         data = \
    #             self.connect.execute(f"SELECT `midTerm` FROM `{self.id}_table` WHERE `studentID`={id} LIMIT 1")
    #         fetched = data.fetchone()
    #         if fetched is None:
    #             self.connect.execute(f"INSERT INTO `{self.id}_table`(`id`,`studentID`,`midTerm`) VALUES (NULL,{id},0)")
