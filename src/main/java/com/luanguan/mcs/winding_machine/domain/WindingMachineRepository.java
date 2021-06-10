package com.luanguan.mcs.winding_machine.domain;

import com.luanguan.mcs.framework.domain.DomainRepository;
import com.luanguan.mcs.shared_kernel.Position;

import io.vavr.control.Option;

public interface WindingMachineRepository extends DomainRepository {

    Option<WindingMachine> findByPosition(Position position);

}
