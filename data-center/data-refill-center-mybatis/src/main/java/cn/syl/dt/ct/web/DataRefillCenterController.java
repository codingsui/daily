package cn.syl.dt.ct.web;

import cn.syl.dt.ct.entity.*;
import cn.syl.dt.ct.service.DataRefillCenterService;
import cn.syl.dt.ct.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

@RequestMapping(value = "center",consumes =  MediaType.APPLICATION_JSON_VALUE)
@Controller
@Slf4j
public class DataRefillCenterController {


    @Autowired
    private DataRefillCenterService dataRefillCenterService;

    @PostMapping(value = "/fill")
    public RefillResponse fill(@RequestBody RefillRequest refillRequest) {
        return dataRefillCenterService.fill(refillRequest);
    }

}
