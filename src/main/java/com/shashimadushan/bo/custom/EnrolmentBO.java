package com.shashimadushan.bo.custom;

import com.shashimadushan.bo.SuperBO;
import com.shashimadushan.dto.EnrolmetDTO;

import java.util.List;


public interface EnrolmentBO  extends SuperBO {
    void save(EnrolmetDTO enrollment);
    void saveAll(List<EnrolmetDTO> enrollments);
    void update(EnrolmetDTO enrollment);
   EnrolmetDTO getEnrollment(String studentId,String programName);
    void deleteEnrollment(String studentId,String programName);

}
