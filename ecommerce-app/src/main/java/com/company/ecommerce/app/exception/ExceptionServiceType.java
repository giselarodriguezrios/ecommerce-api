package com.company.ecommerce.app.exception;

public enum ExceptionServiceType {
    PRODUCT_FILE_READ("1000", "Could not read Product file."),
    PRODUCT("1001", "Could not get products."),

    SIZE_FILE_READ("2000", "Could not read Size file."),
    SIZE("2001", "Could not get size."),

    STOCK_FILE_READ("3000", "Could not read Stock file."),
    STOCK("3001", "Could not get stock.");

    private String code;
    private String description;

    ExceptionServiceType(String code, String description) {
        this.description = description;
        this.code = code;
    }

    public static ExceptionServiceType byCode(String code) {
        for (ExceptionServiceType type : ExceptionServiceType.values()){
            if (type.getCode().equals(code))
                return type;
        }

        return null;
    }

    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
}
