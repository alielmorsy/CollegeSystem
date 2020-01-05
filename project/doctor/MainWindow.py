import sys

from PyQt5 import QtCore, QtGui, QtWidgets
import doctor
from doctor import ProfileInfo
from doctor.TimeTable import TimeTable
from doctor.SubjectListUi import SubjectListUi


class Ui_MainWindow(object):
    def __init__(self, data):
        self.data = data
        self.doctorName = data[2]

    def setupUi(self, MainWindow):
        self.window = MainWindow
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(640, 480)
        self.centralwidget = QtWidgets.QWidget(self.window)
        self.centralwidget.setObjectName("centralwidget")
        self.icon = QtWidgets.QLabel(self.centralwidget)
        self.icon.setGeometry(QtCore.QRect(10, 10, 131, 141))
        self.icon.setObjectName("icon")
        image = QtGui.QPixmap('./files/icon.jpg')
        self.icon.setPixmap(image)

        self.subjectList = QtWidgets.QPushButton(self.centralwidget)
        self.subjectList.setGeometry(QtCore.QRect(250, 140, 131, 71))
        self.subjectList.setObjectName("pushButton")
        self.label = QtWidgets.QLabel(self.centralwidget)
        self.label.setGeometry(QtCore.QRect(180, 20, 451, 41))
        font = QtGui.QFont()
        font.setPointSize(16)
        self.label.setFont(font)
        self.label.setAlignment(QtCore.Qt.AlignLeading | QtCore.Qt.AlignLeft | QtCore.Qt.AlignVCenter)
        self.label.setObjectName("label")
        self.timerTable = QtWidgets.QPushButton(self.centralwidget)
        self.timerTable.setGeometry(QtCore.QRect(250, 290, 131, 71))
        self.timerTable.setObjectName("pushButton_2")
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtWidgets.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 640, 21))
        self.menubar.setObjectName("menubar")
        self.menuUser = QtWidgets.QMenu(self.menubar)
        self.menuUser.setObjectName("menuUser")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)
        self.actionProfile = QtWidgets.QAction(MainWindow)
        self.actionProfile.setObjectName("actionProfile")
        self.actionLog_Out = QtWidgets.QAction(MainWindow)
        self.actionLog_Out.setObjectName("actionLog_Out")
        self.actionLog_Out.triggered.connect(self.logOut)
        self.actionProfile.triggered.connect(self.profile)
        self.menuUser.addAction(self.actionProfile)
        self.menuUser.addAction(self.actionLog_Out)
        self.menubar.addAction(self.menuUser.menuAction())
        self.subjectList.clicked.connect(self.list)
        self.timerTable.clicked.connect(self.table)
        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def profile(self):
        self.go = ProfileInfo.Ui_MainWindow(self.data)
        self.go.setupUi(self.window)

    def list(self):
        self.subjectListUi = SubjectListUi(self.data)
        self.subjectListUi.setupUi(self.window)

    def table(self):
        self.asd = QtWidgets.QMainWindow()
        self.ui = TimeTable(self.data)
        self.ui.setupUi(self.asd)

        self.asd.show()

    def logOut(self):
        sys.exit(0)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.subjectList.setText(_translate("MainWindow", "SubjectsList"))
        self.label.setText(_translate("MainWindow", f"              Welcome {self.doctorName}"))
        self.timerTable.setText(_translate("MainWindow", "TimerTable"))
        self.menuUser.setTitle(_translate("MainWindow", "User"))
        self.actionProfile.setText(_translate("MainWindow", "Profile Info"))
        self.actionLog_Out.setText(_translate("MainWindow", "Log Out"))
        self.icon.setText(_translate("MainWindow", ""))
