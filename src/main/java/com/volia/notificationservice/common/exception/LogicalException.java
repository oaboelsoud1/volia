package com.volia.notificationservice.common.exception;

public class LogicalException extends BaseException {
    private static final long serialVersionUID = -6527073160488586546L;

    public LogicalException(ServerError err) {
        this(err, err.getMessage());
    }


    public LogicalException(ServerError err, String clientMsg) {
        super(err, clientMsg, null);
    }

    public LogicalException(ServerError err, String clientMsg, String developer) {
        super(err, clientMsg, developer);
    }

    public LogicalException apply(Object... params) {
        this.params = params;
        return this;
    }
}
