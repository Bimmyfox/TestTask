import models.LogBook;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * Наполнение базы данных тестовой информацией
 */
@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        if(LogBook.count() == 0) {
            Fixtures.deleteDatabase();
            Fixtures.loadModels("data.yml");
        }
    }

}

