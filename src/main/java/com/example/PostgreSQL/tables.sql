
-- Tables
CREATE TABLE Users (
    username TEXT,
    status INT NOT NULL,
    PRIMARY KEY (username)
);
CREATE TABLE Rooms (
    UserID TEXT REFERENCES Users,
    RoomName TEXT,
    PRIMARY KEY(UserID, RoomName)
);
CREATE TABLE Message (
    MsgUser TEXT REFERENCES Users,
    Msg TEXT NOT NULL,
    TimeMsg TIMESTAMP,
    RoomName TEXT NOT NULL
);
CREATE TABLE MsgImage (
	MsgUser TEXT REFERENCES Users,
    timeMsg TIMESTAMP NOT NULL,
    ImageData BYTEA NOT NULL,
	RoomName TEXT NOT NULL
);

