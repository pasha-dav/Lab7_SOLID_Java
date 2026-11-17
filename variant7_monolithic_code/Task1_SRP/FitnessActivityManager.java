package variant7_monolithic_code.Task1_SRP;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FitnessActivityManager {
    public void processWorkoutSession(WorkoutSessionData session) {
        if (session == null) {
            throw new IllegalArgumentException("Параметр session не може бути null.");
        }

        if (session.getDistanceKm() <= 0 || session.getDistanceKm() > 300) {
            throw new IllegalArgumentException("Некоректна дистанція забігу.");
        }

        if (session.getDurationMinutes() <= 0 || session.getDurationMinutes() > 1440) {
            throw new IllegalArgumentException("Некоректна тривалість тренування.");
        }

        if (session.getAverageHeartRate() < 40 || session.getAverageHeartRate() > 230) {
            throw new IllegalArgumentException("Пульс виходить за межі безпечних фізіологічних показників.");
        }

        double paceMinutesPerKm = session.getDurationMinutes() / session.getDistanceKm();
        double durationHours = session.getDurationMinutes() / 60.0;
        double caloriesBurned = 9.8 * session.getUserWeightKg() * durationHours;

        String heartRateZone;
        if (session.getAverageHeartRate() >= 160) {
            heartRateZone = "Анаеробна / Анаеробний поріг";
        } else if (session.getAverageHeartRate() >= 130) {
            heartRateZone = "Аеробна / Спалювання жиру";
        } else {
            heartRateZone = "Розминка / Легка активність";
        }

        int paceMinutes = (int) Math.floor(paceMinutesPerKm);
        int paceSeconds = (int) Math.round((paceMinutesPerKm - paceMinutes) * 60);

        String jsonOutput = String.format("{\n" +
                "  \"UserId\": \"%s\",\n" +
                "  \"DistanceKm\": %.2f,\n" +
                "  \"Pace\": \"%d:%02d хв/км\",\n" +
                "  \"Calories\": %.0f,\n" +
                "  \"IntensityZone\": \"%s\",\n" +
                "  \"Timestamp\": \"%s\"\n" +
                "}",
                session.getUserId(),
                session.getDistanceKm(),
                paceMinutes, paceSeconds,
                caloriesBurned,
                heartRateZone,
                Instant.now().toString()
        );

        String timestampStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String userStoragePath = String.format("workout_%s_%s.json", session.getUserId(), timestampStr);
        try (FileWriter writer = new FileWriter(userStoragePath)) {
            writer.write(jsonOutput);
        } catch (IOException e) {
            System.err.println("Помилка збереження JSON тренування: " + e.getMessage());
        }

        try {
            String pushPayload = String.format("{\"to\": \"%s\", \"title\": \"Чудове тренування!\", \"body\": \"Ви подолали %.1f км та спалили %.0f ккал!\"}",
                    session.getDeviceToken(), session.getDistanceKm(), caloriesBurned);
            System.out.println("Надсилання Push-сповіщення через http://push-gateway.local/send");
            System.out.println("Payload: " + pushPayload);
        } catch (Exception ex) {
            System.out.println("Помилка надсилання Push-сповіщення: " + ex.getMessage());
        }
    }
}