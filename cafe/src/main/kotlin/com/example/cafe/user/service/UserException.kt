package com.example.cafe.user.service

sealed class UserException : RuntimeException()

class SignUpUserIdConflictException : UserException()

class SignUpBadUserIdException : UserException()

class SignUpBadPasswordException : UserException()

class SignUpBadEmailException : UserException()

class SignUpBadBirthDateException : UserException()

class SignUpBadPhoneNumberException : UserException()

class SignInUserNotFoundException : UserException()

class SignInInvalidPasswordException : UserException()

class SignOutUserNotFoundException() : UserException()

class UserNotFoundException(): UserException()

class NicknameConflictException(): UserException()

class InvalidTokenException(): UserException()

class ExpiredTokenException(): UserException()