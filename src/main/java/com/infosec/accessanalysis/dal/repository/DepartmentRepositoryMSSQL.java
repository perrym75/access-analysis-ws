package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.Department;
import com.infosec.accessanalysis.dal.model.Personage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DepartmentRepositoryMSSQL implements DepartmentRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = Configuration.getDbUrl();

    @Override
    public List<Department> findAll() throws SQLException {
        List<Department> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE IS_DELETED = 0 ORDER BY DEPARTMENT_ID");
        ) {
            while (rs.next()) {
                Department dep = new Department(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3));
                loadChildObjects(dep);
                departments.add(dep);
            }
        }

        return departments;
    }

    @Override
    public List<Department> findRangeOfAll(long from, long count) throws SQLException {
        List<Department> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(";WITH DEP AS (SELECT DEPARTMENT_ID, PARENT_ID, NAME, ROW_NUMBER() OVER (ORDER BY DEPARTMENT_ID) as ROW_NUM FROM dbo.DEPARTMENT WHERE IS_DELETED = 0) SELECT * FROM DEP WHERE ROW_NUM >= ? AND ROW_NUM < ?");
        ) {
            st.setLong(1, from + 1);
            st.setLong(2, from + 1 + count);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Department dep = new Department(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3));
                    loadChildObjects(dep);
                    departments.add(dep);
                }
            }
        }
        catch (SQLException e) {
            logger.info(e.toString());
            throw e;
        }

        return departments;
    }

    @Override
    public List<Department> findChildren(long id) throws SQLException {
        List<Department> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE PARENT_ID = ? AND IS_DELETED = 0");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Department dep = new Department(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3));
                    loadChildObjects(dep);
                    departments.add(dep);
                }
            }
        }

        return departments;
    }

    @Override
    public Department findParent(long id) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE PARENT_ID = ? AND IS_DELETED = 0");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Department dep = new Department(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3));
                    loadChildObjects(dep);
                    return dep;
                }
            }
        }

        return null;
    }

    @Override
    public List<Department> findRoot() throws SQLException {
        List<Department> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE PARENT_ID IS NULL AND IS_DELETED = 0");
        ) {
            while (rs.next()) {
                Department dep = new Department(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3));
                loadChildObjects(dep);
                departments.add(dep);
            }
        }

        return departments;
    }

    @Override
    public Department findOne(long id) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE DEPARTMENT_ID = ? AND IS_DELETED = 0");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Department dep = new Department(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3));
                    loadChildObjects(dep);
                    return dep;
                }
            }
        }

        return null;
    }

    private void loadChildObjects(Department department) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "\tpers.PERSONAGE_ID\n" +
                        "\t, emp.FIRST_NAME\n" +
                        "\t, emp.SECOND_NAME\n" +
                        "\t, emp.LAST_NAME\n" +
                        "\t, pers.EMAIL\n" +
                        "FROM\n" +
                        "\tdbo.POST as post\n" +
                        "INNER JOIN\n" +
                        "\tdbo.PERSONAGE as pers\n" +
                        "ON\n" +
                        "\tpost.POST_ID = pers.POST_ID\n" +
                        "INNER JOIN\n" +
                        "\tdbo.EMPLOYEE as emp\n" +
                        "ON\n" +
                        "\tpers.EMPLOYEE_ID = emp.EMPLOYEE_ID\n" +
                        "WHERE\n" +
                        "\tpost.DEPARTMENT_ID = ? AND\n" +
                        "\tpost.IS_DELETED = 0 AND\n" +
                        "\tpers.IS_DELETED = 0 AND\n" +
                        "\temp.IS_DELETED = 0\n");
        ) {
            st.setLong(1, department.getId());
            try (ResultSet rs = st.executeQuery()) {
                List<Personage> employees = department.getEmployees();
                while (rs.next()) {
                    Personage personage = new Personage(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5));
                    employees.add(personage);
                }
            }
        }
    }
}
