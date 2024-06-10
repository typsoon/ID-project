BEGIN TRANSACTION;
INSERT INTO Players(player_id, password_hash, login) VALUES (10000000, 0, 'myCustomLogin');
INSERT INTO playernickname(player_id, date_from, nickname) VALUES (10000000, current_timestamp, 'newCustomNickname');

-- INSERT INTO Players(player_id, password_hash, login) VALUES (10000000, 0, 'myCustomLogin');
-- INSERT INTO playernickname(player_id, date_from, nickname) VALUES (10000000, current_timestamp, 'newCustomNickname');
END TRANSACTION;

-- Shouldn't execute
    INSERT INTO PlayerRole(date_from, player_id, rank_id) VALUES ('2020-01-01', 55555, 3);

-- Shouldn't execute
    BEGIN TRANSACTION;
        INSERT INTO clans(clan_id) VALUES (10000000);
        INSERT INTO playerclan(date_from, clan_id, player_id, date_to, who_kicked, who_accepted)
        VALUES (current_timestamp, 10000000, 10000000, null, null, null);
        INSERT INTO clanlogos(clan_id, date_from, logo_id) VALUES (10000000, current_timestamp, 1);
        INSERT INTO clanname(clan_id, date_from, cl_name) VALUES (10000000, current_timestamp, 'oo');

    END TRANSACTION;
    BEGIN TRANSACTION;
    INSERT INTO clans(clan_id) VALUES (10000000);
    INSERT INTO playerclan(date_from, clan_id, player_id, date_to, who_kicked, who_accepted)
    VALUES (current_timestamp, 10000000, 10000000, null, null, null);
    INSERT INTO clanlogos(clan_id, date_from, logo_id) VALUES (10000000, current_timestamp, 1);
    INSERT INTO clanname(clan_id, date_from, cl_name) VALUES (10000000, current_timestamp, 'oo');
    INSERT INTO playerrole(date_from, player_id, rank_id) VALUES (current_timestamp, 10000000, 2);
    END TRANSACTION;
