CREATE TABLE threadAttachment (
    tid INTEGER NOT NULL,
    name VARCHAR(50),
    mimeContentType VARCHAR(50),
    contents BLOB,
    PRIMARY KEY (tid, name)
);

CREATE TABLE replyAttachment (
    rid INTEGER NOT NULL,
    name VARCHAR(50),
    mimeContentType VARCHAR(50),
    contents BLOB,
    PRIMARY KEY (rid, name)
);