package com.wenqicode.community.provider;

import com.alibaba.fastjson.JSON;
import com.wenqicode.community.dto.AccessTokenDTO;
import com.wenqicode.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OAuth, 整合GitHub登录
 *
 * @author Wenqi Liang
 * @date 2020/9/27
 */
@Component
public class GithubProvider {

    /**
     * 获取GitHub的access_token
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            // 返回 access_token=325fa775a6af9afd8c11731452a2da536796114b&scope=user&token_type=bearer
            String string = response.body().string();
            return string.split("&")[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取GitHub的用户信息
     *
     * @param accessToken
     * @return
     */
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return JSON.parseObject(string, GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
