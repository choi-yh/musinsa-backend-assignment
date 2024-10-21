package choiyh.musinsabackendassignment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 요청 값을 클라이언트에서 알고 보낸다고 판단하여 400 상태 코드로 처리합니다.
    NOT_EXIST_BRAND(HttpStatus.BAD_REQUEST, "BRAND_001", "Not existing brand"),

    // 요청 값을 클라이언트에서 알고 보낸다고 판단하여 400 상태 코드로 처리합니다.
    NOT_EXIST_PRODUCT(HttpStatus.BAD_REQUEST, "PRODUCT_001", "Not exist product"),
    UNKNOWN_CATEGORY(HttpStatus.BAD_REQUEST, "PRODUCT_002", "Unknown category"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GEN_500", "Internal Server Error"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
