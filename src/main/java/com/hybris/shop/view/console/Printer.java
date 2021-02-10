package com.hybris.shop.view.console;

import com.hybris.shop.annotations.ColumnNameAlias;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class Printer<T> {

    private static final String ROW_END = "|\n";
    private int[] columnWidths;
    private List<String> data;

    public void printLine(String line) {
        System.out.print(line);
    }

    public void printTable(List<T> entity) {
        List<String> columnNames = getColumnNames(entity);
        List<String> data = getColumnsData(entity);
        this.data = new ArrayList<>(data);

        setColumnsWidth(columnNames);

        String[] rowSeparator = getRowSeparator(columnNames);

        printRowSeparator(rowSeparator);

        printHeader(columnNames);

        printRowSeparator(rowSeparator);

        printBody(data);

        printRowSeparator(rowSeparator);
    }

    private List<String> getColumnNames(List<T> entity) {
        return Arrays.stream(entity.get(0).getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ColumnNameAlias.class))
                .map(field -> field.getAnnotation(ColumnNameAlias.class).alias())
                .collect(Collectors.toList());
    }

    private List<String> getColumnsData(List<T> entity) {
        List<String> data = new ArrayList<>();

        for (T e : entity) {
            Class<?> aClass = e.getClass();

            List<Field> declaredFields = Arrays.stream(aClass.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(ColumnNameAlias.class))
                    .collect(Collectors.toList());

            for (Field declaredField : declaredFields) {
                try {
                    Field field = aClass.getDeclaredField(declaredField.getName());

                    field.setAccessible(true);

                    if (!Objects.isNull(field.get(e))) {
                        data.add(field.get(e).toString());
                    } else {
                        data.add("-");
                    }
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return data;
    }

    private void setColumnsWidth(List<String> columnNames) {
        columnWidths = new int[columnNames.size()];

        for (int i = 0; i < columnWidths.length; i++) {
            columnWidths[i] = columnNames.get(i).length();
        }

        for (int i = 0; i < columnWidths.length; i++) {
            for (int j = i; j < data.size(); j += columnWidths.length) {
                columnWidths[i] = Math.max(data.get(j).length(), columnWidths[i]);
            }
        }
    }

    private String[] getRowSeparator(List<String> columnNames) {
        String[] rowSeparator = new String[columnNames.size()];

        for (int i = 0; i < columnWidths.length; i++) {
            rowSeparator[i] = String.format("|%-" + columnWidths[i] + "s", getSeparator(columnWidths[i]));
        }

        return rowSeparator;
    }

    private String getSeparator(int columnWidth) {
        return "-".repeat(Math.max(0, columnWidth));
    }

    private void printRowSeparator(String[] column) {
        for (String s : column) {
            printLine(s);
        }

        printLine(ROW_END);
    }

    private void printHeader(List<String> columnNames) {
        for (int i = 0; i < columnNames.size(); i++) {
            printLine(String.format("|%-" + columnWidths[i] + "s", columnNames.get(i)));
        }

        printLine(ROW_END);
    }

    private void printBody(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            printLine(String.format("|%-" + columnWidths[i] + "s", data.get(i)));
        }

        printLine(ROW_END);
    }
}