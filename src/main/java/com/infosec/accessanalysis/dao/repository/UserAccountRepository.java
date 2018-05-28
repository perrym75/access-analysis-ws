package com.infosec.accessanalysis.dao.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dao.model.UserAccount;
import com.infosec.tools.CachedResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserAccountRepository implements Repository<UserAccount> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String dbUrl = Configuration.getDbUrl();

    private static String getQueryResourceName(String query) {
        return Configuration.getSqlQueryResourcePrefix() + "user_account/" + query + ".sql";
    }

    @Override
    public UserAccount findOne(long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectUserAccount")))
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
    public List<UserAccount> findAll() throws SQLException, IOException {
        List<UserAccount> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        CachedResourceReader.readString(getQueryResourceName("selectAllUserAccounts")))
        ) {
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        }

        return entities;
    }

    @Override
    public List<UserAccount> findRangeOfAll(long from, long count) throws SQLException, IOException {
        return null;
    }

    public List<UserAccount> findByResource(long id) throws SQLException, IOException {
        List<UserAccount> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectUserAccountsByResource")))
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

    public List<UserAccount> findByUnit(long id) throws SQLException, IOException {
        List<UserAccount> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectUserAccountsByUnit")))
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

    public List<UserAccount> findByPersonage(long id) throws SQLException, IOException {
        List<UserAccount> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectUserAccountsByPersonage")))
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

    public List<UserAccount> findByPersonageAndResource(long pers_id, long res_id) throws SQLException, IOException {
        List<UserAccount> entities = new LinkedList<>();

        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        CachedResourceReader.readString(getQueryResourceName("selectUserAccountsByPersonageAndResource")))
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

    private UserAccount createEntity(ResultSet rs) throws SQLException {
        return new UserAccount(
                rs.getLong(1),
                rs.getString(2),
                rs.getLong(3),
                rs.getLong(4),
                rs.getInt(5));
    }
}
