create or replace rule add_player as on insert
    to FullPlayerData
    do instead
    (
    insert into Players (password_hash,login) values (new.password_hash,new.login);
    insert into PlayerNickname (player_ID,nickname) values
        ( (select player_ID from Players where Players.login = new.login),
          new.playernickname
        )


    )
