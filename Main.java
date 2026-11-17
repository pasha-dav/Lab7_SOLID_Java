import variant7_monolithic_code.Task1_SRP.FitnessActivityManager;
import variant7_monolithic_code.Task1_SRP.WorkoutSessionData;
import variant7_monolithic_code.Task2_OCP.ExportFormat;
import variant7_monolithic_code.Task2_OCP.ReportData;
import variant7_monolithic_code.Task2_OCP.ReportExporter;
import variant7_monolithic_code.Task3_LSP.Variant7_EmployeeManagement.Employee;
import variant7_monolithic_code.Task3_LSP.Variant7_EmployeeManagement.PayrollService;
import variant7_monolithic_code.Task3_LSP.Variant7_EmployeeManagement.VolunteerEmployee;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ЗАВДАННЯ 1 (SRP): Тестування обробки тренування ===");
        WorkoutSessionData session = new WorkoutSessionData();
        session.setUserId("user_777");
        session.setDeviceToken("token_abc123");
        session.setDistanceKm(10.5);
        session.setDurationMinutes(55);
        session.setAverageHeartRate(155);
        session.setUserWeightKg(75.0);

        FitnessActivityManager activityManager = new FitnessActivityManager();
        activityManager.processWorkoutSession(session);


        System.out.println("\n=== ЗАВДАННЯ 2 (OCP): Тестування експорту звітів ===");
        ReportData reportData = new ReportData("Квартальний звіт компанії", LocalDateTime.now());
        reportData.getHeaders().addAll(List.of("ID", "Послуга", "Сума"));
        reportData.getRows().add(List.of("1", "Консультація", "1500"));
        
        ReportExporter exporter = new ReportExporter();
        exporter.export(reportData, ExportFormat.JSON);
        exporter.export(reportData, ExportFormat.PDF);


        System.out.println("\n=== ЗАВДАННЯ 3 (LSP): Тестування системи виплат ===");
        Employee regularEmp = new Employee("EMP01", "Олександр", new BigDecimal("3000.00"));
        PayrollService payrollService = new PayrollService();
        
        // Нарахування штатному співробітнику працює успішно
        payrollService.processYearEndBonuses(regularEmp, 4.5);

        // Демонстрація проблеми порушення LSP на волонтері
        Employee volunteer = new VolunteerEmployee("VOL01", "Марія", "Червоний Хрест");
        try {
            payrollService.processYearEndBonuses(volunteer, 5.0);
        } catch (Exception e) {
            System.out.println("[ПОВІДОМЛЕННЯ LSP] Збій через порушення контракту нащадком: " + e.getMessage());
        }
    }
}