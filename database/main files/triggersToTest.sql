-- jeden aktywnt nick na gracza ---------------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_player_has_nickname()
RETURNS TRIGGER AS $check_player_has_nickname$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM PlayerNickname
        WHERE player_ID = NEW.player_ID
    ) THEN
        RAISE EXCEPTION 'Player does not have an active nickname';
    END IF;
    RETURN NEW;
END;
$check_player_has_nickname$ LANGUAGE plpgsql;

CREATE TRIGGER check_player_has_nickname
BEFORE UPDATE OR INSERT ON Players
FOR EACH ROW
EXECUTE FUNCTION check_player_has_nickname();
------------------------------------------------------------------------------------------------------------


-- czy klan ma aktywne logo --------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_clan_has_logo()
RETURNS TRIGGER AS $check_clan_has_logo$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM ClanLogos
        WHERE clan_ID = NEW.clan_ID
    ) THEN
        RAISE EXCEPTION 'Clan does not have an active logo';
    END IF;
    RETURN NEW;
END;
$check_clan_has_logo$ LANGUAGE plpgsql;

CREATE TRIGGER check_clan_has_logo
BEFORE UPDATE OR INSERT ON Clans
FOR EACH ROW
EXECUTE FUNCTION check_clan_has_logo();
------------------------------------------------------------------------------------------------------------


-- single_clan_membership ----------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_player_clan()
RETURNS TRIGGER AS $check_player_clan$
BEGIN
    IF EXISTS (
        SELECT date_from
        FROM PlayerClan
        WHERE player_ID = NEW.player_ID AND date_to IS NULL
    ) THEN
        RAISE EXCEPTION 'Player is already in a clan';
    END IF;
    RETURN NEW;
END;
$check_player_clan$ LANGUAGE plpgsql;

CREATE TRIGGER check_player_clan
BEFORE INSERT ON PlayerClan
FOR EACH ROW
EXECUTE PROCEDURE check_player_clan();
------------------------------------------------------------------------------------------------------------


-- usuwanie zaproszeń do klanu po zaakceptowaniu zaproszenia -----------------------------------------------
CREATE OR REPLACE FUNCTION remove_clan_invites_after_acceptance()
RETURNS TRIGGER AS $remove_clan_invites_after_acceptance$
BEGIN
    DELETE FROM Applications
    WHERE player_ID = NEW.player_ID AND clan_ID != NEW.clan_ID;
    RETURN NEW;
END;
$remove_clan_invites_after_acceptance$ LANGUAGE plpgsql;

CREATE TRIGGER remove_clan_invites_after_acceptance
AFTER INSERT ON PlayerClan
FOR EACH ROW
EXECUTE PROCEDURE remove_clan_invites_after_acceptance();
------------------------------------------------------------------------------------------------------------


-- usuwanie zaproszeń do znajomych po zaakceptowaniu zaproszenia -----------------------------------------------
CREATE OR REPLACE FUNCTION remove_friend_invites_after_acceptance()
RETURNS TRIGGER AS $remove_friend_invites_after_acceptance$
BEGIN
    DELETE FROM FriendsInvites
    WHERE (player1_ID = NEW.player1_ID AND player2_ID != NEW.player2_ID)
       OR (player1_ID = NEW.player2_ID AND player2_ID != NEW.player1_ID)
       OR (player2_ID = NEW.player1_ID AND player1_ID != NEW.player2_ID)
       OR (player2_ID = NEW.player2_ID AND player1_ID != NEW.player1_ID);
    RETURN NEW;
END;
$remove_friend_invites_after_acceptance$ LANGUAGE plpgsql;

CREATE TRIGGER remove_friend_invites_after_acceptance
AFTER INSERT ON Friends
FOR EACH ROW
EXECUTE FUNCTION remove_friend_invites_after_acceptance();
------------------------------------------------------------------------------------------------------------


-- sprawdzenia, czy gracz ma odpowiednią rangę przed usunięciem kogoś z klanu ------------------------------
CREATE OR REPLACE FUNCTION check_rank_before_kick()
RETURNS TRIGGER AS $check_rank_before_kick$
BEGIN
    IF (SELECT rank_ID FROM PlayerRole pr1 WHERE player_ID = NEW.who_kicked AND clan_ID = NEW.clan_ID) <
       (SELECT rank_ID FROM PlayerRole pr2 WHERE player_ID = NEW.player_ID AND clan_ID = NEW.clan_ID)
