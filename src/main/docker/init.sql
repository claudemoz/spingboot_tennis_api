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


INSERT INTO public.player(last_name, first_name, birth_date, points, rank)
	VALUES ('Nadal', 'Rafael', '1986-06-03', 5000, 1);

INSERT INTO public.player(last_name, first_name, birth_date, points, rank)
    	VALUES ('Djokovic', 'Novak', '1987-05-22', 4000, 2);

INSERT INTO public.player(last_name, first_name, birth_date, points, rank)
    	VALUES ('Federer', 'Roger', '1981-08-08', 3000, 3);

INSERT INTO public.player(last_name, first_name, birth_date, points, rank)
    	VALUES ('Murray', 'Andy', '1987-05-15', 2000, 4);

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

INSERT INTO public.user(login, password, last_name, first_name)
	VALUES ('admin', '$2a$12$RkcdJn2kLrAS9fmvDv/CWehqID8nB3XBWXOtazhQ2PY1ZFwDB3L76', 'Admin', 'Admin');

INSERT INTO public.user(login, password, last_name, first_name)
	VALUES ('user', '$2a$12$VRnUGZfeEsWHG9jb7NyvQuhpISK65N2LtWyqXAi5t1CBWIQ34uRNa', 'Doe', 'John');


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