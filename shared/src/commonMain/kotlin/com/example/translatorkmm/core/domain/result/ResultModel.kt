package com.example.translatorkmm.core.domain.result

sealed class ResultModel<T>(
    open val data: T?,
    open val throwable: Throwable?
) {
    class Success<T>(override val data: T) : ResultModel<T>(data, null)
    class Error<T>(override val throwable: Throwable) : ResultModel<T>(null, throwable)
}

//data class ResultModel<T>(
//    val data: T?,
//    val status: Status
//) {
//    companion object {
//        fun <T> success(data: T?) = ResultModel(
//            data = data,
//            status = Status.Success
//        )
//
//        fun <T> error(errorMessage: String, data: T? = null) = ResultModel<T>(
//            data = null,
//            status = Status.Error(errorMessage)
//        )
//    }
//}
