package com.course.server.service;

import com.course.server.domain.Section;
import com.course.server.dto.PageDto;
import com.course.server.dto.SectionDto;
import com.course.server.mapper.SectionMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SectionService {
    @Resource
    private SectionMapper sectionMapper;
    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        //前端用的都是domain dto 而底层增删改查用的都是domain
        List<Section> sectionList = sectionMapper.selectByExample(null); //插件分页语句规则：调用startPage方法之后，执行的第一个select语句就会进行分页
        PageInfo pageInfo = new PageInfo(sectionList);
        pageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sectionList, SectionDto.class);
        pageDto.setList(sectionDtoList);
    }

    /**
     * 保存
     */
    public void save(SectionDto sectionDto){
        Section section = CopyUtil.copy(sectionDto, Section.class);
        if (StringUtils.isEmpty(sectionDto.getId())){
            this.insert(section);
        }else {
            this.update(section);
        }
    }

    /**
     * 插入
     */
    private void insert(Section section){
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }

    /**
     * 更新
     */
    private void update(Section section){
        sectionMapper.updateByPrimaryKey(section);
    }

    /**
     * 删除
     */
    public void delete(String id){
        sectionMapper.deleteByPrimaryKey(id);
    }
}
