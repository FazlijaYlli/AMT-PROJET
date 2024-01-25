-- Insert mock data into account table
INSERT INTO account (id, username, email, password) VALUES
                                                        (1, 'user1', 'user1@example.com', '$2a$10$Kn2iOHTmVBJXFxy3ZFi3BOS7XVKwEmvv.6ZgnJg54BwhMPh53JLAK'),
                                                        (2, 'user2', 'user2@example.com', '$2a$10$Kn2iOHTmVBJXFxy3ZFi3BOS7XVKwEmvv.6ZgnJg54BwhMPh53JLAK'),
                                                        (3, 'user3', 'user3@example.com', '$2a$10$Kn2iOHTmVBJXFxy3ZFi3BOS7XVKwEmvv.6ZgnJg54BwhMPh53JLAK');


-- Insert mock data into channel table
INSERT INTO channel (id, name) VALUES
                                   (1, 'General'),
                                   (2, 'Random'),
                                   (3, 'Announcements'),
                                   (4, 'Private 1'),
                                   (5, 'Private 2'),
                                   (6, 'Private 3');

-- Insert mock data into server table
INSERT INTO server (id, name, owner_id) VALUES
                                            (1, 'Server 1', 1),
                                            (2, 'Server 2', 2),
                                            (3, 'Server 3', 3);

-- Insert mock data into category table
INSERT INTO category (id, name, server_id) VALUES
                                               (1, 'Category 1', 1),
                                               (2, 'Category 2', 2),
                                               (3, 'Category 3', 3);
-- Insert mock data into serverChannel table
INSERT INTO serverChannel (channel_id, category_id) VALUES
                                                        (1, 1),
                                                        (2, 2),
                                                        (3, 3);

-- Insert mock data into message table
INSERT INTO message (id, text, author_id, timestamp, channel_id) VALUES
                                                                     (1, 'Hello everyone!', 1, '2024-01-24 12:00:00', 1),
                                                                     (2, 'How are you doing', 2, '2024-01-24 12:05:00', 1),
                                                                     (3, 'Welcome to the server!', 3, '2024-01-24 12:10:00', 2),
                                                                     (4, 'Any plans for the weekend?', 1, '2024-01-24 12:15:00', 2),
                                                                     (5, 'Important announcement!', 2, '2024-01-24 12:20:00', 3),
                                                                     (6, 'Let''s discuss the upcoming events.', 3, '2024-01-24 12:25:00', 3);

-- Insert mock data into attachment table
INSERT INTO attachment (id, url, message_id) VALUES
                                                 (1, 'http://example.com/attachment1', 1),
                                                 (2, 'http://example.com/attachment2', 2),
                                                 (3, 'http://example.com/attachment3', 3);

-- Insert mock data into account_server table
INSERT INTO account_server (server_id, account_id) VALUES
                                                       (1, 1),
                                                       (1, 2),
                                                       (2, 2),
                                                       (3, 3);


SELECT pg_catalog.setval('account_id_seq', 50, true);
SELECT pg_catalog.setval('attachment_id_seq', 50, true);
SELECT pg_catalog.setval('category_id_seq', 50, true);
SELECT pg_catalog.setval('channel_id_seq', 50, true);
SELECT pg_catalog.setval('message_id_seq', 50, true);
SELECT pg_catalog.setval('server_id_seq', 50, true);