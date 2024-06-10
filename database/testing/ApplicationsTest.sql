INSERT INTO fullclandata(clanimage, clan_id, clanname, leader, time)
VALUES ('clanLogos/green-logo.png', 10000000, 'newClanName', 1, '2022-01-01');

-- Shouldn't execute
    INSERT INTO applications(clan_id, player_id, date_from, date_to)
    VALUES (2, 1, current_timestamp, null);

    INSERT INTO applications(clan_id, player_id, date_from, date_to)
    VALUES (2, 2, '2022-01-01', '2023-01-01');

    INSERT INTO applications(clan_id, player_id, date_from, date_to)
    VALUES (2, 2, current_timestamp - interval '3 weeks', null);

    INSERT INTO applications(clan_id, player_id, date_from, date_to)
    VALUES (2, 2, current_timestamp + interval '3 weeks', null);

INSERT INTO applications(clan_id, player_id, date_from, date_to)
VALUES (2, 2, current_timestamp - interval '7 days', null);

-- Shouldn't execute
    INSERT INTO applications(clan_id, player_id, date_from, date_to)
    VALUES (2, 2, current_timestamp - interval '5 days', null);
