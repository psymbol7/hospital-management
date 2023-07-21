create database hospital;
\c hospital;

drop table if exists staff cascade;
create table staff (
	staff_id uuid not null,
	user_name varchar(20) not null,
	password varchar(100) not null,
	created_at timestamp default now(),
	updated_at timestamp default now(),
	constraint staff_pkey primary key (staff_id)
);
create index user_name_index on staff(user_name);

drop table if exists patient cascade;
create table patient (
	patient_id uuid not null,
	name varchar(50) not null,
	age int4 not null,
	room int4 not null,
	doctor_name varchar(50) not null,
	expenses decimal not null,
	status int4 not null,
	created_at timestamp default now(),
	updated_at timestamp default now(),
	constraint patient_pkey primary key (patient_id)
);