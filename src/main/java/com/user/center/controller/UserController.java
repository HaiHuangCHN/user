package com.user.center.controller;

import com.user.center.costant.Constants;
import com.user.center.dto.req.CreateUserDetailReqVO;
import com.user.center.dto.req.LoginReq;
import com.user.center.dto.res.ProfileResVO;
import com.user.center.exception.BusinessException;
import com.user.center.service.IUserService;
import com.user.center.util.JacksonUtils;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/profile")
@Api(tags = "User Profile API")
@Slf4j
@RequiredArgsConstructor
public class UserController {


    private final IUserService userService;

    /**
     * 创建用户
     *
     * @param createUserDetailReqVO
     * @param errors
     * @return
     * @throws BusinessException
     * @RequestBody final CreateUserDetailReqVO createUserDetailReqVO
     * <p>
     * Will do validation and throw MethodArgumentNotValidException
     * @Validated @RequestBody final CreateUserDetailReqVO createUserDetailReqVO
     * <p>
     * Will do validation and but won't throw Exception:
     * @Validated @RequestBody final CreateUserDetailReqVO createUserDetailReqVO, Errors errors
     * <p>
     * Will do validation and throw Exception:
     * @Validated @RequestBody final CreateUserDetailReqVO createUserDetailReqVO, Errors errors
     * userService.validateInboundRequest(errors);
     */
    @ApiOperation(value = "Create a new user", notes = "Only when the user is new to the system does it succeed to " +
            "create, or will fail")
    @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response = Boolean.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.ERROR_RESPONSE, response =
                    BusinessException.class)})
    @PostMapping(value = "/create", consumes = {"application/json"}, produces = {"application/json"})
    public @ResponseBody boolean createUser(@Validated @RequestBody final CreateUserDetailReqVO createUserDetailReqVO, Errors errors) throws BusinessException {
        userService.validateInboundRequest(errors);
        Boolean result = userService.createUser(createUserDetailReqVO);
		if (true) {
            // 发 MQ 通知下游系统，实现诸如：短信发送 / 积分发放 / 优惠券发放等
		}
        return result;
    }

    @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response = Boolean.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.ERROR_RESPONSE, response =
                    BusinessException.class)})
    @PostMapping(value = "/login")
    public @ResponseBody
    Boolean login(@RequestBody LoginReq loginReq) {
        return userService.login(loginReq);
    }

    @ApiOperation(value = "Get a user", notes = "To find a user by username")
    @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response = ProfileResVO.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.ERROR_RESPONSE, response = BusinessException.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "Username of the account", example = "hai.huang.a@outlook.com", paramType = "path", defaultValue = "default username", required = true),
            @ApiImplicitParam(name = "id", value = "ID of the user", example = "1", dataType = "int", defaultValue = "1", required = false)})
    @GetMapping(value = "/{username}")
    public ResponseEntity<ProfileResVO> displayProfile(@PathVariable("username") String username) {
        ProfileResVO profileResVO = userService.displayProfile(username);
//		// Test
//		profileResVO.setSign("1234567897946565461321321");
//		// Test
//		profileResVO.setEmail("Attacked");
        log.info(JacksonUtils.objectToJsonCamel(profileResVO));
        // Way 1:
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Cache-Control", "no-cache");
//		return ResponseEntity.status(HttpStatus.SC_OK).headers(headers).body(profileResVO);
        // Way 2
//		return ResponseEntity.status(HttpStatus.SC_OK).cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body
//		(profileResVO);
        return ResponseEntity.status(HttpStatus.SC_OK)/* .cacheControl(CacheControl.noCache()) */.body(profileResVO);
    }

    @DeleteMapping(value = "/delete")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> deleteProfile(@Valid @RequestBody LoginReq loginReq) throws Exception {
        Boolean result = userService.deleteProfile(loginReq);
        return ResponseEntity.status(HttpStatus.SC_OK).body(result);
    }

}

