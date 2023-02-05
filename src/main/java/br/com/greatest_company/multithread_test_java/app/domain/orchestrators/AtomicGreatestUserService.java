package br.com.greatest_company.multithread_test_java.app.domain.orchestrators;

import java.util.concurrent.Callable;

import br.com.greatest_company.multithread_test_java.app.domain.dtos.GreatestUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.services.GreatestUserService;

public class AtomicGreatestUserService implements Callable<String> {

    private GreatestUserDTO dto;
    private GreatestUserService service;

    public AtomicGreatestUserService(GreatestUserDTO dto, GreatestUserService service) {
        this.dto = dto;
        this.service = service;
    }

    @Override
    public String call() throws Exception {
        return this.service.create(dto);
    }
}
