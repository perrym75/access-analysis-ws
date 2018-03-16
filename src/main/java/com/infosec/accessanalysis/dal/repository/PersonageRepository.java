package com.infosec.accessanalysis.dal.repository;

import com.infosec.accessanalysis.dal.model.Personage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface PersonageRepository extends Repository<Personage> {
    List<Personage> findByResource(long id) throws SQLException, IOException;
}
