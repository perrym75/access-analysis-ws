package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dao.model.Agent;
import com.infosec.accessanalysis.dao.model.Resource;
import com.infosec.accessanalysis.dao.model.Unit;
import com.infosec.accessanalysis.dao.model.UserAccount;
import com.infosec.accessanalysis.dao.repository.AgentRepository;
import com.infosec.accessanalysis.dao.repository.UserAccountRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/1.0/useraccount")
public class UserAccountController {

    private final UserAccountRepository userAccountRepository = new UserAccountRepository();

    @GetMapping
    public List<UserAccount> getAll(@RequestParam(value="page", defaultValue = "0") long page,
                                    @RequestParam(value="count", defaultValue = "1000000000000000") long count)
            throws SQLException, IOException {
        return userAccountRepository.findAll();//findRangeOfAll(page * count, count);
    }

    @GetMapping("/{id}")
    public UserAccount getOne(@PathVariable(value="id") long id) throws SQLException, IOException {
        return userAccountRepository.findOne(id);
    }

/*    @GetMapping("/children")
    public List<UserAccount> getRoot() throws SQLException, IOException {
        return userAccountRepository.findRoot();
    }

    @GetMapping("/{id}/children")
    public List<UserAccount> getChildren(@PathVariable(value="id") long id) throws SQLException, IOException {
        return userAccountRepository.findChildren(id);
    }*/
}
