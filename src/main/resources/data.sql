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
