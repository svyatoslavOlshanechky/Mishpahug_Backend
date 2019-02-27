package telran.ashkelon2018.mishpahug.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.SessionConfiguration;
import telran.ashkelon2018.mishpahug.dto.StaticFieldsDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.service.AccountService;

@RestController
@RequestMapping("/user") // all will be start from user
public class AccountManagementController {
	@Autowired
	AccountService accountService;

	@Autowired
	AccountConfiguration accountConfiguration;

//	HttpServletre


	@Autowired
	SessionConfiguration sessionConfiguration;


	// Authorized requests
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/registration")
	public UserProfileDto register(@RequestHeader("Authorization") String token) {
		// * add a token to the http session in order to check it on other endpoints *//
		sessionConfiguration.setAttributeToken(token);
		return accountService.addUser(token);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/profile")
	public UserProfileDto updateUserProfile(@RequestBody UserProfileDto userProfileDto) {
		String sessionLogin = sessionConfiguration.sessionUserName();
		return accountService.editUserProfile(userProfileDto, sessionLogin);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/profile")
	public UserProfileDto getProfile() {
		String sessionLogin = sessionConfiguration.sessionUserName();
		return accountService.getUserProfile(sessionLogin);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/login")
	public UserProfileDto loginUser(@RequestHeader("Authorization") String token) {

		System.out.println("Token= " + token);

		// * add a token to the http session in order to check it on other endpoints *//
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
		HttpSession httpSession = attributes.getRequest().getSession(true);
//		String hsr = hsr.getHeader(token);
		System.out.println("UserRegSessionId: "+httpSession.getId());
		httpSession.setAttribute("U_TOKEN", token);
		

		sessionConfiguration.setAttributeToken(token);
		return accountService.login(token);
	}

	// Unauthorized requests

	@GetMapping("/staticfields")
	public StaticFieldsDto staticFields(@RequestBody StaticFieldsDto staticFieldsDto) {
		return accountService.getStaticFields(staticFieldsDto);

	}
}