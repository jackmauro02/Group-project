DROP TABLE IF EXISTS Visit CASCADE;
DROP TABLE IF EXISTS DoctorDetails CASCADE;
DROP TABLE IF EXISTS Prescription CASCADE;
DROP TABLE IF EXISTS user_activity_logs CASCADE;
DROP TABLE IF EXISTS Users CASCADE;

CREATE TABLE Users (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(200) NOT NULL,
  email varchar(200) NOT NULL,
  phone varchar(200) DEFAULT NULL,
  address varchar(200) DEFAULT NULL,
  password varchar(200) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY email (email)
);

INSERT INTO Users VALUES (1,'Jack','Jack@gmail.com','0123456789','Kent','Liverpool');
INSERT INTO Users VALUES (2,'oz','oz@gmail','0123','Kent','123');
INSERT INTO Users VALUES (3,'Bobby','Bobby@gmail.com','012356','London','123');

CREATE TABLE DoctorDetails (
  	DoctorID		INT 			PRIMARY KEY 	NOT NULL AUTO_INCREMENT, 
    Photo			VARCHAR(255)					NOT NULL,
	Name 			VARCHAR(25)					 	NOT NULL, 
	Email 			varchar(255)					NOT NULL, 
	Phone 			VARCHAR(11)					 	NOT NULL, 
	Description 	VARCHAR(500)					 NOT NULL
);

INSERT INTO DoctorDetails (DoctorID, Photo, Name, Email, Phone, Description) 
VALUES (1, 'lib/doctor.jpg', 'Joseph Daniels', 'joseph@doctors.com', '0123456789', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.');
INSERT INTO DoctorDetails (DoctorID, Photo, Name, Email, Phone, Description) 
VALUES (2, 'lib/doctor2.jpg', 'James Fredrikson','james@doctors.com', '0212345678', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.');
INSERT INTO DoctorDetails (DoctorID, Photo, Name, Email, Phone, Description) 
VALUES (3, 'lib/Doctor3.jpg', 'Lauren Davies','lauren@doctors.com', '0135678908', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.');

CREATE TABLE Prescription (
  	PrescriptionID		INT 			PRIMARY KEY 	NOT NULL AUTO_INCREMENT, 
	Medicine 			VARCHAR(255), 
	Dosage 				varchar(150), 
	Diagnosis 			VARCHAR(150), 
	Duration		 	VARCHAR(25)
);

INSERT INTO Prescription (PrescriptionID, Medicine, Dosage, Diagnosis, Duration) 
VALUES (1, 'Paracetamol 40mg','2 tablets | 4 times a day', 'Headache', '2 Weeks');
INSERT INTO Prescription (PrescriptionID, Medicine, Dosage, Diagnosis, Duration) 
VALUES (2, 'Ibuprofen 700mg','1 tablet | 8 times a day', 'Sore Back', '2 Months');
INSERT INTO Prescription (PrescriptionID, Medicine, Dosage, Diagnosis, Duration) 
VALUES (3, 'MedsRus 100mg','1 spoon every 8 hrs', 'Hydrophobia', '7 days');
INSERT INTO Prescription (PrescriptionID, Medicine, Dosage, Diagnosis, Duration) 
VALUES (4, 'Paracetamol 400mg','2 tablets | 4 times a day', 'Anxiety', '2 Weeks');
INSERT INTO Prescription (PrescriptionID, Medicine, Dosage, Diagnosis, Duration) 
VALUES (5, 'Ibuprofen 70mg','1 tablet | 8 times a day', 'Depression', '2 Months');
INSERT INTO Prescription (PrescriptionID, Medicine, Dosage, Diagnosis, Duration) 
VALUES (6, 'MedsRus 10mg','1 spoon every 8 hrs', 'Pneumonia', '7 days');


CREATE TABLE Visit (
  	VisitID		INT 			PRIMARY KEY 	NOT NULL AUTO_INCREMENT, 
	Date 				DATE						 	NOT NULL, 
	Time 				varchar(150)					NOT NULL, 
	Doctor				INT								NOT NULL,
	FOREIGN KEY (Doctor) REFERENCES DoctorDetails (DoctorID),
	Patient				INT								NOT NULL,
	FOREIGN KEY (Patient) REFERENCES Users (id),
	Prescription		INT,
	FOREIGN KEY (Prescription) REFERENCES Prescription (PrescriptionID)
);

INSERT INTO Visit (VisitID, Date, Time, Doctor, Patient, Prescription) 
VALUES (1, '2023-03-15', '08:00:00', 3, 1, 1);
INSERT INTO Visit (VisitID, Date, Time, Doctor, Patient, Prescription) 
VALUES (2, '2023-03-25', '09:30:00', 1, 2, 2);
INSERT INTO Visit (VisitID, Date, Time, Doctor, Patient, Prescription) 
VALUES (3, '2023-03-19', '12:15:00', 2, 3, 3);
INSERT INTO Visit (VisitID, Date, Time, Doctor, Patient, Prescription) 
VALUES (4, '2023-04-25', '10:30:00', 1, 3, 4);
INSERT INTO Visit (VisitID, Date, Time, Doctor, Patient, Prescription) 
VALUES (5, '2023-04-25', '10:30:00', 1, 3, 5);
INSERT INTO Visit (VisitID, Date, Time, Doctor, Patient, Prescription) 
VALUES (6, '2023-04-25', '10:30:00', 1, 3, 6);

CREATE TABLE user_activity_logs (
  log_id INT NOT NULL AUTO_INCREMENT,
  activity_datetime DATETIME NOT NULL,
  activity_description VARCHAR(255) NOT NULL,
  userID	INT	NOT NULL,
  FOREIGN KEY (userID) REFERENCES Users (id),
  PRIMARY KEY (log_id)
);
