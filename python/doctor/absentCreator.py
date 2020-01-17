import os
from PyQt5 import QtCore, QtGui, QtWidgets, QtSvg
from PyQt5.QtWidgets import QMessageBox, QWidget
from mysql.connector import Error

from connector.connector import connectorClass

import pyqrcode

from threading import *

from doctor import RecordsList, SubjectListUi


class Ui_Form(object):
    def __init__(self, name, data):
        self.closed = False
        self.data = data
        self.timer = Timer(60, self.tick)
        self.name = name
        self._translate = QtCore.QCoreApplication.translate

    def setupUi(self, Form):
        self.Form = Form
        Form.setObjectName("Form")
        Form.resize(640, 480)
        self.showButton = QtWidgets.QPushButton(Form)
        self.showButton.setGeometry(QtCore.QRect(410, 180, 181, 61))
        self.showButton.setObjectName("showButton")
        self.showButton.setEnabled(False)
        self.createButton = QtWidgets.QPushButton(Form)
        self.createButton.setGeometry(QtCore.QRect(410, 360, 181, 61))
        font = QtGui.QFont()
        font.setPointSize(12)
        font.setBold(True)
        font.setWeight(75)
        self.showButton.setFont(font)
        self.createButton.setFont(font)
        self.createButton.setObjectName("createButton")
        self.codeText = QtWidgets.QLineEdit(Form)
        self.codeText.setGeometry(QtCore.QRect(195, 180, 141, 31))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.codeText.setFont(font)
        self.codeText.setMaxLength(10)
        self.codeText.setObjectName("codeText")
        self.label = QtWidgets.QLabel(Form)
        self.label.setGeometry(QtCore.QRect(30, 170, 81, 51))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label.setFont(font)
        self.label.setObjectName("label")
        self.minutes = QtWidgets.QLCDNumber(Form)
        self.minutes.setGeometry(QtCore.QRect(460, 240, 121, 71))
        self.minutes.setObjectName("minutes")
        self.minutesSpan = QtWidgets.QSpinBox(Form)
        self.minutesSpan.setGeometry(QtCore.QRect(210, 250, 91, 61))
        self.minutesSpan.setObjectName("minutesSpan")
        self.label_2 = QtWidgets.QLabel(Form)
        self.label_2.setGeometry(QtCore.QRect(10, 260, 191, 41))
        font = QtGui.QFont()
        font.setPointSize(12)
        self.label_2.setFont(font)
        self.label_2.setObjectName("label_2")
        self.label_3 = QtWidgets.QLabel(Form)
        self.label_3.setGeometry(QtCore.QRect(130, 10, 421, 71))
        font = QtGui.QFont()
        font.setPointSize(16)
        font.setBold(True)
        font.setWeight(75)
        self.label_3.setFont(font)
        self.label_3.setStyleSheet("color:rgb(255, 33, 36);")
        self.label_3.setAlignment(QtCore.Qt.AlignCenter)
        self.label_3.setWordWrap(False)
        self.label_3.setObjectName("label_3")

        self.back = QtWidgets.QPushButton(Form)
        font = QtGui.QFont()
        font.setPointSize(14)
        font.setBold(False)
        font.setWeight(75)
        self.back.setFont(font)
        self.back.setGeometry(QtCore.QRect(10, 10, 141, 81))
        self.closeButton = QtWidgets.QPushButton(Form)
        self.closeButton.setEnabled(False)
        self.closeButton.setGeometry(QtCore.QRect(200, 360, 181, 61))
        font = QtGui.QFont()
        font.setPointSize(12)
        font.setBold(True)
        font.setWeight(75)
        self.closeButton.setFont(font)
        self.closeButton.setObjectName("closeButton")
        self.list = QtWidgets.QPushButton(Form)
        self.list.setObjectName("list")
        self.list.setGeometry(QtCore.QRect(2, 360, 181, 61))
        self.list.setFont(font)
        self.createButton.clicked.connect(self.CreateClick)
        self.list.clicked.connect(self.List)
        self.retranslateUi(Form)
        self.minutesSpan.valueChanged['int'].connect(self.minutes.display)
        self.closeButton.clicked.connect(self.CloseQr)
        self.showButton.clicked.connect(self.ShowSvg)
        QtCore.QMetaObject.connectSlotsByName(Form)
        self.back.clicked.connect(self.Back)

    def Back(self):
        self.window = QtWidgets.QMainWindow()
        self.BACK = SubjectListUi.SubjectListUi(self.data)
        self.BACK.setupUi(self.window)
        self.Form.hide()
        self.window.show()

    def retranslateUi(self, Form):
        self.minutes.setVisible(False)
        Form.setWindowTitle(self._translate("Form", "Form"))
        self.createButton.setText(self._translate("Form", "Create QrCode"))
        self.label.setText(self._translate("Form", "Code"))
        self.label_2.setText(self._translate("Form", "Number of minutes open"))
        self.label_3.setText(self._translate("Form", "QrCode for absent system Creator"))
        self.closeButton.setText(self._translate("Form", "Close"))
        self.showButton.setText(self._translate("Form", "show qr code"))
        self.list.setText(self._translate("Form", "Get records List"))
        self.back.setText(self._translate("Form", "Return"))
        self.list.setEnabled(False)

    def ShowSvg(self):
        if not self.svgWidget.isVisible():
            self.svgWidget.show()

    def List(self):
        print(self.creator.subjectID)
        self.record = RecordsList.Main(self.creator.subjectID, self.codeText.text())
        self.record.show()

    def CreateClick(self):
        if self.minutesSpan.value() == 0:
            QMessageBox.warning(self.Form, "Warning", "you can't use zero minutes")
            return
        if self.codeText.text() == '':
            QMessageBox.warning(self.Form, "Warning", "you can't let code empty")
        elif self.codeText.text().__contains__(' '):
            QMessageBox.warning(self.Form, "Warning", "you can't use space in code")
        else:
            self.creator = Creator(self.name, self.codeText.text())
            print(self.codeText.text())
            if not self.creator.checkCodeIsExists():
                QMessageBox.warning(self.Form, "Already exists", "that code is already created for another doctor ")
            else:

                if self.creator.create():
                    self.list.setEnabled(True)
                    self.createButton.setEnabled(False)
                    self.createButton.setEnabled(False)
                    self.closeButton.setEnabled(True)
                    self.showButton.setEnabled(True)
                    self.svgWidget = QtSvg.QSvgWidget('files/code.svg')
                    self.svgWidget.setObjectName('svg')
                    self.svgWidget.setStyleSheet("background:#fff")
                    self.close = QtWidgets.QPushButton(self.svgWidget)

                    self.close.setGeometry(10, 10, 181, 63)
                    self.svgWidget.setGeometry(50, 50, 759, 668)
                    self.close.setText(self._translate('svg', 'Close'))
                    self.close.clicked.connect(self.closeSvg)
                    self.svgWidget.show()
                    self.TimerSetup()
                else:
                    QMessageBox.warning(self.Form, "Error", "Can't create qrcode please try again after a minute",
                                        buttons=QMessageBox.Cancel)

    def closeSvg(self):
        self.svgWidget.close()

    def TimerSetup(self):
        if not self.closed:
            self.minutes.setVisible(True)
            self.minutesSpan.setVisible(False)
            self.Thread = Thread(target=self.timer.start)
            self.Thread.start()

    def tick(self):
        if not self.closed:
            self.minutes.display(self.minutes.value() - 1)
            if self.minutes.value() == 0:
                self.CloseQr()
            else:
                self.timer = Timer(60, self.tick)
                self.timer.start()

    def CloseQr(self):
        os.remove("files/code.svg")

        self.closed = True
        self.closeButton.setEnabled(False)
        self.createButton.setEnabled(True)
        self.minutesSpan.setVisible(True)
        self.minutes.setVisible(False)
        thread = Thread(target=self.closeThread)
        thread.start()
        if self.svgWidget.isVisible():
            self.closeSvg()

    def closeThread(self):
        self.creator.close()


