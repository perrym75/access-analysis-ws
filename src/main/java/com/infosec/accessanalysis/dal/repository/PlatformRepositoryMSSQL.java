package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PlatformRepositoryMSSQL implements PlatformRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = Configuration.getDbUrl();

    @Override
    public List<Platform> findAll() throws SQLException {
        List<Platform> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT\n" +
                        "  PLATFORM_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , NAME\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.PLATFORM\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0\n")
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public List<Platform> findRangeOfAll(long from, long count) throws SQLException {
        List<Platform> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(";WITH\n" +
                        "    PLAT\n" +
                        "AS\n" +
                        "  (SELECT\n" +
                        "     PLATFORM_ID\n" +
                        "     , PARENT_ID\n" +
                        "     , NAME\n" +
                        "     , DISPLAY_NAME\n" +
                        "     , DESCRIPTION\n" +
                        "    , ROW_NUMBER() OVER (ORDER BY PLATFORM_ID) AS ROW_NUM\n" +
                        "  FROM\n" +
                        "    dbo.[PLATFORM]\n" +
                        "  WHERE\n" +
                        "    IS_DELETED = 0)\n" +
                        "SELECT\n" +
                        "  *\n" +
                        "FROM\n" +
                        "  PLAT\n" +
                        "WHERE\n" +
                        "  ROW_NUM >= ? AND ROW_NUM < ?")
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
    public List<Platform> findChildren(long id) throws SQLException {
        List<Platform> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "  PLATFORM_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , NAME\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.PLATFORM\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0 AND\n" +
                        "  PARENT_ID = ?")
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
    public Platform findParent(long id) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(url);
                //TODO: incorrect sql request
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "  PLATFORM_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , NAME\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.PLATFORM\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0 AND\n" +
                        "  PARENT_ID = ?")
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

    @Override
    public List<Platform> findRoot() throws SQLException {
        List<Platform> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT\n" +
                        "  PLATFORM_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , NAME\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.PLATFORM\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0 AND\n" +
                        "  PARENT_ID IS NULL")
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public Platform findOne(long id) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "  PLATFORM_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , NAME\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.PLATFORM\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0 AND\n" +
                        "  PLATFORM_ID = ?")
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
