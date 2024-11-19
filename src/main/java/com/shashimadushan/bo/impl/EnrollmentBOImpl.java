package com.shashimadushan.bo.impl;

import com.shashimadushan.bo.custom.EnrollmentsBO;
import com.shashimadushan.dao.custom.EnrolmetDAO;
import com.shashimadushan.dao.impl.EnrolmentDAOImpl;
import com.shashimadushan.dto.EnrolmentDTO;
import com.shashimadushan.dto.ProgramDTO;
import com.shashimadushan.dto.StudentDTO;
import com.shashimadushan.entitys.Enrolment;
import com.shashimadushan.entitys.Program;
import com.shashimadushan.entitys.Student;

import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentBOImpl implements EnrollmentsBO {

    private final EnrolmetDAO enrolmetDAO = new EnrolmentDAOImpl();

    @Override
    public void saveEnrolments(List<EnrolmentDTO> enrolmentDTOs) {
        List<Enrolment> enrolments = enrolmentDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        enrolmetDAO.save(enrolments);
    }

    @Override
    public void updateEnrolments(List<EnrolmentDTO> enrolmentDTOs) {
        List<Enrolment> enrolments = enrolmentDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        enrolmetDAO.update(enrolments);
    }

    @Override
    public List<EnrolmentDTO> getEnrolments(String studentId, String programName) {
        List<Enrolment> enrolments = enrolmetDAO.getEnrollments(studentId, programName);
        return enrolments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEnrolment(String studentId, String programName) {
        enrolmetDAO.deleteEnrollments(studentId, programName);
    }

    private Enrolment convertToEntity(EnrolmentDTO dto) {
        Enrolment enrolment = new Enrolment();
        enrolment.setId(dto.getId());
        enrolment.setRegistrationDate(dto.getRegistrationDate());
        enrolment.setPayment(dto.getPayment());

        Student student = new Student();
        student.setId(dto.getStudent().getId());
        enrolment.setStudent(student);

        Program program = new Program();
        program.setProgramId(dto.getProgram().getProgramId());
        enrolment.setProgram(program);

        return enrolment;
    }

    private EnrolmentDTO convertToDTO(Enrolment enrolment) {
        Student student = enrolment.getStudent();
        Program program = enrolment.getProgram();

        EnrolmentDTO dto = new EnrolmentDTO();
        dto.setId(enrolment.getId());
        dto.setRegistrationDate(enrolment.getRegistrationDate());
        dto.setPayment(enrolment.getPayment());

        dto.setProgram(new ProgramDTO(program.getProgramId(), program.getName(), program.getDuration(), program.getFee(), program.getEnrollments()));

        return dto;
    }
}