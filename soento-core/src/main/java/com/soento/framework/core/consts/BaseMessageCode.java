package com.soento.framework.core.consts;

/**
 * 消息码
 */
public class BaseMessageCode {
    /**
     * 0000：成功
     */
    public static final String SUCCESS = "0000";

    // V0001 -> V9999  校验错误码
    /**
     * V0001：{0}不能为空
     */
    public static final String V0001 = "V0001";
    /**
     * V0002：{0}的格式不正确，请输入{1}格式的数据
     */
    public static final String V0002 = "V0002";
    /**
     * V0003：{0}的长度不能超过{1}位
     */
    public static final String V0003 = "V0003";
    /**
     * V0004：{0}的长度不能少于{1}位
     */
    public static final String V0004 = "V0004";
    /**
     * V0005：{0}的长度不在{1}位与{2}位之间
     */
    public static final String V0005 = "V0005";
    /**
     * V0006：{0}的数值不能超过{1}
     */
    public static final String V0006 = "V0006";
    /**
     * V0007：{0}的数值不能小于{1}
     */
    public static final String V0007 = "V0007";
    /**
     * V0008：{0}的数值不在{1}与{2}之间
     */
    public static final String V0008 = "V0008";
    /**
     * V0009：{0}的字节长度不能超过{1}位
     */
    public static final String V0009 = "V0009";
    /**
     * V0010：{0}的字节长度不能少于{1}位
     */
    public static final String V0010 = "V0010";
    /**
     * V0011：{0}的字节长度不在{1}位与{2}位之间
     */
    public static final String V0011 = "V0011";
    /**
     * V0012：{0}的数值不能超过{1}位，其中小数位为不能超过{2}位
     */
    public static final String V0012 = "V0012";
    /**
     * V0013：{0}的不是正确的邮箱
     */
    public static final String V0013 = "V0013";
    /**
     * V0014：{0}的不是正确的身份证号
     */
    public static final String V0014 = "V0014";
    /**
     * V0015：{0}的不是正确的手机号
     */
    public static final String V0015 = "V0015";
    /**
     * V0016：{0}的不是正确的邮编
     */
    public static final String V0016 = "V0016";
    /**
     * V0017：{0}的不是正确的URL路径
     */
    public static final String V0017 = "V0017";
    /**
     * V0018：{0}的格式不正确，请输入汉字
     */
    public static final String V0018 = "V0018";
    /**
     * V0019：{0}的格式不正确，密码必须长度在6到16位之间并且含有英文大小写和数字
     */
    public static final String V0019 = "V0019";
    /**
     * V0020：{0}的不是正确的IP地址
     */
    public static final String V0020 = "V0020";

    // W0001 -> W9999 警告消息码
    /**
     * W0001：本操作有未知风险
     */
    public static final String W0001 = "W0001";

    // E0001 -> E9999 错误消息码
    /**
     * E0001：用户不存在
     */
    public static final String E0001 = "E0001";
    /**
     * E0002：密码不正确
     */
    public static final String E0002 = "E0002";
    /**
     * E0003：用户名或密码不正确
     */
    public static final String E0003 = "E0003";
    /**
     * E0004：{0}不存在
     */
    public static final String E0004 = "E0004";
    /**
     * E0005：{0}不正确
     */
    public static final String E0005 = "E0005";
    /**
     * E9996：无访问权限，请与管理员联系
     */
    public static final String E9996 = "E9996";
    /**
     * E9997：用户未登录或者登录已过期
     */
    public static final String E9997 = "E9997";
    /**
     * E9998：系统繁忙，请稍后重试！
     */
    public static final String E9998 = "E9998";
    /**
     * E9999：系统异常
     */
    public static final String E9999 = "E9999";

    // M0001 -> M9999 提示消息码
    /**
     * M0000：成功
     */
    public static final String M0000 = "M0000";
    /**
     * M0001：操作成功
     */
    public static final String M0001 = "M0001";

}
