CREATE TABLE APP.USERS
(
    ID INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    NAME VARCHAR(64) NOT NULL,
    ONLINE SMALLINT DEFAULT 1 NOT NULL
);

CREATE TABLE APP.ROOMS
(
    ID INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    NAME VARCHAR(30) NOT NULL
);

CREATE TABLE APP.MESSAGES
(
    ID INT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    MESSAGE VARCHAR(150) NOT NULL,
    USER_ID INT NOT NULL,
    ROOM_ID INT,
    FOREIGN KEY ("USER_ID") REFERENCES USERS("ID"),
    FOREIGN KEY ("ROOM_ID") REFERENCES USERS("ID")
);



