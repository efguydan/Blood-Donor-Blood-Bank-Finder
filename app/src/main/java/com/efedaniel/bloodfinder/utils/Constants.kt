package com.efedaniel.bloodfinder.utils

class PrefKeys {
    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }
}

class Utils{
    companion object{
        const val LIMIT = 40
    }
}

class APIDataKeys {
    companion object {
        const val INPUT_ERROR_CODE = 400
    }
}

class ApiKeys{
    companion object{
        const val AUTH_BASE_URL = "https://identitytoolkit.googleapis.com/"
        const val WEB_API_KEY = "AIzaSyA4PDmmaafAasb2l33XrprsH05DDVz4HEU"
    }
}

class ErrorCodes {
    companion object {
        const val EMAIL_NOT_FOUND = "EMAIL_NOT_FOUND"
        const val MISSING_EMAIL = "MISSING_EMAIL"
        const val INVALID_EMAIL = "INVALID_EMAIL"
        const val EMAIL_EXISTS = "EMAIL_EXISTS"
        const val INVALID_PASSWORD = "INVALID_PASSWORD"
        const val USER_DISABLED = "USER_DISABLED"
        const val TOO_MANY_ATTEMPTS = "TOO_MANY_ATTEMPTS_TRY_LATER : Too many unsuccessful login attempts.  Please include reCaptcha verification or try again later"
    }
}