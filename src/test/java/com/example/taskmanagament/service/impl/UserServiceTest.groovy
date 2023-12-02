package com.example.taskmanagament.service.impl

import com.example.taskmanagament.dao.entity.User
import com.example.taskmanagament.repository.UserRepository
import com.example.taskmanagament.service.OtpService
import com.example.taskmanagament.service.UserService
import spock.lang.Specification

class UserServiceTest extends Specification {

    UserRepository userRepository = Mock(UserRepository)
    OtpService otpService = Mock(OtpService)

    UserService userService = new UserServiceImpl(userRepository, otpService)

    def "getUserById should return user by ID"() {
        given:
        def userId = 1L
        def expectedUser = new User(id: userId, username: "testUser", email: "test@example.com")

        userRepository.findById(userId) >> Optional.of(expectedUser)

        when:
        def result = userService.getUserById(userId)

        then:
        result == expectedUser
    }

    def "getUserByUsername should return user by username"() {
        given:
        def username = "testUser"
        def expectedUser = new User(username: username, email: "test@example.com")

        userRepository.findByUsername(username) >> expectedUser

        when:
        def result = userService.getUserByUsername(username)

        then:
        result == expectedUser
    }
    def "createUser should save user and send OTP"() {
        given:
        def user = new User(username: "testUser", email: "test@example.com")

        when:
        userService.createUser(user)

        then:
        1 * userRepository.save(user)
        1 * otpService.sendOtpByEmail(user.email)
    }

    def "createUser should throw CustomException on error"() {
        given:
        def user = new User(username: "testUser", email: "test@example.com")

        userRepository.save(user) >> { throw new RuntimeException("Error saving user") }

        when:
        def exception = thrown(CustomException) {
            userService.createUser(user)
        }

        then:
        exception.message == "Error creating user: Error saving user"
    }

}