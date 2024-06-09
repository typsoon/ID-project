-------------------------------------------------TWORZENIE TABEL------------------------------------------------------
CREATE TABLE Clans
(
    clan_ID      SERIAL PRIMARY KEY,
    ranking_base integer
);

create table ClanWars
(
    clan_war_ID serial,
    clan1_ID    integer references Clans not null,
    clan2_ID    integer references Clans not null,
    date_from   timestamp   default  current_timestamp not null,
    outcome     boolean,
    primary key (clan_war_ID)
);

ALTER TABLE ClanWars
ADD CONSTRAINT check_clan_ids
CHECK (clan1_ID != clan2_ID);

-- ALTER TABLE ClanWars
-- ADD CONSTRAINT unique_active_war_for_clan
-- UNIQUE (clan1_ID)
-- WHERE outcome IS NULL;
--
-- ALTER TABLE ClanWars
-- ADD CONSTRAINT unique_active_war_for_clan2
-- UNIQUE (clan2_ID)
-- WHERE outcome IS NULL;

CREATE TABLE Players
(
    player_ID     SERIAL PRIMARY KEY,
    password_hash NUMERIC(13) not null,
    login         VARCHAR(40) not null unique,
    ranking_base  integer
);

create table ClanChat
(
    sent_date timestamp DEFAULT CURRENT_TIMESTAMP,
    clan_ID   integer references Clans,
    sender_ID integer references Players,
    msg_text  VARCHAR(300) not null,
    primary key (sent_date, clan_ID, sender_ID)
);

create table Duels
(
    duel_ID   serial,
    sender    integer references Players not null,
    receiver  integer references Players not null,
    date_from timestamp  default CURRENT_TIMESTAMP not null,
    date_to timestamp,
    outcome   boolean,
    primary key (duel_ID),
    CHECK ( AGE(COALESCE(date_to, NOW()), date_from) < interval '10 minutes')
);

ALTER TABLE Duels
ADD CONSTRAINT check_players
CHECK (sender != receiver);

create table ArchivedDuels AS SELECT * FROM duels
WHERE duel_ID IS NULL;

create table WarDuels
(
    duel_ID     integer references Duels,
    clan_war_ID integer references ClanWars,
    primary key (duel_ID, clan_war_ID)
);

create table ArchivedWarDuels AS SELECT * FROM WarDuels
WHERE duel_ID IS NULL;

create table Applications
(
    clan_ID   integer references Clans,
    player_ID integer references Players,
    date_from timestamp default CURRENT_TIMESTAMP ,
    date_to   timestamp CHECK (date_from < date_to AND AGE(date_to, date_from) < INTERVAL '2 weeks'),
    primary key (date_from,clan_ID,player_ID)
);

create table PlayerClan
(
    date_from  timestamp default CURRENT_TIMESTAMP ,
    clan_ID    integer references Clans,
    player_ID  integer references Players,
    date_to    timestamp NULL,
    who_kicked integer references Players,
    who_accepted integer references Players,
    primary key (date_from, clan_ID, player_ID)
);

CREATE TABLE FriendsChat
(
    msg_text    varchar(300) not null,
    sent_date   timestamp DEFAULT CURRENT_TIMESTAMP,
    sender_ID   INTEGER REFERENCES Players (player_ID),
    receiver_ID INTEGER REFERENCES Players (player_ID),
    PRIMARY KEY (sent_date, sender_ID, receiver_ID)
);

CREATE TABLE Friends
(
    player1_ID INTEGER REFERENCES Players (player_ID),
    player2_ID INTEGER REFERENCES Players (player_ID),
    date_from  timestamp default CURRENT_TIMESTAMP ,
    date_to    timestamp CHECK (date_from < date_to),
    PRIMARY KEY (player1_ID, player2_ID, date_from),
    CHECK ( player1_ID != player2_ID )
);

