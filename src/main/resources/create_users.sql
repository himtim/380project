CREATE TABLE users (
    uid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    banning VARCHAR(1) NOT NULL,
    role VARCHAR(50) NOT NULL,
    voted VARCHAR(1) NOT NULL,
    PRIMARY KEY (uid)
);

INSERT INTO users(username,password,banning,role,voted) VALUES ('keith', 'keithpw', 'N', 'ROLE_ADMIN', 'N');

INSERT INTO users(username,password,banning,role,voted) VALUES ('andrew', 'andrewpw', 'N', 'ROLE_ADMIN', 'N');

INSERT INTO users(username,password,banning,role,voted) VALUES ('maria', 'mariapw', 'N', 'ROLE_USER', 'N');

INSERT INTO users(username,password,banning,role,voted) VALUES ('oliver', 'oliverpw', 'N', 'ROLE_USER', 'N');