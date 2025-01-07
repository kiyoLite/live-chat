INSERT INTO credential(id, password, is_enable,rol) VALUES (1, "Test_Developer", 1,"User");
INSERT INTO member(id, email,user_name, credential_id) VALUES (1, "test_kiyo@dev.com","test_name_1", 1);
INSERT INTO chat(id, user1_id, user2_id) VALUES (1, 1, 1);

