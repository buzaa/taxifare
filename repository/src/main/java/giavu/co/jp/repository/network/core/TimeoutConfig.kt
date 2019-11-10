package giavu.co.jp.repository.network.core

/**
 * @Author: Hoang Vu
 * @Date:   2018/12/13
 */
interface TimeoutConfig {
    fun connectionTimeoutMillis(): Long
    fun writeTimeoutMillis(): Long
    fun readTimeoutMillis(): Long
}