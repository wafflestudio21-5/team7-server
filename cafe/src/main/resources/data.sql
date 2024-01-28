INSERT into users (id, username, password, name, nickname, email, birth_date, phone_number) VALUES (1, 'hong', 'hong123', '홍길동', 'hong', 'hong@naver.com', '2000-05-03', '01023453444'),
(2, 'doo', 'doo123', '황두현', 'doo', 'doo@naver.com', '1997-04-26', '01023446673'),
(3, 'username1','password1','홍길은','nickname1','email@example.com','2000-02-02','01012345678'),
(4, 'username2','password2','홍길금','nickname2','email@example.com','2000-02-02','01012345679');

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

INSERT into articles (id, title, content, created_at, user_id, board_id, allow_comments, is_notification) VALUES (1, '스프링이란', '<p>스프링은 ..</p>', '2023-01-01', 1, 1, true, true),
(2, 'test2', '<p>content 1</p>', '2023-01-02', 2, 1, true, false),
(3, 'test3', '<p>this is content</p>', '2023-01-03', 3, 1, true, false),
(4, 'test4', '<p>이건 한글 컨텐츠입니다</p>', '2023-01-04', 4, 1, true, false),
(5, 'test5', '<p>이건 영어인척 하는 한글입니다</p>', '2023-01-05', 1, 1, true, false),
(6, 'test6', '<p>스피링은..</p>', '2023-01-06', 2, 1, true, false),
(7, 'test7', '<p>홍길동</p>', '2023-01-07', 3, 1, true, false),
(8, 'test8', '<p>홍길은</p>', '2023-01-08', 4, 2, true, false),
(9, '홍길동', '<p>.</p>', '2023-01-09', 1, 2, true, false),
(10, 'test10', '<p>잘 보고 갑니다</p>', '2023-01-10', 2, 2, true, false),
(11, 'test11', '<p>test11</p>', '2023-01-11', 3, 2, true, false),
(12, 'test12', '<p>.</p>', '2023-01-12', 4, 2, true, false),
(13, 'test13', '<p>.</p>', '2023-01-13', 1, 3, true, false),
(16, 'test16', '<p>.</p>', '2023-01-14', 2, 4, true, false),
(15, 'test15', '<p>.</p>', '2023-01-14', 3, 1, true, false),
(14, 'test14', '<p>이것은 첫 번째 단락입니다. HTML에서 <strong>단락</strong>은 <code>&lt;p&gt;</code>와 <code>&lt;/p&gt;</code> 태그로 묶여 표현됩니다.</p><p>이것은 두 번째 단락입니다. 각 단락은 별도의 블록으로 처리되어, 자동으로 상하 여백이 생깁니다.</p><p>마지막으로, 이것은 세 번째 단락입니다. 웹 페이지에서 텍스트의 구조와 가독성을 높이기 위해 단락을 사용합니다.</p>', '2023-01-14', 4, 2, true, false);

INSERT into comments (id, content, last_modified, user_id, article_id) VALUES (1, '잘 읽었습니다.', CURRENT_TIMESTAMP, 1, 1),
                                                                              (2, '홍길동.', CURRENT_TIMESTAMP, 2, 2),
                                                                              (3, '잘 읽었습니다.', CURRENT_TIMESTAMP, 3, 10),
                                                                              (4, '잘 읽었습니다.', CURRENT_TIMESTAMP, 4, 11);
;

INSERT into recomments (id, content, last_modified, user_id, comment_id) VALUES (1, '감사합니다.', CURRENT_TIMESTAMP, 1, 1);