package pages;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.sql.SQLException;

public class AcademicYearEndTask {
    private StudentClassEnrollmentDAO enrollmentDAO;

    public AcademicYearEndTask() {
        this.enrollmentDAO = new StudentClassEnrollmentDAO();
    }

    public void scheduleYearEndTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the task to run at the end of the year
        scheduler.scheduleAtFixedRate(() -> {
            try {
                enrollmentDAO.updateEnrollmentForNewYear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, getInitialDelay(), 365, TimeUnit.DAYS);
    }

    private long getInitialDelay() {
        Calendar now = Calendar.getInstance();
        Calendar nextYear = Calendar.getInstance();
        nextYear.set(Calendar.MONTH, Calendar.DECEMBER);
        nextYear.set(Calendar.DAY_OF_MONTH, 31);
        nextYear.set(Calendar.HOUR_OF_DAY, 23);
        nextYear.set(Calendar.MINUTE, 59);
        nextYear.set(Calendar.SECOND, 59);
        nextYear.set(Calendar.MILLISECOND, 0);

        long currentTime = now.getTimeInMillis();
        long nextYearTime = nextYear.getTimeInMillis();
        long initialDelay = nextYearTime - currentTime;

        return TimeUnit.MILLISECONDS.toDays(initialDelay);
    }
}
