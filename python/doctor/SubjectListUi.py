import sys

from PyQt5 import QtCore, QtGui, QtWidgets
import doctor
import doctor.Upload
from connector.connector import connectorClass
from doctor import StudentList
from doctor.absentCreator import Ui_Form


class SubjectListUi(object):
    def __init__(self, data):
        self.data = data
        self.database = Database(data)

    def setupUi(self, MainWindow):
        self.window = MainWindow
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(653, 566)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.subjectList = QtWidgets.QListWidget(self.centralwidget)
        self.subjectList.setGeometry(QtCore.QRect(10, 80, 191, 341))
        self.subjectList.setObjectName("subjectList")
        self.upload = QtWidgets.QPushButton(self.centralwidget)
        self.upload.setGeometry(QtCore.QRect(330, 250, 201, 91))
        font = QtGui.QFont()
        font.setPointSize(12)
        font.setBold(True)
        font.setWeight(75)
        self.upload.setFont(font)
        self.upload.setObjectName("upload")

        self.absent = QtWidgets.QPushButton(self.centralwidget)
        self.absent.setGeometry(QtCore.QRect(330, 110, 201, 91))
        font = QtGui.QFont()
        font.setPointSize(12)
        font.setBold(True)
        font.setWeight(75)
        self.absent.setFont(font)
        self.absent.setObjectName("absent")
        self.studentList = QtWidgets.QPushButton(self.centralwidget)
        self.studentList.setGeometry(QtCore.QRect(330, 390, 201, 91))
        font = QtGui.QFont()
        font.setPointSize(12)
        font.setBold(True)
        font.setWeight(75)
        self.studentList.setFont(font)
        self.studentList.setObjectName("studentList")
        self.Return = QtWidgets.QPushButton(self.centralwidget)
        self.Return.setGeometry(QtCore.QRect(20, 10, 91, 51))
        self.Return.setObjectName("Return")
        self.line = QtWidgets.QFrame(self.centralwidget)
        self.line.setGeometry(QtCore.QRect(240, 30, 20, 491))
        self.line.setFrameShape(QtWidgets.QFrame.VLine)
        self.line.setFrameShadow(QtWidgets.QFrame.Sunken)
        self.line.setObjectName("line")
        MainWindow.setCentralWidget(self.centralwidget)
        self.Return.clicked.connect(self.RETRUN)
        self.upload.clicked.connect(self.uploadFile)
        self.absent.clicked.connect(self.Absent)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "SubjectList"))
        self.upload.setText(_translate("MainWindow", "Upload File to students"))
        self.absent.setText(_translate("MainWindow", "Take Absent"))

        self.studentList.setText(_translate("MainWindow", "List Of Students"))
        self.Return.setText(_translate("MainWindow", "Return"))
        self.studentList.clicked.connect(self.StudentList)
        self.getList()
    def StudentList(self):
        name = self.checked()
        if name is not None:
            self.sstudentList=StudentList.Main(name,self.data)
            self.sstudentList.setup(self.window,self)


    def Absent(self):
        name = self.checked()
        if name is not None:
            self.Wwindow = QtWidgets.QMainWindow()
            self.Window = Ui_Form(name, self.data)
            self.Window.setupUi(self.Wwindow)
            self.Wwindow.show()
            self.window.hide()

    def checkItemSelected(self):

        data = self.subjectList.selectedItems()

        if data is not None and len(data) > 0:
            if data[0].text().__contains__("Level"):
                return "null"
            else:
                return data[0].text()

    def uploadFile(self):
        done = self.checked()
        if done is not None:
            self.main = QtWidgets.QMainWindow()
            self.uploadUI = doctor.Upload.Upload(done, self.data)
            self.uploadUI.setupUi(self.main)
            self.window.hide()
            self.main.show()

    def checked(self):
        name = self.checkItemSelected()

        if name == "null":
            QtWidgets.QMessageBox.warning(self.window, "Warning", "you can't choose this")

        elif name is not None:

            return name
        else:
            QtWidgets.QMessageBox.warning(self.window, "Warning", "you must choose one of the list")

    def RETRUN(self):
        self.ui = doctor.MainWindow.Ui_MainWindow(self.data)
        self.ui.setupUi(self.window)

    def getList(self):
        list = self.database.getList()
        self.term = self.database.getTerm()
        self.font = QtGui.QFont()
        self.font.setBold(True)
        self.font.setPixelSize(16)

        self.Sfont = QtGui.QFont()
        self.Sfont.setPixelSize(14)
        item = QtWidgets.QListWidgetItem()
        item.setFont(self.font)
        item.setText("Level One")

        self.subjectList.addItem(item)
        for i in list:
            if i[-2] == (1 + self.term):
                courseName = i[1]
                item = QtWidgets.QListWidgetItem()
                item.setText(courseName)
                item.setFont(self.Sfont)
                self.subjectList.addItem(item)

        item = QtWidgets.QListWidgetItem()
        item.setText("Level Two")
        item.setFont(self.font)
        self.subjectList.addItem(item)
        for i in list:
            if i[-2] == (2 + self.term):
                courseName = i[1]
                item = QtWidgets.QListWidgetItem()
                item.setText(courseName)
                item.setFont(self.Sfont)
                self.subjectList.addItem(item)
        itema = QtWidgets.QListWidgetItem()
        itema.setText("Level Three")
        itema.setFont(self.font)
        self.subjectList.addItem(itema)
        for i in list:
            if i[-2] == (3 + self.term):
                courseName = i[1]
                item.setFont(self.Sfont)
                item = QtWidgets.QListWidgetItem()
                item.setText(courseName)
                self.subjectList.addItem(item)

        item = QtWidgets.QListWidgetItem()
        item.setText("Level Four")
        item.setFont(self.font)
        self.subjectList.addItem(item)
        for i in list:
            if i[-2] == (4 + self.term):
                courseName = i[1]
                item = QtWidgets.QListWidgetItem()
                item.setText(courseName)
                self.subjectList.addItem(item)


class Database:
    def __init__(self, data):
        self.connection = connectorClass()
        self.data = data

    def getList(self):
        if self.connection.isConnected():
            self.cursor = self.connection.execute(
                f"SELECT * FROM `courses` WHERE `doctor_id` ={self.data[0]} ORDER BY `level` ASC"
            )
            return self.cursor.fetchall()
        else:
            return False

    def getTerm(self):
        if self.connection.isConnected():
            self.cursor = self.connection.execute(
                f"SELECT * FROM `students_action`  LIMIT 1"
            )
            return self.cursor.fetchone()[-1]
