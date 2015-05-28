package com.helios.service;

import com.google.gson.Gson;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

import java.io.IOException;
import java.net.URL;

/**
 * Created by kepuss on 16.05.15.
 */
public class HttpJsonReader<T> {
    public T fromJson(String url, Class<T> type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        T object = mapper.readValue(new URL(url), type);
        return object;
    }

    public String toJson(T type) throws IOException {
        DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
        pp.indentObjectsWith(new DefaultPrettyPrinter.FixedSpaceIndenter());
        pp.indentArraysWith(new DefaultPrettyPrinter.NopIndenter());

  //      pp.indentArraysWith(new DefaultPrettyPrinter.Lf2SpacesIndenter());
     //   mapper.writer(pp).writeValue(filePath, mapObject);
        String result =  new ObjectMapper().writer(pp).writeValueAsString(type);
        result = result.replaceAll(" : ",": ");
        result = result.replaceAll("\\{ ","{");
        result = result.replaceAll(" }","}");

        return result;
    }
}
