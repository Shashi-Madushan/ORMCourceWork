package com.shashimadushan.bo;

import com.shashimadushan.bo.impl.ProgramBOImpl;
import com.shashimadushan.bo.impl.StudentBOImpl;
import com.shashimadushan.bo.impl.UserBOImpl;

public class BOFactory {
    public enum BOType {
        STUDENT, PROGRAM, USER
    }

    public static SuperBO getBO(BOType type) {
        switch (type) {
            case STUDENT:
                return new StudentBOImpl();
            case PROGRAM:
                return new ProgramBOImpl();
            case USER:
                return new UserBOImpl();

            default:
                return null;
        }
    }
}
