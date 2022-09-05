package com.encrypter.enc.controllers.google;

import com.encrypter.enc.constants.ErrorMessages;
import com.encrypter.enc.constants.SuccessMessages;
import com.encrypter.enc.constants.URIConstants;
import com.encrypter.enc.controllers.ApiV1Controller;
import com.encrypter.enc.dto.ResponseDTO;
import com.encrypter.enc.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class GoogleAuthController extends ApiV1Controller {

    private final GoogleDriveService googleDriveService;

    @Autowired
    public GoogleAuthController(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }

    @GetMapping(URIConstants.AUTH_STATUS)
    public ResponseDTO<Object> isUserAuthenticated() throws IOException {
        Boolean isUserAuthenticated = googleDriveService.isUserAuthenticated();
        String responseMessage = Boolean.TRUE.equals(isUserAuthenticated) ? SuccessMessages.USER_AUTHENTICATED : ErrorMessages.USER_NOT_AUTHENTICATED;
        return new ResponseDTO<>(responseMessage, isUserAuthenticated);
    }

    @GetMapping(URIConstants.GOOGLE_SIGN_IN)
    public ResponseDTO<Object> googleSignIn(HttpServletResponse response) throws IOException {
        return googleDriveService.signInToGoogle(response);
    }

    @GetMapping(URIConstants.GOOGLE_OAUTH)
    public ResponseDTO<Object> saveAuthorizationCode(HttpServletRequest request) throws IOException {
        String authorizationCode = request.getParameter("code");
        return googleDriveService.saveAuthorizationCode(authorizationCode);
    }
}
