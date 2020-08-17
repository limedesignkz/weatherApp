package kz.limedesign.weatherapp.network

data class Result<out T>(val status: Status, val data: T?, val error: Error?) {

    companion object {
        fun <T> loading(): Result<T> {
            return Result(
                Status.LOADING,
                null,
                null
            )
        }

        fun <T> success(data: T?): Result<T>? {
            return Result(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: Error?): Result<T> {
            return Result(
                Status.ERROR,
                null,
                error
            )
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class Error(val message: String?) {
    constructor(e: Exception): this(e.message)

    override fun toString(): String {
        return message ?: "Undefined error"
    }
}