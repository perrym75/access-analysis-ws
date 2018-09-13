package com.infosec.accessanalysis.dao.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dao.model.UserAccount;
import com.infosec.tools.CachedResourceReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

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
        java.util.Date curDate = new java.util.Date();
        long utcOffset = Calendar.getInstance().getTimeZone().getOffset(Calendar.ZONE_OFFSET);
        int system = rs.getInt(6);
        if (system != 0) {
            Timestamp beginTS = rs.getTimestamp(7);
            Timestamp endTS = rs.getTimestamp(8);
            java.util.Date beginDate = beginTS == null ? null : new java.util.Date(beginTS.getTime() + utcOffset);
            java.util.Date endDate = endTS == null ? null : new java.util.Date(endTS.getTime() + utcOffset);
            system = (beginDate == null || curDate.after(beginDate)) &&
                    (endDate == null || curDate.before(endDate)) ? 1 : 0;
        }
        return new UserAccount(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3),
                rs.getLong(4),
                rs.getLong(5),
                system,
                rs.getInt(9));
    }
}
