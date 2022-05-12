package com.example.soundflows.constant;

import java.io.Serializable;

public class LoginConstant implements Serializable {
    public static Boolean isFromNoti = false, isFromPush = false;

    public static final String emailPattern = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
}
