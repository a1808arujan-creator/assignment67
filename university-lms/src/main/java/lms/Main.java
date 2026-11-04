
package lms;

import lms.listener.*;
import lms.observer.*;
import lms.source.*;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        GradeService gradeService = new GradeService(new EventPublisher());
        DeadlineService deadlineService = new DeadlineService(new EventPublisher());

        StudentNotifier student = new StudentNotifier();
        ParentNotifier parent = new ParentNotifier();
        AdvisorNotifier advisor = new AdvisorNotifier();
        AuditLogger audit = new AuditLogger();
        DeadlineReminder reminder = new DeadlineReminder();

        GpaAggregator aggregator = new GpaAggregator(new EventPublisher());

        gradeService.subscribe(EventType.GRADE_UPDATED, student);
        gradeService.subscribe(EventType.GRADE_UPDATED, parent);
        gradeService.subscribe(EventType.GRADE_UPDATED, advisor);
        gradeService.subscribe(EventType.GRADE_UPDATED, audit);
        gradeService.subscribe(EventType.GRADE_UPDATED, aggregator);

        aggregator.subscribe(EventType.GPA_UPDATED, student);
        aggregator.subscribe(EventType.GPA_UPDATED, advisor);

        deadlineService.subscribe(EventType.DEADLINE_CREATED, student);
        deadlineService.subscribe(EventType.DEADLINE_CREATED, reminder);
        deadlineService.subscribe(EventType.DEADLINE_CHANGED, reminder);
        deadlineService.subscribe(EventType.DEADLINE_CHANGED, audit);

        System.out.println("=== University LMS ===");

        gradeService.updateGrade("DAA", 90);
        gradeService.updateGrade("SDP", 80);
        gradeService.updateGrade("OS", 100);

        deadlineService.createDeadline("SDP", LocalDate.of(2026, 11, 15));
        deadlineService.changeDeadline("SDP", LocalDate.of(2026, 11, 18));
    }
}
