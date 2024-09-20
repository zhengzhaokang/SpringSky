package com.wc.single.sky.common.utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class XmlUtils {
    public static Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    public static List<Map<String,Object>> parseXml(Class clazz,String filePath) throws Exception {
        InputStream is = clazz.getClassLoader().getResourceAsStream(filePath);
        SAXReader reader = new SAXReader();
        Document document = reader.read(is);
        Element root = document.getRootElement();
        List<Map<String, Object>> infoMaps = new CopyOnWriteArrayList<>();
        List<Element>  elements = root.elements("table");
        if (null == elements || elements.size() <= 0) {
            return null;
        }
        for (Element element : elements) {
            infoMaps.add(getChild(element));
        }
        return infoMaps;
    }

    private static Map<String, Object> getChild(Element element) {
        Map<String, Object> infoMap = new HashMap<>();
        addTableAttribute(element,infoMap);
        List<Element> proEles = element.elements();
        List<Map<String, String>> props = new ArrayList<>();
        for(Iterator it = proEles.iterator(); it.hasNext();) {
            Element proEle = (Element)it.next();
            props.add(getAttrMap(proEle));
        }
        infoMap.put("joinInfo", props);
        return infoMap;
    }

    private static Map<String, String> getAttrMap(Element element) {
        List<Attribute> attributes = element.attributes();
        Map<String, String> attrMap = new HashMap<>();
        for (Attribute attribute : attributes) {
            attrMap.put(attribute.getName(), attribute.getStringValue());
        }
        return attrMap;
    }

    private static void addTableAttribute(Element element, Map<String, Object> infoMap) {
        infoMap.put("tableInfo", getAttrMap(element));
    }
}
