CREATE TABLE School (
    school_id INT PRIMARY KEY AUTO_INCREMENT,
    school_name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(20)
);

CREATE TABLE Teachers (
    teacher_id INT PRIMARY KEY AUTO_INCREMENT,
    school_id INT,
    teacher_name VARCHAR(100) NOT NULL,
    subject_taught VARCHAR(100),
    FOREIGN KEY (school_id) REFERENCES School(school_id)
);

CREATE TABLE Students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    school_id INT,
    student_name VARCHAR(100) NOT NULL,
    grade_level VARCHAR(20),
    FOREIGN KEY (school_id) REFERENCES School(school_id)
);

CREATE TABLE Classes (
    class_id INT PRIMARY KEY AUTO_INCREMENT,
    teacher_id INT,
    class_name VARCHAR(100),
    schedule VARCHAR(50),
    FOREIGN KEY (teacher_id) REFERENCES Teachers(teacher_id)
);

--CREATE TABLE Enrollments (
--    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
--    student_id INT,
--    class_id INT,
--    enrollment_date DATE,
--    FOREIGN KEY (student_id) REFERENCES Students(student_id),
--    FOREIGN KEY (class_id) REFERENCES Classes(class_id)
--);

CREATE SEQUENCE enrollment_sequence START WITH 1 INCREMENT BY 1;
CREATE TABLE Enrollments (
    enrollment_id INT PRIMARY KEY DEFAULT NEXTVAL('enrollment_sequence'),
    student_id INT,
    class_id INT,
    enrollment_date DATE,
    FOREIGN KEY (student_id) REFERENCES Students(student_id),
    FOREIGN KEY (class_id) REFERENCES Classes(class_id)
);

INSERT INTO School (school_name, address, phone_number)
VALUES
('Greenfield High School', '123 Main St, Greenfield, MA', '(555) 123-4567'),
('Maplewood Elementary', '456 Elm St, Maplewood, NJ', '(555) 234-5678'),
('Oakridge Middle School', '789 Oak St, Oakridge, CA', '(555) 345-6789'),
('Pinecrest Academy', '101 Pine Ave, Pinecrest, FL', '(555) 456-7890'),
('Sunset Valley School', '321 Sunset Blvd, Sunset Valley, TX', '(555) 567-8901'),
('Riverdale High', '555 River Rd, Riverdale, NY', '(555) 678-9012'),
('Harborview Elementary', '777 Harbor Dr, Harborview, WA', '(555) 789-0123'),
('Cedarwood Middle School', '888 Cedar Ln, Cedarwood, OR', '(555) 890-1234'),
('Mountainview Academy', '999 Mountain View Dr, Mountainview, CO', '(555) 901-2345'),
('Lakefront School', '111 Lakefront Ave, Lakefront, MN', '(555) 012-3456'),
('Westwood Elementary', '222 Westwood Rd, Westwood, TX', '(555) 123-4567'),
('Central High School', '333 Central Ave, Central City, CA', '(555) 234-5678'),
('Springfield Academy', '444 Springfield St, Springfield, IL', '(555) 345-6789'),
('Oceanview School', '555 Ocean Blvd, Oceanview, FL', '(555) 456-7890'),
('Rivertown Middle School', '666 Riverfront Dr, Rivertown, GA', '(555) 567-8901'),
('Pinehill Elementary', '777 Pinehill Ave, Pinehill, AL', '(555) 678-9012'),
('Meadowbrook High', '888 Meadowbrook Ln, Meadowbrook, OH', '(555) 789-0123'),
('Valley Vista School', '999 Valley Vista Dr, Valley Vista, AZ', '(555) 890-1234'),
('Hilltop Academy', '111 Hilltop Rd, Hilltop, NC', '(555) 901-2345'),
('Golden Valley School', '222 Golden Valley Ave, Golden Valley, TX', '(555) 012-3456'),
('Redwood Middle School', '333 Redwood Dr, Redwood, CA', '(555) 123-4567'),
('Sunnyside Elementary', '444 Sunnyside St, Sunnyside, WA', '(555) 234-5678'),
('Lakeside High School', '555 Lakeside Ave, Lakeside, NV', '(555) 345-6789'),
('Mapleton Academy', '666 Maple St, Mapleton, UT', '(555) 456-7890'),
('Hillcrest School', '777 Hillcrest Dr, Hillcrest, KY', '(555) 567-8901'),
('Fairview Middle School', '888 Fairview Rd, Fairview, TN', '(555) 678-9012'),
('Crestview Elementary', '999 Crestview Ave, Crestview, OK', '(555) 789-0123'),
('Parkside High', '111 Parkside Blvd, Parkside, TX', '(555) 890-1234'),
('Woodland Academy', '222 Woodland Ln, Woodland, VA', '(555) 901-2345'),
('Highland School', '333 Highland Rd, Highland, IL', '(555) 012-3456');

