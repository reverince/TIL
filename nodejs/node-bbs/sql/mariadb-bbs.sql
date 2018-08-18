GRANT ALL PRIVILEGES ON javatest.* TO javauser@localhost
 IDENTIFIED BY 'javadude' WITH GRANT OPTION;

create database javatest;

use javatest;

create table javatest.board (
  id int not null auto_increment primary key,
  name varchar(50),
  email varchar(100),
  message text);

insert into javatest.board (name, email, message) 
values ('kenu', 'kenu.heo@gmail.com', '게시판 내용입니다.');
