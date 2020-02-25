# SQL data definition language (DDL)

```sqlite
CREATE TABLE IF NOT EXISTS `Activity`
(
    `activity_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `name`        TEXT COLLATE NOCASE
);

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

CREATE INDEX IF NOT EXISTS `index_Record_start` ON `Record` (`start`);

CREATE INDEX IF NOT EXISTS `index_Record_end` ON `Record` (`end`);

CREATE INDEX IF NOT EXISTS `index_Record_notes` ON `Record` (`notes`);
```

[Download](ddl.sql)