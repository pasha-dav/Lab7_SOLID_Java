package variant7_monolithic_code.Task3_LSP;

import java.math.BigDecimal;

public class Variant7_EmployeeManagement {

    public static class Employee {
        private String employeeId;
        private String fullName;
        private BigDecimal baseSalary;

        public Employee(String employeeId, String fullName, BigDecimal baseSalary) {
            this.employeeId = employeeId;
            this.fullName = fullName;
            this.baseSalary = baseSalary;
        }

        public String getEmployeeId() { return employeeId; }
        public String getFullName() { return fullName; }
        public BigDecimal getBaseSalary() { return baseSalary; }

        public void setSalary(BigDecimal newSalary) {
            if (newSalary.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Salary cannot be negative");
            this.baseSalary = newSalary;
        }

        public BigDecimal calculateAnnualBonus(double performanceRating) {
            if (performanceRating < 1.0 || performanceRating > 5.0) throw new IllegalArgumentException("Invalid rating");
            return baseSalary.multiply(BigDecimal.valueOf(0.10 * performanceRating));
        }
    }

    public static class VolunteerEmployee extends Employee {
        private String organizationName;

        public VolunteerEmployee(String employeeId, String fullName, String organizationName) {
            super(employeeId, fullName, BigDecimal.ZERO);
            this.organizationName = organizationName;
        }

        @Override
        public void setSalary(BigDecimal newSalary) {
            throw new IllegalStateException("Volunteers do not receive a salary and their base rate cannot be modified.");
        }

        @Override
        public BigDecimal calculateAnnualBonus(double performanceRating) {
            throw new UnsupportedOperationException("Volunteers are not eligible for financial annual bonuses.");
        }
    }

    public static class PayrollService {
        public void processYearEndBonuses(Employee employee, double rating) {
            BigDecimal bonus = employee.calculateAnnualBonus(rating);
            System.out.println("Bonus for " + employee.getFullName() + ": $" + bonus);
        }
    }
}