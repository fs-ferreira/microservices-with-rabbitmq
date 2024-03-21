package br.com.fsferreira.productapi.config.exception;

import java.util.Objects;

public class Exception {

    private int status;
    private String message;

    public Exception() {
    }

    public Exception(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exception that = (Exception) o;
        return status == that.status && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }

    @Override
    public String toString() {
        return "GenericException{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
