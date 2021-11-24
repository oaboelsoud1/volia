package com.volia.notificationservice.core.common;

import com.volia.notificationservice.common.exception.LogicalException;
import com.volia.notificationservice.common.exception.ServerError;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class JpaPagingUtil {
    static int PAGE_SIZE = 10;

    /**
     * @param pageNum
     * @param sortDirection true => asc, false => desc
     * @param field
     * @return Pageable
     */
    public static Pageable getPage(Integer pageNum, Direction sortDirection, String field) throws LogicalException {
        return getPage(pageNum, PAGE_SIZE, sortDirection, field);
    }

    /**
     * @param pageNum
     * @return Pageable
     */
    public static Pageable getPage(Integer pageNum) throws LogicalException {
        return getPage(pageNum, null, null);
    }


    public static Pageable getPage(Integer pageNum, Sort sort) throws LogicalException {
        return getPage(pageNum, PAGE_SIZE, null, null);
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param sortDirection
     * @param fields
     * @return
     */
    public static Pageable getPage(Integer pageNum, Integer pageSize, Direction sortDirection, String... fields) throws LogicalException {
        if (validatePageNumberInput(pageNum)) return null;
        int finalPageSize = pageSize == null ? PAGE_SIZE : pageSize;
        Pageable page;
        // convert it to ZERO based paging.
        pageNum--;
        if (sortDirection == null || fields == null) {
            page = PageRequest.of(pageNum, finalPageSize);
        } else {
            page = PageRequest.of(pageNum, finalPageSize, sortDirection, fields);
        }
        return page;
    }

    public static boolean validatePageNumberInput(Integer pageNum) {
        if (pageNum == null) {
            return true;
        }
        if (pageNum <= 0) {
            throw new LogicalException(ServerError.PAGE_INVALID);
        }
        return false;
    }

    public static Pageable getPage(Integer pageNum, Integer pageSize) throws LogicalException {
        return getPage(pageNum, pageSize, null, null);
    }
}