--        AND pr1.clan_ID=pr2.clan_ID
       THEN
        RAISE EXCEPTION 'You do not have sufficient rank to kick this player';
    END IF;
    RETURN NEW;
END;
$check_rank_before_kick$ LANGUAGE plpgsql;

CREATE TRIGGER check_rank_before_kick
BEFORE UPDATE ON PlayerClan
FOR EACH ROW
WHEN (NEW.date_to IS NOT NULL)
EXECUTE PROCEDURE check_rank_before_kick();
------------------------------------------------------------------------------------------------------------


-- funkcja sprawdzająca rangę przed akceptacją aplikacji do klanu ------------------------------------------
CREATE OR REPLACE FUNCTION check_rank_before_accept()
RETURNS TRIGGER AS $check_rank_before_accept$
BEGIN
    IF (SELECT rank_ID FROM PlayerRole pr1 WHERE player_ID = NEW.who_accepted AND playerclanid(pr1.player_id) = NEW.clan_ID) <=
       (SELECT rank_ID FROM Roles WHERE rank_name = 'Member') THEN
        RAISE EXCEPTION 'You do not have sufficient rank to accept this application';
    END IF;
    RETURN NEW;
END;
$check_rank_before_accept$ LANGUAGE plpgsql;

CREATE TRIGGER check_rank_before_accept
BEFORE UPDATE ON Applications
FOR EACH ROW
WHEN (NEW.date_to IS NOT NULL)
EXECUTE FUNCTION check_rank_before_accept();
------------------------------------------------------------------------------------------------------------


-- zaproszenia do znajomcyh wygasają po 2 tygodniach -------------------------------------------------------
CREATE OR REPLACE FUNCTION expire_friends_invites()
RETURNS TRIGGER AS $expire_friends_invites$
BEGIN
    IF NEW.date_to IS NULL AND AGE(NOW(), NEW.date_from) >= INTERVAL '2 weeks' THEN
        DELETE FROM FriendsInvites WHERE player1_ID = NEW.player1_ID AND player2_ID = NEW.player2_ID;
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$expire_friends_invites$ LANGUAGE plpgsql;


CREATE TRIGGER expire_friends_invites
BEFORE INSERT OR UPDATE ON FriendsInvites
FOR EACH ROW
EXECUTE FUNCTION expire_friends_invites();
------------------------------------------------------------------------------------------------------------


-- zaproszenie do clanu wygasa po 2 tygodniach -------------------------------------------------------------
CREATE OR REPLACE FUNCTION expire_clan_applications()
RETURNS TRIGGER AS $expire_clan_applications$
BEGIN
    IF NEW.date_to IS NULL AND AGE(NOW(), NEW.date_from) >= INTERVAL '2 weeks' THEN
        DELETE FROM Applications WHERE player_ID = NEW.player_ID AND clan_ID = NEW.clan_ID;
        RETURN NULL; -- Usunięcie rekordu, trigger nie kontynuuje
    END IF;
    RETURN NEW;
END;
$expire_clan_applications$ LANGUAGE plpgsql;

CREATE TRIGGER expire_clan_applications
BEFORE UPDATE ON Applications
FOR EACH ROW
EXECUTE FUNCTION expire_clan_applications();
------------------------------------------------------------------------------------------------------------


-- czy gracz jest już aktywny w innym pojedynku ------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_active_duel()
RETURNS TRIGGER AS $check_active_duel$
DECLARE
    active_duel_id INTEGER;
BEGIN
    SELECT duel_ID INTO active_duel_id
    FROM Duels
    WHERE 
        ((NEW.sender = sender AND NEW.receiver = receiver) OR (NEW.sender = receiver AND NEW.receiver = sender))
        AND date_to IS NULL AND outcome IS NULL;

    IF active_duel_id IS NOT NULL THEN
        RAISE EXCEPTION 'Ten gracz aktualnie bierze udział w innym pojedynku';
    END IF;

    RETURN NEW;
END;
$check_active_duel$ LANGUAGE plpgsql;

CREATE TRIGGER check_active_duel
BEFORE INSERT ON Duels
FOR EACH ROW
EXECUTE PROCEDURE check_active_duel();
------------------------------------------------------------------------------------------------------------


