package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.Personage;
import com.infosec.accessanalysis.dal.model.Resource;
import com.infosec.accessanalysis.dal.sql.TextResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PersonageRepositoryMSSQL implements PersonageRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String dbUrl = Configuration.getDbUrl();

    private String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "personage/" + query + ".sql";
    }

    @Override
    public Personage findOne(long id) throws SQLException {

        return null;
    }

    @Override
    public List<Personage> findAll() throws SQLException {
        return null;
    }

    @Override
    public List<Personage> findRangeOfAll(long from, long count) throws SQLException {
        return null;
    }

    @Override
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
