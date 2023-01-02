package com.example.forzenbook.repository

import com.example.forzenbook.data.ForzenDao
import com.example.forzenbook.data.ForzenEntity
import com.example.forzenbook.data.toForzenEntity
import com.example.forzenbook.network.ForzenApiService
import com.example.forzenbook.network.LoginRequest
import com.example.forzenbook.network.toCreateAccountRequest

class ForzenRepositoryImpl(
    private val forzenApiService: ForzenApiService,
    private val forzenDao: ForzenDao,
) : ForzenRepository {

    override suspend fun getToken(userName: String, password: String): LoginToken? {
        var entityData: ForzenEntity?
        val token: LoginToken
        try {
            entityData = forzenDao.getLoginToken()
            if (entityData != null) {
                entityData.token?.let {
                    token = LoginToken(it)
                    return token
                }
            } else {
                try {
                    forzenDao.deleteLoginToken()
                    val loginRequest = LoginRequest(userName, password)
                    try {
                        val response = forzenApiService.login(loginRequest)
                        if (response.code() == 200) {
                            try {
                                entityData = response.body()?.toForzenEntity()
                                if (entityData != null) {
                                    try {
                                        forzenDao.insert(entityData)
                                        try {
                                            token = entityData.toLoginToken()
                                            return token
                                        } catch (e: Exception) {
                                            println("Brandon_Test_Data Failed to Convert Entity to Login Token. $e")
                                        }
                                    } catch (e: Exception) {
                                        println("Brandon_Test_Data Failed to Insert entity Data into Database. $e")
                                    }
                                } else {
                                    println("Brandon_Test_Data Entity Data is Null After Conversion.")
                                }
                            } catch (e: Exception) {
                                println("Brandon_Test_Data Failed to Convert Response to Entity. ${response.body()}")
                            }
                        } else {
                            println("Brandon_Test_Data Unexpected Response Code ${response.code()}")
                        }
                    } catch (e: Exception) {
                        println("Brandon_Test_Data Network Call Failed. $e")
                        return null
                    }
                } catch (e: Exception) {
                    println("Brandon_Test_Data Error Deleting Data from Database $e")
                }
            }
        } catch (e: Exception) {
            println("Brandon_Test_Data Error Accessing Database to Check for Data $e")
        }
        return null
    }

    override suspend fun createUser(createUserData: CreateUserData): Boolean {
        try {
            val data = createUserData.toCreateAccountRequest()
            try {
                val response = forzenApiService.createUser(data)
                try {
                    if (response.code() == 200) {
                        return true
                    }
                } catch (e: Exception) {
                    println("Brandon_Test_Data Unexpected response code. Stating Failure. $e")
                }
            } catch (e: Exception) {
                println("Brandon_Test_Data Failed to call api service. $e")
            }
        } catch (e: Exception) {
            println("Brandon_Test_Data Failed to Convert data to Create Request. $e")
        }
        return false
    }

}