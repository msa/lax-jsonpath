# reLAXed JSON Path

## Purpose

Sometimes you need to be very lenient when parsing incoming JSON documents. Maybe you have many different JSON-providers of different capabillities, or adhering to different versions of the API or even being only partially complient to the latest version of the API making a strict document parsing undesireable.

### Usage ideas some of which are not implemented yet

```java
    //Look for a value and provide a default
    int result = LaxJsonPath.findOrDefault(context, "$.id", 1234);
```

```java
    //Look several keys in priority order
    int result = LaxJsonPath.findIntForPaths(context, new String [] {"$.id", "$.sid", "$.mid"});
```
