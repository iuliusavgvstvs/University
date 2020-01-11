package service;

import domain.*;

import java.text.DecimalFormat;
import java.util.HashMap;

public class RaportService<ID, E extends Entity<ID>> {
    private StudentService<ID, Student<ID>> studentService;
    private GradeService<ID, Grade<ID>> gradeService;
    private HomeworkService<ID, Homework<ID>> homeworkService;

    public RaportService(StudentService<ID, Student<ID>> studService, GradeService<ID, Grade<ID>> gService, HomeworkService<ID, Homework<ID>> hService){
        this.studentService=studService;
        this.gradeService=gService;
        this.homeworkService = hService;
    }


    public Iterable<Raport> getAverage(double minAvg, double maxAvg){
        HashMap<Integer, Raport> hmap = new HashMap<>();
        for( Student s: studentService.getAll()) {
            float average = 0;
            float weightsum = 0;
            float weight;
            for (Grade g : gradeService.getAll()) {
                if (g.getStudentId().equals( s.getId())) {
                    weight = homeworkService.getWeight((ID) g.getHomeworkID());
                    if (weight != -1) {
                        weightsum += weight;
                        average = average+ (g.getGrade() * weight);
                    }
                }
            }
            average=average/weightsum;
            if (average > 0&&average>=minAvg &&average<=maxAvg) {
                DecimalFormat df = new DecimalFormat("#.##");
                Raport rap = new Raport(s,Double.parseDouble(df.format(average)));
                hmap.put((Integer.parseInt((String) s.getId())), rap);
            }
        }
        return hmap.values();
    }
    public Iterable<Raport> getAverageByHomeworks(){
        HashMap<Integer,Raport> hmap = new HashMap<>();
        for(Homework h: homeworkService.getAll()){
            float average=0;
            float weightsum=0;
            float weight;
            for(Grade g: gradeService.getAll()){
                if(g.getHomeworkID().equals(h.getId())){
                    weight = homeworkService.getWeight((ID) g.getHomeworkID());
                    if(weight!=-1){
                        weightsum += weight;
                        average = average+(g.getGrade()*weight);
                    }
                }
            }
            if(average>0){
                DecimalFormat df = new DecimalFormat("#.##");
                Raport rap = new Raport(h,Double.parseDouble(df.format(average / weightsum)));
                hmap.put((Integer.parseInt((String) h.getId())), rap);
            }
        }
        return hmap.values();
    }

    public Iterable<Raport> getGradedAtTime() {
        HashMap<Integer, Raport> hmap = new HashMap<>();
        for( Student s: studentService.getAll()) {
            boolean gradedAtTime=true;
            float average = 0;
            float weightsum = 0;
            float weight;
            for (Grade g : gradeService.getAll()) {
                if (g.getStudentId().equals( s.getId())) {
                    weight = homeworkService.getWeight((ID) g.getHomeworkID());
                    if (weight != -1) {
                        weightsum += weight;
                        average = average+ (g.getGrade() * weight);
                    }
                    if(g.getWeek()>homeworkService.find((ID) g.getHomeworkID()).getDeadlineWeek())
                        gradedAtTime=false;
                }
            }
            if (average > 0&& gradedAtTime) {
                DecimalFormat df = new DecimalFormat("#.##");
                Raport rap = new Raport(s,Double.parseDouble(df.format(average / weightsum)));
                hmap.put((Integer.parseInt((String) s.getId())), rap);
            }
        }
        return hmap.values();
    }
}
