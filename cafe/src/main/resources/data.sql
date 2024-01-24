INSERT into users (id, username, password, name, nickname, email, birth_date, phone_number) VALUES (1, 'hong', 'hong123', '홍길동', '홍길동', 'hong@naver.com', '2000-05-03', '01023453444'),
(2, 'doo', 'doo123', '황두현', '황두현', 'doo@naver.com', '1997-04-26', '01023446673');

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

INSERT into articles (id, title, content, created_at, user_id, board_id, allow_comments, is_notification) VALUES (1, '스프링이란', '스프링은 ..', CURRENT_TIMESTAMP, 1, 1, true, true);

INSERT into comments (id, content, last_modified, user_id, article_id) VALUES (1, '잘 읽었습니다.', CURRENT_TIMESTAMP, 2, 1);

INSERT into recomments (id, content, last_modified, user_id, comment_id) VALUES (1, '감사합니다.', CURRENT_TIMESTAMP, 1, 1);