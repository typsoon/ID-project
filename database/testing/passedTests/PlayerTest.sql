-- This should execute (unless there is player with such id)
BEGIN TRANSACTION;
INSERT INTO playernickname(player_id, date_from, nickname) VALUES (10000000, current_timestamp, 'newCustomNickname');
INSERT INTO Players(player_id, password_hash, login) VALUES (10000000, 0, 'myCustomLogin');
END TRANSACTION;
