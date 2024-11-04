package com.shashimadushan.dao;

import com.shashimadushan.dao.impl.EnrolmentDAOImpl;
import com.shashimadushan.dao.impl.ProgramDAOImpl;
import com.shashimadushan.dao.impl.StudenDAOImpl;
import com.shashimadushan.dao.impl.UserDAOImpl;

public class DAOFactory {
    public enum DAOTypes {
        STUDENT, PROGRAM, USER, ENROLMENT
    }

    public static SuperDAO getDAO(DAOTypes type) {
        switch (type) {
            case STUDENT:
                return new StudenDAOImpl();
            case PROGRAM:
                return new ProgramDAOImpl();

            case USER:
                return new UserDAOImpl();

            case ENROLMENT:
                return new EnrolmentDAOImpl();

            default:
                return null;
        }
    }
}
