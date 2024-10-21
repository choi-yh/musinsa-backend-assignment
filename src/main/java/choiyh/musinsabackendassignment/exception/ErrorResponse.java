package choiyh.musinsabackendassignment.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {

    private HttpStatus status;
    private String code;
    private String message;

    public ErrorResponse(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;

        if (this.status == null) {
            this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if (this.code == null) {
            this.code = ErrorCode.INTERNAL_SERVER_ERROR.getCode();
        }

        if (this.message == null) {
            this.message = ErrorCode.INTERNAL_SERVER_ERROR.getMessage();
        }
    }

    public static ErrorResponse of(HttpStatus status, String code, String message) {
        return ErrorResponse.builder()
                .status(status)
                .code(code)
                .message(message)
                .build();
    }

}
