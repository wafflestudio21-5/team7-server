INSERT into users (id, username, password, name, nickname, email, birth_date, phone_number) VALUES (1, 'hong', 'hong123', '홍길동', '홍길동', 'hong@naver.com', '2000-05-03', '01023453444'),
(2, 'doo', 'doo123', '황두현', 'doo', 'doo@naver.com', '1997-04-26', '01023446673');

INSERT into board_groups (id, name) VALUES (1, '백엔드'),
(2, '프론트엔드'),
(3, '프로그래밍 언어');

INSERT into boards (id, name, group_id) VALUES (1, '스프링', 1),
(2, '장고', 1),
(3, 'MySQL', 1),
(4, '코틀린', 3),
(5, '리액트', 2),
(6, '타입스크립트', 3),
(7, '자바스크립트', 3),
(8, '리덕스', 2);

INSERT into articles (id, title, content, created_at, user_id, board_id, allow_comments, is_notification) VALUES (1, '스프링이란', '스프링은 ..', '2023-01-01', 1, 1, true, true),
(2, 'test2', '.', '2023-01-02', 1, 1, true, false),
(3, 'test3', '.', '2023-01-03', 1, 1, true, false),
(4, 'test4', '.', '2023-01-04', 1, 1, true, false),
(5, 'test5', '.', '2023-01-05', 1, 1, true, false),
(6, 'test6', '.', '2023-01-06', 1, 1, true, false),
(7, 'test7', '.', '2023-01-07', 1, 1, true, false),
(8, 'test8', '.', '2023-01-08', 1, 1, true, false),
(9, 'test9', '.', '2023-01-09', 1, 1, true, false),
(10, 'test10', '.', '2023-01-10', 1, 1, true, false),
(11, 'test11', '.', '2023-01-11', 1, 1, true, false),
(12, 'test12', '.', '2023-01-12', 1, 1, true, false),
(13, 'test13', '.', '2023-01-13', 2, 1, true, false),
(16, 'test16', '.', '2023-01-14', 2, 1, true, false),
(15, 'test15', '.', '2023-01-14', 2, 1, true, false),
(14, 'test14', '.', '2023-01-14', 2, 1, true, false),
(17, 'A', '.', '2023-01-14', 1, 2, true, false),
(18, 'B', '.', '2023-01-14', 1, 2, true, false),
(19, 'C', '.', '2023-01-14', 1, 2, true, false);

INSERT into comments (id, content, last_modified, user_id, article_id) VALUES (1, '잘 읽었습니다.', CURRENT_TIMESTAMP, 2, 1);

INSERT into recomments (id, content, last_modified, user_id, comment_id) VALUES (1, '감사합니다.', CURRENT_TIMESTAMP, 1, 1);

INSERT into cafe (id, name, created_at, member_cnt) VALUES (1, '와플', CURRENT_TIMESTAMP, 0);