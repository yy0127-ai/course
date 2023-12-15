package com.course.server.service;

import com.course.server.domain.${Domain};
import com.course.server.domain.${Domain}Example;
import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.${Domain}Mapper;
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

<#list typeSet as type>
    <#if type='Date'>
import java.util.Date;
    </#if>
</#list>

@Service
public class ${Domain}Service {
    @Resource
    private ${Domain}Mapper ${domain}Mapper;
    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        <#list fieldList as field>
            <#if field.nameHump=='sort'>
        ${domain}Example.setOrderByClause("sort asc");
            </#if>
        </#list>
        //前端用的都是domain dto 而底层增删改查用的都是domain
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example); //插件分页语句规则：调用startPage方法之后，执行的第一个select语句就会进行分页
        PageInfo pageInfo = new PageInfo(${domain}List);
        pageDto.setTotal(pageInfo.getTotal());
        List<${Domain}Dto> ${domain}DtoList = CopyUtil.copyList(${domain}List, ${Domain}Dto.class);
        pageDto.setList(${domain}DtoList);
    }

    /**
     * 保存
     */
    public void save(${Domain}Dto ${domain}Dto){
        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto, ${Domain}.class);
        if (StringUtils.isEmpty(${domain}Dto.getId())){
            this.insert(${domain});
        }else {
            this.update(${domain});
        }
    }

    /**
     * 插入
     */
    private void insert(${Domain} ${domain}){
        Date now = new Date();
        <#list fieldList as field>
            <#if field.nameHump =='createdAt'>
        ${domain}.setCreatedAt(now);
            </#if>
            <#if field.nameHump =='updatedAt'>
        ${domain}.setUpdatedAt(now);
            </#if>
        </#list>
        ${domain}.setId(UuidUtil.getShortUuid());
        ${domain}Mapper.insert(${domain});
    }

    /**
     * 更新
     */
    private void update(${Domain} ${domain}){
        <#list fieldList as field>
            <#if field.nameHump =='updatedAt'>
        ${domain}.setUpdatedAt(new Date());
            </#if>
        </#list>
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    /**
     * 删除
     */
    public void delete(String id){
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
