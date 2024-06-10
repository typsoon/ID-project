CREATE OR REPLACE FUNCTION prevent_delete_update()
RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'DELETE and UPDATE operations are not allowed on this table';
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION prevent_delete()
RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'DELETE operation are not allowed on this table';
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION prevent_invalid_update_clanwars()
RETURNS TRIGGER AS $$
BEGIN
    IF (OLD.clan1_ID IS DISTINCT FROM NEW.clan1_ID OR
        OLD.clan2_ID IS DISTINCT FROM NEW.clan2_ID OR
        OLD.date_from IS DISTINCT FROM NEW.date_from) THEN
        RAISE EXCEPTION 'Only the outcome column can be updated';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION prevent_invalid_update_players()
RETURNS TRIGGER AS $$
BEGIN
    IF (OLD.login IS DISTINCT FROM NEW.login OR
        OLD.password_hash IS DISTINCT FROM NEW.password_hash) THEN
        IF (OLD.ranking_base IS DISTINCT FROM NEW.ranking_base) THEN
            RAISE EXCEPTION 'Only the login and password_hash columns can be updated';
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION prevent_invalid_update_duels()
RETURNS TRIGGER AS $$
BEGIN
    IF (OLD.date_to IS DISTINCT FROM NEW.date_to OR
        OLD.outcome IS DISTINCT FROM NEW.outcome) THEN
        IF (OLD.sender IS DISTINCT FROM NEW.sender OR
            OLD.receiver IS DISTINCT FROM NEW.receiver OR
            OLD.date_from IS DISTINCT FROM NEW.date_from) THEN
            RAISE EXCEPTION 'Only the date_to and outcome columns can be updated';
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION prevent_invalid_update_playerclan()
RETURNS TRIGGER AS $$
BEGIN
    IF (NEW.date_to IS DISTINCT FROM OLD.date_to OR
        (NEW.who_kicked IS DISTINCT FROM OLD.who_kicked AND OLD.who_kicked IS NULL)) THEN
        IF (NEW.date_from IS DISTINCT FROM OLD.date_from OR
            NEW.clan_ID IS DISTINCT FROM OLD.clan_ID OR
            NEW.player_ID IS DISTINCT FROM OLD.player_ID OR
            NEW.who_accepted IS DISTINCT FROM OLD.who_accepted OR
            (NEW.who_kicked IS DISTINCT FROM OLD.who_kicked AND OLD.who_kicked IS NOT NULL)) THEN
            RAISE EXCEPTION 'Only the date_to and who_kicked columns can be updated';
        END IF;
    ELSE
        RAISE EXCEPTION 'Only the date_to and who_kicked columns can be updated';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION prevent_invalid_update_friends()
RETURNS TRIGGER AS $$
BEGIN
    IF (NEW.date_to IS DISTINCT FROM OLD.date_to) THEN
        IF (NEW.player1_ID IS DISTINCT FROM OLD.player1_ID OR
            NEW.player2_ID IS DISTINCT FROM OLD.player2_ID OR
            NEW.date_from IS DISTINCT FROM OLD.date_from) THEN
            RAISE EXCEPTION 'Only the date_to column can be updated';
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION prevent_invalid_update_challenges()
RETURNS TRIGGER AS $$
BEGIN
    IF (NEW.date_to IS DISTINCT FROM OLD.date_to) THEN
        IF (NEW.date_from IS DISTINCT FROM OLD.date_from OR
            NEW.objective IS DISTINCT FROM OLD.objective OR
            NEW.description IS DISTINCT FROM OLD.description) THEN
            RAISE EXCEPTION 'Only the date_to column can be updated in the Challenges table';
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;



-- Clans
CREATE TRIGGER prevent_delete_update_clans
BEFORE DELETE OR UPDATE ON Clans
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- ClanWars
CREATE TRIGGER prevent_delete_clanwars
BEFORE DELETE ON ClanWars
FOR EACH ROW EXECUTE FUNCTION prevent_delete();

CREATE TRIGGER trigger_prevent_invalid_update_clanwars
BEFORE UPDATE ON ClanWars
FOR EACH ROW
EXECUTE FUNCTION prevent_invalid_update_clanwars();


-- Players
CREATE TRIGGER prevent_delete_players
BEFORE DELETE OR UPDATE ON Players
FOR EACH ROW EXECUTE FUNCTION prevent_delete();