INSERT INTO Teachers (school_id, teacher_name, subject_taught)
VALUES
(1, 'John Smith', 'Mathematics'),
(2, 'Emily Johnson', 'Science'),
(3, 'Michael Williams', 'English'),
(4, 'Jessica Brown', 'History'),
(5, 'Christopher Jones', 'Physical Education'),
(6, 'Amanda Davis', 'Art'),
(7, 'Matthew Wilson', 'Music'),
(8, 'Elizabeth Martinez', 'Computer Science'),
(9, 'Daniel Anderson', 'Biology'),
(10, 'Sarah Taylor', 'Chemistry'),
(11, 'James Thomas', 'Physics'),
(12, 'Olivia Jackson', 'Geography'),
(13, 'David White', 'Economics'),
(14, 'Jennifer Harris', 'Spanish'),
(15, 'William Martin', 'French'),
(16, 'Samantha Thompson', 'German'),
(17, 'Andrew Garcia', 'Latin'),
(18, 'Ashley Martinez', 'Physical Education'),
(19, 'Ryan Robinson', 'Drama'),
(20, 'Michelle Clark', 'Health'),
(21, 'Kevin Lewis', 'Business'),
(22, 'Stephanie Walker', 'Psychology'),
(23, 'Mark Hall', 'Sociology'),
(24, 'Melissa Allen', 'Anthropology'),
(25, 'Brian Young', 'Philosophy'),
(26, 'Nicole King', 'Religion'),
(27, 'Eric Lee', 'Environmental Science'),
(28, 'Lauren Scott', 'Political Science'),
(29, 'Brandon Green', 'Criminal Justice'),
(30, 'Kimberly Adams', 'Public Speaking');

