package com.infosec.accessanalysis.dao.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dao.model.Platform;
import com.infosec.tools.CachedResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PlatformRepository implements HierarchicalRepository<Platform> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String url = Configuration.getDbUrl();

    private static String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "platform/" + query + ".sql";
    }

    @Override
    public List<Platform> findAll() throws SQLException, IOException {
        List<Platform> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        CachedResourceReader.readString(getQueryResourceName("selectAllPlatforms")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public List<Platform> findRangeOfAll(long from, long count) throws SQLException, IOException {
        List<Platform> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectRangeOfPlatforms")))
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
    public List<Platform> findChildren(long id) throws SQLException, IOException {
        List<Platform> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectChildPlatforms")))
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
    public List<Platform> findRoot() throws SQLException, IOException {
        List<Platform> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        CachedResourceReader.readString(getQueryResourceName("selectRootPlatforms")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public Platform findOne(long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectPlatform")))
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

    private Platform createEntity(ResultSet rs) throws SQLException {
        return new Platform(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5));
    }
}
