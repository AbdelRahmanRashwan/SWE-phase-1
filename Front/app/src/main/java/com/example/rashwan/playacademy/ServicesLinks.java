package com.example.rashwan.playacademy;

/**
 * Created by Rashwan on 4/16/2017.
 */

public final class ServicesLinks {

    public static final String SERVER="http://192.168.1.91:8080";
    public static final String LOGIN_URL=SERVER+"/login";
    public static final String REGISTER_STUDENT_URL=SERVER+"/student/register";
    public static final String REGISTER_TEACHER_URL=SERVER+"/teacher/register";
    public static final String GET_STUDENT_COURSES_URL=SERVER+"/courses/attendeted/student";
    public static final String GET_GAMES_IN_COURSE_URL=SERVER+"/gamescourse/get";
    public static final String GET_DELETED_GAMES_IN_COURSE_URL=SERVER+"/gamescourse/get/deleted";
    public static final String JUDGE_ANSWER=SERVER+"/judgeGame";
    public static final String GET_COURSES_BY_TEACHER_URL=SERVER+"/courses/created/teacher/";
    public static final String UPDATE_SCORE_LINK =SERVER+"/game/score/update";
    public static final String CREATE_MCQ_GAME = SERVER+"/game/mcq/create";
    public static final String CREATE_TAF_GAME = SERVER+"/game/trueandfalse/create";
    public static final String GET_ALL_COURSES_URL=SERVER+"/course/getAll";
    public static final String ADD_COURSE_URL=SERVER+"/course/create";
    public static final String ENROLL_URL=SERVER+"/course/attend";
    public static final String IS_ENROLLED_URL=SERVER+"/courses/enrollment";
    public static final String GET_SCOREBOARD_URL=SERVER+"/student/scoreBoard";
    public static final String NOTIFICATION_STUDENT_URL=SERVER+"/student/notifications";
    public static final String NOTIFICATION_TEACHER_URL=SERVER+"/teacher/notifications";
    public static final String GET_COMMENTS_URL=SERVER+"/comments/get";
    public static final String ADD_COMMENT_URL=SERVER+"/comment/add";
    public static final String CANCEL_GAME_URL=SERVER+"/game/cancel";
    public static final String UN_CANCEL_GAME_URL=SERVER+"/game/un-cancel";

}
