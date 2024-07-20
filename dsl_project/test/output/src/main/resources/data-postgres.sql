--insert into address (street, city, zipcode, deleted)
--values ('admin', 'sdasd', 'sfdada', false);
--
--insert into customer (age, deleted)
--values (40, false);
--
--insert into dva (sds, deleted)
--values ('nestoo', false);
--
--insert into dva (sds, deleted)
--values ('fdgdfg', false);
--insert into dva (sds, deleted)
--values ('ffffff', false);
--
--insert into jedan (street, deleted)
--values ('r', false);
--
--insert into jedan (street, deleted)
--values ('d', false);
--insert into jedan (street, deleted)
--values ('dgdf', false);
INSERT INTO role_table (id, name)
VALUES (1, 'ADMIN');
INSERT INTO role_table (id, name)
VALUES (2, 'SELLER');
INSERT INTO role_table (id, name)
VALUES (3, 'CUSTOMER');
insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('admin', 'test', 'False', 'False', 1);
insert into admin (id)
values (1);

insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('seler', 'test', 'False', 'False', 2);
insert into seller (id)
values (2);
insert into system_user (username, password, deleted, loggedFirstTime, role_id)
values ('customer', 'test', 'False', 'False', 3);
insert into customer (id)
values (3);

insert into item (name, quantity, deleted)
values ('hleb', 3, false);

insert into item (name, quantity, deleted)
values ('pavlaka', 2, false);

insert into item (name, quantity, deleted)
values ('so', 3, false);

insert into basket ( formular, deleted, customer_id)
values ('fff', false,3);

insert into itemwithprice (currentprice, iscurrent, deleted)
values (300, true, false);
insert into itemwithprice (currentprice, iscurrent, deleted)
values (400, true, false);
insert into itemwithprice (currentprice, iscurrent, deleted)
values (500, true, false);
