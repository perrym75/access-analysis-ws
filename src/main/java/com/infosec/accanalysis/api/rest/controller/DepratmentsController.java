package com.infosec.accanalysis.api.rest.controller;

import com.infosec.accanalysis.api.rest.model.Department;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/1.0/department")
public class DepratmentsController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public List<Department> department() {
        String url = "jdbc:sqlserver://10.2.2.20;database=CL_INFOSEC;user=cl_infosec;password=cl_infosec";
        List<Department> deps = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT");
                ) {

            while (rs.next()) {
                Department dep = new Department(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3));
                deps.add(dep);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return deps;
    }

    @GetMapping("/{id}")
    public List<Department> department(@RequestParam(value="id", defaultValue="null") String id) {
        List<Department> deps = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT DEPARTMENT_ID, PARENT_ID, NAME FROM dbo.DEPARTMENT");
        ) {

            while (rs.next()) {
                Department dep = new Department(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3));
                deps.add(dep);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return deps;
    }
}
