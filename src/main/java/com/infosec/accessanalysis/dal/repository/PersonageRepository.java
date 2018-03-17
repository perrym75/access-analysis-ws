package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.Personage;
import com.infosec.tools.TextResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PersonageRepository implements Repository<Personage> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String dbUrl = Configuration.getDbUrl();

    private static String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "personage/" + query + ".sql";
    }

    @Override
    public Personage findOne(long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceLoader.loadResource(getQueryResourceName("selectPersonage")))
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
    public List<Personage> findAll() throws SQLException, IOException {
        List<Personage> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        TextResourceLoader.loadResource(getQueryResourceName("selectAllPersonages")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public List<Personage> findRangeOfAll(long from, long count) throws SQLException, IOException {
        return null;
    }

    public List<Personage> findByResource(long id) throws SQLException, IOException {
        List<Personage> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceLoader.loadResource(getQueryResourceName("selectPersonageByResource")))
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

    public List<Personage> findByDepartment(long id) throws SQLException, IOException {
        List<Personage> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceLoader.loadResource(getQueryResourceName("selectPersonagesByDepartment")))
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

    private Personage createEntity(ResultSet rs) throws SQLException {
        return new Personage(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getLong(6));
    }
}
