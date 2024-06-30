package com.learn.blog.utils;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class CommonFn {
    public static Sort commonSort(String sortBy, String sortDir){
        List<String> sortByList = List.of(sortBy.split(","));
        List<String> sortDirList = List.of(sortDir.split(","));

        List<Sort.Order> orders = new ArrayList<>();

        for(int i = 0, j = 0; i < sortByList.size() && j < sortDirList.size(); i++, j++){
            String sortByVal = sortByList.get(i);
            String sortDirVal = sortDirList.get(j);

            Sort.Order sortOrder = new Sort.Order(sortDirVal.equalsIgnoreCase(Sort.Direction.ASC.toString()) ?
                    Sort.Direction.ASC : Sort.Direction.DESC,
                    sortByVal);
            orders.add(sortOrder);
        }
        return Sort.by(orders);
    }
}
