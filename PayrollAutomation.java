package pages;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PayrollAutomation {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask payrollTask = new TimerTask() {
            @Override
            public void run() {
                TeachersPayrollDAO payrollDAO = new TeachersPayrollDAO();
                try {
                    List<TeachersPayroll> pendingPayrolls = payrollDAO.getPendingPayrolls();
                    for (TeachersPayroll payroll : pendingPayrolls) {
                        // Calculate net salary
                        double netSalary = payroll.getSalary() + payroll.getBonus() - payroll.getDeductions();
                        payroll.setNetSalary(netSalary);
                        payroll.setStatus("paid");

                        // Update the payroll in the database
                        payrollDAO.updatePayroll(payroll);
                        System.out.println("Processed payroll for Teacher National ID: " + payroll.getNationalId());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };

        // Schedule the task to run every 24 hours (86400000 milliseconds)
        timer.scheduleAtFixedRate(payrollTask, 0, 86400000);
    }
}
