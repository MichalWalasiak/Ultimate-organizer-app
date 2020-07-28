drop table if exists job_events;
create table job_events(
    id Integer primary key auto_increment,
    job_id Integer,
    occurrence datetime,
    name varchar(30)
)