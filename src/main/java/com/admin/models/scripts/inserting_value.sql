INSERT INTO `User` (`username`, `full_name`, `address`, `dob`, `gender`, `email`, `password`, `is_admin`, `status`, `created_at`)
VALUES
("hollow_knight", "The Knight", "Forgotten Crossroads", "1995-05-12", "male", "knight@hollowknight.com", "password1", FALSE, "offline", "2024-04-04 08:30:00"),
("hornet", "Hornet", "Kingdom's Edge", "1993-08-21", "female", "hornet@hollowknight.com", "password2", FALSE, "online", "2024-02-01 08:45:00"),
("quirrel", "Quirrel", "Greenpath", "1992-03-15", "male", "quirrel@hollowknight.com", "password3", FALSE, "offline", "2024-08-01 09:00:00"),
("zelda", "Zelda", "Dirtmouth", "1994-07-19", "female", "zelda@hollowknight.com", "password4", TRUE, "offline", "2024-04-01 09:15:00"),
("myla", "Myla", "Crystal Peak", "1994-11-11", "female", "myla@hollowknight.com", "password5", FALSE, "online", "2024-05-01 09:30:00"),
("mk", "Mantis Knight", "Mantis Village", "1990-12-05", "female", "mantisknight@hollowknight.com", "password6", FALSE, "offline", "2024-06-01 09:45:00"),
("bafanada", "Bafanada", "Resting Grounds", "1991-06-30", "female", "bafanada@hollowknight.com", "password7", FALSE, "offline", "2024-07-01 10:00:00"),
("zelda_night", "Zelda Night", "City of Tears", "1990-10-10", "female", "zelda_night@hollowknight.com", "password8", TRUE, "online", "2024-09-01 10:15:00"),
("brother", "Brother Grimm", "Kingdom's Edge", "1989-04-22", "male", "brother@hollowknight.com", "password9", FALSE, "locked", "2024-10-01 10:30:00"),
("elina", "Elina", "The White Palace", "1994-12-10", "female", "elina@hollowknight.com", "password10", FALSE, "offline", "2024-11-01 10:45:00");

INSERT INTO `Login_History` (`user_id`, `login_time`)
VALUES
(1, "2024-12-03 08:30:00"),
(2, "2024-12-03 08:45:00"),
(3, "2024-12-03 09:00:00"),
(4, "2024-12-03 09:15:00"),
(5, "2024-12-03 09:30:00"),
(6, "2024-12-03 09:45:00"),
(7, "2024-12-03 10:00:00"),
(8, "2024-12-03 10:15:00"),
(9, "2024-12-03 10:30:00"),
(10, "2024-12-03 10:45:00");

INSERT INTO `Chat_Groups` (`group_name`, `created_at`)
VALUES
("Hollow Knights", "2024-12-01 12:00:00"),
("The Wanderers", "2024-12-01 12:30:00"),
("Nailmasters", "2024-12-01 13:00:00"),
("The Pale King", "2024-12-01 13:30:00"),
("Explorers", "2024-12-01 14:00:00"),
("Hall of Gods", "2024-12-01 14:30:00"),
("The Mantis Clan", "2024-12-01 15:00:00"),
("The Forgotten", "2024-12-01 15:30:00"),
("Crystal Caverns", "2024-12-01 16:00:00"),
("Shadebound", "2024-12-01 16:30:00");

INSERT INTO `Friends` (`user_id`, `friend_id`, `status`, `created_at`)
VALUES
(1, 2, "accepted", "2024-12-01 12:00:00"),
(2, 1, "accepted", "2024-12-01 12:00:00"),
(1, 3, "accepted", "2024-12-01 12:30:00"),
(3, 1, "accepted", "2024-12-01 12:30:00"),
(2, 4, "accepted", "2024-12-01 13:00:00"),
(4, 2, "accepted", "2024-12-01 13:00:00"),
(3, 5, "accepted", "2024-12-01 13:30:00"),
(5, 3, "accepted", "2024-12-01 13:30:00"),
(4, 6, "accepted", "2024-12-01 14:00:00"),
(6, 4, "accepted", "2024-12-01 14:00:00");

INSERT INTO `Friends_Request` (`sender_id`, `receiver_id`)
VALUES
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 6),
(6, 7),
(7, 8),
(8, 9),
(9, 10),
(10, 1);

