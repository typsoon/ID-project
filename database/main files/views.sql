create or replace view FullPlayerData as
(
select p.password_hash, p.login, PlayerClanID(p.player_ID),p.player_ID,
       PlayerNickname(p.player_ID) from Players p
    );

