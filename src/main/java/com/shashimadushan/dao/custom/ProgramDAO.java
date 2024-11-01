package com.shashimadushan.dao.custom;


import com.shashimadushan.dao.SuperDAO;
import com.shashimadushan.entitys.Program;

import java.util.List;

public interface ProgramDAO extends SuperDAO {

    public void addProgram(Program program);

    public List<Program> getAllPrograms();

    public void updateProgram(Program program);

    public void deleteProgram(Program program);

    public List<Program> searchPrograms(String query);

}