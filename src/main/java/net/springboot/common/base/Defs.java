package net.springboot.common.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Defs {
    public static boolean WRITE_SQL = true;

    public static final DateFormat sqlDateFormat=new SimpleDateFormat("yyyy-MM-dd");

    public static final String SECURITY_HEADER = "Authorization";

    public static final Integer DEFAULT_LIMIT = 10;
    public static final Integer DEFAULT_PAGE = 0;

}