class Creator:
    def __init__(self, name, code):

        self.subjectID = None
        self.subjectName = name
        self.level = None
        self.connector = connectorClass()
        self.code = code
        self.getID()
        self.cursor = self.connector.connection.cursor()

    def getID(self):
        data = self.connector.execute(f'SELECT * FROM `courses` WHERE `name`="{self.subjectName}"')
        a = data.fetchone()
        self.subjectID = a[0]
        self.level = a[-2]

    def clear(self):
        self.connector.execute(f"DELETE FROM `codes` WHERE `subjectID` ={self.subjectID}")

    def checkCodeIsExists(self):
        data = self.connector.execute(f'SELECT * FROM `codes` WHERE `code`="{self.code}"')
        list = data.fetchall()
        if list is not None:
            for i in list:
                if i[1] == self.code or i[-2] == self.subjectID:
                    return False
            return True
        else:
            return True

    def CreateCode(self):
        if self.connector.connection.is_connected():
            qr = pyqrcode.create(self.code)
            qr.svg('files/code.svg', scale=12)

    def insertCode(self):
        try:
            if self.connector.connection.is_connected():
                self.connector.cursor.execute(
                    f'INSERT INTO `codes` (`id`, `code`, `subjectID`, `level`) VALUES (NULL, "{self.code}", "{self.subjectID}"," {self.level}")')
                return True
        except Error:
            print("error")
            return False

    def create(self):
        if self.insertCode():
            self.CreateCode()
            return True

    def close(self):
        print("closed")
        if self.connector.connection.is_connected():
            name = str(self.subjectName).replace(" ", "_")
            self.cursor.execute(f"SELECT * FROM `{name}_table` WHERE `done`='0' ")
            list = self.cursor.fetchall()
            for i in list:
                text = f'you didnt attend for 3 time in {self.subjectName} if you dont come for {i[-3] + 1} times more you cant enter the final exam '
                if i[-3] == 2:
                    self.cursor.execute(
                        f" UPDATE `{name}_table` SET `nWarning`={1} WHERE `studentId`={i[1]}")
                    self.cursor.execute(
                        f"INSERT INTO `notification` (`id`, `title`, `sender_id`, `recipient_id`, `unread`, `type`, `message`, `created_at`) VALUES (NULL, 'Absent Warning', '0', '{i[1]}', '1', 'Warning', '{text}', current_timestamp())")
                elif i[-3] == 3:
                    self.cursor.execute(
                        f" UPDATE `{name}_table` SET `nWarning`={2} WHERE  `studentId`={i[1]}")
                elif i[-3] == 4:
                    self.cursor.execute(
                        f" INSERT INTO `students_block_subject_list` (`id`, `subject_id`, `student_id`, `reason`, "
                        f"`time`) VALUES (NULL, '{self.subjectID}', '{i[1]}', \'absent\', current_timestamp())")
                if i[-3] <= 4:
                    self.cursor.execute(
                        f" UPDATE `{name}_table` SET `nAbsent`={i[-3] + 1} WHERE  `studentId`={i[1]}")

            # self.cursor.execute(f"SELECT * FROM `students` WHERE `level` ={self.level}")
            # list2 = self.cursor.fetchall()
            # for i in list2:
            #     if len(list) == 0:
            #         sql = f"INSERT  INTO `absent` (`id`, `subjectId`, `studentId`, `level`, `nAbsent`, `nWarning`, `done`) VALUES (NULL, '{self.subjectID}', '{i[1]}', '{self.level}', '1', '0', '0')"
            #         self.cursor.execute(sql)
            #     else:
            #         for l in list:
            #             if i[1] != l[2]:
            #                 sql = f"INSERT  INTO `absent` (`id`, `subjectId`, `studentId`, `level`, `nAbsent`, `nWarning`, `done`) VALUES (NULL, '{self.subjectID}', '{i[1]}', '{self.level}', '1', '0', '0')"
            #                 self.cursor.execute(sql)

            self.cursor.execute(f" UPDATE `{name}_table` SET `done`=0")
        self.cursor.execute(f"DELETE FROM `codes` WHERE `code`='{self.code}'")


def disconnect(self):
    self.connector.connection.close()
