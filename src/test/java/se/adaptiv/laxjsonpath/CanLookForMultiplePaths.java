package se.adaptiv.laxjsonpath;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CanLookForMultiplePaths {

    @Test
    public void whenFirstPathFailsItWillLookAtSecondPathForIntValues() {
        DocumentContext context = JsonPath.parse("{\"sid\": 4321}");
        int result = LaxJsonPath.findIntForPaths(context, new String [] {"$.id", "$.sid"});
        assertThat(result, equalTo(4321));
    }

    @Test
    public void whenFirstPathFailsItWillLookAtSecondPathForStringValues() {
        DocumentContext context = JsonPath.parse("{\"sid\": 4321}");
        String result = LaxJsonPath.findStringForPaths(context, new String [] {"$.id", "$.sid"});
        assertThat(result, equalTo("4321"));
    }


    @Test
    public void whenAllPathsFailItWillReThrowException() {
        DocumentContext context = JsonPath.parse("{\"nid\": 4321}");
        assertThrows(PathNotFoundException.class, () -> {
            LaxJsonPath.findIntForPaths(context, new String [] {"$.id", "$.sid"});
        });
    }

    @Test
    public void prioritizesByOrderFirstIsHighest() {
        DocumentContext context = JsonPath.parse("{\"sid\": 4324, \"nid\": 4323, \"mid\": 4322, \"id\": 4321}");
        int result = LaxJsonPath.findIntForPaths(context, new String [] {"$.id", "$.sid", "$.mid"});
        assertThat(result, equalTo(4321));
    }

    @Test
    public void prioritizesByOrderLastIsLowest() {
        DocumentContext context = JsonPath.parse("{\"nid\": 4324, \"rid\": 4323, \"mid\": 4322, \"pid\": 4321}");
        int result = LaxJsonPath.findIntForPaths(context, new String [] {"$.id", "$.sid", "$.mid"});
        assertThat(result, equalTo(4322));
    }

    @Test
    public void prioritizesByOrderSecondIsHigherThanThird() {
        DocumentContext context = JsonPath.parse("{\"nid\": 4324, \"sid\": 4323, \"mid\": 4322, \"pid\": 4321}");
        int result = LaxJsonPath.findIntForPaths(context, new String [] {"$.id", "$.sid", "$.mid"});
        assertThat(result, equalTo(4323));
    }

}
