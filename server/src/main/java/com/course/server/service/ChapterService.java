package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ChapterMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {
    @Resource
    private ChapterMapper chapterMapper;
    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        //前端用的都是domain dto 而底层增删改查用的都是domain
        List<Chapter> chapterList = chapterMapper.selectByExample(null); //插件分页语句规则：调用startPage方法之后，执行的第一个select语句就会进行分页
        PageInfo pageInfo = new PageInfo(chapterList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapterList, ChapterDto.class);
        pageDto.setList(chapterDtoList);
    }

    /**
     * 保存
     */
    public void save(ChapterDto chapterDto){
        Chapter chapter = CopyUtil.copy(chapterDto, Chapter.class);
        if (StringUtils.isEmpty(chapterDto.getId())){
            this.insert(chapter);
        }else {
            this.update(chapter);
        }
    }

    /**
     * 插入
     */
    private void insert(Chapter chapter){
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }

    /**
     * 更新
     */
    private void update(Chapter chapter){
        chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 删除
     */
    public void delete(String id){
        chapterMapper.deleteByPrimaryKey(id);
    }
}
