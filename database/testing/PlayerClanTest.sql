INSERT INTO fullclandata(clanimage, clan_id, clanname, leader, time)
VALUES ('clanLogos/green-logo.png', 250, 'newClanName', 1, '2022-01-01');

--Shouldn't execute
    INSERT INTO playerclan(date_from, clan_id, player_id, date_to, who_kicked, who_accepted)
    VALUES (current_timestamp, 250, 2, null, null, 1);

    INSERT INTO playerclan(date_from, clan_id, player_id, date_to, who_kicked, who_accepted)
    VALUES (current_timestamp, 2, 1, null, null, 1);

INSERT INTO applications(clan_id, player_id, date_from, date_to)
VALUES (250, 2, current_timestamp-interval '3 days', null);

INSERT INTO applications(clan_id, player_id, date_from, date_to)
VALUES (250, 3, current_timestamp-interval '3 days', null);

SELECT acceptmember(1, 2);

-- Should fail
    SELECT acceptmember(1, 2);

    BEGIN TRANSACTION;
        delete from Applications where clan_id = 250 and player_id = 3;
        insert into playerclan (clan_ID,player_ID,who_accepted) values (250,3,2);
        insert into playerrole (player_id, rank_id) values  (3,3);
    END TRANSACTION ;

SELECT acceptmember(1, 3);

-- Shouldn't work
    UPDATE playerclan SET
          who_kicked = 3,
          date_to = current_timestamp
    WHERE player_id = 2;

