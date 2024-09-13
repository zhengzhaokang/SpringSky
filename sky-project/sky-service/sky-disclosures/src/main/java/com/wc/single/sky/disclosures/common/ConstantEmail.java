package com.wc.single.sky.disclosures.common;


/**
 * @author shirui3
 */
public class ConstantEmail {

    private ConstantEmail() {
        throw new IllegalStateException("Utility class");
    }

    public static final String HTML_BEGIN = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<!DOCTYPE stylesheet  [\n" +
            "        <!ENTITY nbsp   \"&#160;\">\n" +
//            "<!ENTITY copy   \"&#169;\">\n" +
//            "<!ENTITY reg    \"&#174;\">\n" +
//            "<!ENTITY trade  \"&#8482;\">\n" +
//            "<!ENTITY mdash  \"&#8212;\">\n" +
//            "<!ENTITY ldquo  \"&#8220;\">\n" +
//            "<!ENTITY rdquo  \"&#8221;\">\n" +
//            "<!ENTITY pound  \"&#163;\">\n" +
//            "<!ENTITY yen    \"&#165;\">\n" +
//            "<!ENTITY euro   \"&#8364;\">\n" +
            "]><html lang=\"en\">";


    public static final String HTML_END = "</html>";
    public static final String HTML_DOCTYPE = "<!DOCTYPE";

}
