package com.course.business.controller.admin;

import com.course.server.domain.Chapter;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.exception.ValidatorException;
import com.course.server.service.ChapterService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {
    public static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);
    @Resource
    private ChapterService chapterService;
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        LOG.info("pageDto: {}",pageDto);
        ResponseDto responseDto = new ResponseDto();
        chapterService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto){
        LOG.info("chapterDto: {}",chapterDto);
        //异常捕捉 一步步的修改过程
        //首先：对传入的数据进行校验
//        ValidatorUtil.require(chapterDto.getName(), "名称");
//        ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
//        ValidatorUtil.length(chapterDto.getCourseId(), "课程ID", 1, 8);
//        存在问题：如果此处不做捕捉异常的话，前端将会一直卡在那个界面无法进行下去，所以进行异常捕捉
//        try {
//            ValidatorUtil.require(chapterDto.getName(), "名称");
//            ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
//            ValidatorUtil.length(chapterDto.getCourseId(), "课程ID", 1, 8);
//        } catch (ValidatorException e){
//            ResponseDto responseDto = new ResponseDto();
//            responseDto.setSuccess(false);
//            responseDto.setMessage(e.getMessage());
//        }
        //存在问题：代码冗余
        ValidatorUtil.require(chapterDto.getName(), "名称");
        ValidatorUtil.require(chapterDto.getCourseId(), "课程ID");
        ValidatorUtil.length(chapterDto.getCourseId(), "课程ID", 1, 8);
        //此时只要代码有异常，且抛出来的异常为ValidatorException的话，就会被validatorExceptionHandler拦截掉，进行处理
        ResponseDto responseDto = new ResponseDto();
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return responseDto;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
        LOG.info("delete id: {}", id);
        ResponseDto responseDto = new ResponseDto();
        chapterService.delete(id);
        return responseDto;
    }
}
