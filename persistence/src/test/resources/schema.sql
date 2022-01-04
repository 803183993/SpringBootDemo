CREATE TABLE Addresses (
    uprn varchar(255) NOT NULL,
    property_number int,
    main_thoroughfare varchar(255),
    city varchar(255),
    post_code varchar(255),
    PRIMARY KEY (uprn)
);

CREATE TABLE Persons (
    person_id int,
    title varchar(5),
    gender varchar(10),
    last_name varchar(255),
    first_name varchar(255),
    uprn varchar(255),
    PRIMARY KEY (person_id),
    CONSTRAINT fk_uprn FOREIGN KEY (uprn) REFERENCES Addresses(uprn)
);

CREATE TABLE Phones (
    id bigint GENERATED ALWAYS AS IDENTITY,
    person_id int,
    type varchar(10),
    phone_number varchar(255),
    PRIMARY KEY (id),
    CONSTRAINT FK_phonesPersonId FOREIGN KEY (person_id) REFERENCES Persons(person_id)
);