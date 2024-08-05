package com.teachmall.media.service;


import com.teachmall.base.model.PageParams;
import com.teachmall.base.model.PageResult;
import com.teachmall.media.model.dto.QueryMediaParamsDto;
import com.teachmall.media.model.po.MediaFiles;


public interface MediaFileService {


 public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);


}
