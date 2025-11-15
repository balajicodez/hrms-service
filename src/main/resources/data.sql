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

INSERT INTO organization (id, name,parent_organization_id) VALUES
(1000, 'DivyaSree Pvt Ltd, Gulzar House',null),
(1001, 'DivyaSree Pvt Ltd, Ameerpet',1000);

INSERT INTO master_entity (id, name) VALUES
(1, 'Full-time'),
(2, 'Part-time'),
(3, 'Active'),
(4, 'Inactive');

INSERT INTO employee (organization_id, employee_no, first_name, last_name, middle_name, other_info, employment_type_id, status_id)
VALUES
(1001, 'EMP001', 'Rakesh', 'Bhattacharya', NULL, '{"designation":"Supervisor","region":"Dhaka"}', 1, 3),
(1001, 'EMP002', 'Priya', 'Sharma', NULL, '{"designation":"Worker","region":"Delhi"}', 1, 3),
(1001, 'EMP003', 'Arjun', 'Patel', 'Kumar', '{"designation":"Worker","region":"Gujarat"}', 2, 4),
(1001, 'EMP004', 'Meena', 'Das', NULL, '{"designation":"Worker","region":"Kolkata"}', 1, 3);

INSERT INTO expense_type_master (id, description,subtype, type) VALUES
(1, 'in' , 'in', 'CASH-IN'),
(2, 'out' , 'out', 'CASH-OUT');

INSERT INTO expense (
  id, description, reference_number, amount, employee_id,
  created_date, transaction_date, created_by_user, created_by_user_id,
  expense_type, expense_sub_type, organization_name, organization_id,
  image_data, image_file_name, image_content_type
) VALUES (
  1,
  'Office supplies purchase',
  'REF-1001',
  12500,
  42,
  DATE '2025-11-10',
  DATE '2025-11-10',
  '',
  '',
  'CASH-OUT',
  'STATIONERY',
  'DivyaSree Pvt Ltd, Gulzar House',
  1,
  NULL,
  NULL,
  NULL
);

INSERT INTO expense (
  id, description, reference_number, amount, employee_id,
  created_date, transaction_date, created_by_user, created_by_user_id,
  expense_type, expense_sub_type, organization_name, organization_id,
  image_data, image_file_name, image_content_type
) VALUES (
  2,
  'Client refund',
  'REF-2002',
  50000,
  55,
  DATE '2025-11-10',
  DATE '2025-11-10',
  '',
  '',
  'CASH-IN',
  'REFUND',
  'DivyaSree Pvt Ltd, Gulzar House',
  1,
  NULL,
  NULL,
  NULL
);

INSERT INTO expense (
  id, description, reference_number, amount, employee_id,
  created_date, transaction_date, created_by_user, created_by_user_id,
  expense_type, expense_sub_type, organization_name, organization_id,
  image_data, image_file_name, image_content_type
) VALUES (
  3,
  'Travel reimbursement',
  'REF-3003',
  600,
  77,
  DATE '2025-11-10',
  DATE '2025-11-10',
  '',
  '',
  'CASH-OUT',
  'TRAVEL',
  'DivyaSree Pvt Ltd, Gulzar House',
  1,
  NULL,
  NULL,
  NULL
);

INSERT INTO expense ( id,
  description, reference_number, amount, employee_id,
  created_date, transaction_date, created_by_user, created_by_user_id,
  expense_type, expense_sub_type, organization_name, organization_id
) VALUES (
  7,
  'Office snack reimbursement',
  'REF-4004',
  3200,
  88,
  DATE '2025-11-10',
  DATE '2025-11-10',
  '',
  '',
  'CASH-OUT',
  'FOOD',
  'DivyaSree Pvt Ltd, Gulzar House',
  1
);

INSERT INTO expense (
  id, description, reference_number, amount, employee_id,
  created_date, transaction_date, created_by_user, created_by_user_id,
  expense_type, expense_sub_type, organization_name, organization_id,
  image_data, image_file_name, image_content_type
) VALUES (
  4,
  'Office supplies purchase',
  'REF-1001',
  12500,
  42,
  DATE '2025-11-10',
  DATE '2025-11-10',
  '',
  '',
  'CASH-OUT',
  'STATIONERY',
  'DivyaSree Pvt Ltd, Ameerpet',
  2,
  NULL,
  NULL,
  NULL
);

INSERT INTO expense (
  id, description, reference_number, amount, employee_id,
  created_date, transaction_date, created_by_user, created_by_user_id,
  expense_type, expense_sub_type, organization_name, organization_id,
  image_data, image_file_name, image_content_type
) VALUES (
  5,
  'Client refund',
  'REF-2002',
  50000,
  55,
  DATE '2025-11-10',
  DATE '2025-11-10',
  '',
  '',
  'CASH-IN',
  'REFUND',
  'DivyaSree Pvt Ltd, Ameerpet',
  2,
  NULL,
  NULL,
  NULL
);

INSERT INTO expense (
  id, description, reference_number, amount, employee_id,
  created_date, transaction_date, created_by_user, created_by_user_id,
  expense_type, expense_sub_type, organization_name, organization_id,
  image_data, image_file_name, image_content_type
) VALUES (
  6,
  'Travel reimbursement',
  'REF-3003',
  800,
  77,
  DATE '2025-11-10',
  DATE '2025-11-10',
  '',
  '',
  'CASH-OUT',
  'TRAVEL',
  'DivyaSree Pvt Ltd, Ameerpet',
  2,
  NULL,
  NULL,
  NULL
);

INSERT INTO expense (
  id, description, reference_number, amount, employee_id,
  created_date, transaction_date, created_by_user, created_by_user_id,
  expense_type, expense_sub_type, organization_name, organization_id
) VALUES (
  8,
  'Office snack reimbursement',
  'REF-4004',
  3200,
  88,
  DATE '2025-11-10',
  DATE '2025-11-10',
  '',
  '',
  'CASH-OUT',
  'FOOD',
  'DivyaSree Pvt Ltd, Ameerpet',
  2
);