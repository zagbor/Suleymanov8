insert into accountstatuses values(1, 'ACTIVE');
GO
insert accountstatuses values(2, 'BANNED');
GO
insert accountstatuses values(3, 'DELETED');


GO
insert into specialties (name) value ("Архитектор");
GO
insert into specialties (name) value ("Поэт");
GO
insert into specialties (name) value ("Писатель");
GO
insert into specialties (name) value ("Строитель");
GO
insert into specialties (name) value ("Программист");
GO
insert into specialties (name) value ("Спортсмен");
GO

insert into accounts (accountstatus_id) value (1);
GO
insert into customers (name, account_id) value ("Лермонтов", 1);
GO
insert into customers_specialties(customer_id, specialty_id) value(1, 1);
GO

insert into accounts (accountstatus_id) value (2);
GO
insert into customers (name, account_id) value ("Пушкин", "2");
GO
insert into customers_specialties(customer_id, specialty_id) value(2, 1);
GO
insert into customers_specialties(customer_id, specialty_id) value(2, 2);
GO
insert into accounts (accountstatus_id) value (3);
GO
insert into customers (name, account_id) value ("Борис", 3);
GO
insert into customers_specialties(customer_id, specialty_id) value(3, 4);
GO








