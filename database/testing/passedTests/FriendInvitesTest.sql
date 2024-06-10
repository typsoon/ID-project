--tests that shouldn't execute often have to be executed after previous statements

-- shouldn't execute
    INSERT INTO friendsinvites(player1_id, player2_id, date_from, date_to)
    VALUES (2, 1, NOW(), null);


INSERT INTO friendsinvites(player1_id, player2_id, date_from, date_to)
VALUES (1, 2, NOW(), null);

-- shouldn't execute
    INSERT INTO friendsinvites(player1_id, player2_id, date_from, date_to)
    VALUES (1, 2, NOW(), null);

    INSERT INTO friendsinvites(player1_id, player2_id, date_from, date_to)
    VALUES (2, 1, NOW(), null);

    INSERT INTO friendsinvites(player1_id, player2_id, date_from, date_to)
    VALUES (2, 100, NOW(), now()+interval '3 weeks');

    INSERT INTO friends(player1_id, player2_id, date_from, date_to) VALUES (2, 1, now(), null);

    INSERT INTO friends(player1_id, player2_id, date_from, date_to) VALUES (1, 2, NOW(), null);
-- should be empty
    SELECT * FROM friendsinvites WHERE player1_id = 1 AND player2_id = 2;

    SELECT * FROM friendsinvites WHERE player1_id = 2 AND player2_id = 1;

-- shouldn't execute
    INSERT INTO friendsinvites(player1_id, player2_id, date_from, date_to)
    VALUES (1, 2, NOW(), null);
