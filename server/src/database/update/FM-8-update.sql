BEGIN;

ALTER TABLE match_game ADD COLUMN round INT NOT NULL DEFAULT 0;

COMMIT;