CREATE TABLE FriendsInvites
(
    player1_ID INTEGER REFERENCES Players (player_ID),
    player2_ID INTEGER REFERENCES Players (player_ID),
    date_from  timestamp default CURRENT_TIMESTAMP ,
    date_to    timestamp CHECK (date_from < date_to AND AGE(date_to, date_from) < INTERVAL '2 weeks'
) ,
    PRIMARY KEY (player1_ID, player2_ID, date_from)
);

CREATE TABLE PlayerNickname
(
    player_ID INTEGER REFERENCES Players (player_ID),
    date_from timestamp default CURRENT_TIMESTAMP ,
    nickname  varchar(40) not null,
    PRIMARY KEY (player_ID, date_from)
);

CREATE TABLE ClanName
(
    clan_ID   INTEGER REFERENCES Clans,
    date_from timestamp default CURRENT_TIMESTAMP ,
    cl_name   varchar(40) not null,
    PRIMARY KEY (clan_ID, date_from)
);

CREATE TABLE Logos
(
    logo_ID       SERIAL PRIMARY KEY,
    image_address VARCHAR not null
);

CREATE TABLE ClanLogos
(
    clan_ID   INTEGER REFERENCES Clans,
    date_from timestamp default CURRENT_TIMESTAMP ,
    logo_ID   integer REFERENCES Logos not null,
    primary key (clan_ID, date_from)
);
CREATE TABLE Roles
(
    rank_ID   SERIAL PRIMARY KEY,
    rank_name varchar(20) unique not null
);
CREATE TABLE PlayerRole
(
    date_from timestamp default CURRENT_TIMESTAMP ,
    player_ID INTEGER REFERENCES Players,
    rank_ID   integer REFERENCES Roles not null,
    primary key (player_ID, date_from)
);

CREATE TABLE Tournaments
(
    matchup_id serial,
    tournament_id INTEGER,
    duel_id integer unique references Duels,
    left_child integer unique references Duels,
    right_child integer unique references Duels,
    CHECK ( (left_child IS NULL AND right_child IS NULL) OR (left_child IS NOT NULL AND right_child IS NOT NULL) ),
    PRIMARY KEY (tournament_id, duel_id)
);

CREATE TABLE Challenges
(
    challenge_id SERIAL PRIMARY KEY,
    date_from TIMESTAMP default CURRENT_TIMESTAMP  NOT NULL,
    date_to TIMESTAMP NOT NULL,
    objective INTEGER NOT NULL CHECK ( objective > 0 ),
    description VARCHAR(200) NOT NULL
);
CREATE TABLE PlayerChallenge
(
    player_id INTEGER REFERENCES Players,
    challenge_id INTEGER REFERENCES Challenges,
    PRIMARY KEY (player_id, challenge_id)
);