INSERT INTO `Messages` (`sender_id`, `receiver_id`, `group_id`, `content`, `sent_time`)
VALUES
(1, 2, 1, "Hello Hornet, how are you?", "2024-12-01 12:00:00"),
(2, 1, 1, "I am fine, Knight. How about you?", "2024-12-01 12:15:00"),
(1, 3, 2, "Quirrel, have you found any new areas to explore?", "2024-12-01 12:30:00"),
(3, 1, 2, "Not yet, Knight, but I will let you know.", "2024-12-01 12:45:00"),
(4, 5, 3, "Myla, I have an idea for a new quest!", "2024-12-01 13:00:00"),
(5, 4, 3, "What is it, Zelda?", "2024-12-01 13:15:00"),
(6, 7, 4, "Mantis Knight, are you interested in joining our group?", "2024-12-01 13:30:00"),
(7, 6, 4, "I might be interested, let's talk later.", "2024-12-01 13:45:00"),
(8, 9, 5, "Brother Grimm, there is something strange happening in the city.", "2024-12-01 14:00:00"),
(9, 8, 5, "We need to investigate further.", "2024-12-01 14:15:00");

INSERT INTO `User_Messages` (`message_id`, `user_id`, `is_visible`)
VALUES
(1, 1, TRUE),
(1, 2, TRUE),
(2, 3, TRUE),
(2, 1, TRUE),
(3, 4, TRUE),
(3, 5, TRUE),
(4, 6, TRUE),
(4, 7, TRUE),
(5, 8, TRUE),
(5, 9, TRUE);

INSERT INTO `Group_Members` (`group_id`, `user_id`, `joined_at`)
VALUES
(1, 1, "2024-12-01 12:00:00"),
(1, 2, "2024-12-01 12:00:00"),
(2, 3, "2024-12-01 12:30:00"),
(2, 1, "2024-12-01 12:30:00"),
(3, 4, "2024-12-01 13:00:00"),
(3, 5, "2024-12-01 13:00:00"),
(4, 6, "2024-12-01 13:30:00"),
(4, 7, "2024-12-01 13:30:00"),
(5, 8, "2024-12-01 14:00:00"),
(5, 9, "2024-12-01 14:00:00"),
(6, 10, "2024-12-01 14:30:00"),
(6, 1, "2024-12-01 14:30:00"),
(7, 2, "2024-12-01 15:00:00"),
(7, 3, "2024-12-01 15:00:00");

INSERT INTO `Group_Admins` (`group_id`, `user_id`)
VALUES
(1, 1),
(2, 2),
(3, 4),
(4, 5),
(5, 6),
(6, 7),
(7, 8),
(8, 9),
(9, 10),
(10, 1);

INSERT INTO `Spam_Reports` (`user_id`, `report_time`, `reason`)
VALUES
(1, "2024-12-01 16:00:00", "Spam messages in group"),
(2, "2024-12-01 16:30:00", "Inappropriate behavior"),
(3, "2024-12-01 17:00:00", "Sending irrelevant content"),
(4, "2024-12-01 17:30:00", "Offensive language"),
(5, "2024-12-01 18:00:00", "Abusive behavior"),
(6, "2024-12-01 18:30:00", "Sending too many messages"),
(7, "2024-12-01 19:00:00", "Unwanted advertisements"),
(8, "2024-12-01 19:30:00", "Excessive pinging"),
(9, "2024-12-01 20:00:00", "Harassment"),
(10, "2024-12-01 20:30:00", "Trolling");

INSERT INTO `User_Activity` (`user_id`, `activity_type`, `description`, `activity_time`)
VALUES
(1, "app open", "Opened the Hollow Knight app", "2024-12-01 12:00:00"),
(2, "chat", "Sent a message in the group Hollow Knights", "2024-12-01 12:15:00"),
(3, "group join", "Joined the group The Wanderers", "2024-12-01 12:30:00"),
(4, "chat", "Sent a message in the group Nailmasters", "2024-12-01 13:00:00"),
(5, "app open", "Opened the Hollow Knight app", "2024-12-01 13:30:00"),
(6, "group join", "Joined the group The Pale King", "2024-12-01 14:00:00"),
(7, "chat", "Sent a message in the group Explorers", "2024-12-01 14:30:00"),
(8, "app open", "Opened the Hollow Knight app", "2024-12-01 15:00:00"),
(9, "chat", "Sent a message in the group Hall of Gods", "2024-12-01 15:30:00"),
(10, "group join", "Joined the group The Forgotten", "2024-12-01 16:00:00");
