package it.iwkz.api.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class ListResponse<T> implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> data;

    private int page = 0;

    private int pageSize = 0;

    private long count = 0;

    public ListResponse() {
        super();
    }

    public ListResponse(List<T> data) {
        this.data = Optional.of(data).orElse(null);
    }
}