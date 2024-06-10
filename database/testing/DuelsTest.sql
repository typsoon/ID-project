-- Shouldn't execute
    INSERT INTO duels(sender, receiver, date_from, date_to, outcome)
    VALUES (1, 3, '2022-01-01 9:00:00', '2022-01-01 9:04:00', null);

    INSERT INTO duels(sender, receiver, date_from, date_to, outcome)
    VALUES (1, 3, '2022-01-01 9:00:00', '2023-01-01 9:05:00', true);

    INSERT INTO duels(sender, receiver, date_from, date_to, outcome)
    VALUES (1, 3, '2022-01-01 9:00:00', null, true);

    INSERT INTO duels(sender, receiver, date_from, date_to, outcome)
    VALUES (3, 1, current_timestamp, null, null);

INSERT INTO duels(sender, receiver, date_from, date_to, outcome)
VALUES (1, 3, current_timestamp, null, null);

-- Shouldn't execute
    INSERT INTO duels(sender, receiver, date_from, date_to, outcome)
    VALUES (1, 4, NOW() + interval '1 minute', null, null);

