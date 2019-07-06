package se.adaptiv.laxjsonpath;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.spi.mapper.MappingException;

import java.util.*;

public class LaxJsonPath {

    public static <T> T findOrDefault(DocumentContext context, String s, T defaultValue, Class<T> type) {
        try {
            return context.read(s, type);
        } catch (PathNotFoundException pnfe) {
            return defaultValue;
        } catch (MappingException me) {
            return defaultValue;
        }
    }


    public static String findStringForPaths(DocumentContext context, List<String> paths) {

        String res = "";

        Deque<String> pathStack = stackifyPaths(paths);
        boolean notFound = true;
        do {
            try {
                res = context.read(pathStack.pop(), String.class);
                notFound = false;
            } catch (PathNotFoundException e) {

            }
        } while (notFound && !pathStack.isEmpty());
        if (notFound && pathStack.isEmpty()) throw new PathNotFoundException("Nothing found for paths: " + paths);
        return res;
    }

    public static int findIntForPaths(DocumentContext context, List<String> paths) {
        int res = 0;

        Deque<String> pathStack = stackifyPaths(paths);
        boolean notFound = true;
        do {
            try {
                res = context.read(pathStack.pop(), Integer.class);
                notFound = false;
            } catch (PathNotFoundException e) {

            }
        } while (notFound && !pathStack.isEmpty());
        if (notFound && pathStack.isEmpty()) throw new PathNotFoundException("Nothing found for paths: " + paths);
        return res;

    }

    public static String findStringForPaths(DocumentContext context, String[] paths) {
        return findStringForPaths(context, Arrays.asList(paths));
    }

    public static int findIntForPaths(DocumentContext context, String[] paths) {
        return findIntForPaths(context, Arrays.asList(paths));
    }

    public static int findIntForPaths(DocumentContext context, String[] paths, int defaultValue) {
        int res;
        try {
            res = findIntForPaths(context, paths);
        } catch (PathNotFoundException pnfe) {
            return defaultValue;
        }
        return res;
    }

    private static Deque<String> stackifyPaths(List<String> pathList) {
        Deque<String> pathStack = new ArrayDeque<String>();
        pathStack.addAll(pathList);
        return pathStack;
    }
}
