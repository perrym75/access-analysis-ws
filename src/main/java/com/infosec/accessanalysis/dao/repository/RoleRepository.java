package com.infosec.accessanalysis.dao.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dao.model.Role;
import com.infosec.tools.CachedResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class RoleRepository implements Repository<Role> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String dbUrl = Configuration.getDbUrl();

    private static String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "role/" + query + ".sql";
    }

    @Override
    public Role findOne(long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectRole")))
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
    public List<Role> findAll() throws SQLException, IOException {
        List<Role> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        CachedResourceReader.readString(getQueryResourceName("selectAllRoles")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public List<Role> findRangeOfAll(long from, long count) throws SQLException, IOException {
        return null;
    }

    public List<Role> findByResource(long id) throws SQLException, IOException {
        List<Role> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectRolesByResource")))
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

    public List<Role> findByPersonage(long id) throws SQLException, IOException {
        List<Role> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectRolesByPersonage")))
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

    public List<Role> findByPersonageAndResource(long pers_id, long res_id) throws SQLException, IOException {
        List<Role> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectRolesByPersonageAndResource")))
        ) {
            st.setLong(1, pers_id);
            st.setLong(2, res_id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    entities.add(createEntity(rs));
                }
            }
        }

        return entities;
    }

    private Role createEntity(ResultSet rs) throws SQLException {
        return new Role(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3));
    }
}
