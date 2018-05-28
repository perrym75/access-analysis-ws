package com.infosec.accessanalysis.dao.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dao.model.Department;
import com.infosec.tools.CachedResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DepartmentRepository implements HierarchicalRepository<Department> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String url = Configuration.getDbUrl();
    private final PersonageRepository personageRepository = new PersonageRepository();

    private static String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "department/" + query + ".sql";
    }

    @Override
    public List<Department> findAll() throws SQLException, IOException {
        List<Department> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(CachedResourceReader.readString(
                        getQueryResourceName("selectAllDepartments")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public List<Department> findRangeOfAll(long from, long count) throws SQLException, IOException {
        List<Department> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectRangeOfDepartments")))
        ) {
            st.setLong(1, from + 1);
            st.setLong(2, from + 1 + count);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }
        catch (SQLException e) {
            logger.info(e.toString());
            throw e;
        }

        return entities;
    }

    @Override
    public List<Department> findChildren(long id) throws SQLException, IOException {
        List<Department> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectChildDepartments")))
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }

    @Override
    public List<Department> findRoot() throws SQLException, IOException {
        List<Department> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        CachedResourceReader.readString(getQueryResourceName("selectRootDepartments")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public Department findOne(long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectDepartment")))
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return createEntity(rs);
                }
            }
        }

        return null;
    }

    private Department createEntity(ResultSet rs) throws SQLException, IOException{
        Department entity = new Department(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3));
        entity.setEmployees(personageRepository.findByDepartment(entity.getId()));

        return entity;
    }
}
