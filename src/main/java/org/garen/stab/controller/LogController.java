package org.garen.stab.controller;

import org.garen.stab.response.CodeEnum;
import org.garen.stab.response.Response;
import org.garen.stab.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/stab/log"})
public class LogController {

    @Autowired
    LogService logService;

//    @RequestMapping(value={"/save"}, method={RequestMethod.POST})
//    public Response save(@RequestParam String phone, @RequestBody Log log) {
//        logService.save(phone, log);
//        return new Response(CodeEnum.OK.code(), CodeEnum.OK.msg(), log);
//    }

    @RequestMapping(value={"/list"}, method={RequestMethod.GET})
    public Response list(String phone) {
        return new Response(CodeEnum.OK.code(), CodeEnum.OK.msg(), logService.userLogs(phone));
    }

}
