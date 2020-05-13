package model.validator;

import model.exceptions.ValidationException;

public interface IValidator<E> {
    static boolean isLetterAndSpace(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }

        char[] chars = s.toCharArray();
        for (int index = 0; index < chars.length; index++) {
            int codePoint = Character.codePointAt(chars, index);
            if (!Character.isLetter(codePoint) && !Character.isSpaceChar(codePoint)) {
                return true;
            }
        }

        return false;
    }

    void validate(E entity) throws ValidationException;
}