package com.example.githubclient

import com.example.githubclient.utils.ClassForTest
import org.junit.Test
import org.junit.Assert.*

class ClassForTestTest {
    @Test
    fun getUsersArray_isEqualRequestArray() {
        assertArrayEquals(ClassForTest.getUsersArray(), Array<String>(3) { "user1"; "user2"; "user3" })
    }

    @Test
    fun getOneUser_isEqualRequestLogin() {
        assertEquals(ClassForTest.getOneUser(), "first_user")
    }

    @Test
    fun getOneUser_isNotEqualRequestLogin() {
        assertNotEquals(ClassForTest.getOneUser(), "second_user")
    }

    @Test
    fun getEmptyUser_isNull() {
        assertNull(ClassForTest.getEmptyUser())
    }

    @Test
    fun getOneUser_isSameGetOneUserOneMoreTime() {
        assertSame(ClassForTest.getOneUser(), ClassForTest.getOneUserOneMoreTime())
    }

}