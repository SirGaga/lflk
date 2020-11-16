package com.asideal.lflk.response;

/**
 *
 * 返回码定义
 * 规定:
 * #200表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * #后面对什么的操作自己在这里注明就行了
 * @author ZhangJie
 * @since 2020-11-03
 */
public enum ResultCode implements CustomResultCode{
    /* 成功 */
    SUCCESS(200, "成功"),
    USER_LOGOUT_SUCCESS(201, "账号退出成功"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    JWT_CREATE_ERROR(1005, "JWT创建异常"),
    JWT_VERIFY_ERROR(1006, "JWT验证异常"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "用户/密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    USER_ACCOUNT_UPDATE_FAILURE(2010, "账号更新失败"),
    USER_ACCOUNT_ADD_FAILURE(2011, "账号添加失败"),
    USER_ACCOUNT_DELETE_FAILURE(2012, "账号删除失败"),


    /*部门错误*/
    DEPARTMENT_NOT_EXIST(3007, "部门不存在"),
    DEPARTMENT_ALREADY_EXIST(3008, "部门已存在"),

    /*部门错误*/
    ROLE_ADD_FAILURE(4001, "角色添加失败"),
    ROLE_UPDATE_FAILURE(4002, "角色更新失败"),
    ROLE_DELETE_FAILURE(4003, "角色删除失败"),

    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限"),

    /*运行时异常*/
    ARITHMETIC_EXCEPTION(9001,"算数异常");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
