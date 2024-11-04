package com.shashimadushan.dao.custom;

import com.shashimadushan.dao.SuperDAO;
import com.shashimadushan.entitys.Enrolment;

public interface EnrolmetDAO extends SuperDAO {
    void save(Enrolment enrollment);
    void update(Enrolment enrollment);
    Enrolment getEnrollment(String studentId,String programName);
    void deleteEnrollment(String studentId,String programName);
}