CREATE TRIGGER trigger_prevent_invalid_update_players
BEFORE UPDATE ON Players
FOR EACH ROW
EXECUTE FUNCTION prevent_invalid_update_players();


-- ClanChat
CREATE TRIGGER prevent_delete_update_clanchat
BEFORE DELETE OR UPDATE ON ClanChat
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- Duels
CREATE TRIGGER prevent_delete_duels
BEFORE DELETE ON Duels
FOR EACH ROW EXECUTE FUNCTION prevent_delete();

CREATE TRIGGER trigger_prevent_invalid_update_duels
BEFORE UPDATE ON Duels
FOR EACH ROW
EXECUTE FUNCTION prevent_invalid_update_duels();


-- ArchivedDuels
CREATE TRIGGER prevent_delete_update_archivedduels
BEFORE DELETE OR UPDATE ON ArchivedDuels
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();

-- DuelPoints
-- WE CAN DELETE OR UPDATE HERE

-- WarDuels
CREATE TRIGGER prevent_delete_update_warduels
BEFORE DELETE OR UPDATE ON WarDuels
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();

-- ArchivedWarDuels
CREATE TRIGGER prevent_delete_update_archivedwarduels
BEFORE DELETE OR UPDATE ON ArchivedWarDuels
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();

-- -- Applications
-- WE CAN DELETE OR UPDATE HERE


-- PlayerClan
CREATE TRIGGER prevent_delete_playerclan
BEFORE DELETE ON PlayerClan
FOR EACH ROW EXECUTE FUNCTION prevent_delete();

CREATE TRIGGER trigger_prevent_invalid_update_playerclan
BEFORE UPDATE ON PlayerClan
FOR EACH ROW
EXECUTE FUNCTION prevent_invalid_update_playerclan();


-- FriendsChat
CREATE TRIGGER prevent_delete_update_friendschat
BEFORE DELETE OR UPDATE ON FriendsChat
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();

-- Friends
CREATE TRIGGER prevent_delete_friends
BEFORE DELETE ON Friends
FOR EACH ROW EXECUTE FUNCTION prevent_delete();

CREATE TRIGGER trigger_prevent_invalid_update_friends
BEFORE UPDATE ON Friends
FOR EACH ROW
EXECUTE FUNCTION prevent_invalid_update_friends();


-- FriendsInvites
-- WE CAN DELETE OR UPDATE HERE


-- PlayerNickname
CREATE TRIGGER prevent_delete_update_playernickname
BEFORE DELETE OR UPDATE ON PlayerNickname
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- ClanName
CREATE TRIGGER prevent_delete_update_clanname
BEFORE DELETE OR UPDATE ON ClanName
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- Logos
CREATE TRIGGER prevent_delete_update_logos
BEFORE DELETE OR UPDATE ON Logos
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- ClanLogos
CREATE TRIGGER prevent_delete_update_clanlogos
BEFORE DELETE OR UPDATE ON ClanLogos
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- Roles
CREATE TRIGGER prevent_delete_update_roles
BEFORE DELETE OR UPDATE ON Roles
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- PlayerRole
CREATE TRIGGER prevent_delete_update_playerrole
BEFORE DELETE OR UPDATE ON PlayerRole
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- TournamentsName
CREATE TRIGGER prevent_delete_update_tournamentsname
BEFORE DELETE OR UPDATE ON TournamentsName
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- Tournaments
CREATE TRIGGER prevent_delete_update_tournaments
BEFORE DELETE OR UPDATE ON Tournaments
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- Challenges
CREATE TRIGGER prevent_delete_challenges
BEFORE DELETE ON Challenges
FOR EACH ROW EXECUTE FUNCTION prevent_delete();

CREATE TRIGGER trigger_prevent_invalid_update_challenges
BEFORE UPDATE ON Challenges
FOR EACH ROW
EXECUTE FUNCTION prevent_invalid_update_challenges();


-- PlayerChallenge
CREATE TRIGGER prevent_delete_update_playerchallenge
BEFORE DELETE OR UPDATE ON PlayerChallenge
FOR EACH ROW EXECUTE FUNCTION prevent_delete_update();


-- ewentualnie: usunąć i dodać trigger podczas archwizacji