package com.rentmen.app.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response {

    @JsonProperty("total_records")
    private Long totalRecords;
    private String message;
    private Object data;

    public Response() {

    }

    private Response(String message) {
        this.message = message;
    }

    private Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    private Response(String message, Long totalRecords, Object data) {
        this.message = message;
        this.totalRecords = totalRecords;
        this.data = data;
    }

    /**
     * This is public static method responsible to provide creation of
     * Response object.
     *
     * @param message Message as per requirements
     * @return Response object
     */
    public static Response getInstance(String message) {
        return new Response(message);
    }

    /**
     * This is public static method responsible to provide creation of
     * Response Object.
     *
     * @param message Message as per requirements
     * @param data    Data to be returned as response
     * @return Response object
     */
    public static Response getInstance(String message, Object data) {
        return new Response(message, data);
    }

    /**
     * This is public static method responsible to provide creation of
     * paginationResponse Object.
     *
     * @param message      Message as per requirements
     * @param totalRecords totalrecordCount as per number of records
     * @param data         Data to be returned as response
     * @return paginationResponse object
     */
    public static Response getInstance(String message, Long totalRecords, Object data) {
        return new Response(message, totalRecords, data);
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
