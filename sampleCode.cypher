// Load CSV files into Neo4j
```
// Create constraints
CREATE CONSTRAINT FOR (s:Student) REQUIRE s.studentID IS UNIQUE;
CREATE CONSTRAINT FOR (c:Course) REQUIRE c.courseID IS UNIQUE;
CREATE CONSTRAINT FOR (d:Department) REQUIRE d.deptID IS UNIQUE;

SHOW ALL constraints;

// Load courses
LOAD CSV WITH HEADERS FROM 'file:///courses/courses.csv' AS row
MERGE (c:Course {courseID: row.course_id})
SET c.name = row.name, c.hours = toInteger(row.hours)
RETURN c;

// Load students
LOAD CSV WITH HEADERS FROM 'file:///courses/students.csv' AS row
MERGE (s:Student {studentID: row.student_id})
SET s.name = row.name, s.gpa = toFloat(row.gpa)
RETURN s;

// Load departments
LOAD CSV WITH HEADERS FROM 'file:///courses/departments.csv' AS row
MERGE (d:Department {deptID: row.dept_id})
SET d.name = row.name, d.dean = row.dean, d.building = row.building, d.room = toInteger(row.room)
RETURN d;

// Load enrollments
LOAD CSV WITH HEADERS FROM 'file:///courses/enrollments.csv' AS row
MATCH (s:Course {courseID: row.course_id})
MATCH (c:Student {studentID: row.student_id})
MERGE (e:Enrollment {student_id: row.student_id, course_id: row.course_id})
return e;

// Create relationships
LOAD CSV WITH HEADERS FROM 'file:///courses/enrollments.csv' AS row
MATCH (s:Student {studentID: row.student_id})
MATCH (c:Course {courseID: row.course_id})
MERGE (s)-[:ENROLLED]->(c)

LOAD CSV WITH HEADERS FROM 'file:///courses/courses.csv' AS row
MATCH (c:Course {courseID: row.course_id})
MATCH (d:Department {deptID: row.dept_id})
MERGE (c)-[:BELONGS_TO]->(d)
```



