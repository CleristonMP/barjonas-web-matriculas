INSERT INTO tb_user (name, last_Name, email, password) VALUES ('Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, last_Name, email, password) VALUES ('Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_phase (description)
VALUES
  ('1_SERIE'),
  ('2_SERIE'),
  ('3_SERIE'),
  ('EJATEC_I'),
  ('EJATEC_II');

INSERT INTO tb_school_class (name,period,phase_id)
VALUES
  ('101ABC','MORNING',1),
  ('202EBC','EVENING',2),
  ('303CQC','NIGHT',3),
  ('404SBT','MORNING',4),
  ('101CNN','EVENING',5);

INSERT INTO tb_state (name)
VALUES
  ('PI'),
  ('CE'),
  ('SE'),
  ('MA'),
  ('PA'),
  ('PB'),
  ('RN'),
  ('BA');

INSERT INTO tb_city (name, state_id)
VALUES
  ('Bukit Merah',1),
  ('Moose Jaw',1),
  ('Zhejiang',2),
  ('Bad Ischl',2),
  ('Molde',3),
  ('Gansu',3),
  ('Emalahleni',4),
  ('Chimbote',4),
  ('Itanagar',5),
  ('Zhytomyr',5);
INSERT INTO tb_city (name, state_id)
VALUES
  ('Creil',6),
  ('Hougang',6),
  ('Carletonville',7),
  ('Dublin',7),
  ('Koppervik',8),
  ('Stalowa Wola',8),
  ('Izium',1),
  ('Dornbirn',2),
  ('Bauchi',3),
  ('Kraków',4);

INSERT INTO tb_address (zip_Code,district,number,complement,city_id)
VALUES
  (25579325,'Hải Phòng','017Q','facilisi. Sed',1),
  (70742545,'Oklahoma','135I','non, luctus',2),
  (11755366,'Zuid Holland','272B','cursus. Integer',3),
  (21322896,'Gilgit Baltistan','636W','lacus, varius',4),
  (52744333,'Coquimbo','483E','amet luctus',5),
  (84879629,'Northern Mindanao','316S','bibendum ullamcorper.',6),
  (97918136,'Vichada','551H','ornare. Fusce',7),
  (32084327,'Limburg','501D','consectetuer adipiscing',8),
  (63070569,'Balochistan','748D','ac orci.',9),
  (84870194,'Balochistan','294S','sem eget',10);
INSERT INTO tb_address (zip_Code,district,number,complement,city_id)
VALUES
  (61129702,'Northern Mindanao','466C','aliquet vel,',11),
  (29492764,'Puntarenas','361J','eleifend, nunc',12),
  (67706806,'Yucatán','464I','dictum eleifend,',13),
  (30060656,'Midi-Pyrénées','578Q','Etiam gravida',14),
  (73660177,'Balıkesir','378E','rutrum, justo.',15),
  (79023073,'Małopolskie','490G','sagittis lobortis',16),
  (81295532,'Poitou-Charentes','965M','lorem tristique',17),
  (50301342,'East Region','124C','nibh enim,',18),
  (49652341,'Xīběi','331U','libero est,',19),
  (84864515,'Southwestern Tagalog Region','756K','bibendum fermentum',20);
INSERT INTO tb_address (zip_Code,district,number,complement,city_id)
VALUES
  (99593385,'Bình Định','238K','et, lacinia',1),
  (98658845,'Newfoundland and Labrador','655V','magna. Lorem',2),
  (16265208,'British Columbia','826M','Vivamus molestie',3),
  (39612021,'Podkarpackie','860P','Mauris nulla.',4),
  (37569183,'Ulster','374E','nec quam.',5),
  (43886787,'Drenthe','755M','nulla at',6),
  (26450356,'Perthshire','640X','sagittis augue,',7),
  (40354545,'Oost-Vlaanderen','931J','amet metus.',8),
  (35186418,'Nairnshire','222Q','nec tempus',9),
  (77298503,'Brussels Hoofdstedelijk Gewest','560H','nunc id',10);

INSERT INTO tb_birth_place (city,state,country)
VALUES
  ('Hammerfest','PE','Brazil'),
  ('Heilongjiang','PI','Brazil'),
  ('Puerto Asís','RN','Brazil'),
  ('Vetlanda','RN','Brazil'),
  ('Palembang','PI','Brazil'),
  ('Huế','PA','Brazil'),
  ('Tarma','SE','Brazil'),
  ('Quảng Ngãi','PE','Brazil'),
  ('Itagüí','AL','Brazil'),
  ('Nazilli','PE','Brazil');
INSERT INTO tb_birth_place (city,state,country)
VALUES
  ('Tampines','PA','Brazil'),
  ('Palu','PB','Brazil'),
  ('Dutse','PE','Brazil'),
  ('Gwalior','PI','Brazil'),
  ('Cork','PE','Brazil'),
  ('León','PE','Brazil'),
  ('La Rochelle','RN','Brazil'),
  ('Upper Hutt','PE','Brazil'),
  ('Gunsan','PE','Brazil'),
  ('Gorzów Wielkopolski','MA','Brazil');
INSERT INTO tb_birth_place (city,state,country)
VALUES
  ('Isabela City','AL','Brazil'),
  ('Poitiers','PI','Brazil'),
  ('Jecheon','CE','Brazil'),
  ('Ciudad Obregón','PI','Brazil'),
  ('Pazarcık','CE','Brazil'),
  ('Hubei','PA','Brazil'),
  ('Agartala','PI','Brazil'),
  ('Voronezh','PI','Brazil'),
  ('Aserrí','PB','Brazil'),
  ('Culemborg','PI','Brazil');

INSERT INTO tb_student (enrollment,name,last_Name,social_Assistance,disability,email,social_Id,race,gender,birth_Date,address_id,birth_place_id,school_class_id)
VALUES
  (92944271,'Chester','Mcgowan','true','Múltiplas','suspendisse.aliquet.sem@outlook.edu',91853886065,'BROWN','FEMALE','2023-02-18',1,1,1),
  (80131365,'Kelly','Rojas','true','Surdez','nunc@icloud.com',88294426571,'BLACK','MALE','2023-03-28',2,2,1),
  (50603894,'Connor','Buckner','true','Múltiplas','condimentum.donec@icloud.com',85263253447,'ASIAN','MALE','2023-04-30',3,3,1),
  (45284360,'Jamalia','Stephens','true','Outros','cras.eu.tellus@yahoo.net',17000516323,'BROWN','MALE','2022-11-29',4,4,1),
  (97463650,'Halee','French','true','Outros','vivamus.euismod@icloud.net',60197756690,'WHITE','FEMALE','2023-02-23',5,5,1),
  (90653246,'Leroy','Hunt','false','Física','rutrum@aol.com',83822787524,'BROWN','MALE','2022-11-06',6,6,1),
  (14719122,'Barrett','Brennan','false','Surdez','ac.arcu@icloud.ca',89110639636,'WHITE','MALE','2022-05-06',7,7,1),
  (32717746,'Forrest','Pate','true','Física','morbi.metus@icloud.org',52913290614,'BLACK','MALE','2022-12-23',8,8,1),
  (13715914,'Hilda','Battle','false','Autismo','dui.fusce.diam@outlook.com',52322458232,'ASIAN','FEMALE','2023-05-24',9,9,1),
  (83110035,'Kevyn','Tyler','false','Surdez','lectus.sit@outlook.com',67804530117,'NATIVE','MALE','2023-03-24',10,10,1);
INSERT INTO tb_student (enrollment,name,last_Name,social_Assistance,disability,email,social_Id,race,gender,birth_Date,address_id,birth_place_id,school_class_id)
VALUES
  (12406426,'Yuli','Albert','false','Outros','accumsan.sed@aol.org',74145297043,'ASIAN','FEMALE','2023-10-22',11,11,2),
  (24144647,'Hamilton','Galloway','true','Múltiplas','fringilla@yahoo.couk',81906883405,'WHITE','FEMALE','2022-09-03',12,12,2),
  (32114916,'Raya','Flynn','true','Outros','nonummy.ultricies.ornare@hotmail.org',81262369633,'BROWN','MALE','2022-08-05',13,13,2),
  (17605278,'Galena','Carter','false','Múltiplas','tincidunt.tempus@google.net',47977573138,'BROWN','FEMALE','2023-11-18',14,14,2),
  (72739704,'Emerson','Bush','false','Física','nec.malesuada.ut@outlook.couk',43026056006,'BROWN','MALE','2023-02-04',15,15,2),
  (94735298,'Drew','Donaldson','true','Surdez','arcu@hotmail.com',64567862575,'BROWN','MALE','2022-12-10',16,16,2),
  (69437606,'Alana','Meyers','true','Outros','varius.nam@hotmail.ca',25182863978,'BLACK','MALE','2022-05-31',17,17,2),
  (17731246,'Shad','Sweeney','true','Múltiplas','enim.etiam.gravida@aol.edu',56558373928,'BROWN','MALE','2023-05-14',18,18,2),
  (99929093,'Erica','Walters','true','Outros','augue.eu.tellus@aol.net',55949489703,'ASIAN','FEMALE','2022-10-01',19,19,2),
  (38285704,'Jillian','Soto','true','Outros','placerat@icloud.net',48283627814,'BROWN','FEMALE','2023-05-14',20,20,2);
INSERT INTO tb_student (enrollment,name,last_Name,social_Assistance,disability,email,social_Id,race,gender,birth_Date,address_id,birth_place_id,school_class_id)
VALUES
  (70442301,'Knox','Bender','true','Surdez','nunc.mauris@hotmail.org',61328916975,'ASIAN','MALE','2022-04-15',21,21,3),
  (23402552,'Geoffrey','Daugherty','false','Outros','purus.mauris.a@hotmail.edu',32674431677,'ASIAN','MALE','2023-08-11',22,22,3),
  (49678528,'Barry','Ward','true','Múltiplas','quisque@yahoo.com',74160463238,'BROWN','MALE','2023-05-07',23,23,3),
  (84815449,'Shannon','Campbell','false','Física','dictum.magna@icloud.edu',76166898829,'BROWN','FEMALE','2022-06-12',24,24,3),
  (91564804,'Bevis','Chase','false','Múltiplas','aenean.euismod.mauris@protonmail.org',77651632027,'ASIAN','FEMALE','2022-03-19',25,25,3),
  (45665007,'Alexandra','Rosales','true','Física','sapien.gravida.non@hotmail.org',30352188179,'ASIAN','MALE','2023-06-16',26,26,3),
  (46325124,'Giacomo','Gonzales','false','Surdez','urna.convallis@hotmail.net',75624247790,'ASIAN','MALE','2022-08-16',27,27,3),
  (81314135,'Hedley','Weeks','true','Múltiplas','sociis.natoque@yahoo.org',95662078722,'BROWN','MALE','2023-11-16',28,28,3),
  (12132893,'Len','Howell','true','Surdez','laoreet@google.edu',97003073463,'ASIAN','MALE','2021-12-09',29,29,3),
  (41861435,'Graham','Sheppard','false','Outros','dignissim.pharetra@outlook.edu',25684909104,'WHITE','MALE','2022-07-07',30,30,3);
INSERT INTO tb_student (enrollment,name,last_Name,social_Assistance,disability,email,social_Id,race,gender,birth_Date,address_id,birth_place_id,school_class_id)
VALUES
  (52878810,'Bruno','Hernandez','false','Outros','sodales.at.velit@yahoo.couk',83045324116,'NATIVE','MALE','2022-09-23',1,1,4),
  (96931295,'Uriel','Rose','true','Física','varius.orci.in@icloud.ca',91796353719,'BROWN','FEMALE','2022-06-21',2,2,4),
  (90649565,'Logan','Moody','false','Outros','quisque.purus.sapien@outlook.ca',45311470246,'BROWN','MALE','2023-11-21',3,3,4),
  (68593030,'Colin','Jordan','false','Surdez','mollis.duis@outlook.net',67753772203,'WHITE','MALE','2023-04-21',4,4,4),
  (31251306,'Amal','Wolfe','false','Surdez','ipsum.sodales.purus@yahoo.org',91941529448,'ASIAN','FEMALE','2023-07-30',5,5,4),
  (41367078,'Nigel','Gibson','false','Surdez','felis.ullamcorper@protonmail.ca',65585190902,'BLACK','FEMALE','2023-10-08',6,6,4),
  (52696820,'Noelle','Foley','false','Surdez','metus.aliquam@protonmail.couk',60008714051,'WHITE','MALE','2022-01-25',7,7,4),
  (73119465,'Rosalyn','Mccray','false','Física','ligula.aenean@yahoo.org',83648731492,'BROWN','MALE','2023-10-01',8,8,4),
  (64735870,'Leila','Marks','true','Surdez','pretium.et.rutrum@protonmail.couk',65522956085,'BLACK','MALE','2023-06-27',9,9,4),
  (11259268,'Kimberley','Schwartz','true','Surdez','facilisis@aol.ca',83951576324,'NATIVE','FEMALE','2022-01-23',10,10,4);
INSERT INTO tb_student (enrollment,name,last_Name,social_Assistance,disability,email,social_Id,race,gender,birth_Date,address_id,birth_place_id,school_class_id)
VALUES
  (17030663,'Sophia','Faulkner','true','Múltiplas','laoreet.posuere@hotmail.org',61996102812,'BROWN','MALE','2022-07-27',11,11,5),
  (28682449,'Kylee','Fry','true','Surdez','nulla.eu@aol.edu',59259408872,'BROWN','MALE','2023-07-07',12,12,5),
  (64064086,'Blythe','Whitney','true','Física','cum@google.ca',79912922446,'BROWN','MALE','2023-09-17',13,13,5),
  (69335266,'Cameron','Nash','false','Outros','sed.leo@outlook.edu',14842056403,'BLACK','FEMALE','2023-09-27',14,14,5),
  (98963702,'Blaze','Martin','false','Surdez','interdum.libero@hotmail.couk',65158039965,'BROWN','FEMALE','2023-08-03',15,15,5),
  (48892239,'Dylan','Gillespie','false','Autismo','neque.sed@protonmail.edu',26228138904,'BLACK','FEMALE','2022-05-10',16,16,5),
  (75083305,'Benedict','Pratt','false','Surdez','sapien@protonmail.net',37327495438,'BLACK','FEMALE','2023-01-23',17,17,5),
  (18747214,'Kibo','Grimes','true','Física','varius@icloud.net',99839969561,'BLACK','MALE','2022-04-07',18,18,5),
  (35561538,'Jane','Walters','true','Outros','enim@hotmail.org',46625690046,'ASIAN','MALE','2022-03-04',19,19,5),
  (56117465,'Rebekah','Pace','true','Outros','augue.eu@yahoo.couk',12678490661,'WHITE','FEMALE','2023-11-11',20,20,5);

INSERT INTO tb_parent (name,last_Name,student_id)
VALUES
  ('Christen','Farrell',83110035),
  ('Eric','Brewer',13715914),
  ('Caleb','Strickland',32717746),
  ('Keefe','Willis',14719122),
  ('Jolene','Donaldson',90653246),
  ('Brendan','Drake',97463650),
  ('Montana','Gamble',45284360),
  ('Dalton','Sanford',50603894),
  ('Boris','Riddle',80131365),
  ('Miriam','Parks',92944271);
INSERT INTO tb_parent (name,last_Name,student_id)
VALUES
  ('Marny','Downs',83110035),
  ('Gray','O''donnell',13715914),
  ('Lucian','Fry',32717746),
  ('Jonas','Wise',14719122),
  ('Bernard','Meyers',90653246),
  ('Neville','Freeman',97463650),
  ('Tyrone','Weeks',45284360),
  ('Sharon','Santiago',50603894),
  ('Megan','Peterson',80131365),
  ('Melissa','Battle',92944271);

INSERT INTO tb_phone (number,student_id)
VALUES
  ('55321980327',92944271),
  ('98379787785',92944271),
  ('66854568157',80131365),
  ('25472493336',50603894),
  ('11958663469',45284360),
  ('91813172677',97463650),
  ('44552341198',90653246),
  ('87436126248',90653246),
  ('60230646236',14719122),
  ('77822547291',32717746);
INSERT INTO tb_phone (number,student_id)
VALUES
  ('26828636426',11259268),
  ('77811664323',11259268),
  ('52665632081',99929093),
  ('16406332874',99929093),
  ('01638320083',38285704),
  ('02964392226',38285704),
  ('84626472508',70442301),
  ('27183119572',70442301),
  ('37225644321',12132893),
  ('64801301227',12406426);
INSERT INTO tb_phone (number,student_id)
VALUES
  ('48936679199',13715914),
  ('84777557657',83110035),
  ('18763866117',12406426),
  ('77837156141',24144647),
  ('88404812448',32114916),
  ('47465969915',17605278),
  ('77425924159',72739704),
  ('48766273753',94735298),
  ('19474310489',69437606),
  ('95041272394',17731246);
  
INSERT INTO tb_national_id (number,issuing_Entity,state,city,student_id)
VALUES
  (999632277845,'SSP','MA','Canberra',92944271),
  (805148401461,'SSP','MA','Dyatkovo',80131365),
  (597690032170,'SSP','MA','Molde',50603894),
  (405833807345,'SSP','MA','Mohmand Agency',45284360),
  (621775896488,'SSP','MA','Melilla',97463650),
  (789104256170,'SSP','MA','Stalowa Wola',90653246),
  (536136966487,'SSP','MA','Cape Breton Island',14719122),
  (444844178225,'SSP','MA','Belmont',32717746),
  (267889807919,'SSP','MA','Cuernavaca',13715914),
  (410092287142,'SSP','MA','Dunoon',83110035);
INSERT INTO tb_national_id (number,issuing_Entity,state,city,student_id)
VALUES
  (568675599304,'SSP','MA','Queenstown',12406426),
  (411493069338,'SSP','MA','Mabalacat',38285704),
  (451833589845,'SSP','MA','Castelmarte',99929093),
  (513097349204,'SSP','MA','Ashburton',17731246),
  (886745277912,'SSP','MA','Itanagar',69437606),
  (757222749392,'SSP','MA','Kelso',94735298),
  (921465460450,'SSP','MA','Piscinas',72739704),
  (226065854791,'SSP','MA','Jelenia Góra',17605278),
  (168969884198,'SSP','MA','San Juan de Pasto',32114916),
  (344177862051,'SSP','MA','Palmerston',24144647);
INSERT INTO tb_national_id (number,issuing_Entity,state,city,student_id)
VALUES
  (430195087473,'SSP','MA','Kędzierzyn-Koźle',70442301),
  (940784445600,'SSP','MA','Sengkang',23402552),
  (481734197066,'SSP','MA','Gasteiz',49678528),
  (169405605490,'SSP','MA','Iloilo City,student_id',84815449),
  (702357588962,'SSP','MA','Chongqing',91564804),
  (888328466820,'SSP','MA','Kano',45665007),
  (298112197286,'SSP','MA','Tumba',46325124),
  (491842402237,'SSP','MA','Lanester',81314135),
  (242545567962,'SSP','MA','Palma de Mallorca',12132893),
  (801602928184,'SSP','MA','Puerto Vallarta',41861435);
INSERT INTO tb_national_id (number,issuing_Entity,state,city,student_id)
VALUES
  (590096174055,'SSP','MA','Kristiansand',11259268),
  (342701914009,'SSP','MA','Whangarei',64735870),
  (531151436700,'SSP','MA','Bodø',73119465),
  (657543822325,'SSP','MA','Bernburg',52696820),
  (720208670245,'SSP','MA','Bogotá',41367078),
  (888963281101,'SSP','MA','Sandviken',31251306),
  (249473317784,'SSP','MA','San José de Alajuela',68593030),
  (247874457159,'SSP','MA','Ham-sur-Heure-Nalinnes',90649565),
  (942862971995,'SSP','MA','Cusco',96931295),
  (406738264698,'SSP','MA','Duque de Caxias',52878810);
INSERT INTO tb_national_id (number,issuing_Entity,state,city,student_id)
VALUES
  (963993410110,'SSP','MA','Pervomaisk',56117465),
  (660509228713,'SSP','MA','Parys',35561538),
  (898949297628,'SSP','MA','Metairie',18747214),
  (270874492018,'SSP','MA','Alto del Carmen',75083305),
  (450578178456,'SSP','MA','San Juan del Cesar',48892239),
  (600402152310,'SSP','MA','Río Bueno',98963702),
  (820177682948,'SSP','MA','Woerden',69335266),
  (620859432876,'SSP','MA','Baguio',64064086),
  (240882333328,'SSP','MA','Virginia',28682449),
  (301624871782,'SSP','MA','Turrialba',17030663);
