package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.UserAccount;
import com.infosec.tools.TextResourceReader;
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
        return Configuration.getSqlQueryResourcePrefix() + "useraccount/" + query + ".sql";
    }

    @Override
    public UserAccount findOne(long id) throws SQLException, IOException {
        try (
                Connection conn = DriverManager.getConnection(dbUrl);
                PreparedStatement st = conn.prepareStatement(
                        TextResourceReader.readResource(getQueryResourceName("selectUserAccount")))
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
                        TextResourceReader.readResource(getQueryResourceName("selectAllUserAccounts")))
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
                        TextResourceReader.readResource(getQueryResourceName("selectUserAccountsByResource")))
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
                        TextResourceReader.readResource(getQueryResourceName("selectUserAccountsByUnit")))
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

    private UserAccount createEntity(ResultSet rs) throws SQLException {
        return new UserAccount(
                rs.getLong(1),
                rs.getString(2),
                rs.getLong(3),
                rs.getInt(4));
    }
}
