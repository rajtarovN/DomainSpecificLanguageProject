--insert into address (street, city, zipcode, deleted)
--values ('admin', 'sdasd', 'sfdada', false);
--
--insert into person (age, deleted)
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

insert into system_user (username, password, loggedFirstTime, deleted)
values ('admin', 'test', false, false);

insert into person (name, lastname, username, deleted)
values ('ana', 'ana', 'ana', false);

insert into item (name, quantity, deleted)
values ('hleb', 3, false);

insert into item (name, quantity, deleted)
values ('pavlaka', 2, false);

insert into item (name, quantity, deleted)
values ('so', 3, false);

insert into itemwithprice (currentprice, iscurrent, deleted)
values (300, true, false);
insert into itemwithprice (currentprice, iscurrent, deleted)
values (400, true, false);
insert into itemwithprice (currentprice, iscurrent, deleted)
values (500, true, false);
