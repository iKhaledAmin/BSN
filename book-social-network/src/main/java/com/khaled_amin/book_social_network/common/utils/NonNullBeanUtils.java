package com.khaled_amin.book_social_network.common.utils;


import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class NonNullBeanUtils extends BeanUtilsBean {

    public void copyProperties(Object source, Object target, String... ignoreProperties) {

        Set<String> ignoreSet = new HashSet<>(Arrays.asList(ignoreProperties));

        try {

            for (PropertyDescriptor descriptor : getPropertyUtils().getPropertyDescriptors(source)) {

                String name = descriptor.getName();

                if ("class".equals(name)) {
                    continue;
                }

                if (ignoreSet.contains(name)) {
                    continue;
                }

                if (getPropertyUtils().isReadable(source, name)
                        && getPropertyUtils().isWriteable(target, name)) {

                    Object value = getPropertyUtils().getSimpleProperty(source, name);

                    if (value != null) {
                        super.copyProperty(target, name, value);
                    }
                }
            }

        } catch (Exception ex) {
            throw new RuntimeException("Error copying properties", ex);
        }
    }
}
