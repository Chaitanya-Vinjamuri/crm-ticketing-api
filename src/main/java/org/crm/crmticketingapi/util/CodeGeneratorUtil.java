package org.crm.crmticketingapi.util;

public class CodeGeneratorUtil {

    private CodeGeneratorUtil() {
    }

    public static String generateAgentCode() {

        return "AGT" +
                System.currentTimeMillis();
    }

    public static String generateTicketCode() {

        return "TKT" +
                System.currentTimeMillis();
    }

    public static String generateCommentCode() {

        return "CMT" +
                System.currentTimeMillis();
    }
}