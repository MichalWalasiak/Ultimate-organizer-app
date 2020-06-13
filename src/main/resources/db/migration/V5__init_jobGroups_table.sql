create table job_groups
(
    id Integer primary key auto_increment,
    specification varchar(200) not null,
    complete bit,
    created_on datetime null,
    updated_on datetime null
);
alter table jobs add column job_groups_id Integer null;
alter table jobs add foreign key(job_groups_id) references job_groups(id);