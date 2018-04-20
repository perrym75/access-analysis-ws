package com.infosec.accessanalysis.dao.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dao.model.Resource;
import com.infosec.tools.TextResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ResourceRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String url = Configuration.getDbUrl();

    private static String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "resource/" + query + ".sql";
    }

    public List<Resource> findAll(long model_id) throws SQLException, IOException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectAllResources")))
        ) {
            st.setLong(1, model_id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }

    public List<Resource> findRangeOfAll(long model_id, long from, long count) throws SQLException, IOException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectRangeOfResources")))
        ) {
            st.setLong(1, model_id);
            st.setLong(2, from + 1);
            st.setLong(3, from + 1 + count);
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

    public List<Resource> findChildren(long model_id, long id) throws SQLException, IOException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectChildResources")))
        ) {
            st.setLong(1, model_id);
            st.setLong(2, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }

    public List<Resource> findRoot(long model_id) throws SQLException, IOException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectRootResources")))
        ) {
            st.setLong(1, model_id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }

    public Resource findOne(long model_id, long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectResource")))
        ) {
            st.setLong(1, model_id);
            st.setLong(2, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return createEntity(rs);
                }
            }
        }

        return null;
    }

    public List<Resource> findByAgent(long model_id, long id) throws SQLException, IOException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectResourcesByAgent")))
        ) {
            st.setLong(1, model_id);
            st.setLong(2, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }

    public List<Resource> findByPersonage(long model_id, long id) throws SQLException, IOException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectResourcesByPersonage")))
        ) {
            st.setLong(1, model_id);
            st.setLong(2, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }

    private Resource createEntity(ResultSet rs) throws SQLException {
        String name = rs.getString(3);
        name = name.substring(name.lastIndexOf('/') + 1);
        return new Resource(
                rs.getLong(1),
                rs.getLong(2),
                name,
                rs.getString(4),
                rs.getString(5),
                rs.getLong(6),
                rs.getInt(7));
    }
}
