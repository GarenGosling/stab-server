package org.garen.stab.controller;

import org.garen.stab.model.SqlVo;
import org.garen.stab.response.CodeEnum;
import org.garen.stab.response.Response;
import org.garen.stab.service.SqlService;
import org.garen.stab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/stab/sql"})
public class SqlController {

    @Autowired
    SqlService sqlService;
    @Autowired
    UserService userService;

    @RequestMapping(value={"/crud"}, method={RequestMethod.POST})
    public Response sql(@RequestHeader String token, @RequestBody SqlVo vo) {
        return new Response(CodeEnum.OK.code(), CodeEnum.OK.msg(), sqlService.execute(token, vo));
    }

}
