package com.leifu.commonlib.util

import java.math.BigDecimal
import java.text.NumberFormat

/**
 * 创建人:雷富
 * 创建时间:2018/8/4 14:03
 * 描述:
 */
object ArithUtils {
    /**
     * 加法
     */
    fun add(value1: Double, value2: Double): Double {
        val b1 = BigDecimal(value1)
        val b2 = BigDecimal(value2)
        return b1.add(b2).toDouble()
    }


    fun add(value1: String, value2: String): String {
        val b1 = BigDecimal(value1)
        val b2 = BigDecimal(value2)
        return b1.add(b2).toString()
    }

    /**
     * 减法
     */
    fun sub(value1: Double, value2: Double): Double {
        val b1 = BigDecimal(value1)
        val b2 = BigDecimal(value2)
        return b1.subtract(b2).toDouble()
    }

    fun sub(value1: String, value2: String): String {
        val b1 = BigDecimal(value1)
        val b2 = BigDecimal(value2)
        return b1.subtract(b2).toString()
    }

    fun subWithDouble(value1: String, value2: String): Double {
        val b1 = BigDecimal(value1)
        val b2 = BigDecimal(value2)
        return b1.subtract(b2).toDouble()
    }

    /**
     * 乘法
     */
    fun mul(value1: Double, value2: Double): Double {
        val b1 = BigDecimal(value1)
        val b2 = BigDecimal(value2)
        return b1.multiply(b2).toDouble()
    }

    fun mul(value1: String, value2: String): String {
        val b1 = BigDecimal(value1)
        val b2 = BigDecimal(value2)
        return b1.multiply(b2).toString()
    }

    fun mul(value1: String, value2: String, value3: String): String {
        val b1 = BigDecimal(value1)
        val b2 = BigDecimal(value2)
        val b3 = BigDecimal(value3)
        return b1.multiply(b2).multiply(b3).toString()
    }

    /**
     * 除法
     */
    @Throws(IllegalAccessException::class)
    fun div(value1: Double, value2: Double, scale: Int): Double {
        // 如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw IllegalAccessException("精确度不能小于0")
        }
        val b1 = BigDecimal(value1)
        val b2 = BigDecimal(value2)
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_DOWN).toDouble()
    }

    @Throws(IllegalAccessException::class)
    fun div(value1: String, value2: String, scale: Int): String {
        // 如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw IllegalAccessException("精确度不能小于0")
        }
        val b1 = BigDecimal(value1)
        val b2 = BigDecimal(value2)
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_DOWN).toString()
    }

    /**
     * 百分数
     *
     * @param value1
     * @param value2
     */
    fun percentString(value1: Int, value2: Int): String {
        // 创建一个数值格式化对象
        val numberFormat = NumberFormat.getInstance()
        // 设置精确到小数点后2位
        numberFormat.maximumFractionDigits = 2
        if (value1 == 0 || value2 == 0) {
            return "0%"
        }
        return numberFormat.format((value1.toFloat() / value2.toFloat() * 100).toDouble()) + "%"
    }

    fun percentDouble(value1: Int, value2: Int): Double {
        // 创建一个数值格式化对象
        val numberFormat = NumberFormat.getInstance()
        // 设置精确到小数点后2位
        numberFormat.maximumFractionDigits = 2
        if (value1 == 0 || value2 == 0) {
            return 0.0
        }
        return numberFormat.format((value1.toFloat() / value2.toFloat() * 100)).toDouble()
    }
}
