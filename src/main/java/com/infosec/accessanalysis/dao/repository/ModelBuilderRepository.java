package com.infosec.accessanalysis.dao.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dao.model.PersResAccRights;
import com.infosec.tools.TextResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ModelBuilderRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String dbUrl = Configuration.getDbUrl();

    private static String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "model_builder/" + query + ".sql";
    }

    public List<PersResAccRights> findAll() throws SQLException, IOException {
        List<PersResAccRights> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        TextResourceReader.readResource(getQueryResourceName("selectAllPersonageAccess")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }
    private PersResAccRights createEntity(ResultSet rs) throws SQLException {
        return new PersResAccRights(
                rs.getLong(1),
                rs.getLong(2),
                rs.getLong(3),
                rs.getString(4));
    }
}
