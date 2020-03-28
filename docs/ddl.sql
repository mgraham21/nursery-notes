CREATE TABLE IF NOT EXISTS `Activity`
(
    `activity_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `created`     INTEGER                           NOT NULL,
    `name`        TEXT COLLATE NOCASE,
    `text`        TEXT                              NOT NULL
);

CREATE INDEX IF NOT EXISTS `index_Activity_created` ON `Activity` (`created`);

CREATE TABLE IF NOT EXISTS `Child`
(
    `child_id`   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `last_name`  TEXT COLLATE NOCASE,
    `first_name` TEXT COLLATE NOCASE,
    `birth_date` INTEGER
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_Child_last_name_first_name` ON `Child` (`last_name`, `first_name`);

CREATE INDEX IF NOT EXISTS `index_Child_birth_date` ON `Child` (`birth_date`);

CREATE TABLE IF NOT EXISTS `Record`
(
    `record_id`   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `child_id`    INTEGER                           NOT NULL,
    `activity_id` INTEGER                           NOT NULL,
    `start`       INTEGER,
    `end`         INTEGER,
    `notes`       TEXT,
    FOREIGN KEY (`child_id`) REFERENCES `Child` (`child_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`activity_id`) REFERENCES `Activity` (`activity_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_Record_child_id_activity_id` ON `Record` (`child_id`, `activity_id`);

CREATE INDEX IF NOT EXISTS `index_Record_child_id` ON `Record` (`child_id`);

CREATE INDEX IF NOT EXISTS `index_Record_activity_id` ON `Record` (`activity_id`);

CREATE INDEX IF NOT EXISTS `index_Record_start` ON `Record` (`start`);

CREATE INDEX IF NOT EXISTS `index_Record_end` ON `Record` (`end`);

CREATE INDEX IF NOT EXISTS `index_Record_notes` ON `Record` (`notes`);