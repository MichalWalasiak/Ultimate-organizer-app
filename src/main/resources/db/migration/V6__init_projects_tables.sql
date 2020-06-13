create table projects
(
    id Integer primary key auto_increment,
    specification varchar(200) not null

);

create table project_steps
(

    id Integer primary key auto_increment,
    specification varchar(200)  not null,
    days_to_deadline Integer    not null,
    projects_id Integer         not null,
    foreign key (projects_id) references projects(id)
);

alter table job_groups add column projects_id Integer null;
alter table job_groups add foreign key (projects_id) references projects(id);
