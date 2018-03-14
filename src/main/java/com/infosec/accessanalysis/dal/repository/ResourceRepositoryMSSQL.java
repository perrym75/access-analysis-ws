package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ResourceRepositoryMSSQL implements ResourceRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = Configuration.getDbUrl();

    @Override
    public List<Resource> findAll() throws SQLException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT\n" +
                        "\tres.RESOURCE_ID\n" +
                        "\t, res.PARENT_ID\n" +
                        "\t, res.NAME\n" +
                        "\t, res.SYSTEM_ID\n" +
                        "\t, rt.NAME as TYPE_NAME\n" +
                        "\t, res.AGENT_ID\n" +
                        "FROM\n" +
                        "\tdbo.[RESOURCE] as res\n" +
                        "INNER JOIN\n" +
                        "\tdbo.RESOURCE_TYPE as rt\n" +
                        "ON\n" +
                        "\tres.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID\n");
        ) {
            while (rs.next()) {
                Resource entity = new Resource(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getLong(6));
                entities.add(entity);
            }
        }

        return entities;
    }

    @Override
    public List<Resource> findRangeOfAll(long from, long count) throws SQLException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(";WITH\n" +
                        "\tRES\n" +
                        "AS\n" +
                        "\t(SELECT\n" +
                        "\t\tres.RESOURCE_ID\n" +
                        "\t\t, res.PARENT_ID\n" +
                        "\t\t, res.NAME\n" +
                        "\t\t, res.SYSTEM_ID\n" +
                        "\t\t, rt.NAME as TYPE_NAME\n" +
                        "\t\t, res.AGENT_ID\n" +
                        "\t\t, ROW_NUMBER() OVER (ORDER BY res.RESOURCE_ID) as ROW_NUM\n" +
                        "\tFROM\n" +
                        "\t\tdbo.[RESOURCE] as res\n" +
                        "\tINNER JOIN\n" +
                        "\t\tdbo.RESOURCE_TYPE as rt\n" +
                        "\tON\n" +
                        "\t\tres.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID)\n" +
                        "SELECT\n" +
                        "\t*\n" +
                        "FROM\n" +
                        "\tRES\n" +
                        "WHERE\n" +
                        "\tROW_NUM >= ? AND ROW_NUM < ?\n");
        ) {
            st.setLong(1, from + 1);
            st.setLong(2, from + 1 + count);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Resource entity = new Resource(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getLong(6));
                    entities.add(entity);
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
    public List<Resource> findChildren(long id) throws SQLException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "\tres.RESOURCE_ID\n" +
                        "\t, res.PARENT_ID\n" +
                        "\t, res.NAME\n" +
                        "\t, res.SYSTEM_ID\n" +
                        "\t, rt.NAME as TYPE_NAME\n" +
                        "\t, res.AGENT_ID\n" +
                        "FROM\n" +
                        "\tdbo.[RESOURCE] as res\n" +
                        "INNER JOIN\n" +
                        "\tdbo.RESOURCE_TYPE as rt\n" +
                        "ON\n" +
                        "\tres.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID\n" +
                        "WHERE\n" +
                        "\tres.PARENT_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Resource entity = new Resource(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getLong(6));
                    entities.add(entity);
                }
            }
        }

        return entities;
    }

    @Override
    public Resource findParent(long id) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(url);
                //TODO: incorrect sql request
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "\tres.RESOURCE_ID\n" +
                        "\t, res.PARENT_ID\n" +
                        "\t, res.NAME\n" +
                        "\t, res.SYSTEM_ID\n" +
                        "\t, rt.NAME as TYPE_NAME\n" +
                        "\t, res.AGENT_ID\n" +
                        "FROM\n" +
                        "\tdbo.[RESOURCE] as res\n" +
                        "INNER JOIN\n" +
                        "\tdbo.RESOURCE_TYPE as rt\n" +
                        "ON\n" +
                        "\tres.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID\n" +
                        "WHERE\n" +
                        "\tres.PARENT_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Resource entity = new Resource(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getLong(6));
                    return entity;
                }
            }
        }

        return null;
    }

    @Override
    public List<Resource> findRoot() throws SQLException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT\n" +
                        "\tres.RESOURCE_ID\n" +
                        "\t, res.PARENT_ID\n" +
                        "\t, res.NAME\n" +
                        "\t, res.SYSTEM_ID\n" +
                        "\t, rt.NAME as TYPE_NAME\n" +
                        "\t, res.AGENT_ID\n" +
                        "FROM\n" +
                        "\tdbo.[RESOURCE] as res\n" +
                        "INNER JOIN\n" +
                        "\tdbo.RESOURCE_TYPE as rt\n" +
                        "ON\n" +
                        "\tres.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID\n" +
                        "WHERE\n" +
                        "\tres.PARENT_ID IS NULL");
        ) {
            while (rs.next()) {
                Resource entity = new Resource(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getLong(6));
                entities.add(entity);
            }
        }

        return entities;
    }

    @Override
    public Resource findOne(long id) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "\tres.RESOURCE_ID\n" +
                        "\t, res.PARENT_ID\n" +
                        "\t, res.NAME\n" +
                        "\t, res.SYSTEM_ID\n" +
                        "\t, rt.NAME as TYPE_NAME\n" +
                        "\t, res.AGENT_ID\n" +
                        "FROM\n" +
                        "\tdbo.[RESOURCE] as res\n" +
                        "INNER JOIN\n" +
                        "\tdbo.RESOURCE_TYPE as rt\n" +
                        "ON\n" +
                        "\tres.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID\n" +
                        "WHERE\n" +
                        "\tres.RESOURCE_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Resource entity = new Resource(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getLong(6));
                    return entity;
                }
            }
        }

        return null;
    }

    @Override
    public List<Resource> findByAgent(long id) throws SQLException {
        List<Resource> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement("SELECT\n" +
                        "\tres.RESOURCE_ID\n" +
                        "\t, res.PARENT_ID\n" +
                        "\t, res.NAME\n" +
                        "\t, res.SYSTEM_ID\n" +
                        "\t, rt.NAME as TYPE_NAME\n" +
                        "\t, res.AGENT_ID\n" +
                        "FROM\n" +
                        "\tdbo.[RESOURCE] as res\n" +
                        "INNER JOIN\n" +
                        "\tdbo.RESOURCE_TYPE as rt\n" +
                        "ON\n" +
                        "\tres.RESOURCE_TYPE_ID = rt.RESOURCE_TYPE_ID\n" +
                        "WHERE\n" +
                        "\tres.AGENT_ID = ?");
        ) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Resource entity = new Resource(
                            rs.getLong(1),
                            rs.getLong(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getLong(6));
                    entities.add(entity);
                }
            }
        }

        return entities;
    }
}