INSERT INTO Students (school_id, student_name, grade_level)
VALUES
(1, 'Emily Johnson', '10th Grade'),
(1, 'Michael Williams', '11th Grade'),
(1, 'Jessica Brown', '9th Grade'),
(1, 'Christopher Jones', '12th Grade'),
(2, 'Amanda Davis', '9th Grade'),
(2, 'Matthew Wilson', '11th Grade'),
(2, 'Elizabeth Martinez', '10th Grade'),
(2, 'Daniel Anderson', '12th Grade'),
(3, 'Sarah Taylor', '9th Grade'),
(3, 'James Thomas', '10th Grade'),
(3, 'Olivia Jackson', '11th Grade'),
(3, 'David White', '12th Grade'),
(4, 'Jennifer Harris', '9th Grade'),
(4, 'William Martin', '10th Grade'),
(4, 'Samantha Thompson', '11th Grade'),
(4, 'Andrew Garcia', '12th Grade'),
(5, 'Ashley Martinez', '9th Grade'),
(5, 'Ryan Robinson', '10th Grade'),
(5, 'Michelle Clark', '11th Grade'),
(5, 'Kevin Lewis', '12th Grade'),
(6, 'Stephanie Walker', '9th Grade'),
(6, 'Mark Hall', '10th Grade'),
(6, 'Melissa Allen', '11th Grade'),
(6, 'Brian Young', '12th Grade'),
(7, 'Nicole King', '9th Grade'),
(7, 'Eric Lee', '10th Grade'),
(7, 'Lauren Scott', '11th Grade'),
(7, 'Brandon Green', '12th Grade'),
(8, 'Kimberly Adams', '9th Grade'),
(8, 'Alexis Turner', '10th Grade'),
(8, 'Connor Baker', '11th Grade'),
(8, 'Tyler Carter', '12th Grade'),
(9, 'Emma Stewart', '9th Grade'),
(9, 'Joshua Hill', '10th Grade'),
(9, 'Madison Harris', '11th Grade'),
(9, 'Noah Murphy', '12th Grade'),
(10, 'Ava Morris', '9th Grade'),
(10, 'Caleb Wood', '10th Grade'),
(10, 'Chloe Nelson', '11th Grade'),
(10, 'Ethan King', '12th Grade'),
(11, 'Liam Bell', '9th Grade'),
(11, 'Mia Coleman', '10th Grade'),
(11, 'Mason Reed', '11th Grade'),
(11, 'Natalie Perez', '12th Grade'),
(12, 'Lucas Rivera', '9th Grade'),
(12, 'Sophia Ward', '10th Grade'),
(12, 'Jackson Cox', '11th Grade'),
(12, 'Isabella Ross', '12th Grade'),
(13, 'Aiden Powell', '9th Grade'),
(13, 'Grace Bailey', '10th Grade'),
(13, 'Elijah Murphy', '11th Grade'),
(13, 'Avery Lopez', '12th Grade'),
(14, 'Oliver Hill', '9th Grade'),
(14, 'Charlotte Green', '10th Grade'),
(14, 'Logan Scott', '11th Grade'),
(14, 'Lily Davis', '12th Grade'),
(15, 'Benjamin Martinez', '9th Grade'),
(15, 'Zoe Rodriguez', '10th Grade'),
(15, 'Ella Richardson', '11th Grade'),
(15, 'Carter Carter', '12th Grade'),
(16, 'Leo Nelson', '9th Grade'),
(16, 'Madelyn Evans', '10th Grade'),
(16, 'Lincoln Cox', '11th Grade'),
(16, 'Hannah Perez', '12th Grade'),
(17, 'Evelyn Bailey', '9th Grade'),
(17, 'Gabriel Lopez', '10th Grade'),
(17, 'Aria Gonzalez', '11th Grade'),
(17, 'Julian Allen', '12th Grade'),
(18, 'Landon Hall', '9th Grade'),
(18, 'Scarlett Perez', '10th Grade'),
(18, 'Jack Griffin', '11th Grade'),
(18, 'Amelia Turner', '12th Grade'),
(19, 'Daniel Hill', '9th Grade'),
(19, 'Aurora Wood', '10th Grade'),
(19, 'Henry Nelson', '11th Grade'),
(19, 'Luna Mitchell', '12th Grade'),
(20, 'Wyatt Cox', '9th Grade'),
(20, 'Paisley Davis', '10th Grade'),
(20, 'Maverick Bell', '11th Grade'),
(20, 'Hazel Ward', '12th Grade'),
(21, 'Christian Lopez', '9th Grade'),
(21, 'Nova Green', '10th Grade'),
(21, 'Eli Richardson', '11th Grade'),
(21, 'Addison Martinez', '12th Grade'),
(22, 'Brooklyn Perez', '9th Grade'),
(22, 'Zachary Hill', '10th Grade'),
(22, 'Serenity Evans', '11th Grade'),
(22, 'Nolan Carter', '12th Grade'),
(23, 'Savannah Bailey', '9th Grade'),
(23, 'Dominic Cox', '10th Grade'),
(23, 'Autumn Perez', '11th Grade'),
(23, 'Gavin Scott', '12th Grade'),
(24, 'Penelope Gonzalez', '9th Grade'),
(24, 'Elias Nelson', '10th Grade'),
(24, 'Aaliyah Hall', '11th Grade'),
(24, 'Miles Turner', '12th Grade'),
(25, 'Xavier Wood', '9th Grade'),
(25, 'Maya Allen', '10th Grade'),
(25, 'Austin Hill', '11th Grade'),
(25, 'Athena Ward', '12th Grade'),
(26, 'Ezekiel Mitchell', '9th Grade'),
(26, 'Camila Cox', '10th Grade'),
(26, 'Blake Perez', '11th Grade'),
(26, 'Nevaeh Davis', '12th Grade'),
(27, 'Carson Green', '9th Grade'),
(27, 'Isabelle Martinez', '10th Grade'),
(27, 'Morgan Lopez', '11th Grade'),
(27, 'Eleanor Griffin', '12th Grade'),
(28, 'Grayson Richardson', '9th Grade'),
(28, 'Lillian Bailey', '10th Grade'),
(28, 'Eliana Wood', '11th Grade'),
(28, 'Jaxon Cox', '12th Grade'),
(29, 'Levi Perez', '9th Grade'),
(29, 'Hudson Hill', '10th Grade'),
(29, 'Adeline Evans', '11th Grade'),
(29, 'Josephine Carter', '12th Grade'),
(30, 'Luke Scott', '9th Grade'),
(30, 'Violet Gonzalez', '10th Grade'),
(30, 'Gabriel Nelson', '11th Grade'),
(30, 'Madeline Hall', '12th Grade');

