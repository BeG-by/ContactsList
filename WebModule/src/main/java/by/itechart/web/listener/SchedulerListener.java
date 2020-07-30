package by.itechart.web.listener;

import by.itechart.logic.util.BirthdayNotificationJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class SchedulerListener implements ServletContextListener {

    private Scheduler scheduler;

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerListener.class);
    private static final String cronExpression = "0 0 12 * * ?"; // every day at 12:00

    public void contextInitialized(ServletContextEvent sce) {

        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

            JobDetail job = JobBuilder.newJob(BirthdayNotificationJob.class)
                    .withIdentity("birthdayNotification")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("birthdayTrigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();

            scheduler.scheduleJob(job, trigger);
            LOGGER.info("Scheduler started !");

        } catch (SchedulerException e) {
            LOGGER.error("Scheduler failed !", e);
        }

    }

    public void contextDestroyed(ServletContextEvent sce) {

        try {
            scheduler.shutdown();
            LOGGER.info("Scheduler was closed");
        } catch (Exception e) {
            LOGGER.error("Scheduler wasn't closed !", e);
        }
    }

}
