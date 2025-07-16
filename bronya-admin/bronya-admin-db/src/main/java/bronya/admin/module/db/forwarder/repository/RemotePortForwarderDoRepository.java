package bronya.admin.module.db.forwarder.repository;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import bronya.admin.module.db.forwarder.domain.RemotePortForwarderDo;
import bronya.admin.module.db.forwarder.mapper.RemotePortForwarderDoMapper;

@Repository
@RequiredArgsConstructor
public class RemotePortForwarderDoRepository extends BaseRepository<RemotePortForwarderDoMapper, RemotePortForwarderDo> {
}
