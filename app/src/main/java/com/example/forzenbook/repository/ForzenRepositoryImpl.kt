package com.example.forzenbook.repository

import android.content.Context
import com.example.forzenbook.data.ForzenDao
import com.example.forzenbook.data.ForzenEntity
import com.example.forzenbook.data.toForzenEntity
import com.example.forzenbook.network.ForzenApiService
import com.example.forzenbook.network.LoginRequest
import com.example.forzenbook.network.toCreateAccountRequest

class ForzenRepositoryImpl(
    private val forzenApiService: ForzenApiService,
    private val forzenDao: ForzenDao,
    private val context: Context,
) : ForzenRepository {

    // Will need a timestamp check later on to know when to delete the token
    override suspend fun getToken(userName: String, password: String): LoginToken? {
        var entityData: ForzenEntity?
        var token: LoginToken
        try {
            entityData = forzenDao.getLoginToken()
            if (entityData != null) {
                entityData.token?.let {
                    // should check if its too old here and delete it if it is
                    token = LoginToken(it)
                    return token
                }
            } else {
                try {
                    forzenDao.deleteLoginToken()
                    if (forzenApiService.isNetworkAvailable(context)) {
                        val loginRequest = LoginRequest(userName, password)
                        try {
                            val response = forzenApiService.login(loginRequest)
                            if (response.code() == 200) {
                                try {
                                    entityData = response.body()?.toForzenEntity()
                                    if (entityData != null) {
                                        return try {
                                            forzenDao.insert(entityData)
                                            try {
                                                token = entityData.toLoginToken()
                                                token
                                            } catch (e: Exception) {
                                                println("Brandon_Test_Data Failed to Convert Entity to Login Token. $e")
                                                LoginToken(token = null, isDatabaseError = true)
                                            }
                                        } catch (e: Exception) {
                                            println("Brandon_Test_Data Failed to Insert entity Data into Database. $e")
                                            LoginToken(token = null, isDatabaseError = true)
                                        }
                                    } else {
                                        println("Brandon_Test_Data Entity Data is Null After Conversion.")
                                        return LoginToken(token = null, isDatabaseError = true)
                                    }
                                } catch (e: Exception) {
                                    println("Brandon_Test_Data Failed to Convert Response to Entity. ${response.body()}")
                                    return LoginToken(token = null, isServiceError = true)
                                }
                            } else {
                                println("Brandon_Test_Data Unexpected Response Code ${response.code()}")
                                return LoginToken(token = null, isServiceError = true)
                            }
                        } catch (e: Exception) {
                            println("Brandon_Test_Data Network Call Failed. $e")
                            return LoginToken(token = null, isServiceError = true)
                        }
                    } else {
                        println("Brandon_Test_Data Network No Internet Access.")
                        return LoginToken(token = null, isNetworkError = true)
                    }
                } catch (e: Exception) {
                    println("Brandon_Test_Data Error Deleting Data from Database $e")
                    return LoginToken(token = null, isDatabaseError = true)
                }
            }
        } catch (e: Exception) {
            println("Brandon_Test_Data Error Accessing Database to Check for Data $e")
            return LoginToken(token = null, isDatabaseError = true)
        }
        println("Brandon_Test_Data Reached End of Block without Returning.")
        return LoginToken(token = null, isDatabaseError = true, isServiceError = true)
    }

    override suspend fun createUser(createUserData: CreateUserData): CreateUserErrors {
        try {
            val data = createUserData.toCreateAccountRequest()
            if (forzenApiService.isNetworkAvailable(context)) {
                try {
                    val response = forzenApiService.createUser(data)
                    try {
                        if (response.code() == 200) {
                            return CreateUserErrors()
                        }
                    } catch (e: Exception) {
                        println("Brandon_Test_Data Unexpected response code. Stating Failure. $e")
                        return CreateUserErrors(isServiceError = true)
                    }
                } catch (e: Exception) {
                    println("Brandon_Test_Data Failed to call api service. $e")
                    return CreateUserErrors(isServiceError = true)
                }
            } else {
                println("Brandon_Test_Data No Internet Connection")
                return CreateUserErrors(isNetworkError = true)
            }
        } catch (e: Exception) {
            println("Brandon_Test_Data Failed to Convert data to Create Request. $e")
            return CreateUserErrors(isDataError = true)
        }
        println("Brandon_Test_Data Connected to Internet but still failed to convert data properly or make service call.")
        return CreateUserErrors(isDataError = true, isServiceError = true)
    }

}