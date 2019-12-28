package org.garen.stab.controller;

import org.garen.stab.model.UserVo;
import org.garen.stab.response.CodeEnum;
import org.garen.stab.response.Response;
import org.garen.stab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/stab/user"})
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value={"/signUp"}, method={RequestMethod.POST})
    public Response signUp(@RequestBody UserVo vo) {
        return new Response(CodeEnum.OK.code(), CodeEnum.OK.msg(), userService.signUp(vo));
    }

    @RequestMapping(value={"/signIn"}, method={RequestMethod.POST})
    public Response signIn(@RequestBody UserVo vo) {
        return new Response(CodeEnum.OK.code(), CodeEnum.OK.msg(), userService.signIn(vo));
    }

    @RequestMapping(value={"/signIn/token"}, method={RequestMethod.GET})
    public Response signInToken(@RequestHeader String token) {
        return new Response(CodeEnum.OK.code(), CodeEnum.OK.msg(), userService.signInToken(token));
    }

    @RequestMapping(value={"/audit"}, method={RequestMethod.GET})
    public Response audit(@RequestHeader String token, @RequestParam String phone) {
        return new Response(CodeEnum.OK.code(), CodeEnum.OK.msg(), userService.audit(token, phone));
    }

    @RequestMapping(value={"/del"}, method={RequestMethod.DELETE})
    public Response del(@RequestParam String password, @RequestParam String phone) {
        userService.delUser(password, phone);
        return new Response(CodeEnum.OK.code(), CodeEnum.OK.msg(), null);
    }

    @RequestMapping(value={"/list"}, method={RequestMethod.GET})
    public Response list() {
        return new Response(CodeEnum.OK.code(), CodeEnum.OK.msg(), userService.listAll());
    }

    @RequestMapping(value={"/get"}, method={RequestMethod.GET})
    public Response get(@RequestParam String phone) {
        return new Response(CodeEnum.OK.code(), CodeEnum.OK.msg(), userService.get(phone));
    }
}
