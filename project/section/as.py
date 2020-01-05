# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'section_ui.ui'
#
# Created by: PyQt5 UI code generator 5.13.0
#
# WARNING! All changes made in this file will be lost!


from PyQt5 import QtCore, QtGui, QtWidgets,QtSvg

import pyqrcode
u=pyqrcode.create('asdw')
u.svg('a.svg')
class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        self.svgWidget = QtSvg.QSvgWidget('a.svg')
        self.svgWidget.setGeometry(50,50,759,668)
        
        MainWindow.resize(804, 587)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.pushButton = QtWidgets.QPushButton(self.centralwidget)
        self.pushButton.setGeometry(QtCore.QRect(310, 100, 131, 71))
        font = QtGui.QFont()
        font.setPointSize(12)
        font.setUnderline(False)
        self.pushButton.setFont(font)
        self.pushButton.setObjectName("pushButton")
        self.pushButton_2 = QtWidgets.QPushButton(self.centralwidget)
        self.pushButton_2.setGeometry(QtCore.QRect(310, 230, 131, 71))
        self.pushButton_2.clicked.connect(self.clicked);
        font = QtGui.QFont()
        font.setPointSize(12)
        self.pushButton_2.setFont(font)
        self.pushButton_2.setObjectName("pushButton_2")
        self.pushButton_3 = QtWidgets.QPushButton(self.centralwidget)
        self.pushButton_3.setGeometry(QtCore.QRect(310, 360, 131, 71))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.pushButton_3.setFont(font)
        self.pushButton_3.setObjectName("pushButton_3")
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtWidgets.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 804, 21))
        self.menubar.setObjectName("menubar")
        self.menuUser = QtWidgets.QMenu(self.menubar)
        self.menuUser.setObjectName("menuUser")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)
        self.actionProfileInfo = QtWidgets.QAction(MainWindow)
        self.actionProfileInfo.setObjectName("actionProfileInfo")
        self.actionLogOut = QtWidgets.QAction(MainWindow)
        self.actionLogOut.setObjectName("actionLogOut")
        self.menuUser.addAction(self.actionProfileInfo)
        self.menuUser.addAction(self.actionLogOut)
        self.menubar.addAction(self.menuUser.menuAction())

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.pushButton.setText(_translate("MainWindow", "Timetable"))
        self.svgWidget.show()
        self.pushButton_2.setText(_translate("MainWindow", "List of sections"))
        self.pushButton_3.setText(_translate("MainWindow", "doctor new data"))
        self.menuUser.setTitle(_translate("MainWindow", "User"))
        self.actionProfileInfo.setText(_translate("MainWindow", "ProfileInfo"))
        self.actionLogOut.setText(_translate("MainWindow", "LogOut"))

    def clicked(self,cliked):
        self.pushButton.setText("asdw")
if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    MainWindow = QtWidgets.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())
