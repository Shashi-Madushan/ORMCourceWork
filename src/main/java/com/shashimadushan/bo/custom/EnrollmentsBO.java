package com.shashimadushan.bo.custom;

import com.shashimadushan.bo.SuperBO;
import com.shashimadushan.dto.EnrolmentDTO;
import java.util.List;

public interface EnrollmentsBO extends SuperBO {
    void saveEnrolments(List<EnrolmentDTO> enrolmentDTOs);
    void updateEnrolments(List<EnrolmentDTO> enrolmentDTOs);
    List<EnrolmentDTO> getEnrolments(String studentId, String programName);
    void deleteEnrolment(String studentId, String programName);
}