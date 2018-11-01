package com.hisense.hinetapi.provider;

import android.content.Context;

import com.hisense.hibeans.net.BaseEntity;
import com.hisense.hinetapi.NetConstant;
import com.hisense.hinetapi.entity.request.CheckPhoneRequest;
import com.hisense.hinetapi.entity.request.CheckVerifyCodeRequest;
import com.hisense.hinetapi.entity.request.CipherBackResetRequest;
import com.hisense.hinetapi.entity.request.LoginRequest;
import com.hisense.hinetapi.entity.request.RegisterRequest;
import com.hisense.hinetapi.entity.request.SendCipherBackCodeRequest;
import com.hisense.hinetapi.entity.request.SendLoginCodeRequest;
import com.hisense.hinetapi.entity.request.SendVerifyCodeRequest;
import com.hisense.hinetapi.entity.response.CheckVerifyCodeResponse;
import com.hisense.hinetapi.entity.response.LoginResponse;
import com.hisense.hinetapi.entity.response.RegisterResponse;
import com.hisense.hinetapi.service.AuthorizeService;
import com.hisense.hiretrofit.builder.RetrofitBuilder;
import com.hisense.hiretrofit.observer.BaseObserver;
import com.hisense.hiretrofit.transformer.IResultTransformer;
import com.hisense.hiretrofit.transformer.RetrofitTransformer;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.ObservableSource;
import okhttp3.RequestBody;

/**
 * Created by liudunjian on 2018/6/19.
 */

public class AuthorizeProvider {

    private Context context;
    private AuthorizeService authorizeService;

    public AuthorizeProvider(Context context) {
        this.context = context;
    }


    public void generateAuthorizeService() {
        this.authorizeService = new RetrofitBuilder()
                .baseURL(NetConstant.SERVICE_BASE_URL)   //120.221.95.249:11181 //192.168.10.126:8080
                .connectTimeout(NetConstant.CONNECT_TIMEOUT_SECOND)
                .readTimeout(NetConstant.READ_TIMEOUT_SECOND)
                .enableDebug(true)
                .headers(this.headers())
                .userAgent(context.getPackageName())
                .builder(AuthorizeService.class);
    }

    private Map<String, String> headers() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json;charset=UTF-8");
        return headers;
    }


    //登录
    public void login(String account, String validateCode, String password, BaseObserver<BaseEntity<LoginResponse>> observer) {
        RetrofitTransformer
                .requestTransformer(
                        new LoginRequest(account, password, validateCode), observer, null,
                        new IResultTransformer<BaseEntity<LoginResponse>>() {
                            @Override
                            public ObservableSource<BaseEntity<LoginResponse>> transformer(RequestBody requestBody) throws Exception {
                                return authorizeService.login(requestBody);
                            }
                        }
                );
    }

    //检测手机是否可用
    public void checkPhoneAvailable(String accountName, String region, BaseObserver<BaseEntity<Boolean>> observer) {
        RetrofitTransformer
                .requestTransformer(
                        new CheckPhoneRequest(accountName, region), observer, null,
                        new IResultTransformer<BaseEntity<Boolean>>() {
                            @Override
                            public ObservableSource<BaseEntity<Boolean>> transformer(RequestBody requestBody) throws Exception {
                                return authorizeService.checkPhoneAvailable(requestBody);
                            }
                        }
                );
    }

    //发送注册验证码
    public void sendVerifyCode(String region, String phone, BaseObserver<BaseEntity<Boolean>> observer) {
        RetrofitTransformer
                .requestTransformer(
                        new SendVerifyCodeRequest(region, phone), observer, null,
                        new IResultTransformer<BaseEntity<Boolean>>() {
                            @Override
                            public ObservableSource<BaseEntity<Boolean>> transformer(RequestBody requestBody) throws Exception {
                                return authorizeService.sendVerifyCode(requestBody);
                            }
                        }
                );
    }

    //发送登录验证码
    public void sendLoginCode(String region, String phone, BaseObserver<BaseEntity<Boolean>> observer) {
        RetrofitTransformer
                .requestTransformer(
                        new SendLoginCodeRequest(phone), observer, null,
                        new IResultTransformer<BaseEntity<Boolean>>() {
                            @Override
                            public ObservableSource<BaseEntity<Boolean>> transformer(RequestBody requestBody) throws Exception {
                                return authorizeService.sendLoginCode(requestBody);
                            }
                        }
                );
    }

    //找回密码发送验证码
    public void sendResetCipherVerifyCode(final String phone, BaseObserver<BaseEntity<Boolean>> observer) {
        RetrofitTransformer
                .requestTransformer(new SendCipherBackCodeRequest(phone), observer, null,
                        new IResultTransformer<BaseEntity<Boolean>>() {
                            @Override
                            public ObservableSource<BaseEntity<Boolean>> transformer(RequestBody requestBody) throws Exception {
                                return authorizeService.sendResetCipherVerifyCode(requestBody);
                            }
                        });
    }

    //验证验证码是否正确(必选先用手机号码调sendcode)
    public void CheckVerifyCode(String region, String phone, String code, BaseObserver<BaseEntity<CheckVerifyCodeResponse>> observer) {
        RetrofitTransformer
                .requestTransformer(
                        new CheckVerifyCodeRequest(region, phone, code), observer, null,
                        new IResultTransformer<BaseEntity<CheckVerifyCodeResponse>>() {
                            @Override
                            public ObservableSource<BaseEntity<CheckVerifyCodeResponse>> transformer(RequestBody requestBody) throws Exception {
                                return authorizeService.checkVerifyCode(requestBody);
                            }
                        }
                );
    }

    //注册
    public void register(String nickname, String password, final String accountName, final String verifyCode, BaseObserver<BaseEntity<RegisterResponse>> observer) {
        RetrofitTransformer
                .requestTransformer(
                        new RegisterRequest(accountName, nickname, password, verifyCode), observer, null,
                        new IResultTransformer<BaseEntity<RegisterResponse>>() {
                            @Override
                            public ObservableSource<BaseEntity<RegisterResponse>> transformer(RequestBody requestBody) throws Exception {
                                return authorizeService.register(requestBody);
                            }
                        }
                );
    }

    //追回密码
    public void resetMyCipher(String accountName, String password, String validateCode, BaseObserver<BaseEntity> observer) {
        RetrofitTransformer
                .requestTransformer(
                        new CipherBackResetRequest(accountName, password, validateCode), observer, null,
                        new IResultTransformer<BaseEntity>() {
                            @Override
                            public ObservableSource<BaseEntity> transformer(RequestBody requestBody) throws Exception {
                                return authorizeService.resetMyCipher(requestBody);
                            }
                        }
                );
    }
}
