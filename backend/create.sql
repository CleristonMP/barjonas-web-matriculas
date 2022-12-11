create table tb_address (student_id int8 not null, complement varchar(255), district varchar(255), number varchar(255), zip_code int4, city_id int8, primary key (student_id));
create table tb_city (id  bigserial not null, name varchar(255), state_id int8, primary key (id));
create table tb_national_id (student_id int8 not null, issuing_entity varchar(255), number int8, city_id int8, primary key (student_id));
create table tb_parent (id  bigserial not null, last_name varchar(255), name varchar(255), primary key (id));
create table tb_phase (id  bigserial not null, description varchar(255), primary key (id));
create table tb_phone (number int8 not null, primary key (number));
create table tb_role (id  bigserial not null, authority varchar(255), primary key (id));
create table tb_school_class (id  bigserial not null, created_at TIMESTAMP WITHOUT TIME ZONE, name varchar(255), period varchar(255), updated_at TIMESTAMP WITHOUT TIME ZONE, phase_id int8 not null, primary key (id));
create table tb_state (id  bigserial not null, country varchar(255), name varchar(255), primary key (id));
create table tb_student (enrollment int8 not null, birth_date TIMESTAMP WITHOUT TIME ZONE, created_at TIMESTAMP WITHOUT TIME ZONE, disability varchar(255), email varchar(255), gender varchar(255), last_name varchar(255), name varchar(255), nationality varchar(255), race varchar(255), social_assistance boolean, social_id int8, updated_at TIMESTAMP WITHOUT TIME ZONE, birth_place_id int8, school_class_id int8, primary key (enrollment));
create table tb_student_parent (student_id int8 not null, parent_id int8 not null, primary key (student_id, parent_id));
create table tb_student_phone (student_id int8 not null, phone_id int8 not null, primary key (student_id, phone_id));
create table tb_user (id  bigserial not null, email varchar(255), last_name varchar(255), name varchar(255), password varchar(255), primary key (id));
create table tb_user_role (user_id int8 not null, role_id int8 not null, primary key (user_id, role_id));
alter table tb_national_id add constraint UK_iseq69ugw3h2lhf2brj6ha4fo unique (number);
alter table tb_state add constraint UK_8ervk8yyq1mprvgaby5x9g0pq unique (name);
alter table tb_student add constraint UK_mse5pjuuk0c0bn39xgh4cefek unique (social_id);
alter table tb_user add constraint UK_4vih17mube9j7cqyjlfbcrk4m unique (email);
alter table tb_address add constraint FKpm4x3qy2wea2p4ea7bs217nr5 foreign key (city_id) references tb_city;
alter table tb_address add constraint FKm0iksxnh7bifeknbpah7ptw0h foreign key (student_id) references tb_student;
alter table tb_city add constraint FK1rn7oty4mwqviyw8vk67crapo foreign key (state_id) references tb_state;
alter table tb_national_id add constraint FKe8n7pxe14ctnm13cxg66pcps5 foreign key (city_id) references tb_city;
alter table tb_national_id add constraint FKc5f4jc72og7rgga92ciqe18rs foreign key (student_id) references tb_student;
alter table tb_school_class add constraint FKat892oxuolopn1c8d8ytpyd90 foreign key (phase_id) references tb_phase;
alter table tb_student add constraint FK4fqsejjwesvkt407k0arsu5rq foreign key (birth_place_id) references tb_city;
alter table tb_student add constraint FK2r3evncag5a6ysgixdp3gnler foreign key (school_class_id) references tb_school_class;
alter table tb_student_parent add constraint FK99bb52m2ufrom0x913sj4sekt foreign key (parent_id) references tb_parent;
alter table tb_student_parent add constraint FKnl9vb2yw8b7qvvxikmlcbm07w foreign key (student_id) references tb_student;
alter table tb_student_phone add constraint FK1q4ro6cdjva2upg7xdhnndulv foreign key (phone_id) references tb_phone;
alter table tb_student_phone add constraint FK3stvearh29qurgh6s55gntkh4 foreign key (student_id) references tb_student;
alter table tb_user_role add constraint FKea2ootw6b6bb0xt3ptl28bymv foreign key (role_id) references tb_role;
alter table tb_user_role add constraint FK7vn3h53d0tqdimm8cp45gc0kl foreign key (user_id) references tb_user;
