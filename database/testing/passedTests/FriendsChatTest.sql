-- 1 and 3 were never friends

--shouldn't execute
    INSERT INTO FriendsChat(sent_date, sender_id, receiver_id, msg_text) VALUES (current_timestamp, 1, 3, 'test');

INSERT INTO friends(player1_id, player2_id, date_from, date_to) VALUES (1, 3, '2022-01-01', '2023-01-01');
-- check what happens
    INSERT INTO FriendsChat(sent_date, sender_id, receiver_id, msg_text) VALUES ('2022-01-01', 1, 3, 'test');
INSERT INTO FriendsChat(sent_date, sender_id, receiver_id, msg_text) VALUES ('2022-01-01 0:00:1', 1, 3, 'test');
INSERT INTO FriendsChat(sent_date, receiver_id, sender_id, msg_text) VALUES ('2022-01-01 0:00:1', 1, 3, 'test');

--shouldn't execute
    INSERT INTO FriendsChat(sent_date, sender_id, receiver_id, msg_text) VALUES ('2020-01-01', 1, 3, 'test');
    INSERT INTO FriendsChat(sent_date, receiver_id, sender_id, msg_text) VALUES ('2020-01-01', 1, 3, 'test');
    DELETE FROM FriendsChat WHERE TRUE;
-- INSERT INTO FriendsChat(sent_date, sender_id, receiver_id, msg_text) VALUES ('2022-06-01', 1, 3, 'test');
-- INSERT INTO FriendsChat(sent_date, receiver_id, sender_id, msg_text) VALUES ('2022-06-01', 1, 3, 'test');