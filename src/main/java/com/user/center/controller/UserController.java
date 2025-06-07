package com.user.center.controller;

import com.user.center.costant.Constants;
import com.user.center.dto.response.ProfileResp;
import com.user.center.exception.BusinessException;
import com.user.center.exception.InputParameterException;
import com.user.center.service.IUserService;
import com.user.center.dto.request.AddUserDetailReq;
import com.user.center.dto.request.LoginReq;
import io.swagger.annotations.*;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/profile")
@Api(tags = "Profile API")
public class UserController {

    @Autowired
    private IUserService userService;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    /**
     * Note:
     * Won't do validation:
     *
     * @param addUserDetailReq
     * @param errors
     * @return
     * @throws BusinessException
     * @throws InputParameterException
     * @RequestBody final AddUserDetailReq addUserDetailReq
     * <p>
     * Will do validation and throw MethodArgumentNotValidException
     * @Validated @RequestBody final AddUserDetailReq addUserDetailReq
     * <p>
     * Will do validation and but won't throw Exception:
     * @Validated @RequestBody final AddUserDetailReq addUserDetailReq, Errors errors
     * <p>
     * Will do validation and throw Exception:
     * @Validated @RequestBody final AddUserDetailReq addUserDetailReq, Errors errors
     * userService.validateInboundRequest(errors);
     */
    @ApiOperation(value = "Create a new user", notes = "Only when the user is new to the system does it succeed to " +
            "create, or will fail")
    @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response = Boolean.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.ERROR_RESPONSE, response =
                    BusinessException.class)})
    @PostMapping(value = "/add", consumes = {"application/json"}, produces = {"application/json"})
    public @ResponseBody
    boolean addUser(@Validated @RequestBody final AddUserDetailReq addUserDetailReq, Errors errors) throws BusinessException, InputParameterException {
        userService.validateInboundRequest(errors);
        Boolean result = userService.add(addUserDetailReq);
//		if (true) {
//			rabbmitMqSenderService.send();
//		}
//        return result;
        return true;
    }

    @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response = Boolean.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.ERROR_RESPONSE, response =
                    BusinessException.class)})
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    Boolean login(@RequestBody LoginReq loginReq) {
        return userService.login(loginReq);
    }

    @ApiOperation(value = "Get a user", notes = "To find a user by username")
    @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response =
            ProfileResp.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.ERROR_RESPONSE, response =
                    BusinessException.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Username of the account", example = "Huang, Hai",
                    paramType = "path", defaultValue = "default username", required = true),
            @ApiImplicitParam(name = "id", value = "ID of the user", example = "1", dataType = "int", defaultValue =
                    "1", required = false)})
    @GetMapping(value = "/{username}/{id}")
    @Transactional(rollbackFor = Exception.class)// TODO remove it!
    public ResponseEntity<ProfileResp> displayProfile(@PathVariable("username") String username,
                                                      @PathVariable("id") Integer id) {
        ProfileResp profileResp = userService.displayProfile(username);
//		// Test
//		profileResp.setSign("1234567897946565461321321");
//		// Test
//		profileResp.setEmail("Attacked");
        logger.info(profileResp);
        // Way 1:
//		HttpHeaders headers = new HttpHeaders(); 
//		headers.add("Cache-Control", "no-cache");
//		return ResponseEntity.status(HttpStatus.SC_OK).headers(headers).body(profileResp);
        // Way 2
//		return ResponseEntity.status(HttpStatus.SC_OK).cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body
//		(profileResp);
        return ResponseEntity.status(HttpStatus.SC_OK)/* .cacheControl(CacheControl.noCache()) */.body(profileResp);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteProfile(@Valid @RequestBody LoginReq loginReq) throws Exception {
        Boolean result = userService.deleteProfile(loginReq);
        return ResponseEntity.status(HttpStatus.SC_OK).body(result);
    }

}

