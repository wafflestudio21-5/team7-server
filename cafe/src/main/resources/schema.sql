drop table if exists users cascade;
drop table if exists articles cascade;
drop table if exists article_likes cascade;
drop table if exists article_views cascade;
drop table if exists boards cascade;
drop table if exists board_groups cascade;
drop table if exists board_likes cascade;
drop table if exists board_views cascade;
drop table if exists comments cascade;
drop table if exists recomments cascade;

create table users (
    id bigint auto_increment,
    username varchar(100) default null,
    password varchar(20) default null,
    sns_id varchar(100) default null,
    name varchar(30),
    nickname varchar(100),
    email varchar(100),
    birth_date date,
    phone_number varchar(20),
    rank varchar(20) default '씨앗',
    register_date datetime not null default current_timestamp,
    introduction varchar(10000) default '',
    image varchar(200) default null,
    visit_count bigint default 0,
    articles_count bigint default 0,
    comments_count bigint default 0,
    primary key (id),
    unique(nickname)
);
create table articles (
    id bigint auto_increment,
    title varchar(255),
    content varchar(10000),
    created_at datetime not null default current_timestamp,
    view_cnt bigint default 0,
    like_cnt bigint default 0,
    comment_cnt bigint default 0,
    min_user_rank_allowed varchar(20) default '새싹',
    user_id bigint,
    board_id bigint,
    allow_comments boolean not null,
    is_notification boolean not null,
    primary key (id)
);
create table article_likes (
    id bigint auto_increment,
    article_id bigint not null,
    user_id bigint not null,
    primary key (id)
);
create table article_views (
    id bigint auto_increment,
    article_id bigint not null,
    user_id bigint not null,
    created_at datetime not null,
    primary key (id)
);
create table boards (
    id bigint auto_increment,
    name varchar(255),
    group_id bigint,
    view_cnt bigint default 0,
    like_cnt bigint default 0,
    primary key (id)
);
create table board_groups (
    id bigint auto_increment,
    name varchar(255),
    primary key (id)
);
create table board_likes (
    id bigint auto_increment,
    board_id bigint not null,
    user_id bigint not null,
    primary key (id)
);
create table board_views (
    id bigint auto_increment,
    board_id bigint not null,
    user_id bigint not null,
    created_at datetime not null,
    primary key (id)
);
create table comments (
    id bigint auto_increment,
    content varchar(10000),
    last_modified datetime not null,
    user_id bigint,
    article_id bigint,
    primary key (id)
);
create table recomments (
    id bigint auto_increment,
    content varchar(10000),
    last_modified datetime not null,
    user_id bigint,
    comment_id bigint,
    primary key (id)
);