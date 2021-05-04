package com.notemanagementsystem.utils;

import com.notemanagementsystem.entity.User;

public class RegularExpressions {

    /*checks the format of the email
    if true returns true and vice versa
    @Param User
    @Return boolean
    */
    public static Boolean regexEmail(String email){

        if(email.matches("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+")){
            return true;
        }

        return false;
    }

    /*checks the format of the password
    if true returns true and vice versa
    @Param User
    @Return boolean
    */
    public static Boolean regexPassword(String password){

        if(password.matches(".{6,}")){
            return true;
        }

        return false;
    }
}
