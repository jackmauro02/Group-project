DROP TABLE IF EXISTS DoctorDetails;
DROP TABLE IF EXISTS Prescription;
DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  email varchar(100) NOT NULL,
  phone varchar(11) DEFAULT NULL,
  address varchar(50) DEFAULT NULL,
  password varchar(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY email (email)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO Users VALUES (1,'Jack','Jack@gmail.com','0123456789','Kent','Liverpool');
INSERT INTO Users VALUES (13,'oz','oz@gmail','0123','Kent','123')
INSERT INTO Users VALUES (22,'Bobby','Bobby@gmail.com','012356','London','123');
SELECT * FROM Users;

CREATE TABLE DoctorDetails (
  DoctorID INT PRIMARY KEY 	NOT NULL, 
	Name 	VARCHAR(50)					NOT NULL, 
	Email varchar(100)				NOT NULL, 
	Phone VARCHAR(11)					NOT NULL, 
	Description VARCHAR(500)  NOT NULL
);

INSERT INTO DoctorDetails (DoctorID, Name, Email, Phone, Description) 
VALUES (1, 'Joseph Daniels', 'joseph@doctors.com', '0123456789', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.');
INSERT INTO DoctorDetails (DoctorID, Name, Email, Phone, Description) 
VALUES (2, 'James Fredrikson','james@doctors.com', '0212345678', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.');
INSERT INTO DoctorDetails (DoctorID, Name, Email, Phone, Description) 
VALUES (3, 'Lauren Davies','lauren@doctors.com', '0135678908', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.');

SELECT * FROM DoctorDetails;

CREATE TABLE Prescription (
PrescriptionID INT PRIMARY KEY NOT NULL,
Medicine VARCHAR(50) NOT NULL,
Diagnosis VARCHAR(50) NOT NULL,
Duration VARCHAR(50) NOT NULL
);

INSERT INTO Prescription (PrescriptionID, Medicine, Dosage, Diagnosis, Duration)
VALUES (1, 'Paracetamol 40mg','2 tablets | 4 times a day', 'Headache', '2 Weeks');

INSERT INTO Prescription (PrescriptionID, Medicine, Dosage, Diagnosis, Duration)
VALUES (2,'Ibuprofen 700mg','1 tablet | 8 times a day', 'Sore Back', '2 Months');

INSERT INTO Prescription (PrescriptionID , Medicine , Dosage , Diagnosis , Duration )
VALUES (3,'MedsRus 100mg','1 spoon every 8 hrs', 'Hydrophobia' ,'7 days');


CREATE TABLE Booking (
VisitID INT PRIMARY KEY NOT NULL,
Date DATE NOT NULL,
Time varchar(50) NOT NULL,
Doctor INT NOT NULL REFERENCES Users(id),
Patient INT NOT NULL REFERENCES Users(id),
Prescription INT NOT NULL REFERENCES Prescription(PrescriptionID));

INSERT INTO Booking (VisitID , Date , Time , Doctor , Patient , Prescription )
VALUES (1,'2023-03-15','08:00:00',3,2,1);

INSERT INTO Booking (VisitID , Date , Time , Doctor, Patient , Prescription ) 
VALUES (2,'2023-03-25','09:30:00',1,3,3);

INSERT INTO Booking (VisitID , Date , Time , Doctor , Patient , Prescription )
VALUES (3,'2023-03-19','12:15:00',2,1,1);