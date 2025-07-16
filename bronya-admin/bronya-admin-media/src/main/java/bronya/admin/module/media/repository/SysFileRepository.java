package bronya.admin.module.media.repository;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import bronya.admin.module.media.domain.SysFile;
import bronya.admin.module.media.mapper.SysFileMapper;

@Repository
public class SysFileRepository extends BaseRepository<SysFileMapper, SysFile> {
    public SysFile findByFileName(String fileNameMd5){
        LambdaQueryWrapper<SysFile> query = new LambdaQueryWrapper<>();
        query.eq(SysFile::getFilename,fileNameMd5);
        return this.getOne(query);
    }
    public SysFile findByUrl(String url){
        LambdaQueryWrapper<SysFile> query = new LambdaQueryWrapper<>();
        query.eq(SysFile::getUrl,url);
        return this.getOne(query);
    }
}
