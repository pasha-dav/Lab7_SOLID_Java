import java.util.Objects;

interface DataRepository {
    void save(String entity);
}

class SqlDataRepository implements DataRepository {
    @Override
    public void save(String entity) {
        // Запис у базу даних
    }
}

public class BusinessProcessor {
    private final DataRepository repository;

    // CI з перевіркою
    public BusinessProcessor(DataRepository repository) {
        this.repository = Objects.requireNonNull(repository, "Репозиторій не може бути null!");
    }

    public void process(String data) {
        if (data == null || data.trim().isEmpty()) {
            throw new IllegalArgumentException("Дані порожні!");
        }
        repository.save(data);
    }
}