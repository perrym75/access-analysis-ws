package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.dal.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DepartmentRepositoryMSSQL implements DepartmentRepository {

    private static String url = "jdbc:sqlserver://10.2.2.20;database=CL_INFOSEC;user=cl_infosec;password=cl_infosec";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Department> findAll() throws SQLException {
        List<Department> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT ORDER BY DEPARTMENT_ID");
        ) {
            while (rs.next()) {
                Department dep = new Department(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3));
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
                PreparedStatement st = conn.prepareStatement(";WITH DEP AS (SELECT DEPARTMENT_ID, PARENT_ID, NAME, ROW_NUMBER() OVER (ORDER BY DEPARTMENT_ID) as ROW_NUM FROM dbo.DEPARTMENT) SELECT * FROM DEP WHERE ROW_NUM >= ? AND ROW_NUM < ?");
        ) {
            st.setLong(1, from + 1);
            st.setLong(2, from + 1 + count);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Department dep = new Department(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3));
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
                PreparedStatement st = conn.prepareStatement("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE PARENT_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Department dep = new Department(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3));
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
                PreparedStatement st = conn.prepareStatement("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE PARENT_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Department(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3));
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
                ResultSet rs = st.executeQuery("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE PARENT_ID IS NULL");
        ) {
            while (rs.next()) {
                Department dep = new Department(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3));
                departments.add(dep);
            }
        }

        return departments;
    }

    @Override
    public Department findOne(long id) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE DEPARTMENT_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Department(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3));
                }
            }
        }

        return null;
    }
}
