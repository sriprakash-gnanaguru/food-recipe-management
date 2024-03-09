package com.food.recipe.manage.common;

import java.util.Random;

import static com.food.recipe.manage.common.Constants.SYSTEM_ID;

public class CommonUtil {

    public static Long getUniqueId(){
        return getRandomNumberString();
    }

    public static Long getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        Long number = rnd.nextLong(999999);
        // this will convert any number sequence into 6 character.
        return number;
    }
}