INSERT INTO Classes (teacher_id, class_name, schedule)
VALUES
(1, 'Algebra I', 'Monday, Wednesday, Friday 9:00 AM - 10:30 AM'),
(2, 'Biology', 'Tuesday, Thursday 10:00 AM - 11:30 AM'),
(3, 'English Literature', 'Monday, Wednesday 11:00 AM - 12:30 PM'),
(4, 'World History', 'Tuesday, Thursday 9:00 AM - 10:30 AM'),
(5, 'Physical Education', 'Monday, Wednesday, Friday 1:00 PM - 2:30 PM'),
(6, 'Art Appreciation', 'Tuesday, Thursday 1:00 PM - 2:30 PM'),
(7, 'Music Theory', 'Monday, Wednesday 2:00 PM - 3:30 PM'),
(8, 'Computer Programming', 'Tuesday, Thursday 11:00 AM - 12:30 PM'),
(9, 'Chemistry', 'Monday, Wednesday 9:00 AM - 10:30 AM'),
(10, 'Physics', 'Tuesday, Thursday 2:00 PM - 3:30 PM');

-- Generar 100 registros aleatorios
INSERT INTO Enrollments (student_id, class_id, enrollment_date)
SELECT
    ROUND(RANDOM() * 29 + 1) AS student_id,
    ROUND(RANDOM() * 9 + 1) AS class_id,
    DATEADD('DAY', ROUND(RANDOM() * DATEDIFF('DAY', '2024-01-01', '2024-12-31')), '2024-01-01') AS enrollment_date
FROM
    SYSTEM_RANGE(1, 100);


--DECLARE @counter INT = 1;
--DECLARE @startDate DATE = '2024-01-01';
--DECLARE @endDate DATE = '2024-12-31';
--
--WHILE @counter <= 100
--BEGIN
--    -- Generar un número aleatorio para student_id y class_id (entre 1 y 30)
--    DECLARE @studentId INT = (SELECT ROUND(RAND() * 29 + 1, 0));
--    DECLARE @classId INT = (SELECT ROUND(RAND() * 9 + 1, 0));
--
--    -- Obtener una fecha aleatoria entre @startDate y @endDate
--    DECLARE @randomDate DATE = (SELECT DATEADD(DAY, ROUND(RAND() * DATEDIFF(DAY, @startDate, @endDate), 0), @startDate));
--
--    -- Insertar el registro en la tabla Enrollments
--    INSERT INTO Enrollments (student_id, class_id, enrollment_date)
--    VALUES (@studentId, @classId, @randomDate);
--
--    -- Incrementar el contador del bucle
--    SET @counter = @counter + 1;
--END;
--GO