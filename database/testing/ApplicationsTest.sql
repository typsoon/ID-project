INSERT INTO fullclandata(clanimage, clan_id, clanname, leader, time)
VALUES ('clanLogos/green-logo.png', 10000000, 'newClanName', 1, '2022-01-01');

-- Shouldn't execute
    INSERT INTO applications(clan_id, player_id, date_from, date_to)
    VALUES (2, 1)