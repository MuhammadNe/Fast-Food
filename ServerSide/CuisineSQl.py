#!/usr/bin/python
# -*- coding: utf-8 -*-

import MySQLdb as mdb

con = mdb.connect('localhost', 'testuser', 'test623', 'testdb');

with con:
    
    cur = con.cursor()
    cur.execute("DROP TABLE IF EXISTS Restaurants")
    cur.execute("CREATE TABLE Restaurants(Id INT PRIMARY KEY AUTO_INCREMENT, \
                 Name VARCHAR(25),\
				 Address VARCHAR(90),\
				 Latitude double,\
				 Longitude double)")
    cur.execute("INSERT INTO Restaurants(Name,Address,Latitude,Longitude) VALUES('Jack London')")
    
	rows = cur.fetchall()
	