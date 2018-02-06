-- 获取从高到低排名第N位的薪水
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
  BEGIN
    DECLARE M INT;
    SET M=N-1;
    RETURN (
      # Write your MySQL query statement below.
      SELECT DISTINCT Salary FROM Employee ORDER BY Salary DESC LIMIT M, 1
    );
  END