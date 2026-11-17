package variant7_monolithic_code.Task2_OCP;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportExporter {
    public String export(ReportData data, ExportFormat format) {
        StringBuilder builder = new StringBuilder();

        switch (format) {
            case PDF:
                builder.append("%PDF-1.7 HEADER\n");
                builder.append("TITLE: ").append(data.getTitle()).append("\n");
                builder.append("GENERATED: ").append(data.getGeneratedAt().format(DateTimeFormatter.ISO_LOCAL_DATE)).append("\n");
                builder.append("[PDF Table Stream Rendered with ").append(data.getRows().size()).append(" rows]\n");
                break;

            case EXCEL:
                builder.append("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\">\n");
                builder.append("  <Worksheet ss:Name=\"").append(data.getTitle()).append("\">\n");
                builder.append("    <!-- Rendered ").append(data.getRows().size()).append(" rows -->\n");
                builder.append("  </Worksheet>\n");
                builder.append("</Workbook>\n");
                break;

            case CSV:
                builder.append(String.join(",", data.getHeaders())).append("\n");
                for (List<String> row : data.getRows()) {
                    builder.append(String.join(",", row)).append("\n");
                }
                break;

            case JSON:
                builder.append("{\n");
                builder.append("  \"title\": \"").append(data.getTitle()).append("\",\n");
                builder.append("  \"generatedAt\": \"").append(data.getGeneratedAt()).append("\",\n");
                builder.append("  \"totalRows\": ").append(data.getRows().size()).append("\n");
                builder.append("}\n");
                break;

            default:
                throw new UnsupportedOperationException("Формат експорту " + format + " не підтримується.");
        }

        System.out.println("[EXPORTER] Успішно створено звіт '" + data.getTitle() + "' у форматі " + format + ". Довжина: " + builder.length() + " символів.");
        return builder.toString();
    }
}