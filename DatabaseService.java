// Можна залишити без public перед інтерфейсами всередині одного файлу
interface DataWriter {
    void saveData(String data);
}

interface AppLogger {
    void log(String message);
}

interface ReportExporter {
    byte[] exportReport();
}

public class DatabaseService implements DataWriter, AppLogger {
    @Override
    public void saveData(String data) {
        // Логіка збереження в базу
    }

    @Override
    public void log(String message) {
        // Логіка логування
    }
}

class PdfReportService implements ReportExporter {
    @Override
    public byte[] exportReport() {
        return new byte[0];
    }
}