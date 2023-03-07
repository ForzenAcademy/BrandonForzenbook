package com.brandon.loginmodulejava.data;

import android.util.Log;

import com.brandon.corejava.data.ForzenDaoJava;
import com.brandon.corejava.data.ForzenEntityJava;
import com.brandon.corejava.network.ForzenApiServiceJava;
import com.brandon.corejava.network.login.LoginResponseJava;

import javax.inject.Inject;

import retrofit2.Response;

public class LoginRepoImplJava implements LoginRepoJava {
    private static final String DATA_ERROR_TAG = "Brandon_Test_Data";
    private static final String API_CALL_FAILED = "Failed to Connect to API Service.";
    private static final String FAILED_ENTITY_CONVERT = "Could Not Convert Response to Entity.";
    private static final String RESPONSE_CODE_ERROR = "Unexpected Response Code.";

    private final ForzenApiServiceJava forzenApiService;
    private final ForzenDaoJava forzenDaoJava;

    @Inject
    public LoginRepoImplJava(ForzenApiServiceJava forzenApiServiceJava, ForzenDaoJava forzenDaoJava) {
        this.forzenApiService = forzenApiServiceJava;
        this.forzenDaoJava = forzenDaoJava;
    }

    @Override
    public void getCode(String email) throws Exception {
        final Response<Void> response;
        try {
            response = forzenApiService.requestEmailCode(email).execute();
            if (!response.isSuccessful()) {
                Log.e(DATA_ERROR_TAG, RESPONSE_CODE_ERROR + " " + response.code());
                throw new Exception(RESPONSE_CODE_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(DATA_ERROR_TAG, API_CALL_FAILED);
            throw new Exception(API_CALL_FAILED);
        }
    }

    @Override
    public void getToken(String email, String code) throws Exception {
        final Response<LoginResponseJava> response;
        try {
            response = forzenApiService.loginWithEmailCode(email, code).execute();
            if (!response.isSuccessful()) {
                Log.e(DATA_ERROR_TAG, RESPONSE_CODE_ERROR + response.code());
                throw new Exception(RESPONSE_CODE_ERROR);
            } else {
                try {
                    assert response.body() != null;
                    final ForzenEntityJava entity = new ForzenEntityJava(
                            response.body().getToken(), System.currentTimeMillis()
                    );
                    Log.e(DATA_ERROR_TAG, "Putting In Database");
                    forzenDaoJava.insert(entity);
                } catch (Exception e) {
                    Log.e(DATA_ERROR_TAG, "Failed Putting In Database");
                    Log.e(DATA_ERROR_TAG, FAILED_ENTITY_CONVERT);
                    throw new Exception(FAILED_ENTITY_CONVERT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(DATA_ERROR_TAG, API_CALL_FAILED);
            throw new Exception(API_CALL_FAILED);
        }
    }
}
