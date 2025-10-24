-- Supervisor
INSERT INTO EMPLOYEE ( name, skill, region, age, is_migrant_worker, supervisor_id)
VALUES ( 'Rakesh Bhattacharya', 'Supervisor', 'Dhaka', 45, false, NULL);

-- Workers
INSERT INTO EMPLOYEE (name, skill, region, age, is_migrant_worker, supervisor_id)
VALUES
('Rohit Das', 'Stitching', 'Dhaka', 28, true, 1),
('Shyam Ganguly', 'Embroidery', 'Dhaka', 38, true, 1),
('Hiren Patel', 'Stitching', 'Surat', 32, true, NULL),
('Sunita Kumari', 'Packing', 'Kolkata', 25, true, NULL);

-- Inserting expense type master records
--INSERT INTO expense_type_master (id, description, type, subtype)
--VALUES (1, 'Travel to Branch', 'CASH_IN', 'TRAVEL');
--
--INSERT INTO expense_type_master (id, description, type, subtype)
--VALUES (2, 'Visit from other branch', 'CASH_IN', 'TRAVEL');
--
--INSERT INTO expense_type_master (id, description, type, subtype)
--VALUES (3, 'Lunch expenses', 'CASH_OUT', 'REFRESHMENTS');
--
--INSERT INTO expense_type_master (id, description, type, subtype)
--VALUES (4, 'Dinner expenses', 'CASH_OUT', 'REFRESHMENTS');


-- Inserting expense records referencing expense_type_master
--INSERT INTO expense (id, description, amount, employee_id, expense_type)
--VALUES (1, 'Team lunch', 2500, 101, 3);
--
--INSERT INTO expense (id, description, amount, employee_id, expense_type)
--VALUES (2, 'Client dinner', 4000, 102, 4);
--
--INSERT INTO expense (id, description, amount, employee_id, expense_type)
--VALUES (3, 'Trip to Adilabad', 15000, 103, 1);
--
--INSERT INTO expense (id, description, amount, employee_id, expense_type)
--VALUES (4, 'Visit by Manager of other Branch', 85000, 104, 2);
