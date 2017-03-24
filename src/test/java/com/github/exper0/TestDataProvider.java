package com.github.exper0;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Andrei Eliseev (aeg.exper0@gmail.com)
 */
public class TestDataProvider {
    public static Collection<Object[]> read(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream resource = TestDataProvider.class.getClassLoader().getResourceAsStream(path);
        TestCase[] cases = mapper.readValue(resource, TestCase[].class);
        Collection<Object[]> result = new ArrayList<>();
        for (int i=0; i<cases.length; ++i) {
            result.add(new Object[] {cases[i]});
        }
        return result;
    }

}
