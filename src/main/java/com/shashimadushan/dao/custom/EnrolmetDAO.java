package com.shashimadushan.dao.custom;

import com.shashimadushan.dao.SuperDAO;
import com.shashimadushan.entitys.Enrolment;
import java.util.List;

public interface EnrolmetDAO extends SuperDAO {
    void save(List<Enrolment> enrolments);
    void update(List<Enrolment> enrolments);
    List<Enrolment> getEnrollments(String studentId, String programName);
    void deleteEnrollments(String studentId, String programName);
}