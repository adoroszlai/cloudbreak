-- // CLOUD-47683 new columns for detailed stack and cluster statuses
-- Migration SQL that makes the change goes here.

--ALTER TABLE cloudbreakevent ADD COLUMN statustype varchar(255);
--ALTER TABLE cloudbreakevent ADD COLUMN detailedStackStatus varchar(255);

CREATE SEQUENCE stackstatus_id_seq START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE stackstatus (
    id BIGINT NOT NULL,
    stack_id bigint,
    status character varying(255),
    detailedstackstatus character varying(255)
);

--ALTER TABLE stack
--    ADD COLUMN stackstatus_id bigint;

ALTER TABLE stackstatus
    ADD CONSTRAINT PK_stackstatus PRIMARY KEY (id),
    ALTER COLUMN id SET DEFAULT nextval ('stackstatus_id_seq');

-- //@UNDO
-- SQL to undo the change goes here.

-- ALTER TABLE cloudbreakevent DROP COLUMN statustype;
-- ALTER TABLE cloudbreakevent DROP COLUMN detailedStackStatus;

DROP TABLE stackstatus;
DROP SEQUENCE stackstatus_id_seq;
