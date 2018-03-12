package com.infosec.accessanalysis.dal.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<Entity> {
    Entity findOne(long id) throws SQLException;
    List<Entity> findAll() throws SQLException;
    List<Entity> findRangeOfAll(long from, long count) throws SQLException;
}
