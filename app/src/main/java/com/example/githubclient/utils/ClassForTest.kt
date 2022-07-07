package com.example.githubclient.utils

class ClassForTest {
    companion object {
        private val TEST_USERS_ARRAY = Array<String>(3) { "user1"; "user2"; "user3" }
        private val MY_USER = "first_user"
        fun getUsersArray() : Array<String> {
            return TEST_USERS_ARRAY
        }
        fun getOneUser() : String {
            return MY_USER
        }

        fun getOneUserOneMoreTime() : String {
            return MY_USER
        }

        fun getEmptyUser() : String? {
            return null
        }
    }
}