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
    userId varchar(100),
    username varchar(100),
    password varchar(20),
    email varchar(100),
    birthDate varchar(20),
    phoneNumber varchar(20),
    primary key (id),
    unique(userId)
);
create table articles (
    id bigint auto_increment,
    title varchar(255),
    content varchar(10000),
    created_at datetime not null,
    view_cnt bigint default 0,
    like_cnt bigint default 0,
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
    created_at datetime not null,
    user_id bigint,
    article_id bigint,
    primary key (id)
);
create table recomments (
    id bigint auto_increment,
    content varchar(10000),
    created_at datetime not null,
    user_id bigint,
    comment_id bigint,
    primary key (id)
);