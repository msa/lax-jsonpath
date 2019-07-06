package se.adaptiv.laxjsonpath;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CanRetrieveWithDefaultValue {

    @Test
    public void canWorkWithDefaultValueOfSameType() {
        DocumentContext context = JsonPath.parse("{\"nothing_to_see_here\": 4321}");
        int result = LaxJsonPath.findOrDefault(context, "$.id", 1234, Integer.class);
        assertThat(result, equalTo(1234));
    }

    @Test
    public void defaultValueNotUsedWhenKeyIsFound() {
        DocumentContext context = JsonPath.parse("{\"id\": 4321}");
        int result = LaxJsonPath.findOrDefault(context, "$.id", 1234, Integer.class);
        assertThat(result, equalTo(4321));
    }

    @Test
    public void aFoundStringValueForIntegerDefaultTypeWillBeConverted() {
        DocumentContext context = JsonPath.parse("{\"id\": \"4321\"}");
        int result = LaxJsonPath.findOrDefault(context, "$.id", 1234, Integer.class);
        assertThat(result, equalTo(4321));

    }

    @Test
    public void aFoundTextValueForIntegerDefaultTypeWillBeIgnored() {
        DocumentContext context = JsonPath.parse("{\"id\": \"Stevens\"}");
        int result = LaxJsonPath.findOrDefault(context, "$.id", 1234, Integer.class);
        assertThat(result, equalTo(1234));

    }

    @Test
    public void canSpecifyDefaultValueForMultiplePaths() {
        DocumentContext context = JsonPath.parse("{\"nid\": 4324, \"sid\": 4323, \"mid\": 4322, \"pid\": 4321}");
        int result = LaxJsonPath.findIntForPaths(context, new String [] {"$.fid", "$.lid", "$.wid"}, 1234);
        assertThat(result, equalTo(1234));
    }

}
