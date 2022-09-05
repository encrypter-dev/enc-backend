package com.encrypter.enc.service;

import com.encrypter.enc.constants.ErrorMessages;
import com.encrypter.enc.constants.FileMimeTypes;
import com.encrypter.enc.constants.GlobalConstants;
import com.encrypter.enc.constants.SuccessMessages;
import com.encrypter.enc.dto.ResponseDTO;
import com.encrypter.enc.utils.GoogleAuthorizationUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

/**
 * This service includes the methods for interacting with Google Drive
 */
@Slf4j
@Service
public class GoogleDriveService {

    private static final String USER_IDENTIFIER_KEY = "MY_DUMMY_USER";

    @Value("${google.application.name}")
    private String applicationName;

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

    /**
     * This method uploads a file to Google Drive
     *
     * @param file File to be uploaded
     * @return ID of the uploaded file.
     * @throws IOException in case there's any error while uploading the file.
     */
    public String uploadFile(MultipartFile file) throws IOException {
        log.info("FILE_UPLOAD_REQUEST: for file type: {}, file size: {} bytes", file.getContentType(), file.getSize());
        Credential credential = googleAuthorizationUtil.getGoogleAuthorizationCodeFlow().loadCredential(USER_IDENTIFIER_KEY);
        Drive drive = new Drive.Builder(googleAuthorizationUtil.getHttpTransport(), googleAuthorizationUtil.getJsonFactory(), credential)
                .setApplicationName(applicationName).build();

        File googleFile = new File();
        //TODO Add a logic to store filename mapping to handle user adding files with same name
        googleFile.setName(file.getName());
        googleFile.setParents(Collections.singletonList(getUserFolderId()));

        java.io.File javaFile = new java.io.File(file.getName());
        FileOutputStream fileOutputStream = new FileOutputStream(javaFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        FileContent fileContent = new FileContent(file.getContentType(), javaFile);
        File uploadedFile = drive.files().create(googleFile, fileContent).setFields("id, parents").execute();

        log.info("FILE_UPLOADED_TO_DRIVE: for user: {}, fileID: {}", USER_IDENTIFIER_KEY, uploadedFile.getId());
        return uploadedFile.getId();
    }

    /**
     * This method returns the ID of the folder to store files of the user
     *
     * @return enc-data folder ID
     * @throws IOException in case there's any error while fetching/uploading the folder.
     */
    private String getUserFolderId() throws IOException {
        log.info("GET_FOLDER_REQUEST: for user: {}", USER_IDENTIFIER_KEY);
        Credential credential = googleAuthorizationUtil.getGoogleAuthorizationCodeFlow().loadCredential(USER_IDENTIFIER_KEY);
        Drive drive = new Drive.Builder(googleAuthorizationUtil.getHttpTransport(), googleAuthorizationUtil.getJsonFactory(), credential)
                .setApplicationName(applicationName).build();

        FileList fileList = drive.files().list()
                .setQ(String.format("mimeType='%s'", FileMimeTypes.GOOGLE_FOLDER))
                .execute();

        for (File file : fileList.getFiles()) {
            if (file.getName().equals(GlobalConstants.ENC_DATA_DRIVE_FOLDER)) {
                log.info("FOUND_FOLDER: for user: {}, folderId: {}", USER_IDENTIFIER_KEY, file.getId());
                return file.getId();
            }
        }

        log.info("FOLDER_NOT_FOUND_CREATING_NEW_FOLDER: for user: {}", USER_IDENTIFIER_KEY);
        File googleFolder = new File();
        googleFolder.setName(GlobalConstants.ENC_DATA_DRIVE_FOLDER);
        googleFolder.setMimeType(FileMimeTypes.GOOGLE_FOLDER);

        File uploadedFolder = drive.files().create(googleFolder).setFields("id").execute();
        log.info("FOLDER_CREATED: for user: {}, folderID: {}", USER_IDENTIFIER_KEY, uploadedFolder.getId());
        return uploadedFolder.getId();
    }
}
