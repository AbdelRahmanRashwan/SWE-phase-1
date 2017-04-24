package com.example.rashwan.playacademy;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by Rashwan on 4/16/2017.
 */

public final class ServicesLinks {

    public static final String SERVER="http://192.168.23.3:8080";
    public static final String LOGIN_URL=SERVER+"/login";
    public static final String REGISTER_STUDENT_URL=SERVER+"/student/register";
    public static final String REGISTER_TEACHER_URL=SERVER+"/teacher/register";
    public static final String GET_STUDENT_COURSES_URL=SERVER+"/courses/attendeted/student";
    public static final String GET_GAMES_IN_COURSE_URL=SERVER+"/gamescourse/get";
    public static final String JUDGE_ANSWER=SERVER+"/judgeGame";
    public static final String GET_COURSES_BY_TEACHER_URL=SERVER+"/courses/created/teacher/";
    public static final String UPDATE_SCORE_LINK =SERVER+"/game/score/update";
}