-- wymiana wiadomości tylko miedzy friendsami --------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_friends_chat()
RETURNS TRIGGER AS $check_friends_chat$
BEGIN
    IF NOT EXISTS (
        SELECT player1_ID
        FROM Friends
        WHERE
            (NEW.sender_ID = player1_ID AND NEW.receiver_ID = player2_ID)
            OR (NEW.sender_ID = player2_ID AND NEW.receiver_ID = player1_ID)
    ) THEN
        RAISE EXCEPTION 'Only friends can exchange private messages';
    END IF;
    RETURN NEW;
END;
$check_friends_chat$ LANGUAGE plpgsql;

CREATE TRIGGER check_friends_chat
BEFORE INSERT ON FriendsChat
FOR EACH ROW
EXECUTE PROCEDURE check_friends_chat();
------------------------------------------------------------------------------------------------------------


-- aplikacje do clanów -------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_application()
RETURNS TRIGGER AS $check_application$
BEGIN
    IF EXISTS (
        SELECT date_from
        FROM PlayerClan
        WHERE player_ID = NEW.player_ID AND date_to IS NULL
    ) THEN
        RAISE EXCEPTION 'Player is already in a clan';
    END IF;

    IF EXISTS (
        SELECT clan_ID
        FROM Applications
        WHERE player_ID = NEW.player_ID AND clan_ID = NEW.clan_ID AND date_to IS NULL
    ) THEN
        RAISE EXCEPTION 'Player already has an active application to this clan';
    END IF;

    RETURN NEW;
END;
$check_application$ LANGUAGE plpgsql;

CREATE TRIGGER check_application
BEFORE INSERT ON Applications
FOR EACH ROW
EXECUTE PROCEDURE check_application();
------------------------------------------------------------------------------------------------------------


-- zaproszenia do znajomych --------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_friend_invite()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT player1_ID
        FROM FriendsInvites 
        WHERE player1_ID = NEW.player1_ID AND player2_ID = NEW.player2_ID AND date_to IS NULL
    ) THEN
        RAISE EXCEPTION 'Active invitation already exists from sender to receiver';
    END IF;

    IF EXISTS (
        SELECT player1_ID
        FROM FriendsInvites 
        WHERE player1_ID = NEW.player2_ID AND player2_ID = NEW.player1_ID AND date_to IS NULL
    ) THEN
        RAISE EXCEPTION 'Active invitation already exists from receiver to sender';
    END IF;

    IF EXISTS (
        SELECT player1_ID
        FROM Friends 
        WHERE (player1_ID = NEW.player1_ID AND player2_ID = NEW.player2_ID AND date_to IS NULL)
           OR (player1_ID = NEW.player2_ID AND player2_ID = NEW.player1_ID AND date_to IS NULL)
    ) THEN
        RAISE EXCEPTION 'Players are already friends';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_friend_invite
BEFORE INSERT ON FriendsInvites
FOR EACH ROW
EXECUTE FUNCTION check_friend_invite();
------------------------------------------------------------------------------------------------------------


-- alceptacja zaproszenia do znajomych --------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION accept_friend_invite(
    p_inviter_id INTEGER,
    p_invitee_id INTEGER
)
RETURNS VOID AS $$
BEGIN
    IF NOT EXISTS (
        SELECT player1_ID
        FROM FriendsInvites 
        WHERE player1_ID = p_inviter_id AND player2_ID = p_invitee_id AND date_to IS NULL
    ) THEN
        RAISE EXCEPTION 'Invitation does not exist or has already been accepted/expired';
    END IF;

    INSERT INTO Friends (player1_ID, player2_ID, date_from)
    VALUES (p_inviter_id, p_invitee_id, CURRENT_TIMESTAMP);

    UPDATE FriendsInvites
    SET date_to = CURRENT_TIMESTAMP
    WHERE player1_ID = p_inviter_id AND player2_ID = p_invitee_id AND date_to IS NULL;

END;
$$ LANGUAGE plpgsql;
------------------------------------------------------------------------------------------------------------


-- kolejność id graczy przy dodawaniu do znajomych ---------------------------------------------------------
CREATE OR REPLACE FUNCTION check_friends_order()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.player1_ID >= NEW.player2_ID THEN
        RAISE EXCEPTION 'player1_ID must be less than player2_ID';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_friends_order
BEFORE INSERT ON Friends
FOR EACH ROW
EXECUTE FUNCTION check_friends_order();
------------------------------------------------------------------------------------------------------------


