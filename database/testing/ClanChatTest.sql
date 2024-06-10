INSERT INTO fullclandata(clanimage, clan_id, clanname, leader, time)
VALUES ('clanLogos/green-logo.png', 250, 'newClanName', 1, '2022-01-01');

INSERT INTO ClanChat(sent_date, sender_id, clan_id, msg_text) VALUES ('2022-01-01 0:00:1', 1, 250, 'test');

-- Shouldn't execute
    INSERT INTO clanchat(sent_date, sender_id, clan_id, msg_text) VALUES ('2021-01-01', 1, 250, 'test');
    INSERT INTO clanchat(sent_date, sender_id, clan_id, msg_text) VALUES ('2022-02-01', 3, 250, 'test');
    DELETE FROM clanchat WHERE sender_id = 1;

--shouldn't execute
    INSERT INTO FriendsChat(sent_date, sender_id, receiver_id, msg_text) VALUES ('2020-01-01', 1, 3, 'test');
    INSERT INTO FriendsChat(sent_date, receiver_id, sender_id, msg_text) VALUES ('2020-01-01', 1, 3, 'test');
    DELETE FROM FriendsChat WHERE sender_id = 1;