package com.wc.single.sky.common.utils;

public class CodeUtils {


    public static final String ibanMaskStr = "****";

    /**
     * Bank Account Number/IBAN 加*
     *
     * @return 处理完成的身份证
     */
    public static String bankAccountNumberMask(String bankNum) {
        String res = "";
        if (!StringUtils.isEmpty(bankNum)) {
            //todo 测试reg  res = bankNum.replaceAll("(\\S{1,4})\\S{1}(\\S{1,20})", "$1****$2");
            StringBuilder stringBuilder = new StringBuilder(bankNum);
            if(bankNum.length()>4){
                res = bankNum.substring(0,4)+ ibanMaskStr +bankNum.substring(bankNum.length() - 4);
            }else{
                res = bankNum.substring(0,1)+ibanMaskStr+bankNum.substring(bankNum.length() - 1);
            }

        }
        return res;
    }

    public static void main(String[] args) {
        CodeUtils cu= new CodeUtils();
        System.out.println(cu.bankAccountNumberMask("123456789"));  //1234****6789
        System.out.println(cu.bankAccountNumberMask("12345678"));  //1234****5678
        System.out.println(cu.bankAccountNumberMask("12345"));  //1234****2345
        System.out.println(cu.bankAccountNumberMask("1234")); //1****4
        System.out.println(cu.bankAccountNumberMask("123"));  //1****3
        System.out.println(cu.bankAccountNumberMask("1"));  //1****1

    }
    /**
     * 用户电话号码的打码隐藏加星号加*
     *
     * @return 处理完成的身份证
     */
    public static String phoneMask(String phone) {
        String res = "";
        if (!StringUtils.isEmpty(phone)) {
            StringBuilder stringBuilder = new StringBuilder(phone);
            res = stringBuilder.replace(3, 7, ibanMaskStr).toString();
        }
        return res;
    }
}



