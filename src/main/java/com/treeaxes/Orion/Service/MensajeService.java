package com.treeaxes.Orion.Service;

import com.treeaxes.Orion.Model.Mensaje;
import com.treeaxes.Orion.Repository.MensajeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    public Mensaje savePrivateMessage(Mensaje mensaje) {
        if(mensaje.getId() == null){
            log.warn("Mensaje privado Rechazado: receiverID invalido ({})", mensaje.getReceiverId());
            throw new IllegalArgumentException("Un mensaje private requiere un Receiver Valido.");
        }

        Mensaje saved = mensajeRepository.save(mensaje);
        log.info("Mensaje privado guardado: ID={}, Desde Usuario={}, Para Usuario={}", saved.getId(), saved.getSenderId(), saved.getReceiverId());
        return saved;
    }

    public Page<Mensaje> obtenerHistorial(Long user1, Long user2, int page, int size) {
        log.info("Obteniendo historico de Mensajes entre Usuario={} y Usuario={} (page={}, size={})", user1, user2, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return mensajeRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByFechaEnvioDesc(user1, user2, user2, user1, pageable);
    }
}