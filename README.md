# ultimate-organizer-app

# Project Name
ultimate-organizer-List
> RESTful Api that allows the User to plan the daily tasks list. The User can easily add tasks one after another.
tasks can be gruped into groups, or they can create a project. All tasks are hold in h2 database.

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Inspiration](#inspiration)
* [Contact](#contact)

## General info
The purpose of this project was to create my own full-stack aplication with connection to database. 
Aplication that can be used by the User in the real world.

## Technologies
* Java - version 11
* Spring Boot - version 2
* HTML - version 5
* JavaScript -  version ECMASCRIPT 6
* Hibernate - version 5
* H2 database - version 1.4.199
* flyway - version 6
* Junit - version 5
* Mockito
* Thymeleaf

## Setup
To run my application User need to have web browser(chrome, firefox, etc.) 
User needs to have h2 database instaled on local computer, and configure connection with aplication by JDBC URL adress.
It is hosted not in memory, but as a file.

## Code Examples

CreateGroup method
 
    public GroupReadModel createGroup(LocalDateTime deadline, Integer projectId) {

        if (!properties.getTemplate().isAllowMultipleJobs() && groupsRepository.existsByCompleteIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("only one incomplete group in project is allowed");
        }
        return projectRepository.findById(projectId)
                .map(project -> {
                    var target = new GroupWriteModel();
                    target.setSpecification(project.getSpecification());
                    target.setJobs(
                            project.getSteps().stream()
                                    .map(projectSteps -> {
                                        var job = new GroupJobWriteModel();
                                        job.setSpecification(projectSteps.getSpecification());
                                        job.setDeadline(deadline.plusDays(projectSteps.getDaysToDeadline()));
                                        return job;
                                    }).collect(Collectors.toSet())
                    );
                    return service.createGroup(target, project);
                }).orElseThrow(() -> new IllegalArgumentException("project with given Id do not exists"));
    }

## Features
List of features ready 
* add task to group
* set deadline to task
* set specification of each task
* create a project based on particular taskas and task groups

## Status
Project is: in progress. 

## Contact
Created by [m.walasiak@gmail.com] - feel free to contact me!
