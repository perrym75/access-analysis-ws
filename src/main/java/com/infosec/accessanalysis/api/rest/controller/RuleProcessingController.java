package com.infosec.accessanalysis.api.rest.controller;

import com.infosec.accessanalysis.algorithm.Subsets;
import com.infosec.accessanalysis.business.ResourceAccess;
import com.infosec.accessanalysis.dao.model.PersResAccRights;
import com.infosec.accessanalysis.dao.repository.ModelBuilderRepository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.groupingBy;

@RestController
@RequestMapping("/api/1.0/rule-process")
public class RuleProcessingController {

    private final AtomicLong counter = new AtomicLong();
    private ModelBuilderRepository modelBuilderRepository = new ModelBuilderRepository();

    @PutMapping
    public long processRules() {

        return 0;
    }

    @GetMapping("/{id}/progress")
    public long getProcessProgress(@PathVariable(value="id") long id) {
        return counter.incrementAndGet();
    }


    @GetMapping("/{id}/result")
    public Map<Long, Map<Set<ResourceAccess>, Set<Long>>> getProcessResult(@PathVariable(value="id") long id)
            throws SQLException, IOException {
        Map<Long, Map<Set<ResourceAccess>, Set<Long>>> result = new HashMap<>();

        List<PersResAccRights> rights = modelBuilderRepository.findAll();
        Map<Long, List<PersResAccRights>> groupByDep = rights.stream().collect(groupingBy(PersResAccRights::getDepartmentId));

        for (Map.Entry<Long, List<PersResAccRights>> departmentEntry : groupByDep.entrySet()) {
            Long departmentId = departmentEntry.getKey();
            List<PersResAccRights> allAccOfDep = departmentEntry.getValue();
            Map<Long, List<PersResAccRights>> groupByPers =
                    allAccOfDep.stream().collect(groupingBy(PersResAccRights::getPersonageId));

            Map<Set<ResourceAccess>, Set<Long>> departmentRoles = new HashMap<>();
            for (Map.Entry<Long, List<PersResAccRights>> personageEntry : groupByPers.entrySet()) {
                Long personageId = personageEntry.getKey();
                List<PersResAccRights> allAccOfPers = personageEntry.getValue();
                Set<ResourceAccess> personageAccess = new HashSet<>();
                for (PersResAccRights persAcc : allAccOfPers) {
                    ResourceAccess ra = new ResourceAccess(persAcc.getResourceId());
                    ra.addAccessRights(persAcc.getAccessRights());
                    personageAccess.add(ra);
                }

                Set<Set<ResourceAccess>> subsets = Subsets.getSubsets(personageAccess);

                for (Set<ResourceAccess> item : subsets) {
                    if (!departmentRoles.containsKey(item)) {
                        departmentRoles.put(item, new HashSet<>());
                    }
                    departmentRoles.get(item).add(personageId);
                }
            }

            boolean eliminated = true;
            whileloop:
            while (eliminated) {
                eliminated = false;
                for (Set<ResourceAccess> item1 : departmentRoles.keySet()) {
                    for (Set<ResourceAccess> item2 : departmentRoles.keySet()) {
                        if (!item1.equals(item2) && departmentRoles.get(item1).equals(departmentRoles.get(item2))) {
                            if (item1.containsAll(item2)) {
                                departmentRoles.remove(item2);
                                eliminated = true;
                                continue whileloop;
                            }

                            if (item2.containsAll(item1)) {
                                departmentRoles.remove(item1);
                                eliminated = true;
                                continue whileloop;
                            }
                        }
                    }
                }
            }

            result.put(departmentId, departmentRoles);
        }
        return result;
    }
}
