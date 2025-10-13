/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.specification;

import com.google.common.collect.Lists;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.QueryOpEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeSerializer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BaseSpecification {

    /*
     *   “eq” (equals)
     *   “ne” (not equals)
     *   “lt” (less than)
     *   “lte” (less that or equals)
     *   “gt” (greater than)
     *   “gte” (greater than or equals)
     *   “in” (in un insieme) – supporta una lista di valori di confronto
     *   “nin” (not in) – supporta una lista di valori di confrontoli
     *   “c” (contains)
     *   “ci” (contains, ignore case)
     *   “s” (starts with)
     *   “si” (starts with, ignore case)
     *   “e” (ends with)
     *   “ei” (ends with, ignore case)
     */

    protected static Predicate getPredicateString(CriteriaBuilder builder, Path pathObj, Map filterVOValue) {

        List<Predicate> predicates = new ArrayList<>();
        if (null != filterVOValue) {
            for (Object s : filterVOValue.keySet()) {
                Predicate p = null;
                QueryOpEnum op = QueryOpEnum.findByValue((String) s);

                Object obj = filterVOValue.get(s);
	            if(obj instanceof String) {
	                String token = (String)obj;
	
	            	Path<String> path = (Path<String>)pathObj;
	            	
	                if (StringUtils.isNotBlank(token)) {
	                    if (op.equals(QueryOpEnum.EQUALS))
	                        p = builder.like(builder.lower(path), builder.lower(builder.literal(token.trim().toLowerCase())));
                        else if (op.equals(QueryOpEnum.NOT_EQUALS))
                            p = builder.notEqual(path, token);
	                    else if (op.equals(QueryOpEnum.LIKE))
	                        p = builder.like(path, "%" + token.trim() + "%");
	                    else if (op.equals(QueryOpEnum.LIKE_IGNORE_CASE))
	                        p = builder.like(builder.lower(path), builder.lower(builder.literal("%" + token.trim().toLowerCase() + "%")));
	                    else if (op.equals(QueryOpEnum.START_WITH))
	                        p = builder.like(path, token.trim() + "%");
	                    else if (op.equals(QueryOpEnum.START_WITH_IGNORE_CASE))
	                        p = builder.like(builder.lower(path), builder.lower(builder.literal(token.trim().toLowerCase() + "%")));
	                    else if (op.equals(QueryOpEnum.END_WITH))
	                        p = builder.like(path, "%" + token.trim());
	                    else if (op.equals(QueryOpEnum.END_WITH_IGNORE_CASE))
	                        p = builder.like(builder.lower(path), builder.lower(builder.literal("%" + token.trim().toLowerCase())));
                        else if (op.equals(QueryOpEnum.LESS_THAN))
                            p = builder.lessThan(path, token);
                        else if (op.equals(QueryOpEnum.LESS_THAN_OR_EQUALS))
                            p = builder.lessThanOrEqualTo(path, token);
                        else if (op.equals(QueryOpEnum.GREATER_THAN))
                            p = builder.greaterThan(path, token);
                        else if (op.equals(QueryOpEnum.GREATER_THAN_OR_EQUALS))
                            p = builder.greaterThanOrEqualTo(path, token);
	                    else if (op.equals(QueryOpEnum.IN) || op.equals(QueryOpEnum.NOT_IN)) {
	                    	Path<List<String>> __path = (Path<List<String>>)pathObj;
	                        p = getPredicateListString(builder, __path, filterVOValue);                        
	                    }
	
	                    predicates.add(p);
	                }
            	}
                else if(obj instanceof List) {
                	List<String> tokens = (List<String>)obj;
                	
                	if(tokens != null && tokens.size() > 0) {
	                	Path<List<String>> path = (Path<List<String>>)pathObj;
	                    if (op.equals(QueryOpEnum.IN))
	                        p = path.in(tokens);
	                    else if (op.equals(QueryOpEnum.NOT_IN))
	                    	p = path.in(tokens).not();
	
	                    predicates.add(p);
                	}
                	
                }
            }
        }

        if (predicates.isEmpty())
            return builder.and(builder.isTrue(builder.literal(true)));

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    protected static Predicate getPredicateLong(CriteriaBuilder builder, Object pathObj, Map filterVOValue) {
        List<Predicate> predicates = new ArrayList<>();
        if (null != filterVOValue) {
            for (Object s : filterVOValue.keySet()) {
                Predicate p = null;
                QueryOpEnum op = QueryOpEnum.findByValue((String) s);

                Object obj = filterVOValue.get(s);
                if(obj instanceof Number) {
                	Long token = ((Number)obj).longValue();
                    if (null != token) {
                    	Path<Long> path = (Path<Long>)pathObj;
                    	
                        if (op.equals(QueryOpEnum.EQUALS))
                            p = builder.equal(path, token);
                        else if (op.equals(QueryOpEnum.NOT_EQUALS))
                            p = builder.notEqual(path, token);
                        else if (op.equals(QueryOpEnum.LESS_THAN))
                            p = builder.lessThan(path, token);
                        else if (op.equals(QueryOpEnum.LESS_THAN_OR_EQUALS))
                            p = builder.lessThanOrEqualTo(path, token);
                        else if (op.equals(QueryOpEnum.GREATER_THAN))
                            p = builder.greaterThan(path, token);
                        else if (op.equals(QueryOpEnum.GREATER_THAN_OR_EQUALS))
                            p = builder.greaterThanOrEqualTo(path, token);

                        predicates.add(p);
                    }
                }
                else if(obj instanceof List) {
                	List<Long> tokens = (List<Long>)obj;
                	
                	if(tokens != null && tokens.size() > 0) {
	                	Path<List<Long>> path = (Path<List<Long>>)pathObj;
	                    if (op.equals(QueryOpEnum.IN))
	                        p = path.in(tokens);
	                    else if (op.equals(QueryOpEnum.NOT_IN))
	                    	p = path.in(tokens).not();
	
	                    predicates.add(p);
                	}
                }
            }
        }

        if (predicates.isEmpty())
            return builder.and(builder.isTrue(builder.literal(true)));

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    protected static Predicate getPredicateDate(CriteriaBuilder builder, Path<Date> path, Map filterVOValue) {

        List<Predicate> predicates = new ArrayList<>();

        if (null != filterVOValue) {
            for (Object s : filterVOValue.keySet()) {
                Predicate p = null;
                QueryOpEnum op = QueryOpEnum.findByValue((String) s);
                String dateAsString = (String) filterVOValue.get(s);
                LocalDate ld = LocalDate.parse(dateAsString, CustomDateTimeSerializer.DATE_FORMATTER);
                Date token = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

                if (null != token) {
                    if (op.equals(QueryOpEnum.EQUALS)) {
                        p = builder.equal(path, token);
                    } else if (op.equals(QueryOpEnum.LESS_THAN)) {
                        p = builder.lessThan(path, token);
                    } else if (op.equals(QueryOpEnum.LESS_THAN_OR_EQUALS)) {
                        p = builder.lessThanOrEqualTo(path, token);
                    } else if (op.equals(QueryOpEnum.GREATER_THAN)) {
                        p = builder.lessThan(path, token);
                    } else if (op.equals(QueryOpEnum.GREATER_THAN_OR_EQUALS)) {
                        p = builder.lessThanOrEqualTo(path, token);
                    }
                    predicates.add(p);
                }
            }
        }

        if (predicates.isEmpty())
            return builder.and(builder.isTrue(builder.literal(true)));

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    protected static Predicate getPredicateBoolean(CriteriaBuilder builder, Path<Boolean> path, Map filterVOValue) {
        List<Predicate> predicates = new ArrayList<>();

        if (null != filterVOValue) {
            for (Object s : filterVOValue.keySet()) {
                Predicate p = null;
                QueryOpEnum op = QueryOpEnum.findByValue((String) s);
                Boolean token = (Boolean) filterVOValue.get(s);

                if (null != token) {
                    if (op.equals(QueryOpEnum.EQUALS)) {
                        return builder.equal(path, token);
                    }
                    predicates.add(p);
                }
            }
        }

        if (predicates.isEmpty())
            return builder.and(builder.isTrue(builder.literal(true)));

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    protected static Predicate getPredicateListString(CriteriaBuilder builder, Path<List<String>> path, Map filterVOValue) {
        List<Predicate> predicates = new ArrayList<>();

        if (null != filterVOValue) {
            for (Object s : filterVOValue.keySet()) {
                Predicate p = null;
                QueryOpEnum op = QueryOpEnum.findByValue((String) s);
                List<String> token = null;
                Object obj = filterVOValue.get(s);
                if (null != obj) {
                    if (obj instanceof String) {
                        String[] strArr = ((String) obj).split(",");
                        token = Lists.newArrayList(strArr);
                    } else {
                        token = (List<String>) obj;
                    }

                    if (op.equals(QueryOpEnum.IN)) {
                        return path.in(token);
                    } else if (op.equals(QueryOpEnum.NOT_IN)) {
                        return path.in(token).not();
                    }
                    predicates.add(p);
                }
            }
        }

        if (predicates.isEmpty())
            return builder.and(builder.isTrue(builder.literal(true)));

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    protected static Predicate getPredicateListString(CriteriaBuilder builder, Path<List<String>> path, List filterVOValue) {
        //List<Predicate> predicates = new ArrayList<>();

        if (null != filterVOValue) {
        	return path.in(filterVOValue);
        }

        return builder.and(builder.isTrue(builder.literal(true)));
    }

/*
    protected static Predicate getPredicateListString(CriteriaBuilder builder, Path<List<String>> path, List filterVOValue) {
        //List<Predicate> predicates = new ArrayList<>();

        if (null != filterVOValue) {
            for (Object s : filterVOValue) {
                //Predicate p = null;
                //QueryOpEnum op = QueryOpEnum.IN;
                List<String> token = null;
                //Object obj = s;
                if (null != s) {
                    if (s instanceof String) {
                        String[] strArr = ((String) s).split(",");
                        token = Lists.newArrayList(strArr);
                    } else {
                        token = (List<String>) s;
                    }
                    return path.in(token);
                }
            }
        }

        //if (predicates.isEmpty())
            return builder.and(builder.isTrue(builder.literal(true)));

        //return builder.and(predicates.toArray(new Predicate[0]));
    }
    protected static Predicate getPredicateListLong(CriteriaBuilder builder, Path<List<Long>> path, Map filterVOValue) {
        List<Predicate> predicates = new ArrayList<>();

        if (null != filterVOValue) {
            for (Object s : filterVOValue.keySet()) {
                Predicate p = null;
                QueryOpEnum op = QueryOpEnum.findByValue((String) s);
                List<Long> token = (List<Long>) filterVOValue.get(s);

                if (null != token) {
                    if (op.equals(QueryOpEnum.IN))
                        p = path.in(token);
                    else if (op.equals(QueryOpEnum.NOT_IN))
                    	p = path.in(token).not();

                    predicates.add(p);
                }
            }
        }

        if (predicates.isEmpty())
            return builder.and(builder.isTrue(builder.literal(true)));

        return builder.and(predicates.toArray(new Predicate[0]));
    }
*/    

}