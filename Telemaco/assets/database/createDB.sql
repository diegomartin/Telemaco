CREATE TABLE Trip (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, start_date TEXT, end_date TEXT);
CREATE TABLE Country (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, plug INTEGER, currency INTEGER, languages INTEGER);
CREATE TABLE City (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, country INTEGER, timezone INTEGER);
CREATE TABLE CityVisit(id INTEGER PRIMARY KEY AUTOINCREMENT, trip INTEGER, city, INTEGER, date TEXT);
CREATE TABLE PlaceVisit(id INTEGER PRIMARY KEY AUTOINCREMENT, trip INTEGER, place INTEGER, date TEXT, ordernation INTEGER);
CREATE TABLE Note(id INTEGER PRIMARY KEY AUTOINCREMENT, trip INTEGER, name TEXT, text TEXT);
CREATE TABLE Currency(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, code TEXT, rate DECIMAL);
CREATE TABLE Plug(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT);
CREATE TABLE Language(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, code TEXT);
CREATE TABLE Item(id INTEGER PRIMARY KEY AUTOINCREMENT, place INTEGER, name TEXT, description TEXT);
CREATE TABLE Transport(id INTEGER PRIMARY KEY AUTOINCREMENT, trip INTEGER, origin INTEGER, destination INTEGER, place TEXT, date TEXT, code TEXT, reservation TEXT, type TEXT);
