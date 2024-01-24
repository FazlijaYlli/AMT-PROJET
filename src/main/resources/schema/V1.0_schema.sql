-- DROP --

-- Drop Tables

-- TODO modify to fit project

DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS server;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS channel;
DROP TABLE IF EXISTS privateChannel;
DROP TABLE IF EXISTS serverChannel;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS attachment;

-- DROP SEQUENCE IF EXISTSS

-- TODO modify to fit project

DROP SEQUENCE IF EXISTS account_id_seq;
DROP SEQUENCE IF EXISTS server_id_seq;
DROP SEQUENCE IF EXISTS category_id_seq;
DROP SEQUENCE IF EXISTS channel_id_seq;
/*DROP SEQUENCE IF EXISTS privateChannel_id_seq;
DROP SEQUENCE IF EXISTS serverChannel_id_seq;*/
DROP SEQUENCE IF EXISTS message_id_seq;
DROP SEQUENCE IF EXISTS attachment_id_seq;


-- CREATE --

--
-- PostgreSQL database dump
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE OR REPLACE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

CREATE SEQUENCE account_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE server_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE category_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE channel_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

/*CREATE SEQUENCE privateChannel_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE serverChannel_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;*/

CREATE SEQUENCE message_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE attachment_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.account_id_seq OWNER TO postgres;

--
-- Name: account; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE account (
    id integer DEFAULT nextval('account_id_seq'::regclass) NOT NULL,
    username varchar(45) NOT NULL,
    email varchar(255) NOT NULL,
    password varchar(80)
);

ALTER TABLE ONLY account
    ADD CONSTRAINT pk_account PRIMARY KEY (id);

ALTER TABLE public.account OWNER TO postgres;

--
-- Name: Channel; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--


ALTER TABLE public.channel_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE TABLE channel (
    id integer DEFAULT nextval('channel_id_seq'::regclass) NOT NULL,
    name varchar(45) NOT NULL
);

ALTER TABLE ONLY channel
    ADD CONSTRAINT pk_channel PRIMARY KEY (id);

ALTER TABLE public.channel OWNER TO postgres;


--
-- Name: PrivateChannel; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--


--ALTER TABLE public.privateChannel_id_seq OWNER TO postgres;


CREATE TABLE privateChannel (
    --privateChannel_id integer DEFAULT nextval('privateChannel_id_seq'::regclass) NOT NULL,
    channel_id integer DEFAULT NOT NULL
);

ALTER TABLE privateChannel
    ADD CONSTRAINT fk_channel_id FOREIGN KEY (channel_id) REFERENCES channel (id);


ALTER TABLE public.privateChannel OWNER TO postgres;


--
-- Name: ServerChannel; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

--ALTER TABLE public.serverChannel_id_seq OWNER TO postgres;


CREATE TABLE serverChannel (
    --serverChannel_id integer DEFAULT nextval('serverChannel_id_seq'::regclass) NOT NULL,
    channel_id integer DEFAULT NOT NULL,
    category_id integer DEFAULT NOT NULL
);

ALTER TABLE serverChannel
    ADD CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category (id),
    ADD CONSTRAINT fk_channel_id FOREIGN KEY (channel_id) REFERENCES channel (id)

ALTER TABLE public.serverChannel OWNER TO postgres;


--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE public.category_id_seq OWNER TO postgres;

CREATE TABLE category (
    id integer DEFAULT nextval('category_id_seq'::regclass) NOT NULL,
    name varchar(45) NOT NULL
);

ALTER TABLE ONLY category
    ADD CONSTRAINT pk_category PRIMARY KEY (id);

ALTER TABLE public.category OWNER TO postgres;


--
-- Name: server; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE public.server_id_seq OWNER TO postgres;


CREATE TABLE server (
    id integer DEFAULT nextval('server_id_seq'::regclass) NOT NULL,
    name varchar(45) NOT NULL,
    owner_id integer NOT NULL
);

ALTER TABLE ONLY server
    ADD CONSTRAINT pk_server PRIMARY KEY (id);


ALTER TABLE server
    ADD CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES account (id);


ALTER TABLE public.server OWNER TO postgres;


--
-- Name: message; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE public.message_id_seq OWNER TO postgres;

CREATE TABLE message (
    id integer DEFAULT nextval('message_id_seq'::regclass) NOT NULL,
    text integer NOT NULL,
    author_id integer NOT NULL,
    timestamp timestamp NOT NULL,
    channel_id integer NOT NULL
);

ALTER TABLE ONLY message
    ADD CONSTRAINT pk_message PRIMARY KEY (id);

ALTER TABLE message
    ADD CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES account (id),
    ADD CONSTRAINT fk_channel_id FOREIGN KEY (channel_id) REFERENCES channel (id);


ALTER TABLE public.message OWNER TO postgres;



--
-- Name: attachment; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

ALTER TABLE public.attachment_id_seq OWNER TO postgres;

CREATE TABLE attachment (
    id integer DEFAULT nextval('attachment_id_seq'::regclass) NOT NULL,
    url text NOT NULL,
    message_id integer NOT NULL
);

ALTER TABLE ONLY attachment
    ADD CONSTRAINT pk_attachment PRIMARY KEY (id);

ALTER TABLE attachment
    ADD CONSTRAINT fk_message_id FOREIGN KEY (message_id) REFERENCES message (id);


ALTER TABLE public.attachment OWNER TO postgres;


CREATE TABLE account_server (
    server_id integer DEFAULT NOT NULL,
    account_id integer DEFAULT NOT NULL
);

ALTER TABLE ONLY account_server
    ADD CONSTRAINT pk_account_server PRIMARY KEY (server_id, account_id);

ALTER TABLE account_server
    ADD CONSTRAINT fk_server_id FOREIGN KEY (server_id) REFERENCES server (id),
    ADD CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE public.account_server OWNER TO postgres;

--

CREATE TABLE account_privateChannel (
    channel_id integer DEFAULT NOT NULL,
    account_id integer DEFAULT NOT NULL
);

ALTER TABLE ONLY account_privateChannel
    ADD CONSTRAINT pk_account_server PRIMARY KEY (channel_id, account_id);

ALTER TABLE account_privateChannel
    ADD CONSTRAINT fk_channel_id FOREIGN KEY (channel_id) REFERENCES channel (id),
    ADD CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE public.account_privateChannel OWNER TO postgres;

--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;