-- =========================================================
--  ORGANIZATION
-- =========================================================
CREATE TABLE IF NOT EXISTS organization (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- =========================================================
--  MASTER_ENTITY
-- =========================================================
CREATE TABLE IF NOT EXISTS master_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- =========================================================
--  EMPLOYEE
-- =========================================================
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organization_id BIGINT,
    employee_no VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    middle_name VARCHAR(255),
    other_info TEXT,
    employment_type_id BIGINT,
    status_id BIGINT,
    CONSTRAINT fk_org FOREIGN KEY (organization_id) REFERENCES organization(id),
    CONSTRAINT fk_emp_type FOREIGN KEY (employment_type_id) REFERENCES master_entity(id),
    CONSTRAINT fk_status FOREIGN KEY (status_id) REFERENCES master_entity(id)
);

-- =========================================================
--  DATA SEEDING
-- =========================================================

INSERT INTO organization (id, name) VALUES
(1, 'SimplERP'),
(2, 'HR Solutions Ltd');

INSERT INTO master_entity (id, name) VALUES
(1, 'Full-time'),
(2, 'Part-time'),
(3, 'Active'),
(4, 'Inactive');

INSERT INTO employee (organization_id, employee_no, first_name, last_name, middle_name, other_info, employment_type_id, status_id)
VALUES
(1, 'EMP001', 'Rakesh', 'Bhattacharya', NULL, '{"designation":"Supervisor","region":"Dhaka"}', 1, 3),
(1, 'EMP002', 'Priya', 'Sharma', NULL, '{"designation":"Worker","region":"Delhi"}', 1, 3),
(2, 'EMP003', 'Arjun', 'Patel', 'Kumar', '{"designation":"Worker","region":"Gujarat"}', 2, 4),
(2, 'EMP004', 'Meena', 'Das', NULL, '{"designation":"Worker","region":"Kolkata"}', 1, 3);

INSERT INTO expense_type_master (id, description,subtype, type) VALUES
(1, 'in' , 'in', 'CASH-IN'),
(2, 'out' , 'out', 'CASH-OUT');