---------------------------------------------WSTAWIANIE PRZYKŁADOWYCH DANYCH-----------------------------------------------
-- INSERT INTO Clans DEFAULT
-- VALUES;
-- INSERT INTO Clans DEFAULT
-- VALUES;
-- INSERT INTO Clans DEFAULT
-- VALUES;
--
-- INSERT INTO ClanWars (clan1_ID, clan2_ID, date_from, outcome)
-- VALUES (1, 2, '2024-04-01 10:30:00', true),
--        (2, 3, '2024-04-10 21:30:00', false),
--        (3, 1, '2024-04-20 8:12:00', true);
--
-- INSERT INTO Players (password_hash, login)
-- VALUES (1234567890123, 'player1'),
--        (2345678901234, 'player2'),
--        (3456789012345, 'player3');
--
-- INSERT INTO ClanChat (sent_date, clan_ID, sender_ID, msg_text)
-- VALUES ('2024-04-01 1:30:12', 1, 1, 'Hello from clan 1!'),
--        ('2024-04-02 2:17:09', 2, 2, 'Greetings from clan 2!'),
--        ('2024-04-03 16:20:12', 3, 3, 'Hey clan 3, how are you?');
--
-- INSERT INTO Duels (sender, receiver, date_from, date_to, outcome)
-- VALUES (1, 2, '2024-04-01 11:30:12', '2024-04-01 11:35:12', true),
--        (2, 3, '2024-04-10 9:21:12', '2024-04-10 9:23:12', false),
--        (3, 1, '2024-04-20 12:10:12', '2024-04-20 12:10:18', true);
--
-- INSERT INTO WarDuels (duel_ID, clan_war_ID)
-- VALUES (1, 1),
--        (2, 2),
--        (3, 3);
--
-- INSERT INTO Applications (clan_ID, player_ID, date_from, date_to)
-- VALUES (1, 2, '2024-03-20', '2024-04-01 10:00:00'),
--        (2, 3, '2024-04-02', '2024-04-10 11:30:12'),
--        (3, 1, '2024-04-15 10:00:00', '2024-04-20 10:00:00');
--
-- INSERT INTO PlayerClan (date_from, clan_ID, player_ID, date_to, who_accepted)
-- VALUES ('2024-04-01 10:00:00', 1, 1, '2024-04-15 10:00:00', null),
--        ('2024-04-10 11:30:12', 2, 3, '2024-04-25 10:00:00', null),
--        ('2024-04-20 10:00:00', 3, 2, '2024-05-05 10:00:00', null);
--
-- INSERT INTO FriendsChat (sent_date, sender_ID, receiver_ID, msg_text)
-- VALUES ('2024-04-02', 1, 2, 'modelki'),
--        ('2024-04-12', 2, 3, 'on'),
--        ('2024-04-23', 3, 1, 'top');
--
-- INSERT INTO Friends (player1_ID, player2_ID, date_from, date_to)
-- VALUES (1, 2, '2024-04-11 8:12:32', '2024-04-15 8:12:32'),
--        (2, 3, '2024-04-10 8:12:32', '2024-04-25 8:12:32'),
--        (3, 1, '2024-04-20 8:12:32', '2024-05-05 8:12:32');
--
-- INSERT INTO FriendsInvites (player1_ID, player2_ID, date_from, date_to)
-- VALUES (1, 2, '2024-04-01 8:12:32', '2024-04-11 8:12:32'),
--        (2, 3, '2024-04-08 8:12:32', '2024-04-10 8:12:32'),
--        (3, 1, '2024-04-08 8:12:32', '2024-04-20 8:12:32');
--
-- INSERT INTO PlayerNickname (player_ID, date_from, nickname)
-- VALUES (1, '2024-04-01', 'Player1Nick'),
--        (2, '2024-04-10', 'Player2Nick'),
--        (3, '2024-04-20', 'Player3Nick');
--
-- INSERT INTO ClanNameData (clan_ID, date_from, cl_name)
-- VALUES (1, '2024-04-01', 'Clan1'),
--        (2, '2024-04-10', 'Clan2'),
--        (3, '2024-04-20', 'Clan3');

INSERT INTO Logos (image_address)
VALUES ('clanLogos/red-logo.png'),
       ('clanLogos/blue-logo.png'),
       ('clanLogos/yellow-logo.png'),
       ('clanLogos/green-logo.png');

-- INSERT INTO ClanLogos (clan_ID, date_from, logo_ID)
-- VALUES (1, '2024-04-01 12:45:12', 1),
--        (2, '2024-04-10 12:45:12', 2),
--        (3, '2024-04-20 12:45:12', 3);

INSERT INTO Roles (rank_name)
VALUES ('Leader'),
       ('Elder'),
       ('Member');

-- INSERT INTO PlayerRole (date_from, player_ID, rank_ID)
-- VALUES ('2024-04-01 13:34:56', 1, 1),
--        ('2024-04-10 13:34:56', 2, 1),
--        ('2024-04-20 18:09:13', 3, 1);