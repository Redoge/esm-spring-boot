package com.epam.esm.exceptions;

import static com.epam.esm.util.StringConst.objectIsExistsById;
import static com.epam.esm.util.StringConst.objectIsExistsByName;

public class ObjectIsExistException extends Exception{
    public ObjectIsExistException(String objectName, Long id) {
        super(String.format(objectIsExistsById, objectName, id));
    }
    public ObjectIsExistException(String objectName, String name) {
        super(String.format(objectIsExistsByName, objectName, name));
    }

}
