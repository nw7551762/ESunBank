CREATE DATABASE esunDB;

use esunDB;

CREATE TABLE member (
    userid int not null identity(1,1) primary key,
    username VARCHAR(255) NOT NULL UNIQUE,
    email NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    salt NVARCHAR(255) NOT NULL,
    coverimage VARBINARY(MAX),
    biography NVARCHAR(MAX)
);

CREATE TABLE Post (
  postid int not null identity(1,1) primary key,
  userid INT,
  content TEXT,
  img VARBINARY(MAX),
  createdDate DATETIME,
  CONSTRAINT fk_userid FOREIGN KEY (userid) REFERENCES member(userid)
);


CREATE TABLE Comment (
    commentid int not null identity(1,1) primary key,
    userid INT,
    postid INT,
    content NVARCHAR(255),
    createdat DATETIME,
    FOREIGN KEY (UserID) REFERENCES member(userid),
    FOREIGN KEY (postid) REFERENCES post(postid)
);



