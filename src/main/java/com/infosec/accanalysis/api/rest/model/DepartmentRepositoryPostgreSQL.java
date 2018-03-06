package com.infosec.accanalysis.api.rest.model;

import com.infosec.accanalysis.dbo.model.Department;
import com.infosec.accanalysis.dbo.repository.DepartmentRepository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DepartmentRepositoryPostgreSQL implements DepartmentRepository {

    private static String url = "jdbc:sqlserver://10.2.2.20;database=CL_INFOSEC;user=cl_infosec;password=cl_infosec";

    @Override
    public List<Department> findAllDepartments() {
        List<Department> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT");
        ) {
            while (rs.next()) {
                Department dep = new Department(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3));
                departments.add(dep);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return departments;
    }

    @Override
    public List<Department> findChildDepartments(int parentId) {
        List<Department> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE PARENT_ID = ?");
        ) {
            st.setInt(1, parentId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Department dep = new Department(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3));
                    departments.add(dep);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return departments;
    }

    @Override
    public List<Department> findChildDepartments() {
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return departments;
    }

    @Override
    public Department findDepartment(int departmentId) {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT WHERE DEPARTMENT_ID = ?");
        ) {
            st.setInt(1, departmentId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    return new Department(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
