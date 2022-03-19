package net.springboot.common.util;

import net.springboot.common.annotation.Obj;

import javax.persistence.Query;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

public class SearchUtil {
    public String generateSearchQuery(Object criteria){
        String whereQuery = "";
        boolean isFound = false;
        String fieldName = "";
        String colName = "";
        for(Field criteriaField : criteria.getClass().getDeclaredFields()){

            isFound = false;

            fieldName = criteriaField.getName();

            if(fieldName.equalsIgnoreCase("startIndex") || fieldName.equalsIgnoreCase("limit") || fieldName.equalsIgnoreCase("startIndex") || fieldName.equalsIgnoreCase("limit") || fieldName.equalsIgnoreCase("order") || fieldName.equalsIgnoreCase("sortColumnName") || fieldName.equalsIgnoreCase("SORT_COLUMNS")){
                continue;
            }

            try {
                Character first = fieldName.charAt(0);
                String getter = "";
                if(!criteriaField.getType().toString().equals(Boolean.class.toString())){
                    getter = "get" + first.toString().toUpperCase() + fieldName.substring(1, fieldName.length());
                }
                Method m = criteria.getClass().getMethod(getter);
                m.setAccessible(true);
                if(criteriaField.getType().toString().equals(String.class.toString())){
                    String val = (String) m.invoke(criteria);
                    if(!Utils.isOk(val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Integer.class.toString())){
                    Integer val = (Integer) m.invoke(criteria);
                    if(!Utils.isNull(val)){
                        isFound = true;
                    }
                }

                if(criteriaField.getType().toString().equals(Long.class.toString())){
                    Long val = (Long) m.invoke(criteria);
                    if(!Utils.isOk(val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(BigInteger.class.toString())){
                    BigInteger val = (BigInteger) m.invoke(criteria);
                    if(!Utils.isOk(val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Short.class.toString())){
                    Short val = (Short) m.invoke(criteria);
                    if(!Utils.isOk(val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Date.class.toString())){
                    Date val = (Date) m.invoke(criteria);
                    if(val != null){
                        isFound = true;
                    }
                }
                if(isFound){
                    boolean likeSearch = false;
                    boolean fromDate = false;
                    boolean toDate = false;
                    boolean upper = false;
                    boolean gte = false;
                    boolean lte = false;
                    String objAnnotation = "";
                    if(criteriaField.getAnnotations().length > 0){
                        for(Annotation an : criteriaField.getAnnotations()){
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.Like")){
                                likeSearch = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.FromDate")){
                                fromDate = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.ToDate")){
                                toDate = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.ToUpper")){
                                upper = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.Gte")){
                                gte = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.Lte")){
                                lte = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.Obj")){
                                Obj obj = criteriaField.getAnnotation(Obj.class);
                                if(obj != null){
                                    objAnnotation = obj.value();
                                }
                            }
                        }
                    }
                    if(likeSearch){
                        if(upper){
                            whereQuery += " and upper(o." + fieldName + ") like :" + fieldName;
                        }
                        else{
                            whereQuery += " and o." + fieldName + " like :" + fieldName;
                        }
                    }
                    else if(fromDate){
                        colName = fieldName.replace("From", "");
                        whereQuery += " and o." + colName + " >= :" + fieldName;
                    }
                    else if(toDate){
                        colName = fieldName.replace("To", "");
                        whereQuery += " and o." + colName + " <= :" + fieldName;
                    }
                    else if(gte){
                        fieldName = fieldName.replace("Gte", "");
                        whereQuery += " and o." + fieldName + " >= :" + fieldName;
                    }
                    else if(lte){
                        fieldName = fieldName.replace("Lte", "");
                        whereQuery += " and o." + fieldName + " <= :" + fieldName;
                    }
                    else{
                        if(!Utils.isOk(objAnnotation)){
                            whereQuery += " and o." + fieldName + "." + objAnnotation + " = :" + fieldName;
                        }
                        else{
                            if(upper){
                                whereQuery += " and upper(o." + fieldName + ") = :" + fieldName;
                            }
                            else{
                                whereQuery += " and o." + fieldName + " = :" + fieldName;
                            }
                        }
                    }
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return whereQuery;
    }

    public Query setQueryParameter(Query q, Object criteria){

        boolean isFound = false;
        String fieldName = "";
        for(Field criteriaField : criteria.getClass().getDeclaredFields()){

            isFound = false;

            fieldName = criteriaField.getName();

            if(fieldName.equalsIgnoreCase("startIndex") || fieldName.equalsIgnoreCase("limit")){
                continue;
            }
            Object val = null;
            try {
                Character first = fieldName.charAt(0);
                String getter = "";
                if(!criteriaField.getType().toString().equals(Boolean.class.toString())){
                    getter = "get" + first.toString().toUpperCase() + fieldName.substring(1, fieldName.length());
                }
                Method m = criteria.getClass().getMethod(getter);
                m.setAccessible(true);

                if(criteriaField.getType().toString().equals(String.class.toString())){
                    val = m.invoke(criteria);
                    if(!Utils.isOk((String)val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Integer.class.toString())){
                    val = m.invoke(criteria);
                    if(!Utils.isNull((Integer)val)){
                        isFound = true;
                    }
                }

                if(criteriaField.getType().toString().equals(Long.class.toString())){
                    val = m.invoke(criteria);
                    if(!Utils.isOk((Long)val)){
                        isFound = true;
                    }
                }

                if(criteriaField.getType().toString().equals(BigInteger.class.toString())){
                    val = m.invoke(criteria);
                    if(!Utils.isOk((BigInteger)val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Short.class.toString())){
                    val = m.invoke(criteria);
                    if(!Utils.isOk((Short) val)){
                        isFound = true;
                    }
                }
                if(criteriaField.getType().toString().equals(Date.class.toString())){
                    val = m.invoke(criteria);
                    if((Date) val != null){
                        isFound = true;
                    }
                }
                if(isFound){
                    boolean likeSearch = false;
                    boolean fromDate = false;
                    boolean toDate = false;
                    boolean upper = false;
                    if(criteriaField.getAnnotations().length > 0){
                        for(Annotation an : criteriaField.getAnnotations()){
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.Like")){
                                likeSearch = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.FromDate")){
                                fromDate = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.ToDate")){
                                toDate = true;
                            }
                            if(an.annotationType().getCanonicalName().equalsIgnoreCase("net.springboot.common.annotation.ToUpper")){
                                upper = true;
                            }
                        }
                    }
                    if(likeSearch){
                        q.setParameter(fieldName, "%" + (upper ? ((String) val).toUpperCase() : (String) val) + "%");
                    }
                    else if(fromDate){
                        //fieldName = fieldName.replace("From", "");
                        q.setParameter(fieldName, (Date)val);
                    }
                    else if(toDate){
                        //fieldName = fieldName.replace("To", "");
                        q.setParameter(fieldName, (Date)val);
                    }
                    else{
                        if (upper && val != null) {
                            q.setParameter(fieldName, ((String) val).toUpperCase());
                        } else {
                            q.setParameter(fieldName, val);
                        }

                    }
                }
            } catch (Exception ex) {
                if(ex.getMessage() != null && ex.getMessage().contains("did not match expected type")){
                    if(val instanceof Integer){
                        Short s = ((Integer)val).shortValue();
                        q.setParameter(fieldName, s);
                    }
                    if(val instanceof Short){
                        Integer s = ((Short)val).intValue();
                        q.setParameter(fieldName, s);
                    }
                }
                //ex.printStackTrace();
            }
        }
        return q;
    }

    public static void setQueryParameter(Query q, Map<String, Object> param){
        if(param != null){
            for(String key : param.keySet()){
                q.setParameter(key, param.get(key));
            }
        }
    }
}
