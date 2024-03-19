package tech.remiges.workshop.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CommonUtils {

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public static String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static int calculateMean(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("List of numbers is empty or null");
        }

        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }

        return sum / numbers.size();
    }

    // Utility method to get null property names
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    // Utility method to get null property names
    public static List<String> getPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        List<String> Names = new ArrayList<>();
        for (java.beans.PropertyDescriptor pd : pds) {

            Names.add(pd.getName());
        }

        return Names;
    }

    // Utility method to get null property names
    public static List<String> getPropertyData(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        List<String> Names = new ArrayList<>();
        for (java.beans.PropertyDescriptor pd : pds) {

            Object srcValue = src.getPropertyValue(pd.getName());

            if (srcValue != null)
                Names.add(srcValue.toString());
            else
                Names.add("");
        }

        return Names;
    }

}
