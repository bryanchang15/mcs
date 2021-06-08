package com.luanguan.mcs.winding_machine.domain;

import java.util.Optional;

import com.luanguan.mcs.framework.domain.DomainRepository;
import com.luanguan.mcs.shared_kernel.Position;

public interface WindingMachineRepository extends DomainRepository {

    Optional<WindingMachine> findByPosition(Position position);

}
