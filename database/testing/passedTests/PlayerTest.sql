-- This should execute (unless there is player with such id)
BEGIN TRANSACTION;
INSERT INTO Players(player_id, password_hash, login) VALUES (10000000, 0, 'myCustomLogin');
INSERT INTO playernickname(player_id, date_from, nickname) VALUES (10000000, current_timestamp, 'newCustomNickname');

END TRANSACTION;
