package br.com.greatest_company.multithread_test_java.app.domain;

import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.models.GithubUser;

public class DomainFactory {

    private DomainFactory() {}

    public static final GithubUser buildFromDTO(GithubUserDTO dto) {
        return GithubUser.builder()
                .id(dto.getId())
                .url(dto.getUrl())
                .login(dto.getLogin())
                .nodeID(dto.getNode_id())
                .htmlUrl(dto.getHtml_url())
            .build();
    }

    public static final GithubUserDTO buildFromModel(GithubUser model) {
        return GithubUserDTO.builder()
                .id(model.getId())
                .url(model.getUrl())
                .login(model.getLogin())
                .node_id(model.getNodeID())
                .html_url(model.getHtmlUrl())
                .build();
    }
}
