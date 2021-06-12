package com.hutcwp.common.mvp;

/**
 * description:统一的错误信息
 * Unified error message
 *
 * @param <T> the type parameter
 */
public class BizError<T> {

    private final T error;

    public BizError(T error) {
        this.error = error;
    }

    /**
     * Gets error.
     * 得到错误
     *
     * @return the error
     */
    public T getError() {
        return error;
    }
}
