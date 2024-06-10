
-- Shouldn't execute
    INSERT INTO friends(player1_id, player2_id, date_from, date_to) VALUES (3, 1, '2022-01-01', '2023-01-01');
    INSERT INTO friends(player1_id, player2_id, date_from, date_to) VALUES (3, 1, '2023-01-01', '2022-01-01');

INSERT INTO friends(player1_id, player2_id, date_from, date_to) VALUES (1, 3, '2022-01-01', '2023-01-01');
INSERT INTO friends(player1_id, player2_id, date_from, date_to) VALUES (1, 3, '2023-05-01', '2024-05-01');

-- Shouldn't execute
    INSERT INTO friends(player1_id, player2_id, date_from, date_to) VALUES (1, 3, '2022-05-01', '2023-01-01');

INSERT INTO friends(player1_id, player2_id, date_from, date_to) VALUES (4, 5, '2022-01-01', null);

-- Shouldn't execute
    INSERT INTO friends(player1_id, player2_id, date_from, date_to) VALUES (4, 5, '2022-05-01', '2023-01-01');


-- Shouldn't execute
    DELETE FROM friends
    WHERE (player1_id, player2_id, date_from, date_to) = (4, 5, '2022-01-01', null);



