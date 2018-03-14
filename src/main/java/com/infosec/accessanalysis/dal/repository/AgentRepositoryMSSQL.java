package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AgentRepositoryMSSQL implements AgentRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = Configuration.getDbUrl();

    @Override
    public List<Agent> findAll() throws SQLException {
        List<Agent> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT\n" +
                        "  AGENT_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , PLATFORM_ID\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.AGENT\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0");
        ) {
            while (rs.next()) {
                Agent entity = new Agent(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getLong(3),
                        rs.getString(4),
                        rs.getString(5));
                departments.add(entity);
            }
        }

        return departments;
    }

    @Override
    public List<Agent> findRangeOfAll(long from, long count) throws SQLException {
        List<Agent> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(";WITH\n" +
                        "    AGENT\n" +
                        "AS\n" +
                        "  (SELECT\n" +
                        "     AGENT_ID\n" +
                        "     , PARENT_ID\n" +
                        "     , PLATFORM_ID\n" +
                        "     , DISPLAY_NAME\n" +
                        "     , DESCRIPTION\n" +
                        "    , ROW_NUMBER() OVER (ORDER BY AGENT_ID) AS ROW_NUM\n" +
                        "  FROM\n" +
                        "    dbo.[AGENT]\n" +
                        "  WHERE\n" +
                        "    IS_DELETED = 0)\n" +
                        "SELECT\n" +
                        "  *\n" +
                        "FROM\n" +
                        "  AGENT\n" +
                        "WHERE\n" +
                        "  ROW_NUM >= ? AND ROW_NUM < ?");
        ) {
            st.setLong(1, from + 1);
            st.setLong(2, from + 1 + count);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Agent entity = new Agent(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getLong(3),
                            rs.getString(4),
                            rs.getString(5));
                    departments.add(entity);
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
    public List<Agent> findChildren(long id) throws SQLException {
        List<Agent> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "  AGENT_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , PLATFORM_ID\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.AGENT\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0 AND\n" +
                        "  PARENT_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Agent entity = new Agent(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getLong(3),
                            rs.getString(4),
                            rs.getString(5));
                    departments.add(entity);
                }
            }
        }

        return departments;
    }

    @Override
    public Agent findParent(long id) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(url);
                //TODO: incorrect sql request
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "  AGENT_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , PLATFORM_ID\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.AGENT\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0 AND\n" +
                        "  PARENT_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Agent entity = new Agent(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getLong(3),
                            rs.getString(4),
                            rs.getString(5));
                    return entity;
                }
            }
        }

        return null;
    }

    @Override
    public List<Agent> findRoot() throws SQLException {
        List<Agent> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT\n" +
                        "  AGENT_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , PLATFORM_ID\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.AGENT\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0 AND\n" +
                        "  PARENT_ID IS NULL");
        ) {
            while (rs.next()) {
                Agent entity = new Agent(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getLong(3),
                        rs.getString(4),
                        rs.getString(5));
                departments.add(entity);
            }
        }

        return departments;
    }

    @Override
    public Agent findOne(long id) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "  AGENT_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , PLATFORM_ID\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.AGENT\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0 AND\n" +
                        "  AGENT_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Agent entity = new Agent(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getLong(3),
                            rs.getString(4),
                            rs.getString(5));
                    return entity;
                }
            }
        }

        return null;
    }

    @Override
    public List<Agent> findByPlatform(long id) throws SQLException {
        List<Agent> departments = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "  AGENT_ID\n" +
                        "  , PARENT_ID\n" +
                        "  , PLATFORM_ID\n" +
                        "  , DISPLAY_NAME\n" +
                        "  , DESCRIPTION\n" +
                        "FROM\n" +
                        "  dbo.AGENT\n" +
                        "WHERE\n" +
                        "  IS_DELETED = 0 AND\n" +
                        "  PLATFORM_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Agent entity = new Agent(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getLong(3),
                            rs.getString(4),
                            rs.getString(5));
                    departments.add(entity);
                }
            }
        }

        return departments;
    }
}