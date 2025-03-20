package pages;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class BillingScheduler {
    private BillingService billingService;

    public BillingScheduler() {
        billingService = new BillingService();
        scheduleBilling();
    }

    private void scheduleBilling() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String trimesterId = billingService.getCurrentTrimesterId();
                billingService.generateBillsForTrimester(trimesterId);
            }
        };

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long period = 24 * 60 * 60 * 1000L; // 24 hours in milliseconds
        timer.scheduleAtFixedRate(task, calendar.getTime(), period);
    }

    public static void main(String[] args) {
        new BillingScheduler(); // Start the billing scheduler
    }
}
