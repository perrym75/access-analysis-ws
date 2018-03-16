package com.infosec.accessanalysis.api.rest;

import com.infosec.accessanalysis.dal.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.infosec.accessanalysis.api.rest",
        "com.infosec.accessanalysis.api.rest.controller"})
public class Application {

    public static void main(String[] args) {
        RepositoryFactory.registerRepository("mssql:department", DepartmentRepositoryMSSQL.class);
        RepositoryFactory.registerRepository("mssql:resource", ResourceRepositoryMSSQL.class);
        RepositoryFactory.registerRepository("mssql:platform", PlatformRepositoryMSSQL.class);
        RepositoryFactory.registerRepository("mssql:agent", AgentRepositoryMSSQL.class);
        RepositoryFactory.registerRepository("mssql:personage", PersonageRepositoryMSSQL.class);

        SpringApplication.run(Application.class, args);
    }
}
