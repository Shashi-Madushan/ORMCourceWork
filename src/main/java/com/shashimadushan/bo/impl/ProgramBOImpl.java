package com.shashimadushan.bo.impl;

import com.shashimadushan.bo.custom.ProgramBO;
import com.shashimadushan.dao.DAOFactory;
import com.shashimadushan.dao.custom.ProgramDAO;
import com.shashimadushan.dao.impl.ProgramDAOImpl;
import com.shashimadushan.dto.ProgramDTO;
import com.shashimadushan.entitys.Program;

import java.util.ArrayList;
import java.util.List;

public class ProgramBOImpl implements ProgramBO {
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDAO(DAOFactory.DAOTypes.PROGRAM);
    @Override
    public void addProgram(ProgramDTO programDTO) {
        programDAO.addProgram(new Program(programDTO.getProgramId(),programDTO.getName(),programDTO.getDurationMonths(),programDTO.getFee(),programDTO.getEnrollments()));
    }

    @Override
    public List<ProgramDTO> getAllPrograms() {
        List<Program> allCulinaryProgram = programDAO.getAllPrograms();
        List<ProgramDTO> programDTOS = new ArrayList<>();

        for (Program culinaryProgram : allCulinaryProgram) {
            programDTOS.add(new ProgramDTO(culinaryProgram.getProgramId(), culinaryProgram.getName(), culinaryProgram.getDuration(), culinaryProgram.getFee(), culinaryProgram.getEnrollments()));
        }
        return programDTOS;
    }

    @Override
    public void updateProgram(ProgramDTO programDTO) {
        programDAO.updateProgram(new Program(programDTO.getProgramId(),programDTO.getName(),programDTO.getDurationMonths(),programDTO.getFee(),programDTO.getEnrollments()));

    }

    @Override
    public void deleteProgram(ProgramDTO programDTO) {
    programDAO.deleteProgram(new Program(programDTO.getProgramId(),programDTO.getName(),programDTO.getDurationMonths(),programDTO.getFee(),programDTO.getEnrollments()));
    }

    @Override
    public List<ProgramDTO> searchPrograms(String query) {
        return List.of();
    }

    public Long getProgramCount(){
      return    programDAO.getProgramCount();
    }
}
