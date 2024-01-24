--
-- PostgreSQL database dump

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;


SET search_path = public, pg_catalog;


SELECT pg_catalog.setval('account_id_seq', 4, true);
SELECT pg_catalog.setval('server_id_seq', 4, true);
SELECT pg_catalog.setval('category_id_seq', 4, true);
SELECT pg_catalog.setval('channel_id_seq', 4, true);
SELECT pg_catalog.setval('message_id_seq', 7, true);
SELECT pg_catalog.setval('attachment_id_seq', 4, true);


ALTER TABLE account DISABLE TRIGGER ALL;

-- Insert mock data into account table
COPY account (id, username, email, password) FROM stdin;
1   user1   user1@example.com  password1
2   user2   user2@example.com  password2
3   user3   user3@example.com  password3
\.

ALTER TABLE account ENABLE TRIGGER ALL;

ALTER TABLE category DISABLE TRIGGER ALL;

-- Insert mock data into category table
COPY category (id, name) FROM stdin;
1   Category 1
2   Category 2
3   Category 3
\.

ALTER TABLE category ENABLE TRIGGER ALL;

ALTER TABLE channel DISABLE TRIGGER ALL;

-- Insert mock data into channel table
COPY channel (id, name) FROM stdin;
1   General
2   Random
3   Announcements
\.

ALTER TABLE channel ENABLE TRIGGER ALL;

ALTER TABLE server DISABLE TRIGGER ALL;

-- Insert mock data into server table
COPY server (id, name, owner_id) FROM stdin;
1   Server 1    1
2   Server 2    2
3   Server 3    3
\.

ALTER TABLE server ENABLE TRIGGER ALL;


ALTER TABLE serverChannel DISABLE TRIGGER ALL;

-- Insert mock data into serverChannel table
COPY serverChannel (channel_id, category_id) FROM stdin;
1   1
2   1
3   2
\.

ALTER TABLE serverChannel ENABLE TRIGGER ALL;

ALTER TABLE message DISABLE TRIGGER ALL;

-- Insert mock data into message table
COPY message (id, text, author_id, timestamp, channel_id) FROM stdin;
1   'Hello, everyone!'     1   '2024-01-24 12:00:00'   1
2   'How are you doing?'   2   '2024-01-24 12:05:00'   1
3   'Welcome to the server!'   3   '2024-01-24 12:10:00'   2
4   'Any plans for the weekend?'   1   '2024-01-24 12:15:00'   2
5   'Important announcement!'   2   '2024-01-24 12:20:00'   3
6   'Let''s discuss the upcoming events.'   3   '2024-01-24 12:25:00'   3
\.

ALTER TABLE message ENABLE TRIGGER ALL;

ALTER TABLE attachment DISABLE TRIGGER ALL;

-- Insert mock data into attachment table
COPY attachment (id, url, message_id) FROM stdin;
1   'http://example.com/attachment1'   1
2   'http://example.com/attachment2'   2
3   'http://example.com/attachment3'   3
\.

ALTER TABLE attachment ENABLE TRIGGER ALL;

ALTER TABLE account_server DISABLE TRIGGER ALL;

-- Insert mock data into account_server table
COPY account_server (server_id, account_id) FROM stdin;
1   1
1   2
2   2
3   3
\.

ALTER TABLE account_server ENABLE TRIGGER ALL;

--
-- Name: actor_actor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--
