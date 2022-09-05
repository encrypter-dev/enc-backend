package com.encrypter.enc.controllers.google;

import com.encrypter.enc.controllers.ApiV1Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleDriveController extends ApiV1Controller {
    //	@GetMapping(value = {"/create"})
//	public void createFile(HttpServletResponse response) throws Exception {
//		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
//
//		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
//				.setApplicationName("googledrivespringbootexample").build();
//
//		File file = new File();
//		file.setName("profile.jpg");
//
//		FileContent content = new FileContent("image/jpeg", new java.io.File("D:\\practice\\sbtgd\\sample.jpg"));
//		File uploadedFile = drive.files().create(file, content).setFields("id").execute();
//
//		String fileReference = String.format("{fileID: '%s'}", uploadedFile.getId());
//		response.getWriter().write(fileReference);
//	}
//
//	@GetMapping(value = {"/uploadinfolder"})
//	public void uploadFileInFolder(HttpServletResponse response) throws Exception {
//		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
//
//		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
//				.setApplicationName("googledrivespringbootexample").build();
//
//		File file = new File();
//		file.setName("digit.jpg");
//		file.setParents(Arrays.asList("1_TsS7arQRBMY2t4NYKNdxta8Ty9r6wva"));
//
//		FileContent content = new FileContent("image/jpeg", new java.io.File("D:\\practice\\sbtgd\\digit.jpg"));
//		File uploadedFile = drive.files().create(file, content).setFields("id").execute();
//
//		String fileReference = String.format("{fileID: '%s'}", uploadedFile.getId());
//		response.getWriter().write(fileReference);
//	}
//
//	@GetMapping(value = {"/listfiles"}, produces = {"application/json"})
//	public @ResponseBody
//	List<FileItemDTO> listFiles() throws Exception {
//		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
//
//		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
//				.setApplicationName("googledrivespringbootexample").build();
//
//		List<FileItemDTO> responseList = new ArrayList<>();
//
//		FileList fileList = drive.files().list().setFields("files(id,name,thumbnailLink)").execute();
//		for (File file : fileList.getFiles()) {
//			FileItemDTO item = new FileItemDTO();
//			item.setId(file.getId());
//			item.setName(file.getName());
//			item.setThumbnailLink(file.getThumbnailLink());
//			responseList.add(item);
//		}
//
//		return responseList;
//	}
//
//	@PostMapping(value = {"/makepublic/{fileId}"}, produces = {"application/json"})
//	public @ResponseBody
//	Message makePublic(@PathVariable(name = "fileId") String fileId) throws Exception {
//		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
//
//		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
//				.setApplicationName("googledrivespringbootexample").build();
//
//		Permission permission = new Permission();
//		permission.setType("anyone");
//		permission.setRole("reader");
//
//		drive.permissions().create(fileId, permission).execute();
//
//		Message message = new Message();
//		message.setMessage("Permission has been successfully granted.");
//		return message;
//	}
//
//	@DeleteMapping(value = {"/deletefile/{fileId}"}, produces = "application/json")
//	public @ResponseBody
//	Message deleteFile(@PathVariable(name = "fileId") String fileId) throws Exception {
//		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
//
//		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
//				.setApplicationName("googledrivespringbootexample").build();
//
//		drive.files().delete(fileId).execute();
//
//		Message message = new Message();
//		message.setMessage("File has been deleted.");
//		return message;
//	}
//
//	@GetMapping(value = {"/createfolder/{folderName}"}, produces = "application/json")
//	public @ResponseBody
//	Message createFolder(@PathVariable(name = "folderName") String folder) throws Exception {
//		Credential cred = flow.loadCredential(USER_IDENTIFIER_KEY);
//
//		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, cred)
//				.setApplicationName("googledrivespringbootexample").build();
//
//		File file = new File();
//		file.setName(folder);
//		file.setMimeType("application/vnd.google-apps.folder");
//
//		drive.files().create(file).execute();
//
//		Message message = new Message();
//		message.setMessage("Folder has been created successfully.");
//		return message;
//	}
//
//	@GetMapping(value = {"/servicelistfiles"}, produces = {"application/json"})
//	public @ResponseBody
//	List<FileItemDTO> listFilesInServiceAccount() throws Exception {
//		Credential cred = GoogleCredential.fromStream(serviceAccountKey.getInputStream());
//
//		GoogleClientRequestInitializer keyInitializer = new CommonGoogleClientRequestInitializer();
//
//		Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, null).setHttpRequestInitializer(cred)
//				.setGoogleClientRequestInitializer(keyInitializer).build();
//
//		List<FileItemDTO> responseList = new ArrayList<>();
//
//		FileList fileList = drive.files().list().setFields("files(id,name,thumbnailLink)").execute();
//		for (File file : fileList.getFiles()) {
//			FileItemDTO item = new FileItemDTO();
//			item.setId(file.getId());
//			item.setName(file.getName());
//			item.setThumbnailLink(file.getThumbnailLink());
//			responseList.add(item);
//		}
//
//		return responseList;
//	}
}
