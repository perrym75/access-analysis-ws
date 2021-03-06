package com.infosec.accessanalysis.dao.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dao.model.AccessRight;
import com.infosec.tools.CachedResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AccessRightRepository implements Repository<AccessRight> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String dbUrl = Configuration.getDbUrl();

    private static String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "access_right/" + query + ".sql";
    }

    @Override
    public AccessRight findOne(long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectAccessRight")))
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
                        CachedResourceReader.readString(getQueryResourceName("selectAllAccessRights")))
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

    public List<AccessRight> findByPersonageResourceUserAccount(long personageId, long resourceId,
                                                               long userAccountId)
            throws SQLException, IOException {
        List<AccessRight> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectResourceAccessViaUserAccount")))
        ) {
            st.setLong(1, personageId);
            st.setLong(2, resourceId);
            st.setLong(3, userAccountId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }

    public List<AccessRight> findByPersonageResourceRole(long personageId, long resourceId, long roleId)
            throws SQLException, IOException {
        List<AccessRight> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectResourceAccessViaRoles")))
        ) {
            st.setLong(1, personageId);
            st.setLong(2, resourceId);
            st.setLong(3, roleId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }

    public List<AccessRight> findByRoleResource(long roleId, long resourceId)
            throws SQLException, IOException {
        List<AccessRight> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectAccessOfRoleResource")))
        ) {
            st.setLong(1, roleId);
            st.setLong(2, resourceId);
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
