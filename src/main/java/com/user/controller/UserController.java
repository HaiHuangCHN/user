package com.user.controller;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.user.costant.Constants;
import com.user.dto.request.AddUserReq;
import com.user.dto.request.LoginReq;
import com.user.dto.response.ProfileResp;
import com.user.exception.ErrorResponseException;
import com.user.exception.InputParameterException;
import com.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(path = "/profile")
@Api(tags = "Profile API")
public class UserController {

    @Autowired
    private UserService profileService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "Create a new user", notes = "Only when the user is new to the system does it succeed to create, or will fail")
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response = Boolean.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.PROFILE_ERROR_RESPONSE, response = ErrorResponseException.class) })
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public @ResponseBody boolean addUser(@Valid @RequestBody AddUserReq addUserReq, Errors errors) throws ErrorResponseException, InputParameterException {
        profileService.validateInboundRequest(errors);
        Boolean result = profileService.add(addUserReq);
//		if (true) {
//			rabbmitMqSenderService.send();
//		}
        return result;
    }

    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response = Boolean.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.PROFILE_ERROR_RESPONSE, response = ErrorResponseException.class) })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody Boolean login(@RequestBody LoginReq loginReq) {
        return profileService.login(loginReq);
    }

    @ApiOperation(value = "Get a user", notes = "To find a user by username")
    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response = ProfileResp.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.PROFILE_ERROR_RESPONSE, response = ErrorResponseException.class) })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Username of the account", example = "Huang, Hai", paramType = "path", defaultValue = "default username", required = true),
            @ApiImplicitParam(name = "id", value = "ID of the user", example = "001", dataType = "int", defaultValue = "1", required = false) })
    @RequestMapping(value = "/{username}/{id}", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> displayProfile(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        ProfileResp profileResp = profileService.displayProfile(username);
//		stub: can be removed
//		profileResp.setSign("1234567897946565461321321");
//		stub: can be removed
//		profileResp.setEmail("Attacked");
        logger.info(profileResp.toString());
        // Way 1:
//		HttpHeaders headers = new HttpHeaders(); 
//		headers.add("Cache-Control", "no-cache");
//		return ResponseEntity.status(HttpStatus.SC_OK).headers(headers).body(profileResp);
        // Way 2
//		return ResponseEntity.status(HttpStatus.SC_OK).cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(profileResp);
        return ResponseEntity.status(HttpStatus.SC_OK)/* .cacheControl(CacheControl.noCache()) */.body(profileResp);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteProfile(@Valid @RequestBody LoginReq loginReq) throws Exception {
        Boolean result = profileService.deleteProfile(loginReq);
        return ResponseEntity.status(HttpStatus.SC_OK).body(result);
    }

}
