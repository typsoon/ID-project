INSERT INTO duels(duel_id, sender, receiver, date_from, date_to, outcome)
VALUES (10000000, 1, 2, current_timestamp-interval '5 minutes', current_timestamp - interval '4 minutes', true);

INSERT INTO duels(duel_id, sender, receiver, date_from, date_to, outcome)
VALUES (10000001, 3, 4, current_timestamp-interval '10 minutes', current_timestamp-interval '6 minutes', true);

INSERT INTO duels(duel_id, sender, receiver, date_from, date_to, outcome)
VALUES (10000002, 5, 6, current_timestamp-interval '10 minutes', current_timestamp-interval '6 minutes', true);

INSERT INTO duels(duel_id, sender, receiver, date_from, date_to, outcome)
VALUES (10000003, 7, 8, current_timestamp-interval '3 minutes', current_timestamp-interval '2 minutes', true);

INSERT INTO duels(duel_id, sender, receiver, date_from, date_to, outcome)
VALUES (10000004, 9, 10, current_timestamp-interval '1 minutes', current_timestamp-interval '1 second', true);


INSERT INTO tournamentsname(tournament_id, tournament_name)
VALUES (1, 'newName');
-- INSERT INTO tournaments(tournament_id, duel_id, left_child, right_child)
-- VALUES (1, 10000000, 10000001, 10000002),
--        (1, 10000003, 10000004);

INSERT INTO tournaments(tournament_id,matchup_id, duel_id, left_child, right_child)
VALUES
    (1,40, 10000001, null, null),
    (1,50, 10000002, null, null),
    (1,60, 10000000, 40, 50);

-- Should fail
    INSERT INTO tournaments(tournament_id,matchup_id, duel_id, left_child, right_child)
    VALUES
    (1,60, 10000001, null, null),
    (1,70, 10000002, null, null),
    (1,80, 10000003, null, null),
    (1,90, 10000000, 60, 70),
    (1,100, 10000004, 80, 90);


--        (1, 10000003, 10000004);