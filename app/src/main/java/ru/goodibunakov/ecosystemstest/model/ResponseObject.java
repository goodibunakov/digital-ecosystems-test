package ru.goodibunakov.ecosystemstest.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs")
public class ResponseObject {

    @Attribute(name = "Date")
    private String date;

    @ElementList(inline = true)
    private List<Currency> list;

    public List<Currency> getList() {
        return list;
    }

    public String getDate() {
        return date;
    }
}
