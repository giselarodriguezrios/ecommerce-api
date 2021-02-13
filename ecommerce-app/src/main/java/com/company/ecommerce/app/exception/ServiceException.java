package com.company.ecommerce.app.exception;

public class ServiceException  extends RuntimeException {

    private final String code;
    private final String description;
    private final ExceptionServiceType type;
    public ServiceException(ExceptionServiceType type){
        super(type.getDescription());
        this.code = type.getCode();
        this.description = type.getDescription();
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public ExceptionServiceType getType() {
        return type;
    }
}
