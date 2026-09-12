package com.treeaxes.Orion.Repository;

import com.treeaxes.Orion.Model.Mensaje;
import com.treeaxes.Orion.Model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje,Long> {
    // Encontrar Historial
    Page<Mensaje> findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByFechaEnvioDesc(Long s1, Long r1, Long s2, Long r2, Pageable pageable);

}
