package com.hisense.hinetapi.service;

import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hinetapi.entity.response.CheckVerifyCodeResponse;
import com.hisense.hinetapi.entity.response.LoginResponse;
import com.hisense.hinetapi.entity.response.RegisterResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by liudunjian on 2018/6/19.
 */

public interface AuthorizeService {

    @POST("authentic/login/login")
    Observable<BaseEntity<LoginResponse>> login(@Body RequestBody body);

    //检查用户名或手机是否被注册
    @POST("account/useraccount/checkAvailable")
    Observable<BaseEntity<Boolean>> checkPhoneAvailable(@Body RequestBody body);

    //发送注册验证码
    @POST("account/useraccount/registerCode")
    Observable<BaseEntity<Boolean>> sendVerifyCode(@Body RequestBody body);

    //发送登录验证码
    @POST("authentic/login/loginCode")
    Observable<BaseEntity<Boolean>> sendLoginCode(@Body RequestBody body);

    //验证验证码是否正确(必选先用手机号码调sendcode)
    @POST("user/verify_code")
    Observable<BaseEntity<CheckVerifyCodeResponse>> checkVerifyCode(@Body RequestBody body);

    //注册
    @POST("account/useraccount/registerUserAccount")
    Observable<BaseEntity<RegisterResponse>> register(@Body RequestBody body);

    //找回密码发送验证码
    @POST("account/useraccount/retrieveCode")
    Observable<BaseEntity<Boolean>> sendResetCipherVerifyCode(@Body RequestBody body);

    //找回并重置密码
    @POST("account/useraccount/changepswd")
    Observable<BaseEntity> resetMyCipher(@Body RequestBody body);
}
