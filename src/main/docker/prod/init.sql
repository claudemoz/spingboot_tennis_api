CREATE SEQUENCE player_id_seq;

CREATE TABLE player
(
    id integer NOT NULL DEFAULT nextval('player_id_seq'),
    last_name character varying(50) NOT NULL,
    first_name character varying(50) NOT NULL,
    birth_date date NOT NULL,
    points integer NOT NULL,
    rank integer NOT NULL,
    PRIMARY KEY (id)
);

ALTER SEQUENCE player_id_seq OWNED BY player.id;

ALTER TABLE IF EXISTS public.player OWNER to postgres;


INSERT INTO public.player(last_name, first_name, birth_date, points, rank) VALUES 
('Djokovic', 'Novak', '1987-05-22', 9855, 1),
('Alcaraz', 'Carlos', '2003-05-05', 8805, 2),
('Sinner', 'Jannik', '2001-08-16', 8270, 3),
('Medvedev', 'Daniil', '1996-02-11', 8015, 4),
('Rublev', 'Andrey', '1997-10-20', 5110, 5),
('Zverev', 'Alexander', '1997-04-20', 5085, 6),
('Rune', 'Holger', '2003-04-29', 3700, 7),
('Hurkacz', 'Hubert', '1997-02-11', 3395, 8),
('De Minaur', 'Alex', '1999-02-17', 3210, 9),
('Fritz', 'Taylor', '1997-10-28', 3150, 10);


CREATE SEQUENCE user_id_seq;

CREATE TABLE "user" (
    id integer NOT NULL DEFAULT nextval('user_id_seq'),
    login character varying(50) NOT NULL,
    password character varying(60) NOT NULL,
    last_name character varying(50) NOT NULL,
    first_name character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

ALTER SEQUENCE user_id_seq OWNED BY player.id;

ALTER TABLE IF EXISTS public.user OWNER to postgres;

INSERT INTO public.user(login, password, last_name, first_name) VALUES
('admin', '$2a$12$VLMmCnWg6g1ZWfctUUYpWeyfArfbPzlq1EC1hi5BPSQeJWMwjmpdy', 'Admin', 'Admin'),
('visitor', '$2a$12$ACcMbD/j30wmsucWNZpMaeJaO2w0tBIswOzDMOjZhVvEp6RzPhgWS', 'Doe', 'John');

CREATE TABLE "role"
(
    name character varying(50) NOT NULL,
    PRIMARY KEY (name)
);

INSERT INTO public.role(name)
	VALUES ('ROLE_ADMIN');

INSERT INTO public.role(name)
	VALUES ('ROLE_USER');

ALTER TABLE IF EXISTS public.user_role OWNER to postgres;

CREATE TABLE user_role
(
    user_id bigint NOT NULL,
    role_name character varying(50) NOT NULL,
    CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_name),
    CONSTRAINT fk_role_name FOREIGN KEY (role_name)
        REFERENCES public.role (name),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
        REFERENCES public.user (id)
);

ALTER TABLE IF EXISTS public.user_role OWNER to postgres;

INSERT INTO public.user_role(user_id, role_name)
	VALUES (1, 'ROLE_ADMIN');

INSERT INTO public.user_role(user_id, role_name)
	VALUES (1, 'ROLE_USER');

INSERT INTO public.user_role(user_id, role_name)
	VALUES (2, 'ROLE_USER');