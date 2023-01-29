package com.brandon.network

import com.brandon.data.ForzenDao
import com.brandon.data.ForzenEntity
import com.brandon.forzenbook.repository.CreateUserData
import com.brandon.forzenbook.repository.CreateUserRepositoryImpl
import com.brandon.forzenbook.repository.UserAlreadyExistsException
import com.brandon.logincore.data.LoginRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import java.sql.Date


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class NetworkUnitTests() {

    @Test
    fun testRequestEmailCode() {
        val apiService = ForzenApiServiceAlwaysSuccessMock()
        val loginRepo = LoginRepositoryImpl(apiService, successDao)
        runBlocking {
            assertEquals(Unit, loginRepo.getCode(TEST_FIRSTNAME))
        }
    }

    @Test
    fun testRequestEmailCodeThrowsApiFailure() {
        val apiService = ForzenApiServiceAlwaysThrowsMock()
        val loginRepo = LoginRepositoryImpl(apiService, successDao)
        runBlocking {
            try {
                loginRepo.getCode(TEST_FIRSTNAME)
                fail(FAILED_TO_THROW_MESSAGE)
            } catch (e: Exception) {
                assert(e.message == API_CALL_FAILED)
            }
        }
    }

    @Test
    fun testRequestEmailCodeThrowsResponseCodeFailure() {
        val apiService = ForzenApiServiceAlwaysFailsMock()
        val loginRepo = LoginRepositoryImpl(apiService, successDao)
        runBlocking {
            try {
                loginRepo.getCode(TEST_FIRSTNAME)
                fail(FAILED_TO_THROW_MESSAGE)
            } catch (e: Exception) {
                assert(e.message == RESPONSE_CODE_ERROR)
            }
        }
    }

    @Test
    fun testLoginWithEmailCode() {
        val apiService = ForzenApiServiceAlwaysSuccessMock()
        val loginRepo = LoginRepositoryImpl(apiService, successDao)
        runBlocking {
            assertEquals(Unit, loginRepo.getToken(TEST_FIRSTNAME, TEST_CODE))
        }
    }

    @Test
    fun testLoginWithEmailCodeThrowsApiFailure() {
        val apiService = ForzenApiServiceAlwaysThrowsMock()
        val loginRepo = LoginRepositoryImpl(apiService, successDao)
        runBlocking {
            try {
                loginRepo.getToken(TEST_FIRSTNAME, TEST_CODE)
                fail(FAILED_TO_THROW_MESSAGE)
            } catch (e: Exception) {
                assert(e.message == API_CALL_FAILED)
            }
        }
    }

    @Test
    fun testLoginWithEmailCodeThrowsResponseCode() {
        val apiService = ForzenApiServiceAlwaysFailsMock()
        val loginRepo = LoginRepositoryImpl(apiService, successDao)
        runBlocking {
            try {
                loginRepo.getToken(TEST_FIRSTNAME, TEST_CODE)
                fail(FAILED_TO_THROW_MESSAGE)
            } catch (e: Exception) {
                assert(e.message == RESPONSE_CODE_ERROR)
            }
        }
    }

    @Test
    fun testLoginWithEmailCodeThrowsAccessDatabaseFailure() {
        val apiService = ForzenApiServiceAlwaysSuccessMock()
        val throwDao = object : ForzenDao {
            override suspend fun insert(forzenEntity: ForzenEntity) =
                throw Exception(DATABASE_ERROR_ACCESS)

            override suspend fun getLoginToken(): ForzenEntity? =
                throw Exception(DATABASE_ERROR_ACCESS)

            override suspend fun deleteLoginToken() = throw Exception(DATABASE_ERROR_ACCESS)
        }
        val loginRepo = LoginRepositoryImpl(apiService, throwDao)
        runBlocking {
            try {
                loginRepo.getToken(TEST_FIRSTNAME, TEST_CODE)
                fail(FAILED_TO_THROW_MESSAGE)
            } catch (e: Exception) {
                assert(e.message == DATABASE_ERROR_ACCESS)
            }
        }
    }

    @Test
    fun testLoginWithEmailCodeThrowsConvertEntityFailure() {
        val apiService = ForzenApiServiceAlwaysSuccessMock()
        val throwDao = object : ForzenDao {
            override suspend fun insert(forzenEntity: ForzenEntity) =
                throw Exception(DATABASE_ERROR_ACCESS)

            override suspend fun getLoginToken(): ForzenEntity? =
                throw Exception(DATABASE_ERROR_ACCESS)

            override suspend fun deleteLoginToken() = Unit
        }
        val loginRepo = LoginRepositoryImpl(apiService, throwDao)
        runBlocking {
            try {
                loginRepo.getToken(TEST_FIRSTNAME, TEST_CODE)
                fail(FAILED_TO_THROW_MESSAGE)
            } catch (e: Exception) {
                assert(e.message == FAILED_ENTITY_CONVERT)
            }
        }
    }

    @Test
    fun testCreateUser() {
        val apiService = ForzenApiServiceAlwaysSuccessMock()
        val createUserRepo = CreateUserRepositoryImpl(apiService)
        val createUserData = CreateUserData(
            firstName = TEST_FIRSTNAME,
            lastName = TEST_LASTNAME,
            email = TEST_EMAIL,
            dateOfBirth = TEST_DATE_OF_BIRTH,
            location = TEST_LOCATION,
        )
        runBlocking {
            assertEquals(Unit, createUserRepo.createUser(createUserData))
        }
    }

    @Test
    fun testCreateUserFails() {
        val apiService = ForzenApiServiceAlwaysFailsMock()
        val createUserRepo = CreateUserRepositoryImpl(apiService)
        val createUserData = CreateUserData(
            firstName = TEST_FIRSTNAME,
            lastName = TEST_LASTNAME,
            email = TEST_EMAIL,
            dateOfBirth = TEST_DATE_OF_BIRTH,
            location = TEST_LOCATION,
        )
        runBlocking {
            try {
                createUserRepo.createUser(createUserData)
                fail(FAILED_TO_THROW_MESSAGE)
            } catch (e: Exception) {
                assert(e.message == RESPONSE_CODE_ERROR)
            }
        }
    }

    @Test
    fun testCreateUserThrowsApiCall() {
        val apiService = ForzenApiServiceAlwaysThrowsMock()
        val createUserRepo = CreateUserRepositoryImpl(apiService)
        val createUserData = CreateUserData(
            firstName = TEST_FIRSTNAME,
            lastName = TEST_LASTNAME,
            email = TEST_EMAIL,
            dateOfBirth = TEST_DATE_OF_BIRTH,
            location = TEST_LOCATION,
        )
        runBlocking {
            try {
                createUserRepo.createUser(createUserData)
                fail(FAILED_TO_THROW_MESSAGE)
            } catch (e: Exception) {
                assert(e.message == API_CALL_FAILED)
            }
        }
    }

    @Test
    fun testCreateUserThrowsDuplicateUser() {
        val apiService = ForzenApiServiceDuplicateUserMock()
        val createUserRepo = CreateUserRepositoryImpl(apiService)
        val createUserData = CreateUserData(
            firstName = TEST_FIRSTNAME,
            lastName = TEST_LASTNAME,
            email = TEST_EMAIL,
            dateOfBirth = TEST_DATE_OF_BIRTH,
            location = TEST_LOCATION,
        )
        runBlocking {
            try {
                createUserRepo.createUser(createUserData)
                fail(FAILED_TO_THROW_MESSAGE)
            } catch (e: UserAlreadyExistsException) {
                assert(e.message == USER_DUPLICATE_ERROR_LOG_MESSAGE)
            } catch (e: Exception) {
                fail(FAILED_TO_THROW_EXPECTED_EXCEPTION)
            }
        }
    }

    companion object {
        const val TEST_EMAIL = "axaxotl@gmail.com"
        const val TEST_CODE = "123456"
        const val TEST_FIRSTNAME = "Garen"
        const val TEST_LASTNAME = "Crownguard"
        val TEST_DATE_OF_BIRTH = Date.valueOf("2000-12-12")
        const val TEST_LOCATION = "Androidvania"

        const val API_CALL_FAILED = "Failed to Connect to API Service."
        const val DATABASE_ERROR_ACCESS = "Error Accessing Database."
        const val FAILED_ENTITY_CONVERT = "Could Not Convert Response to Entity."
        const val RESPONSE_CODE_ERROR = "Unexpected Response Code."
        const val FAILED_TO_THROW_MESSAGE = "Call Did Not Throw."
        const val FAILED_TO_THROW_EXPECTED_EXCEPTION = "Call Did Not Throw Expected Exception."
        const val USER_DUPLICATE_ERROR_LOG_MESSAGE = "User Already Exists."
        const val SUCCESS_MOCK_TOKEN =
            "ThisIsASixtyFour(64)CharacterLongStringForTestingNetworkResponse"

        val successDao = object : ForzenDao {
            override suspend fun insert(forzenEntity: ForzenEntity) = Unit
            override suspend fun getLoginToken(): ForzenEntity? =
                ForzenEntity(0L, SUCCESS_MOCK_TOKEN)

            override suspend fun deleteLoginToken() = Unit
        }
    }
}