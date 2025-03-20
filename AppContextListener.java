package pages;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        AcademicYearEndTask task = new AcademicYearEndTask();
        task.scheduleYearEndTask();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup code if needed
    }
}
