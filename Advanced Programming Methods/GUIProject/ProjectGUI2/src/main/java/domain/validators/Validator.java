package domain.validators;

import domain.validators.ValidationException;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;

    static boolean isLetterAndSpace(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }

        char[] chars = s.toCharArray();
        for (int index = 0; index < chars.length; index++) {
            int codePoint = Character.codePointAt(chars, index);
            if (!Character.isLetter(codePoint) && !Character.isSpaceChar(codePoint)) {
                return false;
            }
        }

        return true;
    }

}
