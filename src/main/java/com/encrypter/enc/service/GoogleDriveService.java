package com.encrypter.enc.service;

import com.encrypter.enc.constants.ErrorMessages;
import com.encrypter.enc.constants.SuccessMessages;
import com.encrypter.enc.dto.ResponseDTO;
import com.encrypter.enc.utils.GoogleAuthorizationUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * This service includes the methods for interacting with Google Drive
 */
@Service
public class GoogleDriveService {

    private static final String USER_IDENTIFIER_KEY = "MY_DUMMY_USER";

    private final GoogleAuthorizationUtil googleAuthorizationUtil;

    @Autowired
    public GoogleDriveService(GoogleAuthorizationUtil googleAuthorizationUtil) {
        this.googleAuthorizationUtil = googleAuthorizationUtil;
    }

    /**
     * This method  sign-ins a user to google
     *
     * @param response response to be sent to google
     * @return This returns the response required
     * @throws IOException This is thrown in case of any error
     */
    public ResponseDTO<Object> signInToGoogle(HttpServletResponse response) throws IOException {
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = googleAuthorizationUtil.getGoogleAuthorizationCodeFlow();
        GoogleAuthorizationCodeRequestUrl url = googleAuthorizationCodeFlow.newAuthorizationUrl();
        String redirectURL = url.setRedirectUri(googleAuthorizationUtil.getCallBackUri()).setAccessType("offline").build();
        response.sendRedirect(redirectURL);
        return new ResponseDTO<>(SuccessMessages.GOOGLE_SIGN_IN, Boolean.TRUE);

    }

    /**
     * This method  checks whether a user is logged in or not
     *
     * @return true if authenticated otherwise false
     * @throws IOException This is thrown in case of any error
     */
    public Boolean isUserAuthenticated() throws IOException {
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = googleAuthorizationUtil.getGoogleAuthorizationCodeFlow();
        Credential credential = googleAuthorizationCodeFlow.loadCredential(USER_IDENTIFIER_KEY);
        return Objects.nonNull(credential) && credential.refreshToken();
    }

    /**
     * This method saves the authorization code
     *
     * @param authorizationCode code sent by google after authorization
     * @return appropriate response
     * @throws IOException This is thrown in case of any error
     */
    public ResponseDTO<Object> saveAuthorizationCode(String authorizationCode) throws IOException {
        if (authorizationCode != null) {
            saveToken(authorizationCode);
            return new ResponseDTO<>(SuccessMessages.GOOGLE_OAUTH_CREDENTIALS_SAVED, Boolean.TRUE);
        }
        return new ResponseDTO<>(ErrorMessages.AUTHORIZATION_CODE_NULL, Boolean.FALSE);
    }

    private void saveToken(String code) throws IOException {
        //TODO: Find the right place to save this code
        GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = googleAuthorizationUtil.getGoogleAuthorizationCodeFlow();
        GoogleTokenResponse response = googleAuthorizationCodeFlow.newTokenRequest(code).setRedirectUri(googleAuthorizationUtil.getCallBackUri()).execute();
        googleAuthorizationCodeFlow.createAndStoreCredential(response, USER_IDENTIFIER_KEY);
    }
}
