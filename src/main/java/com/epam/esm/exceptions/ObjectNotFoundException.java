package com.epam.esm.exceptions;

import static com.epam.esm.util.StringConst.objectIsNotExistsById;
import static com.epam.esm.util.StringConst.objectIsNotExistsByName;

public class ObjectNotFoundException extends Exception{
    public ObjectNotFoundException(String objectName, Long id) {
        super(String.format(objectIsNotExistsById, objectName, id));
    }
    public ObjectNotFoundException(String objectName, String name) {
        super(String.format(objectIsNotExistsByName, objectName, name));
    }
}
