package net.javaguides.springboot.common.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Defs {
    public static boolean WRITE_SQL = true;

    public static final DateFormat sqlDateFormat=new SimpleDateFormat("yyyy-MM-dd");

    public static final String SECURITY_HEADER = "Authorization";

}
