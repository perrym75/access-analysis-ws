package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.api.rest.Configuration;
import com.infosec.accessanalysis.dal.model.Personage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class PersonageRepositoryMSSQL implements PersonageRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = Configuration.getDbUrl();

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
}
