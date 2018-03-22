package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dal.model.Unit;
import com.infosec.accessanalysis.dal.repository.UnitRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/unit")
public class UnitController {

    private final UnitRepository unitRepository = new UnitRepository();

    @GetMapping
    public List<Unit> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                              @RequestParam(value="count", defaultValue = "1000000000000000") long count) throws SQLException, IOException {
        return unitRepository.findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public Unit getOne(@PathVariable(value="id") long id) throws SQLException, IOException {
        return unitRepository.findOne(id);
    }

    @GetMapping("/children")
    public List<Unit> getRoot() throws SQLException, IOException {
        return unitRepository.findRoot();
    }

    @GetMapping("/{id}/children")
    public List<Unit> getChildren(@PathVariable(value="id") long id) throws SQLException, IOException {
        return unitRepository.findChildren(id);
    }
}
