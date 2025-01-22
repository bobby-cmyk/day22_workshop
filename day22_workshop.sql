CREATE DATABASE day22_workshop;

USE day22_workshop;

# Creating user

CREATE USER 'tommy'@'%'
	IDENTIFIED BY 'pass1234';

GRANT ALL PRIVILEGES ON day22_workshop.*
	to 'tommy'@'%';

FLUSH PRIVILEGES;

CREATE TABLE rsvp(
	id INT not null auto_increment,
	name VARCHAR(100),
	email VARCHAR(256),
	phone VARCHAR(256),
	confirmation_date DATE,
	comments TEXT,
	
	CONSTRAINT pk_rsvp_id PRIMARY KEY (id)
);

INSERT INTO rsvp (name, email, phone, confirmation_date, comments) 
	VALUES ('bobby', 'bobby@gmail.com', '98765432', '2025-01-21', 'Will be bringing Polly along!');

INSERT INTO rsvp (name, email, phone, confirmation_date, comments) 
	VALUES ('pauline', 'pauline@gmail.com', '98765432', '2025-01-19', 'Will be 30mins late for the event due to work');

INSERT INTO rsvp (name, email, phone, confirmation_date, comments) 
	VALUES ('charlie', 'charlie@gmail.com', '98765432', '2025-01-17', 'look forward!');

SELECT * FROM rsvp;

SELECT * FROM rsvp;

UPDATE rsvp 
    SET name = 'bobbysalleh', 
        email = 'bobby@gmail.com', 
        phone = '98765432', 
        confirmation_date = '2025-01-21', 
        comments = 'Will be bringing Polly along!'  
WHERE id = 1;

INSERT INTO rsvp (name, email, phone, confirmation_date, comments) 
	VALUES (?, ?, ?, ?, ?);
