package choiyh.musinsabackendassignment.enums;

import choiyh.musinsabackendassignment.exception.CustomException;
import choiyh.musinsabackendassignment.exception.ErrorCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {

    TOP("상의"),
    OUTER("아우터"),
    PANTS("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORY("액세서리");

    private final String korean;

    Category(String korean) {
        this.korean = korean;
    }

    @Override
    public String toString() {
        return korean;
    }

    public static Category getCategoryFromKorean(String korean) {
        return Arrays.stream(Category.values())
                .filter(category -> category.getKorean().equals(korean))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.UNKNOWN_CATEGORY, korean));
    }

}
