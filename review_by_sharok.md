# Service Class

1. In **createEmployee()** on line no. 21, there should be the use of mapping instead of a builder in the user object.
2. In **createEmployee()**, it's good to include an exception for the case where the email ID already exists.
3. In **getEmployeesByDepartment()**, it's advisable to throw an exception when there are no employees in the searched department.
   (You could use a global exception class and apply it wherever needed.)


# Controller class


1. In **getEmployeesByDepartment()** on line no. 32, the department should be given as a request param instead of a path variable, as per the question.

# Application.yml

1. In **application.yml**, the database username and password should not be provided in **application.yml**.




