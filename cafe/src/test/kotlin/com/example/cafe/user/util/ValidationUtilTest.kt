package com.example.cafe.user.util

import org.assertj.core.api.Assertions.*

import org.junit.jupiter.api.Test

class ValidationUtilTest {

    @Test
    fun isUserIdValid() {
        assertThat(ValidationUtil().isUserIdValid("a")).isFalse()
        assertThat(ValidationUtil().isUserIdValid("123456")).isFalse()
        assertThat(ValidationUtil().isUserIdValid("ABC1234")).isFalse()
        assertThat(ValidationUtil().isUserIdValid("abc###")).isFalse()
        assertThat(ValidationUtil().isUserIdValid("-----1")).isFalse()

        assertThat(ValidationUtil().isUserIdValid("iuduf")).isTrue()
        assertThat(ValidationUtil().isUserIdValid("ank123")).isTrue()
        assertThat(ValidationUtil().isUserIdValid("aaaank123")).isTrue()
        assertThat(ValidationUtil().isUserIdValid("abc---")).isTrue()
        assertThat(ValidationUtil().isUserIdValid("abc---")).isTrue()
    }

    @Test
    fun isPasswordValid() {
        assertThat(ValidationUtil().isPasswordValid("a")).isFalse()
        assertThat(ValidationUtil().isPasswordValid("12345678")).isFalse()
        assertThat(ValidationUtil().isPasswordValid("########")).isFalse()

        assertThat(ValidationUtil().isPasswordValid("#abcd1234#")).isTrue()
        assertThat(ValidationUtil().isPasswordValid("ABCD1234")).isTrue()
        assertThat(ValidationUtil().isPasswordValid("ABCD##@%")).isTrue()
    }

    @Test
    fun isEmailValid() {
        assertThat(ValidationUtil().isEmailValid("a")).isFalse()
        assertThat(ValidationUtil().isEmailValid("a@b")).isFalse()
        assertThat(ValidationUtil().isEmailValid("doo@.com")).isFalse()

        assertThat(ValidationUtil().isEmailValid("doo@naver.com")).isTrue()
    }

    @Test
    fun isBirthDateValid() {
        assertThat(ValidationUtil().isBirthDateValid("19970426")).isFalse()
        assertThat(ValidationUtil().isBirthDateValid("20370426")).isFalse()

        assertThat(ValidationUtil().isBirthDateValid("1997.04.26")).isTrue()
    }

    @Test
    fun isPhoneNumberValid() {
        assertThat(ValidationUtil().isPhoneNumberValid("01123456789")).isFalse()
        assertThat(ValidationUtil().isPhoneNumberValid("010123456789")).isFalse()

        assertThat(ValidationUtil().isPhoneNumberValid("01012345678")).isTrue()
    }
}