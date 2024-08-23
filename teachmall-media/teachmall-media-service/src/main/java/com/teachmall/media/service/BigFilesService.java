package com.teachmall.media.service;

import com.teachmall.base.model.RestResponse;
import com.teachmall.media.model.dto.UploadFileParamsDto;
import com.teachmall.media.model.po.MediaFiles;

/**
 * @Author: 陈靖国
 * @Description: 文件分块传输服务接口
 * @Version: 1.0
 */
public interface BigFilesService {
    public RestResponse<Boolean> checkFile(String fileMd5);
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex);

    public RestResponse uploadChunk(String fileMd5,int chunk,String localChunkFilePath);

    public RestResponse mergechunks(Long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto);
    public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);
}
