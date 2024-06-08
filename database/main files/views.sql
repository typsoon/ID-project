create or replace view FullPlayerData as
(
select p.password_hash, p.login, PlayerClanID(p.player_ID),p.player_ID,
       PlayerNickname(p.player_ID) from Players p
    );


create or replace view FullClanData as
(
select ClanImage(cl.clan_ID), cl.clan_ID, ClanName(cl.clan_ID), getclanleader(cl.clan_id) as leader
from clans cl

    );
