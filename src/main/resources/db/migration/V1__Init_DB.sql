create table car (
    id bigint not null auto_increment,
    car varchar(255),
    sits_quantity integer not null,
    type varchar(255),
    primary key (id));

create table car_train (
    id bigint not null auto_increment,
    sequence_number integer not null,
    car_id bigint,
    timetable_id bigint,
    primary key (id));

create table place (
    id bigint not null auto_increment,
    sit integer not null,
    status varchar(255),
    car_train_id bigint,
    primary key (id));

create table station (
    id bigint not null auto_increment,
    station varchar(255),
    primary key (id));

create table ticket (
    id bigint not null auto_increment,
    cost float not null,
    first_name varchar(255),
    fullness varchar(255),
    purchase_date date,
    purchase_time time,
    second_name varchar(255),
    service varchar(255),
    place_id bigint,
    timetable_id bigint,
    primary key (id));

create table timetable (
    id bigint not null auto_increment,
    arrival_date date,
    arrival_time time,
    departure_date date,
    departure_time time,
    arrival_station_id bigint,
    departure_station_id bigint,
    train_route_id bigint,
    primary key (id));

create table train_route (
    id bigint not null auto_increment,
    route varchar(255),
    train varchar(255),
    travel_time float not null,
    primary key (id));

create table user (
    id bigint not null auto_increment,
    activation_code varchar(255),
    active bit not null,
    benefit_card varchar(255),
    benefit_card_number varchar(255),
    email varchar(255),
    first_name varchar(255),
    password varchar(255),
    second_name varchar(255),
    student_card varchar(255),
    student_card_number varchar(255),
    primary key (id));

create table user_role (
    user_id bigint not null,
    roles varchar(255));

alter table car_train
    add constraint car_train_car_fk
        foreign key (car_id) references car (id);

alter table car_train
    add constraint car_train_timetable_fk
        foreign key (timetable_id) references timetable (id);

alter table place
    add constraint place_car_train_fk
        foreign key (car_train_id) references car_train (id);

alter table ticket
    add constraint ticket_place_fk
        foreign key (place_id) references place (id);

alter table ticket
    add constraint ticket_timetable_fk
        foreign key (timetable_id) references timetable (id);

alter table timetable
    add constraint timetable_arrival_station_fk
        foreign key (arrival_station_id) references station (id);

alter table timetable
    add constraint timetable_departure_station_fk
        foreign key (departure_station_id) references station (id);

alter table timetable
    add constraint timetable_train_route_fk
        foreign key (train_route_id) references train_route (id);

alter table user_role
    add constraint user_role_user_fk
        foreign key (user_id) references user (id);