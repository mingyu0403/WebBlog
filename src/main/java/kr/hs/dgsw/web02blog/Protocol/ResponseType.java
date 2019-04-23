package kr.hs.dgsw.web02blog.Protocol;

public enum ResponseType {
    FAIL                (0, "명령을 실행하지 못했습니다"),

    USER_GET_ALL        (101, "모든 사용자를 가져옵니다."),
    USER_GET            (102, "하나의 사용자를 가져옵니다."),
    USER_ADD            (103, "사용자를 등록했습니다."),
    USER_UPDATE         (104, "사용자를 수정했습니다."),
    USER_DELETE         (105, "사용자를 삭제했습니다."),

    POST_GET_ALL        (201, "모든 게시글을 가져옵니다."),
    POST_GET_RECENT     (202, "가장 최근 게시글을 가져옵니다."),
    POST_GET            (203, "하나의 게시글을 가져옵니다."),
    POST_ADD            (204, "게시글을 등록했습니다."),
    POST_UPDATE         (205, "게시글을 수정했습니다."),
    POST_DELETE         (206, "게시글을 삭제했습니다."),

    ATTACHMENT_STORED   (301, ""),
    ATTACHMENT_UPLOAD   (302, "")
    ;

    final private int code;
    final private String desc;

    ResponseType(int code, String desc){
        this.code = code;
        this.desc = desc;
    }
    public int code() {return this.code;}
    public String desc() {return this.desc;}

}
