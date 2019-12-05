package it.iwkz.api.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityResponse<T> implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> data;

    public EntityResponse() {
        super();
    }

    public EntityResponse(T data) {
        this.data = Optional.of(Collections.singletonList(data)).orElse(null);
    }
}
