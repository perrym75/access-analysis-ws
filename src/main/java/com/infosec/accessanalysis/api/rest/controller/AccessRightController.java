package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.dao.model.AccessRight;
import com.infosec.accessanalysis.dao.repository.AccessRightRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/api/1.0/accessright")
public class AccessRightController {

    private final AccessRightRepository accessRightRepository = new AccessRightRepository();

    @GetMapping("/useraccount")
    public Collection<AccessRight> getByPersonageResourceUserAccount(@RequestParam(value="personageId") long personageId,
                                                                     @RequestParam(value="resourceId") long resourceId,
                                                                     @RequestParam(value="userAccountId") long userAccountId)
            throws SQLException, IOException {
        return accessRightRepository.findByPersonageResourceUserAccount(personageId, resourceId, userAccountId);
    }

    @GetMapping("role")
    public Collection<AccessRight> getByPersonageResourceRole(@RequestParam(value="personageId") long personageId,
                                                                     @RequestParam(value="resourceId") long resourceId,
                                                                     @RequestParam(value="roleId") long roleId)
            throws SQLException, IOException {
        return accessRightRepository.findByPersonageResourceRole(personageId, resourceId, roleId);
    }
}
