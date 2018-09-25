package com.rczx.blog.util.restfulbody.validation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IDCard {
    private final String cardNumber;
    private Boolean cacheValidateResult = null;
    private Date cacheBirthDate = null;
    private static final String BIRTH_DATE_FORMAT = "yyyyMMdd";
    private static final Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L);
    private static final int NEW_CARD_NUMBER_LENGTH = 18;
    private static final int OLD_CARD_NUMBER_LENGTH = 15;
    private static final char[] VERIFY_CODE = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    private static final int[] VERIFY_CODE_WEIGHT = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    public boolean validate() {
        if(null == this.cacheValidateResult) {
            boolean result = true;
            result = result && null != this.cardNumber;
            result = result && 18 == this.cardNumber.length();

            for(int e = 0; result && e < 17; ++e) {
                char birthdayPart = this.cardNumber.charAt(e);
                result = result && birthdayPart >= 48 && birthdayPart <= 57;
            }

            result = result && calculateVerifyCode(this.cardNumber) == this.cardNumber.charAt(17);

            try {
                Date var6 = this.getBirthDate();
                result = result && null != var6;
                result = result && var6.before(new Date());
                result = result && var6.after(MINIMAL_BIRTH_DATE);
                String var7 = this.getBirthDayPart();
                String realBirthdayPart = this.createBirthDateParser().format(var6);
                result = result && var7.equals(realBirthdayPart);
            } catch (Exception var5) {
                result = false;
            }

            this.cacheValidateResult = Boolean.valueOf(result);
        }

        return this.cacheValidateResult.booleanValue();
    }

    public IDCard(String cardNumber) {
        if(null != cardNumber) {
            cardNumber = cardNumber.trim();
            if(15 == cardNumber.length()) {
                cardNumber = contertToNewCardNumber(cardNumber);
            }
        }

        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getAddressCode() {
        this.checkIfValid();
        return this.cardNumber.substring(0, 6);
    }

    private Date getBirthDate() {
        if(null == this.cacheBirthDate) {
            try {
                this.cacheBirthDate = this.createBirthDateParser().parse(this.getBirthDayPart());
            } catch (Exception var2) {
                throw new RuntimeException("身份证的出生日期无效");
            }
        }

        return new Date(this.cacheBirthDate.getTime());
    }

    private boolean isMale() {
        return 1 == this.getGenderCode();
    }

    public boolean isFemal() {
        return !this.isMale();
    }

    private int getGenderCode() {
        this.checkIfValid();
        char genderCode = this.cardNumber.charAt(16);
        return genderCode - 48 & 1;
    }

    private String getBirthDayPart() {
        return this.cardNumber.substring(6, 14);
    }

    private SimpleDateFormat createBirthDateParser() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    private void checkIfValid() {
        if(!this.validate()) {
            throw new RuntimeException("身份证号码不正确！");
        }
    }

    private static char calculateVerifyCode(CharSequence cardNumber) {
        int sum = 0;

        for(int i = 0; i < 17; ++i) {
            char ch = cardNumber.charAt(i);
            sum += (ch - 48) * VERIFY_CODE_WEIGHT[i];
        }

        return VERIFY_CODE[sum % 11];
    }

    private static String contertToNewCardNumber(String oldCardNumber) {
        StringBuilder builder = new StringBuilder(18);
        builder.append(oldCardNumber.substring(0, 6));
        builder.append("19");
        builder.append(oldCardNumber.substring(6));
        builder.append(calculateVerifyCode(builder));
        return builder.toString();
    }
}
