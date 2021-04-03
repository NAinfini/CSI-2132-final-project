/*
    If you are using our university's hosted database
    Choose group_b04_g07 server
    Use this cmd to reach for our schema 'hotel'

    SET search_path To hotel;
*/

/*
    If you are using a local database, run this cmd to create the database
*/
CREATE DATABASE hotel;


/*
    Tables
*/



CREATE TABLE ParentHotel (
    Brand_ID SERIAL,
    Brand_name VARCHAR(50) NOT NULL,
    Phone_numbers INT,
    Number_of_hotels INT,
    Email_address INT,
    Physical_address INT NOT NULL,
    CONSTRAINT hotels_num CHECK (Number_of_hotels > 0),
    CONSTRAINT pk_pHotel PRIMARY KEY (Brand_ID),
    CONSTRAINT fk_phone FOREIGN KEY (Phone_numbers)
        REFERENCES Phone_number (Phone_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT email_cons FOREIGN KEY (Email_address)
        REFERENCES email_address(email_id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT address_cons FOREIGN KEY (physical_address)
        REFERENCES address (address_id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE Hotel (
    Hotel_ID SERIAL,
    Brand_ID INT NOT NULL,
    Star_category INT,
    Phone_numbers INT NOT NULL,
    Number_of_rooms INT, 
    Address INT NOT NULL,
    Email_address INT,
    CONSTRAINT stars CHECK (Star_category >= 1 AND Star_category <= 5),
    CONSTRAINT rooms_num CHECK (Number_of_rooms > 0),
    CONSTRAINT pk_Hotel PRIMARY KEY (Hotel_ID),
    CONSTRAINT fk_brand FOREIGN KEY (Brand_ID)
        REFERENCES ParentHotel (Brand_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_phone FOREIGN KEY (Phone_numbers)
        REFERENCES Phone_number (Phone_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT address_cons FOREIGN KEY (address)
        REFERENCES address (address_id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT email_cons FOREIGN KEY (email_address)
        REFERENCES email_address(email_id) 
        ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE Person(
    Person_ID SERIAL,
    Hotel_ID INT NOT NULL, 
    first_Name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL, 
    address_ID INT NOT NULL,
    SIN INT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    CONSTRAINT pk_person PRIMARY KEY (Person_ID),
    CONSTRAINT fk_hotel FOREIGN KEY (Hotel_ID)
        REFERENCES Hotel (Hotel_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_address FOREIGN KEY (address_ID)
        REFERENCES Address (Address_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE Address(
    Address_ID SERIAL,
    apt_number INT,
    street_number INT,
    street_name VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    province VARCHAR(50),
    country VARCHAR(50) NOT NULL,
    postal_code VARCHAR(50),
    CONSTRAINT pk_address PRIMARY KEY (Address_ID),
);

CREATE TABLE Room(
    Room_number SERIAL,
    Hotel_ID INT NOT NULL,
    Price INT,
    Amenities VARCHAR(50),
    Capacity INT,
    Room_view VARCHAR(50),
    Extendable BOOLEAN,
    CONSTRAINT price_cons CHECK (Price > 0),
    CONSTRAINT capacity_cons CHECK (Capacity > 0),
    CONSTRAINT pk_room PRIMARY KEY (Room_number),
    CONSTRAINT fk_hotel FOREIGN KEY (Hotel_ID)
        REFERENCES Hotel (Hotel_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    
);

CREATE TABLE Customer(
    Person_ID INT NOT NULL,
    payment_info VARCHAR(50),
    CONSTRAINT fk_Person FOREIGN KEY (Person_ID)
        REFERENCES Person (Person_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE Employee(
    Person_ID INT NOT NULL,
    Salary INT,
    Role VARCHAR(50),
    Registration_date DATE,
    CONSTRAINT salary_cons CHECK (Salary > 0),
    CONSTRAINT fk_Person FOREIGN KEY (Person_ID)
        REFERENCES Person (Person_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE Booking(
    Booking_ID SERIAL,
    Hotel_ID INT NOT NULL,
    Room_number INT NOT NULL,
    Person_ID INT NOT NULL,
    Date check_in NOT NULL,
    Date check_out NOT NULL,
    CONSTRAINT pk_booking PRIMARY KEY (Booking_ID),
    CONSTRAINT fk_Person FOREIGN KEY (Person_ID)
        REFERENCES Person (Person_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_hotel FOREIGN KEY (Hotel_ID)
        REFERENCES Hotel (Hotel_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE Renting(
    Renting_ID SERIAL, 
    payment_ID INT,
    CONSTRAINT pk_renting PRIMARY KEY (Renting_ID)
);

CREATE TABLE Archive(
    Archive_ID SERIAL,
    Hotel_ID INT NOT NULL,
    Booking_ID INT NOT NULL,
    Renting_ID INT NOT NULL,
    CONSTRAINT pk_archive PRIMARY KEY (Archive_ID),
    CONSTRAINT fk_booking FOREIGN KEY (Booking_ID)
        REFERENCES Booking (Booking_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_hotel FOREIGN KEY (Hotel_ID)
        REFERENCES Hotel (Hotel_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_renting FOREIGN KEY (Renting_ID)
        REFERENCES Renting (Renting_ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
);

CREATE TABLE Email_adderss(
    Email_ID SERIAL,
    email_address VARCHAR(50),
    CONSTRAINT pk_email PRIMARY KEY (Email_ID),
);

CREATE TABLE Phone_number(
    Phone_ID SERIAL,
    phone_number INT NOT NULL,
    CONSTRAINT pk_phone PRIMARY KEY (Phone_ID),
);