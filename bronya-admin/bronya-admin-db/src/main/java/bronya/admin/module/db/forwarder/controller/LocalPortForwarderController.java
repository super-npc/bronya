package bronya.admin.module.db.forwarder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bronya.admin.annotation.AmisIds;
import bronya.admin.module.db.amis.dto.AmisIdsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import bronya.admin.module.db.forwarder.domain.LocalPortForwarderDo;
import bronya.admin.module.db.forwarder.repository.LocalPortForwarderDoRepository;
import bronya.admin.module.db.forwarder.service.LocalPortForwarderService;

@Slf4j
@RestController
@RequestMapping("/admin/local-port-forwarder")
@RequiredArgsConstructor
public class LocalPortForwarderController {
    private final LocalPortForwarderDoRepository localPortForwarderDoRepository;
    private final LocalPortForwarderService localPortForwarderService;

    @GetMapping("/re-bind")
    public void reBind(@AmisIds AmisIdsDto idsDto) {
        for (Long id : idsDto.getIds()) {
            LocalPortForwarderDo localPortForwarderDo = localPortForwarderDoRepository.getById(id);
            localPortForwarderService.rebind(localPortForwarderDo);
        }
    }

    @GetMapping("/bind")
    public void bind(@AmisIds AmisIdsDto idsDto) {
        for (Long id : idsDto.getIds()) {
            LocalPortForwarderDo localPortForwarderDo = localPortForwarderDoRepository.getById(id);
            localPortForwarderService.bind(localPortForwarderDo);
        }
    }
}
