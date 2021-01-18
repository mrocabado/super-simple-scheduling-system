package com.mrocabado.s4.db;

import com.mrocabado.s4.domain.entity.Course;
import com.mrocabado.s4.domain.entity.Student;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

public class SimpleBeanPropertyFilter {

    public static <T> boolean applyFilter(T bean, Map<String, String> filter) {
        boolean isFiltered = true;

        if (Objects.nonNull(filter) && !filter.isEmpty()) {
            try {
                Map<String, Object> properties = PropertyUtils.describe(bean);

                isFiltered = false;
                for (Map.Entry<String, String> filterEntry : filter.entrySet()) {
                    isFiltered = properties.entrySet().stream()
                            .anyMatch( property ->
                                    property.getKey().equals(filterEntry.getKey())
                                            && property.getValue().equals(filterEntry.getValue())
                            );
                    if (isFiltered) {
                        break;
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
                //do nothing
            }
        }

        return isFiltered;
    }

}