-- pojedynek wojny clanów ----------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_war_duel()
RETURNS TRIGGER AS $$
DECLARE
    clan1_id INTEGER;
    clan2_id INTEGER;
    sender_clan_id INTEGER;
    receiver_clan_id INTEGER;
BEGIN
    SELECT clan1_ID, clan2_ID INTO clan1_id, clan2_id
    FROM ClanWars
    WHERE clan_war_ID = NEW.clan_war_ID;

    SELECT clan_ID INTO sender_clan_id
    FROM PlayerClan
    WHERE player_ID = (SELECT sender FROM Duels WHERE duel_ID = NEW.duel_ID)
      AND date_to IS NULL;

    SELECT clan_ID INTO receiver_clan_id
    FROM PlayerClan
    WHERE player_ID = (SELECT receiver FROM Duels WHERE duel_ID = NEW.duel_ID)
      AND date_to IS NULL;

    IF (sender_clan_id = clan1_id AND receiver_clan_id = clan2_id) OR
       (sender_clan_id = clan2_id AND receiver_clan_id = clan1_id) THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Players must be members of respective clans and from different clans';
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_war_duel
BEFORE INSERT ON WarDuels
FOR EACH ROW
EXECUTE FUNCTION check_war_duel();
------------------------------------------------------------------------------------------------------------


-- akceptowanie zgłoszenia do klanu FUNKCJA ----------------------------------------------------------------
CREATE OR REPLACE FUNCTION accept_clan_application(
    p_clan_id INTEGER,
    p_player_id INTEGER,
    p_acceptor_id INTEGER
)
RETURNS VOID AS $$
DECLARE
    acceptor_role VARCHAR(20);
    application_date_from TIMESTAMP;
BEGIN
    SELECT r.rank_name INTO acceptor_role
    FROM PlayerRole pr
    JOIN Roles r ON pr.rank_ID = r.rank_ID
    WHERE pr.player_ID = p_acceptor_id
      AND r.rank_name = 'Leader';

    IF acceptor_role IS NULL THEN
        RAISE EXCEPTION 'The player accepting the application does not have the required role';
    END IF;

    IF EXISTS (
        SELECT date_from
        FROM PlayerClan
        WHERE player_ID = p_player_id
          AND date_to IS NULL
    ) THEN
        RAISE EXCEPTION 'Player is already a member of a clan';
    END IF;

    SELECT date_from INTO application_date_from
    FROM Applications
    WHERE player_ID = p_player_id
      AND clan_ID = p_clan_id
      AND date_to IS NULL;

    IF application_date_from IS NULL THEN
        RAISE EXCEPTION 'No active application found for this player and clan';
    END IF;

    UPDATE Applications
    SET date_to = CURRENT_TIMESTAMP
    WHERE player_ID = p_player_id
      AND clan_ID = p_clan_id
      AND date_to IS NULL;

    INSERT INTO PlayerClan (clan_ID, player_ID, date_from, who_accepted)
    VALUES (p_clan_id, p_player_id, CURRENT_TIMESTAMP, p_acceptor_id);
END;
$$ LANGUAGE plpgsql;
------------------------------------------------------------------------------------------------------------

-- ClanChat sprawdzenie czy członek klanu wysłał -----------------------------------------------------------
CREATE OR REPLACE FUNCTION check_clan_member()
RETURNS TRIGGER AS $check_clan_member$
DECLARE
    is_clan_member BOOLEAN;
BEGIN
    SELECT TRUE INTO is_clan_member
    FROM PlayerClan
    WHERE player_ID = NEW.sender_ID
      AND clan_ID = NEW.clan_ID
      AND date_to IS NULL;

    IF NOT is_clan_member THEN
        RAISE EXCEPTION 'Only members of the clan can post here';
    END IF;

    RETURN NEW;
END;
$check_clan_member$ LANGUAGE plpgsql;

CREATE TRIGGER check_clan_member
BEFORE INSERT ON ClanChat
FOR EACH ROW
EXECUTE PROCEDURE check_clan_member();
------------------------------------------------------------------------------------------------------------

