drop table if exists jobs;
create table jobs(
    id Integer primary key auto_increment,
    specification varchar(200) not null,
    complete bit
)
