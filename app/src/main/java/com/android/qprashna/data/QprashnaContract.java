package com.android.qprashna.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class QprashnaContract {
    public static final String CONTENT_AUTHORITY = "com.android.qprashna";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TASKS = "profile";


    public static final class ProfileEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();


        // Task table and column names
        public static final String TABLE_NAME = "profile";

        public static final String USERID = "user_id";
        public static final String FIRSTNAME = "first_name";
        public static final String LASTNAME = "last_name";
        public static final String PROFILEPIC = "profile_pic";
        public static final String DESIGNATION = "designation";
        public static final String EMAIL = "email";
        public static final String DOB = "dob";
        public static final String GENDER = "gender";
        public static final String COUNTRY = "country";
        public static final String STATE = "state";
    }
}