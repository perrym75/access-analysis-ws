package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.AccessRight;
import com.infosec.tools.TextResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AccessRightRepository implements Repository<AccessRight> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String dbUrl = Configuration.getDbUrl();

    private static String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "accessright/" + query + ".sql";
    }

    @Override
    public AccessRight findOne(long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceLoader.loadResource(getQueryResourceName("selectAccessRight")))
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
    public List<AccessRight> findAll() throws SQLException, IOException {
        List<AccessRight> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        TextResourceLoader.loadResource(getQueryResourceName("selectAllAccessRights")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public List<AccessRight> findRangeOfAll(long from, long count) throws SQLException, IOException {
        return null;
    }

    public Set<AccessRight> findByPersonageAndResourceViaSP(long personageId, long resourceId)
            throws SQLException, IOException {
        Set<AccessRight> entities = new HashSet<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceLoader.loadResource(getQueryResourceName("selectResourceAccessViaSP")))
        ) {
            st.setLong(1, personageId);
            st.setLong(1, resourceId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }

    public Set<AccessRight> findByPersonageAndResourceViaRoles(long personageId, long resourceId)
            throws SQLException, IOException {
        Set<AccessRight> entities = new HashSet<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceLoader.loadResource(getQueryResourceName("selectResourceAccessViaRoles")))
        ) {
            st.setLong(1, personageId);
            st.setLong(1, resourceId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }
    private AccessRight createEntity(ResultSet rs) throws SQLException {
        return new AccessRight(
                rs.getLong(1),
                rs.getString(2));
    }
}
