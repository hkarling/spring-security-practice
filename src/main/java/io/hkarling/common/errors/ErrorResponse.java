package io.hkarling.common.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @JsonProperty("message")
    private String message;
    @JsonProperty("status")
    private int status;

}
