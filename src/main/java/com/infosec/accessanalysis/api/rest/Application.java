package com.infosec.accessanalysis.api.rest;

import com.infosec.accessanalysis.dal.repository.DepartmentRepositoryMSSQL;
import com.infosec.accessanalysis.dal.repository.RepositoryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.infosec.accessanalysis.api.rest.controller"})
public class Application {

    public static void main(String[] args) {
        RepositoryFactory.registerDepartmentRepository("mssql", DepartmentRepositoryMSSQL.class);

        SpringApplication.run(Application.class, args);
    }
}
