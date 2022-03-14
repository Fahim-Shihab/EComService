package net.springboot.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static LocalDate getNextBusinessDateIfWeekend(LocalDate date) {
        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
        if (!weekend.contains(date)) {
            return date;
        }

        return date.getDayOfWeek().equals(DayOfWeek.FRIDAY)
                ? date.plusDays(2) : date.plusDays(1);
    }

    public static final boolean isInList(Integer value, Integer... values) {
        if (!isOk(value) || values == null || values.length == 0) {
            return false;
        }
        for (Integer val : values) {
            if (isOk(val) && val.intValue() == value.intValue()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isInList(Integer value, List<Integer> values) {
        if (!isOk(value) || values == null || values.size() == 0) {
            return false;
        }
        for (Integer val : values) {
            if (isOk(val) && val.intValue() == value.intValue()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInListList(List<Integer> ops, Integer... values) {
        if (ops == null || ops.size() == 0) {
            return false;
        }
        if (values == null || values.length == 0) {
            return false;
        }

        for (Integer op : ops) {
            if (!Utils.isOk(op)) {
                continue;
            }
            for (Integer val : values) {
                if (!Utils.isOk(val)) {
                    continue;
                }
                if (val.equals(op)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static final boolean isInList(String value, String... values) {
        if (!Utils.isOk(value) || values == null || values.length <= 0) {
            return false;
        }
        for (String val : values) {
            if (Utils.isOk(val) && val.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }


    public static boolean byteExists(byte[] value) {
        return !(value == null || value.length == 0);
    }

    public static boolean isOk(Integer value) {
        return !(value == null || value.intValue() <= 0);
    }

    public static boolean isOk(Short value) {
        return !(value == null || value <= 0);
    }

    public static boolean isOk(Long value) {
        return !(value == null || value.longValue() <= 0);
    }

    public static boolean isOk(BigInteger value) {
        return !(value == null || value.intValue() <= 0);
    }

    public static boolean isOk(String str) {
        return !(str == null || str.trim().isEmpty());
    }

    public static final String getMd5(String value) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(value.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static boolean isOk(Enum value) {
        return !(value == null);
    }

    public static boolean isOk(Boolean value) {
        return !(value == null || value.booleanValue() == false);
    }

    public static boolean isOk(LocalDate date) {
        return !(date == null);
    }

    public static boolean isOk(Instant date) {
        return !(date == null);
    }

    public static String getStringFromInstant(Instant instant) {
        if (instant == null) {
            return null;
        }
        try {
            Date myDate = Date.from(instant);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(myDate);
        } catch (Throwable t) {
            t.printStackTrace();
            return "";
        }
    }

    public static String getStringFromInstant(Instant instant, String format) {
        if (instant == null) {
            return null;
        }
        try {
            Date myDate = Date.from(instant);
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(myDate);
        } catch (Throwable t) {
            t.printStackTrace();
            return "";
        }
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        if (dateToConvert != null) {
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        return null;
    }

    public static LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }


    public static String getDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String getDateOnly(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getDateToString(Date date) {
        if (date == null) {
            return "0000-00-00";
        }

        return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    public static String getDateToStringOracle(Date date) {
        if (date == null) {
            return "0000-00-00";
        }

        return (new SimpleDateFormat("dd-MM-yyyy HH:mm")).format(date).toUpperCase();
    }

    public static String getDateToStringOracle(Date date, String format) {
        if (date == null) {
            return null;
        }

        return (new SimpleDateFormat(format)).format(date).toUpperCase();
    }

    public static String getDateToString(Date date, String dateFormat) {
        if (date == null) {
            return null;
        }

        return (new SimpleDateFormat(dateFormat)).format(date);
    }

    public static boolean isNull(Integer value) {
        return value == null || value == 0;
    }

    public static boolean isNull(String value) {
        return value == null || value.length() == 0;
    }

    public static final boolean getBooleanFromObject(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Boolean) {
            return ((Boolean) object).booleanValue();
        }
        if (object instanceof Integer) {
            if (Utils.getIntegerFromObject(object) == 1) {
                return true;
            } else {
                return false;
            }
        }
        if (object instanceof Long) {
            if (((Long) object).intValue() == 1) {
                return true;
            } else {
                return false;
            }
        }
        if (object instanceof Short) {
            if (((Short) object).intValue() == 1) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public static Integer getIntegerFromObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Integer) {
            return ((Integer) object).intValue();
        } else if (object instanceof Long) {
            return ((Long) object).intValue();
        } else if (object instanceof BigDecimal) {
            return ((BigDecimal) object).intValue();
        } else if (object instanceof BigInteger) {
            return ((BigInteger) object).intValue();
        } else if (object instanceof Short) {
            return ((Short) object).intValue();
        } else if (object instanceof Double) {
            return ((Double) object).intValue();
        } else if (object instanceof Number) {
            return ((Number) object).intValue();
        } else if (object instanceof String) {
            try {
                return Integer.parseInt((String) object);
            } catch (Throwable t) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static Date getValidDate(String date) {

        Date mydate = null;
        if (isValidDateFormat(date)) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            try {
                mydate = dateFormat.parse(date);
            } catch (ParseException e) {
                mydate = null;
            }
        }
        return mydate;
    }

    public static String getDayName(int dayofWeek) {

        String dayName = null;
        switch (dayofWeek) {
            case 1:
                dayName = "SUNDAY";
                break;
            case 2:
                dayName = "MONDAY";
                break;
            case 3:
                dayName = "TUESDAY";
                break;
            case 4:
                dayName = "WEDNESDAY";
                break;
            case 5:
                dayName = "THURSDAY";
                break;
            case 6:
                dayName = "FRIDAY";
                break;
            case 7:
                dayName = "SATURDAY";
                break;
        }
        return dayName;
    }

    public static int getDayNumber(String dayofWeek) {

        int dayName = 0;
        switch (dayofWeek) {
            case "SUNDAY":
                dayName = 1;
                break;
            case "MONDAY":
                dayName = 2;
                break;
            case "TUESDAY":
                dayName = 3;
                break;
            case "WEDNESDAY":
                dayName = 4;
                break;
            case "THURSDAY":
                dayName = 5;
                break;
            case "FRIDAY":
                dayName = 6;
                break;
            case "SATURDAY":
                dayName = 7;
                break;
        }
        return dayName;
    }

    private static boolean isValidDateFormat(String date) {

        /*
         * Regular Expression that matches String with format dd/MM/yyyy.
         * dd -> 01-31
         * MM -> 01-12
         * yyyy -> 4 digit number
         */
        String pattern = "(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})";
        boolean result = false;
        if (date.matches(pattern)) {
            result = true;
        }
        return result;
    }


    public static Date dateFromLocalDate(LocalDate ldate) {
        return Date.from(ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static boolean different(String oldValue, String newValue) {
        if (isOk(newValue) && isOk(oldValue)) {
            return !newValue.equalsIgnoreCase(oldValue);
        } else if (isOk(newValue) || isOk(oldValue)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean different(LocalDate oldValue, LocalDate newValue) {
        if (isOk(newValue) && isOk(oldValue)) {
            return newValue.compareTo(oldValue) != 0;
        } else if (isOk(newValue) || isOk(oldValue)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean different(Instant oldValue, Instant newValue) {
        if (isOk(newValue) && isOk(oldValue)) {
            return newValue.compareTo(oldValue) != 0;
        } else if (isOk(newValue) || isOk(oldValue)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean different(Enum oldValue, Enum newValue) {
        if (isOk(newValue) && isOk(oldValue)) {
            return !newValue.name().equals(oldValue.name());
        } else if (isOk(newValue) || isOk(oldValue)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean different(Integer oldValue, Integer newValue) {
        if (isOk(newValue) && isOk(oldValue)) {
            return oldValue.intValue() != newValue.intValue();
        } else if (isOk(newValue) || isOk(oldValue)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean different(Object oldValue, Object currentValue) {
        if (isOk(oldValue) && isOk(currentValue)) {
            if (oldValue instanceof Instant) {
                return different((Instant) oldValue, (Instant) currentValue);
            } else if (oldValue instanceof String) {
                return different((String) oldValue, (String) currentValue);
            } else if (oldValue instanceof Integer) {
                return different((Integer) oldValue, (Integer) currentValue);
            } else if (oldValue instanceof LocalDate) {
                return different((LocalDate) oldValue, (LocalDate) currentValue);
            } else if (oldValue instanceof Enum) {
                return different((Enum) oldValue, (Enum) currentValue);
            } else {
                return false;
            }
        } else if (isOk(oldValue) || isOk(currentValue)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isOk(Object value) {
        return !(value == null);
    }

    public static boolean isOk(byte[] value) {
        return !(value == null);
    }

    public static String dbIdToLdap(Integer id) {
        return Integer.toOctalString(880 + id);
    }

    public static Integer LdapIdToDb(String id) {
        return Integer.parseInt(id, 8) - 880;
    }

    public static String exception(Throwable t) {
        if (t != null) {
            t.printStackTrace();
            return t.getMessage();
        } else {
            return null;
        }
    }

    public static String javaObjectToJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static boolean valueExists(Object[] objecta, int index) {
        if (objecta == null) {
            return false;
        }
        return objecta.length >= (index + 1) && objecta[index] != null;
    }

    public static final String listToString(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        ids.stream().filter(id -> Utils.isOk(id)).forEach(id -> {
            sb.append(id);
            sb.append(',');
        });
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String stringListToStringCode(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        values.stream().filter(id -> Utils.isOk(id)).forEach(id -> {
            sb.append("'" + id + "',");
        });
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }


    public static final String listToString(Integer... ids) {
        if (ids == null || ids.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Integer id : ids) {
            if (!Utils.isOk(id)) {
                continue;
            }
            sb.append(id);
            sb.append(',');
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String datetoReportString(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return "";
        }
    }

    public static Instant getInstantFromDate(Date date) {
        if (date == null)
            return null;
        return date.toInstant();
    }


    public static boolean isValidEmail(String value) {
        final String regex = "^([a-zA-Z0-9]+[._-]?)+@([a-zA-Z0-9]+[._-])+([a-zA-Z0-9]{2,6})$";
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public static String[] getCountryIso2AndYearMonth(String caseReferenceNumber) {
        if (!isOk(caseReferenceNumber) || caseReferenceNumber.length() < 6) {
            return new String[]{"", ""};
        }

        String iso2 = caseReferenceNumber.substring(0, 2);
        String yearMonth = caseReferenceNumber.substring(2, 6);

        return new String[]{iso2, yearMonth};
    }

    public static String getYearMonth() {
        DateFormat df = new SimpleDateFormat("yyMM");
        return df.format(new Date());
    }

    public static <T> void getJavaObject(String jsonValue, T entity) {
        if (!isOk(jsonValue)) {
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new ParameterNamesModule())
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule());
            Object value = mapper.readValue(jsonValue, entity.getClass());
            BeanUtils.copyProperties(value, entity);
        } catch (Throwable t) {
            t.printStackTrace();
            return;
        }
    }

    public static final Timestamp getCurrentTimeStamp() {
        return new Timestamp(new Date().getTime());
    }

    public static Date addDays(Date date, int day) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }


    public static Date getDateParam(Date date, boolean maxDate) {
        if (!isOk(date)) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (maxDate) {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 1);
        }

        return calendar.getTime();
    }

    public static <T> T toUpperCase(T t) {
        if (t == null) {
            return t;
        }
        try {
            for (Field field : t.getClass().getDeclaredFields()) {
                if (field.getType().equals(String.class)) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    if (field.get(t) != null && ((String) field.get(t)).trim() != "") {
                        String val = ((String) field.get(t)).trim().toUpperCase();
                        field.set(t, val);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    public static String lpad(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

    public static Date getCodeDate() {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.parse("01/01/2007");
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    public static Date getCodeDate(Integer date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, date);
            return calendar.getTime();
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }


    public static Date getCurrentDate(String format) {
        try {
            if (!isOk(format)) {
                format = "dd/MM/yyyy";
            }
            DateFormat df = new SimpleDateFormat(format);
            return df.parse(df.format(new Date()));
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    public static int getDifferenceDays(Date toDate, Date fromDate) {
        Long diff = toDate.getTime() - fromDate.getTime();
        Long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays.intValue() == 0 ? 1 : diffDays.intValue();
    }

    public static boolean nulls(Object value) {
        return value == null;
    }

    public static <T> boolean nulls(List<T> value) {
        return (value == null || value.isEmpty());
    }

    public static <T> boolean notnull(List<T> value) {
        return (value != null && !value.isEmpty());
    }

    public static <T> boolean notnull(LinkedHashSet<T> value) {
        return (value != null && !value.isEmpty());
    }

    public static <T> int sizeof(List<T> value) {
        if (notnull(value))
            return value.size();
        else
            return 0;
    }

    public static boolean notnull(Object value) {
        return !(value == null);
    }

    public static boolean empty(String value) {
        return (value == null || value.isEmpty());
    }

    public static boolean notempty(String value) {
        return !(value == null || value.isEmpty());
    }

    public static boolean gt(Integer left, Integer right) {
        if (nulls(left) && nulls(right))
            return false;
        if (nulls(left))
            return false;
        if (nulls(right))
            return true;
        return left > right;
    }

    public static boolean lt(Integer left, Integer right) {
        if (nulls(left) && nulls(right))
            return false;
        if (nulls(left))
            return false;
        if (nulls(right))
            return true;
        return left < right;
    }

    public static boolean eq(String left, String right) {
        if (nulls(left) && nulls(right))
            return false;
        if (nulls(left) || nulls(right))
            return false;
        return left.equalsIgnoreCase(right);
    }

    public static boolean eq(Integer left, Integer right) {
        if (nulls(left) && nulls(right))
            return false;
        if (nulls(left) || nulls(right))
            return false;
        return left.intValue() == right.intValue();
    }

    public static boolean neq(String left, String right) {
        if (nulls(left) && nulls(right))
            return false;
        if (nulls(left) || nulls(right))
            return true;
        return !left.equalsIgnoreCase(right);
    }

    public static boolean isEqual(Date firstDate, Date secondDate) {
        if (!isOk(firstDate) || !isOk(secondDate)) {
            return false;
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(firstDate).equalsIgnoreCase(df.format(secondDate));
    }

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public static String getClientIp() {

        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                return ipList.split(",")[0];
            }
        }

        return request.getRemoteAddr();
    }

    public static byte[] getByte(Object value) {
        if (!isOk(value)) {
            return null;
        }

        try {
            Blob blob = (Blob) value;
            return blob.getBytes(1, new Long(blob.length()).intValue());
        } catch (Throwable t) {
            return null;
        }
    }

    public static Date getDate(String date, String format) {
        if (!isOk(date) || !isOk(format)) {
            return null;
        }
        try {
            DateFormat df = new SimpleDateFormat(format);
            return df.parse(date);
        } catch (Throwable t) {
            return null;
        }
    }

    public static List<String> getUniqueList(List<String> values) {
        if (values == null || values.isEmpty()) {
            return values;
        }
        Map<String, String> map = new HashMap<>();
        for (String value : values) {
            if (!isOk(value)) {
                continue;
            }
            map.put(value, value);
        }
        return new ArrayList<>(map.keySet());
    }


    public static String OMIT_CHAR(String s) {
        if (s == null) return s;
//        return s.replaceAll("[^a-zA-Z]+", "");
        return s.replaceAll("[%\\- \\/\\#_,.!@$^&*()`~]+", "");
    }

}