-- sprawdzanie czy lider + zmiana logo ---------------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_leader_before_logo_change()
RETURNS TRIGGER AS $check_leader_before_logo_change$
BEGIN
    IF (SELECT pr.rank_ID FROM PlayerRole pr
        JOIN Roles r ON pr.rank_ID = r.rank_ID
        WHERE player_ID = NEW.player_ID AND clan_ID = NEW.clan_ID AND r.rank_name = 'Leader') IS NULL THEN
        RAISE EXCEPTION 'Only the clan leader can change the logo';
    END IF;
    RETURN NEW;
END;
$check_leader_before_logo_change$ LANGUAGE plpgsql;

CREATE TRIGGER check_leader_before_logo_change
BEFORE INSERT ON ClanLogos
FOR EACH ROW
EXECUTE FUNCTION check_leader_before_logo_change();
------------------------------------------------------------------------------------------------------------


-- nadawanie roli ------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION grant_clan_role(
    p_player_id INTEGER,
    p_target_player_id INTEGER,
    p_role_name VARCHAR(20)
)
RETURNS VOID AS $$
DECLARE
    granter_role_name VARCHAR(20);
BEGIN
    SELECT r.rank_name INTO granter_role_name
    FROM PlayerRole pr
    JOIN Roles r ON pr.rank_ID = r.rank_ID
    WHERE pr.player_ID = p_player_id;

    IF granter_role_name NOT IN ('Leader', 'Elder') THEN
        RAISE EXCEPTION 'Only members with the role of Leader or Elder can grant clan roles';
    END IF;

    IF NOT EXISTS (
        SELECT rank_ID
        FROM Roles
        WHERE rank_name = p_role_name
    ) THEN
        RAISE EXCEPTION 'Role does not exist';
    END IF;

    IF p_role_name = 'Leader' THEN
        RAISE EXCEPTION 'Role Leader cannot be granted';
    END IF;

    INSERT INTO PlayerRole (player_ID, rank_ID, date_from)
    VALUES (p_target_player_id, (SELECT rank_ID FROM Roles WHERE rank_name = p_role_name), CURRENT_TIMESTAMP);
END;
$$ LANGUAGE plpgsql;
------------------------------------------------------------------------------------------------------------


-- usuwanie applications -----------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION invalidate_applications(p_player_id INTEGER, p_clan_id INTEGER)
RETURNS VOID AS $$
BEGIN
    UPDATE Applications
    SET date_to = CURRENT_TIMESTAMP
    WHERE player_ID = p_player_id AND date_to IS NULL AND clan_ID != p_clan_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION trigger_invalidate_applications()
RETURNS TRIGGER AS $$
BEGIN
    PERFORM invalidate_applications(NEW.player_ID, NEW.clan_ID);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER invalidate_applications_trigger
AFTER INSERT ON PlayerClan
FOR EACH ROW
EXECUTE FUNCTION trigger_invalidate_applications();
------------------------------------------------------------------------------------------------------------


-- update ranking_base -------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION update_ranking_base(duel_iddd INTEGER)
RETURNS VOID AS $$
DECLARE
    duel_record RECORD;
    winner_clan_id INTEGER;
    loser_clan_id INTEGER;
BEGIN
    SELECT *
    INTO duel_record
    FROM Duels d
    WHERE d.duel_ID = duel_iddd;
    
    IF duel_record.outcome IS NULL THEN
        RAISE EXCEPTION 'Duel outcome is not set';
    END IF;

    IF duel_record.outcome THEN
        SELECT clan_ID INTO winner_clan_id
        FROM PlayerClan
        WHERE player_ID = duel_record.sender AND date_to IS NULL;

        SELECT clan_ID INTO loser_clan_id
        FROM PlayerClan
        WHERE player_ID = duel_record.receiver AND date_to IS NULL;
    ELSE
        SELECT clan_ID INTO winner_clan_id
        FROM PlayerClan
        WHERE player_ID = duel_record.receiver AND date_to IS NULL;

        SELECT clan_ID INTO loser_clan_id
        FROM PlayerClan
        WHERE player_ID = duel_record.sender AND date_to IS NULL;
    END IF;

    -- Aktualizacja ranking_base dla zwycięzcy
    UPDATE Clans
    SET ranking_base = ranking_base + 10
    WHERE clan_ID = winner_clan_id;

    -- Aktualizacja ranking_base dla przegranego
    UPDATE Clans
    SET ranking_base = ranking_base - 5
    WHERE clan_ID = loser_clan_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION trigger_update_ranking_base()
