INSERT INTO
  tb_user (name, last_Name, email, password)
VALUES
  (
    'Alex',
    'Brown',
    'alex@gmail.com',
    '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG'
  );

INSERT INTO
  tb_user (name, last_Name, email, password)
VALUES
  (
    'Maria',
    'Green',
    'maria@gmail.com',
    '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG'
  );

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_state (name, country) VALUES ('MA', 'BR');

INSERT INTO tb_city (name, state_id) VALUES ('São Luís', 1);

INSERT INTO tb_phase (description) VALUES ('1_SERIE');
INSERT INTO tb_phase (description) VALUES ('2_SERIE');

INSERT INTO tb_school_class (name, period, phase_id) VALUES ('101ABC', 'MORNING', 1);
INSERT INTO tb_school_class (name, period, phase_id) VALUES ('202BBC', 'EVENING', 2);

INSERT INTO
  tb_student (
    enrollment,
    name,
    last_Name,
    social_Assistance,
    disability,
    email,
    social_Id,
    race,
    gender,
    birth_Date,
    birth_place_id,
    school_class_id
  )
VALUES
  (
    92944271,
    'Chester',
    'Mcgowan',
    'true',
    'Múltiplas',
    'suspendisse.aliquet.sem@outlook.edu',
    91853886065,
    'BROWN',
    'FEMALE',
    '2023-02-18',
    1,
    1
  );

INSERT INTO
  tb_national_id (number, issuing_Entity, state, city, student_id)
VALUES
  (999632277845, 'SSP', 'MA', 'Canberra', 92944271);

INSERT INTO
  tb_address (
    zip_Code,
    district,
    number,
    complement,
    student_id
  )
VALUES
  (
    25579325,
    'Hải Phòng',
    '017Q',
    'facilisi. Sed',
    92944271
  );

INSERT INTO tb_parent (name,last_Name)
VALUES
('Christen','Farrell'),
('Sharon','Santiago');

INSERT INTO tb_student_parent (student_id,parent_id)
VALUES
(92944271,1),
(92944271,2);

INSERT INTO tb_phone (number,student_id)
VALUES
('48936679199',92944271);
