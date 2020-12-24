show databases;
use  hotelm;
show tables ;
select * from employee;
create table client(
    id int not null auto_increment primary key,
    name varchar(20),
    card varchar(20),
    sex varchar(10),
    age int
);
select * from client;
insert into client(name, card, sex, age) value ('张三','321234','男',18);