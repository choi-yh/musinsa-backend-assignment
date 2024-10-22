package choiyh.musinsabackendassignment.enums;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(Category category) {
        if (category == null) {
            return null;
        }
        return category.toString();
    }

    @Override
    public Category convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return Category.getCategoryFromKorean(s);
    }

}
