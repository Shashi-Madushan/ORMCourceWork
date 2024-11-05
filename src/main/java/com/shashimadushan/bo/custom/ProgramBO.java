package com.shashimadushan.bo.custom;

import com.shashimadushan.bo.SuperBO;
import com.shashimadushan.dto.ProgramDTO;

import java.util.List;

public interface ProgramBO extends SuperBO {
    public void addProgram(ProgramDTO programDTO);

    public List<ProgramDTO> getAllPrograms();

    public void updateProgram(ProgramDTO programDTO);

    public void deleteProgram(ProgramDTO programDTO);

    public List<ProgramDTO> searchPrograms(String query);
    public Long getProgramCount();

    }