RETURNS TRIGGER AS $$
BEGIN
    PERFORM update_ranking_base(NEW.duel_ID);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER after_duel_update
AFTER UPDATE OF outcome ON Duels
FOR EACH ROW
WHEN (NEW.outcome IS NOT NULL)
EXECUTE FUNCTION trigger_update_ranking_base();
------------------------------------------------------------------------------------------------------------


-- akceptacja zaproszeń do znqjomych -----------------------------------------------------------------------
CREATE OR REPLACE FUNCTION accept_friend_invite(
    p_inviter_id INTEGER,
    p_invitee_id INTEGER
)
RETURNS VOID AS $$
BEGIN
    IF NOT EXISTS (
        SELECT player1_ID
        FROM FriendsInvites 
        WHERE player1_ID = p_inviter_id AND player2_ID = p_invitee_id AND date_to IS NULL
    ) THEN
        RAISE EXCEPTION 'Invitation does not exist or has already been accepted/expired';
    END IF;

    INSERT INTO Friends (player1_ID, player2_ID, date_from)
    VALUES (p_inviter_id, p_invitee_id, CURRENT_TIMESTAMP);

    UPDATE FriendsInvites
    SET date_to = CURRENT_TIMESTAMP
    WHERE player1_ID = p_inviter_id AND player2_ID = p_invitee_id AND date_to IS NULL;

END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION check_duplicate_invite()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT player1_ID
        FROM FriendsInvites
        WHERE (NEW.player1_ID = player1_ID AND NEW.player2_ID = player2_ID AND date_to IS NULL)
           OR (NEW.player1_ID = player2_ID AND NEW.player2_ID = player1_ID AND date_to IS NULL)
    ) THEN
        RAISE EXCEPTION 'Duplicate invitation';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_check_duplicate_invite
BEFORE INSERT ON FriendsInvites
FOR EACH ROW
EXECUTE FUNCTION check_duplicate_invite();
------------------------------------------------------------------------------------------------------------

-- sprawdzanie turniejów -----------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION fn_check_tournament_rules() RETURNS TRIGGER AS $$
DECLARE
    left_child_level INTEGER;
    right_child_level INTEGER;
BEGIN
    -- Sprawdzenie, czy gracz bierze udział w maksymalnie jednym turnieju jednocześnie
    IF EXISTS (
        SELECT 1 FROM Tournaments WHERE (left_child = NEW.duel_id OR right_child = NEW.duel_id) AND date_to IS NULL
    ) THEN
        RAISE EXCEPTION 'Player is already participating in another tournament.';
    END IF;

    -- Sprawdzenie, czy dzieci mają takie same id_turnieju co ojciec
    IF NEW.left_child IS NOT NULL AND NEW.right_child IS NOT NULL THEN
        IF NOT EXISTS (
            SELECT 1 FROM Tournaments WHERE duel_id IN (NEW.left_child, NEW.right_child) AND tournament_id = NEW.tournament_id
        ) THEN
            RAISE EXCEPTION 'Children must have the same tournament_id as their parent.';
        END IF;
    END IF;

    -- Sprawdzenie, czy dzieci mają ten sam poziom
    IF NEW.left_child IS NOT NULL AND NEW.right_child IS NOT NULL THEN
        left_child_level := get_level(NEW.left_child);
        right_child_level := get_level(NEW.right_child);

        IF left_child_level != right_child_level THEN
            RAISE EXCEPTION 'Children must be on the same level in the tree.';
        END IF;
    END IF;

    -- Sprawdzenie, czy pojedynek rozpoczyna się po dzieciach
    IF NEW.left_child IS NOT NULL AND NEW.right_child IS NOT NULL THEN
        IF NOT EXISTS (
            SELECT 1 FROM Duels WHERE duel_id = NEW.duel_id AND date_from > ALL(SELECT date_to FROM Duels WHERE duel_id IN (NEW.left_child, NEW.right_child))
        ) THEN
            RAISE EXCEPTION 'Parent duel must start after the children duels have ended.';
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_tournament_rules
BEFORE INSERT OR UPDATE ON Tournaments
FOR EACH ROW
EXECUTE FUNCTION fn_check_tournament_rules();
------------------------------------------------------------------------------------------------------------


