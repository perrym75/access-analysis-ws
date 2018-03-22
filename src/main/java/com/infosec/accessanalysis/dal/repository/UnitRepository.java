package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.Unit;
import com.infosec.tools.TextResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UnitRepository implements HierarchicalRepository<Unit> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String url = Configuration.getDbUrl();
    private final UserAccountRepository userAccountRepository = new UserAccountRepository();

    private static String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "unit/" + query + ".sql";
    }

    @Override
    public List<Unit> findAll() throws SQLException, IOException {
        List<Unit> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(TextResourceLoader.loadResource(
                        getQueryResourceName("selectAllUnits")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public List<Unit> findRangeOfAll(long from, long count) throws SQLException, IOException {
        List<Unit> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceLoader.loadResource(getQueryResourceName("selectRangeOfUnits")))
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
    public List<Unit> findChildren(long id) throws SQLException, IOException {
        List<Unit> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceLoader.loadResource(getQueryResourceName("selectChildUnits")))
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
    public List<Unit> findRoot() throws SQLException, IOException {
        List<Unit> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        TextResourceLoader.loadResource(getQueryResourceName("selectRootUnits")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public Unit findOne(long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceLoader.loadResource(getQueryResourceName("selectUnit")))
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

    public List<Unit> findByAgent(long id) throws SQLException, IOException {
        List<Unit> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceLoader.loadResource(getQueryResourceName("selectUnitsByAgent")))
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

    private Unit createEntity(ResultSet rs) throws SQLException, IOException{
        Unit entity = new Unit(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3));
        entity.setUserAccounts(userAccountRepository.findByUnit(entity.getId()));

        return entity;
    }
}
