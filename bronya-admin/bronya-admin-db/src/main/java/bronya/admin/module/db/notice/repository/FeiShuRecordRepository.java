package bronya.admin.module.db.notice.repository;

import lombok.RequiredArgsConstructor;
import org.dromara.mpe.base.repository.BaseRepository;
import bronya.admin.module.db.notice.domain.FeiShuRecord;
import bronya.admin.module.db.notice.mapper.FeiShuRecordMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FeiShuRecordRepository extends BaseRepository<FeiShuRecordMapper, FeiShuRecord> {
}
