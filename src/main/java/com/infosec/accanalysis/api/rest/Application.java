package com.infosec.accanalysis.api.rest;

import com.infosec.accanalysis.api.rest.model.DepartmentRepositoryMSSQL;
import com.infosec.accanalysis.api.rest.model.DepartmentRepositoryPostgreSQL;
import com.infosec.accanalysis.dbo.repository.RepositoryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.infosec.accanalysis.api.rest.controller",
        "com.infosec.accanalysis.api.rest.model"})
public class Application {

    public static void main(String[] args) {
        RepositoryFactory.registerDepartmentRepository("mssql", DepartmentRepositoryMSSQL.class);
        RepositoryFactory.registerDepartmentRepository("pgsql", DepartmentRepositoryPostgreSQL.class);
        
        SpringApplication.run(Application.class, args);
    }
}
