package domain;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.HashSet;
import java.util.Locale;

public class StudyYear<ID> extends Entity<ID> {

    private static int year = Year.now().getValue();
    private static final LocalDate sem1 = LocalDate.of(year,10,1);
    private static final LocalDate sem2 = LocalDate.of(year+1,2,24);

    public StudyYear(ID id) {
        super(id);
    }


    public static int getNumberofWeeks(){
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }

    public static int getSemesterWeek() {
        LocalDate startofSemester;
        if(getNumberofWeeks()>=39||getNumberofWeeks()<=7){
            startofSemester = sem1;
        }
        else
        {
            startofSemester=sem2;
        }
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return getNumberofWeeks()-startofSemester.get(weekFields.weekOfWeekBasedYear())+1;
    }

    public static int getMotivationsBetween(int start, int end, HashSet<Integer> vec){
        int c=0;
        for(int x:vec){
            if(x>=start &&x<=end)
                c++;
        }
        return c;
    }
}
