package com.encrypter.enc.controllers.google;

import com.encrypter.enc.constants.GlobalConstants;
import com.encrypter.enc.constants.URIConstants;
import com.encrypter.enc.controllers.ApiV1Controller;
import com.encrypter.enc.dto.ResponseDTO;
import com.encrypter.enc.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class GoogleDriveController extends ApiV1Controller {

    private final GoogleDriveService googleDriveService;

    @Autowired
    public GoogleDriveController(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }

    /**
     * Upload a file to Drive
     *
     * @param file File to be uploaded
     * @return ID of the uploaded file
     * @throws IOException in case of any errors while uploading the file.
     */
    @PostMapping(name = URIConstants.UPLOAD_FILE, consumes = GlobalConstants.MULTIPART_FORM_DATA)
    public ResponseDTO<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseDTO<>(googleDriveService.uploadFile(file));
    }
}
