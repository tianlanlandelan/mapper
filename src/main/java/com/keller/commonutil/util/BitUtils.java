package com.keller.commonutil.util;

/**
 * 位运算工具类
 * @author yangkaile
 * @date 2021-02-08 22:39:00
 */
public class BitUtils {

    /**
     * 判断一个数第index位是否有值
     * @param number
     * @param index
     * @return
     */
    public static boolean hasBit(long number,int index){
        return (number >> (index - 1) & 1) == 1;
    }

    /**
     * 将一个二进制数的第位设置为0或1
     * @param number
     * @param index
     * @param status
     * @return
     */
    public static long setBit(long number,int index,int status){
        if (status != 0 && status != 1) {
            throw new IllegalArgumentException();
        }
        if (status == 1) {
            if(hasBit(number, index)){
                return number;
            }
            return (1 << (index - 1)) | number;
        } else {
            //先判断原来是不是0,原来是0则直接返回
            if (!hasBit(number,index)){
                return number;
            }
            return number - (1 << (index - 1));
        }
    }


    public static void main(String[] args){
        // false
        Console.println("hasBit(21,2)",hasBit(21,2));
        //20
        Console.println("setBit(21,1,0)",setBit(21,1,0));
        //5
        Console.println("setBit(21,5,0)",setBit(21,5,0));
        //9
        Console.println("setBit(21,4,1)",setBit(21,4,1));

    }

}
