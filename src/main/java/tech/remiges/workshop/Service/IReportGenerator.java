package tech.remiges.workshop.Service;

import java.util.List;

import tech.remiges.workshop.Entity.Employee;

public interface IReportGenerator {

    public byte[] GenerateReport(List<Employee> datalist);
}
