INSERT INTO credential(id,password,is_enable,rol) VALUES (1, "Developer",1,"User"),(2,"123",1,"User");


INSERT INTO member (id,user_name,email,credential_id) VALUES (1, "Kiyo","kiyo@dev.com",1),(2,"Johan","johan.apellido@gmail.com",2);


INSERT INTO chat(id,user1_id,user2_id) VALUES(1,1,2);


INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (1,"hello brother","2024-12-14 13:20:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (2,"Hi, how are you?","2024-12-14 13:21:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (3,"I was wondering if you completed the report.","2024-12-14 13:21:10",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (4,"Let me know if you need help with it.","2024-12-14 13:21:20",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (5,"I'm good, thanks for asking.","2024-12-14 13:22:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (6,"I did complete it, but there's one issue.","2024-12-14 13:22:15",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (7,"What issue?","2024-12-14 13:22:30",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (8,"The summary doesn't align with the data.","2024-12-14 13:22:45",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (9,"I think the calculations were off.","2024-12-14 13:23:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (10,"Hmm, I see. Let me double-check them later.","2024-12-14 13:23:30",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (11,"Sure. Also, we need to prepare for the meeting.","2024-12-14 13:24:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (12,"The meeting is tomorrow, right?","2024-12-14 13:24:15",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (13,"Yes, but the slides need some updates.","2024-12-14 13:24:30",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (14,"Alright, I'll handle that tonight.","2024-12-14 13:25:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (15,"Thanks! I'll send you the data you'll need.","2024-12-14 13:25:30",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (16,"Great. By the way, any plans for lunch?","2024-12-14 13:26:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (17,"Not yet. Want to grab something together?","2024-12-14 13:26:15",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (18,"Sure, I'll message you when I'm free.","2024-12-14 13:26:30",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (19,"Sounds good. Talk soon.","2024-12-14 13:27:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (20,"Alright. Later!","2024-12-14 13:27:30",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (21,"By the way, bring your laptop.","2024-12-14 13:27:40",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (22,"Got it.","2024-12-14 13:28:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (23,"Hey, did you finish the slides for the meeting?","2024-12-15 09:00:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (24,"Yes, I sent them to your email.","2024-12-15 09:15:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (25,"Great! I'll review them this afternoon.","2024-12-15 09:20:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (26,"Let me know if anything needs changing.","2024-12-15 09:25:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (27,"Sure thing.","2024-12-15 09:30:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (28,"Good morning! Did you see the feedback from the client?","2024-12-16 08:45:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (29,"Not yet, is it in the shared folder?","2024-12-16 08:50:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (30,"Yes, I uploaded it yesterday.","2024-12-16 08:55:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (31,"I'll check it out before our call.","2024-12-16 09:00:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (32,"By the way, they mentioned a new feature request.","2024-12-16 09:05:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (33,"Alright, let’s discuss that during the meeting.","2024-12-16 09:10:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (34,"Sounds good.","2024-12-16 09:15:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (35,"Hi! How did the client call go?","2024-12-17 10:00:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (36,"It went well. They loved the new layout.","2024-12-17 10:15:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (37,"That's awesome! Any next steps?","2024-12-17 10:20:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (38,"Yes, they want us to prioritize the new feature.","2024-12-17 10:25:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (39,"Alright, I'll start working on it tomorrow.","2024-12-17 10:30:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (40,"Let me know if you need any help.","2024-12-17 10:35:00",1,1,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (41,"Will do. Thanks!","2024-12-17 10:40:00",1,2,"READ");
INSERT INTO message(id,content,date_creation,chat_id,creator_id,status) VALUES (42,"Hey, can we push the deadline by a week?","2024-12-18 15:00:00",1,2,"UNREAD");

-- 
-- 
-- 
