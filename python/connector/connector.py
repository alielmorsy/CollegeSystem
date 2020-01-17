import mysql.connector
from mysql.connector import Error


class connectorClass:
    def __init__(self):
        data = {
            'user': 'root',
            'password': '',
            'host': 'localhost',
            'database': 'educational system',
            'raise_on_warnings': True,
            'autocommit': True,

        }
        self.connection=None
        try:
            self.connection = mysql.connector.connect(**data)
            if self.connection.is_connected():
                self.cursor = self.connection.cursor()
        except Error:
            print("error")

    def execute(self, sql):
        data = self.cursor.execute(sql)
        return self.cursor

    def isConnected(self):
        return self.connection is not None and self.connection.is_connected()
