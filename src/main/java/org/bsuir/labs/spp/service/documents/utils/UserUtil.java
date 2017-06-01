package org.bsuir.labs.spp.service.documents.utils;

import org.bsuir.labs.spp.domain.User;

public class UserUtil {
    public static String getFullName(User user){
        return user.getFirstName() + " " + user.getLastName();
    }
}
