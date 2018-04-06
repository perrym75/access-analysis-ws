package com.infosec.accessanalysis.dao.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Repository<Entity> {
    Entity findOne(long id) throws SQLException, IOException;
    List<Entity> findAll() throws SQLException, IOException;
    List<Entity> findRangeOfAll(long from, long count) throws SQLException, IOException;
}
