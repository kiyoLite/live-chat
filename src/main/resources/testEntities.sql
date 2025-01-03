INSERT INTO credential(id, password, is_enable) VALUES (1, "Test_Developer", 1);
INSERT INTO credential(id, password, is_enable) VALUES (2, "Test_123", 1);
INSERT INTO member(id, email,user_name, credential_id) VALUES (1, "test_kiyo@dev.com","test_name_1", 1);
INSERT INTO member(id, email,user_name, credential_id) VALUES (2, "test_johan.apellido@gmail.com","test_name_2", 2);
INSERT INTO chat(id, user1_id, user2_id) VALUES (1, 1, 2);

