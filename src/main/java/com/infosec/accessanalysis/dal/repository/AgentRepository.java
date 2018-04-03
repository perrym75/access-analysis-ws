package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.Agent;
import com.infosec.tools.TextResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AgentRepository implements HierarchicalRepository<Agent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String url = Configuration.getDbUrl();

    private String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "agent/" + query + ".sql";
    }

    @Override
    public List<Agent> findAll() throws SQLException, IOException {
        List<Agent> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        TextResourceReader.readResource(getQueryResourceName("selectAllAgents")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public List<Agent> findRangeOfAll(long from, long count) throws SQLException, IOException {
        List<Agent> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectRangeOfAgents")))
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
    public List<Agent> findChildren(long id) throws SQLException, IOException {
        List<Agent> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectChildAgents")))
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
    public List<Agent> findRoot() throws SQLException, IOException {
        List<Agent> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        TextResourceReader.readResource(getQueryResourceName("selectRootAgents")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public Agent findOne(long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectAgent")))
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

    public List<Agent> findByPlatform(long id) throws SQLException, IOException {
        List<Agent> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectAgentsByPlatform")))
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

    private Agent createEntity(ResultSet rs) throws SQLException {
        return new Agent(
                rs.getLong(1),
                rs.getLong(2),
                rs.getLong(3),
                rs.getString(4),
                rs.getString(5));
    }
}
