package com.teachmall.media.service;


import com.teachmall.base.model.PageParams;
import com.teachmall.base.model.PageResult;
import com.teachmall.media.model.dto.QueryMediaParamsDto;
import com.teachmall.media.model.dto.UploadFileParamsDto;
import com.teachmall.media.model.dto.UploadFileResultDto;
import com.teachmall.media.model.po.MediaFiles;


public interface MediaFileService {


 public PageResult<MediaFiles> queryMediaFiels(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);
 public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);
 public MediaFiles addMediaFilesToDb(Long companyId,String fileMd5,UploadFileParamsDto uploadFileParamsDto,String bucket,String objectName);

 MediaFiles getFileById(String mediaId);
}
