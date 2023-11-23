drop table if exists test;
create table test(
 id char(8) not null default '' comment 'id',
 name varchar(50) comment '名称',
 primary key (id)
)engine=innodb default charset=utf8mb4 comment='测试';
insert into test (id, name) values (1, '测试');

drop table if exists chapter;
create table chapter(
    id char(8) not null comment 'ID',
    course_id char(8) comment '课程ID',
    name varchar(50) comment '名称',
    primary key(id)
)engine=innodb default charset=utf8mb4 comment='大章';

insert into chapter (id, course_id, name) values ('00000000', '00000000', '测试大章一');
insert into chapter (id, course_id, name) values ('00000001', '00000000', '测试大章二');
insert into chapter (id, course_id, name) values ('00000002', '00000000', '测试大章三');
insert into chapter (id, course_id, name) values ('00000003', '00000000', '测试大章四');
insert into chapter (id, course_id, name) values ('00000004', '00000000', '测试大章五');
insert into chapter (id, course_id, name) values ('00000005', '00000000', '测试大章六');
insert into chapter (id, course_id, name) values ('00000006', '00000000', '测试大章七');
insert into chapter (id, course_id, name) values ('00000007', '00000000', '测试大章八');
insert into chapter (id, course_id, name) values ('00000008', '00000000', '测试大章九');
insert into chapter (id, course_id, name) values ('00000009', '00000000', '测试大章十');
insert into chapter (id, course_id, name) values ('00000010', '00000000', '测试大章十一');
insert into chapter (id, course_id, name) values ('00000011', '00000000', '测试大章十二');
insert into chapter (id, course_id, name) values ('00000012', '00000000', '测试大章十三');
insert into chapter (id, course_id, name) values ('00000013', '00000000', '测试大章十四');
insert into chapter (id, course_id, name) values ('00000014', '00000000', '测试大章十五');
insert into chapter (id, course_id, name) values ('00000015', '00000000', '测试大章十六');
insert into chapter (id, course_id, name) values ('00000016', '00000000', '测试大章十七');
insert into chapter (id, course_id, name) values ('00000017', '00000000', '测试大章十八');
insert into chapter (id, course_id, name) values ('00000018', '00000000', '测试大章十九');
insert into chapter (id, course_id, name) values ('00000019', '00000000', '测试大章二十');
insert into chapter (id, course_id, name) values ('00000020', '00000000', '测试大章二一');
insert into chapter (id, course_id, name) values ('00000021', '00000000', '测试大章二二');
insert into chapter (id, course_id, name) values ('00000022', '00000000', '测试大章二三');
insert into chapter (id, course_id, name) values ('00000023', '00000000', '测试大章二四');