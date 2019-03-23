# StudentReportCardGenerator
Backend practise. Given 4 input files, generate a text file containing the “report card” of all students in the database.

<h2> Input </h2>
<h3>courses.csv</h3>
This file contains the courses that a student takes. Each course has a <b>unique id</b>, a
<b>name</b>, and a <b>teacher</b>.
<h3>students.csv</h3>
This file contains all existing students in the database. Each student has a <b>unique id</b>,
and a <b>name</b>.
<h3>tests.csv</h3>
This file contains all the tests for each course in the courses.csv file. The file has three
columns: <br />
● <b>id</b>: the test’s unique id<br />
● <b>course_id</b>: the course id that this test belongs to<br />
● <b>weight</b>: how much of the student’s final grade the test is worth. For example, if a
test is worth 50, that means that this test is worth 50% of the final grade for this
course.<br /><br />
The sum of all the weights of all tests in a particular course should add up to 100. You
are allowed to throw errors in your report card generation script if this is not the case.
<h3>marks.csv</h3>
This file contains all the mark the student received for all the tests that they have
written.<br />
The file has three columns:<br />
● <b>test_id</b>: the test’s id<br />
● <b>student_id</b>: the student’s id<br />
● <b>mark</b>: The percentage grade the student received for the test (out of 100)<br /><br /><br />

Note: <b>Not all students are enrolled in all courses</b> – this can be determined by the marks
that they receive. All students should have completed (taken every test for) each course
they are enrolled in. <b>If a student takes no test in a course, then this student is not
enrolled in that course.</b> If there are students that have not completed a course, you can
throw an error in your report card generation script.

<h2> Output </h2>
The output is generated in the <font color="red">output.txt</font> file. Here is an expample of report card: 
