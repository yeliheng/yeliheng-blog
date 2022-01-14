package com.yeliheng.blogcommon.utils;

/**
 * UUID生成器工具类
 *
 */
public class UUIDUtils
{

    /**
     * 生成UUID
     * @return UUID
     */
    public static String generateUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * 生成无横线的UUID
     *
     * @return 无横线的UUID
     */
    public static String generateSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }
}