-- czy klan ma lidera --------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION check_clan_has_leader()
RETURNS TRIGGER AS $check_clan_has_leader$
BEGIN
    IF GetClanLeader(NEW.clan_ID) IS NULL THEN
        RAISE EXCEPTION 'Clan does not have a leader';
    END IF;
    RETURN NEW;
END;
$check_clan_has_leader$ LANGUAGE plpgsql;

CREATE TRIGGER check_clan_has_leader
BEFORE UPDATE OR INSERT ON Clans
FOR EACH ROW
EXECUTE FUNCTION check_clan_has_leader();
------------------------------------------------------------------------------------------------------------

--
CREATE OR REPLACE FUNCTION ensure_clan_has_leader()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.rank_ID = (SELECT rank_ID FROM Roles WHERE rank_name = 'Leader') THEN
        IF GetClanLeader(OLD.clan_ID) IS NULL THEN
            RAISE EXCEPTION 'Cannot remove or update leader. Clan would be left without a leader.';
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER ensure_clan_has_leader_before_delete
BEFORE DELETE ON PlayerRole
FOR EACH ROW
EXECUTE FUNCTION ensure_clan_has_leader();

CREATE TRIGGER ensure_clan_has_leader_before_update
BEFORE UPDATE ON PlayerRole
FOR EACH ROW
EXECUTE FUNCTION ensure_clan_has_leader();
------------------------------------------------------------------------------------------------------------

-- udział w turnieju idk
-- CREATE OR REPLACE FUNCTION check_tournament_participation()
-- RETURNS TRIGGER AS $check_tournament_participation$
-- BEGIN
--     IF EXISTS (
--         SELECT tournament_id
--         FROM Tournaments
--         WHERE (player1_ID = NEW.player1_ID OR player2_ID = NEW.player2_ID OR clan1_ID = NEW.clan1_ID OR clan2_ID = NEW.clan2_ID)
--           AND NEW.date_from BETWEEN date_from AND date_to
--     ) THEN
--         RAISE EXCEPTION 'Player or clan is already participating in a match at this time';
--     END IF;
--     RETURN NEW;
-- END;
-- $check_tournament_participation$ LANGUAGE plpgsql;

-- CREATE TRIGGER check_tournament_participation
-- BEFORE INSERT ON Tournaments
-- FOR EACH ROW
-- EXECUTE PROCEDURE check_tournament_participation();
------------------------------------------------------------------------------------------------------------



-- nie wiem czy ma jakikolwiek sens  ----------------------------------------------------------------------------------------------
-- CREATE VIEW PlayerChat AS
-- SELECT *
-- FROM FriendsChat
-- WHERE
--     -- Sprawdź, czy zalogowany użytkownik jest jednym z dwóch graczy
--     (sender_ID = :logged_in_player_id AND receiver_ID = :other_player_id)
--     OR (sender_ID = :other_player_id AND receiver_ID = :logged_in_player_id);
------------------------------------------------------------------------------------------------------------


-- Player clan A player can have only one clan, who_kicked must point to a player who is a member of the clan and has appropriate role (member, elder - kick and add, leader - superuser)
-- Friends chat Only friends can exchange private messages
-- Gracz staje się członkiem klanu po zaakceptowaniu zgłoszenia przez członka klanu o roli pozwalającej na akceptowanie zgłoszeń

-- funkcja na poziom w drzewie +
--jeden gracz maks w jednym tournamencie jednocześnie
--dzieci te samo id_turnieju co ojciec
--  dzieci ten sam poziom
-- duel na który wskazuje matchup (winner z lewego i prawego childa)
-- czy duel rozpoczyna sie po dzieciach

-- clanClan chat
-- playerChat (jakoś pilnować sender receiver)
-- znajomi danego gracza 
-- historia pojedynków danego gracza (nie z Wara)
-- 	nierankingowe
-- 	rankingowe
-- 	wszystkie
-- to samo z Wara
-- aktywne zaproszenia do znajomych i do klanu (otrzymane/wysłane)
-- liczenie rankingu
-- VIEW
-- wyświetl ranking
-- dla każdego gracza wyświetl obency klan
-- dla każdego gracza wyświetl stare klany
-- wyświetl klan
-- dodawanie playera z rulem
-- usuwanie playera z rulem
-- dodwanie klanu z rulem
-- usuwanie klanu z rulem
-- TRIGGERS
-- opóźnione do: dodawania klanu, dodawanie playera
-- ARCHIWIZACJA 