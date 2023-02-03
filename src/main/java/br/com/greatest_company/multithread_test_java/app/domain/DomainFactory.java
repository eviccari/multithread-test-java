package br.com.greatest_company.multithread_test_java.app.domain;

import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.dtos.GreatestUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.models.GithubUser;
import br.com.greatest_company.multithread_test_java.app.domain.models.GreatestUser;

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

    public static final GreatestUserDTO buildNewFromGithubUserDTO(GithubUserDTO dto) {
        return GreatestUserDTO.builder()
                .legacyID(dto.getId())
                .legacyUrl(dto.getUrl())
                .legacyHtmlUrl(dto.getHtml_url())
                .legacyNodeID(dto.getNode_id())
                .legacyLogin(dto.getLogin())
        .build();
    }

    public static final GreatestUser buildFromDTO(GreatestUserDTO dto) {
        return GreatestUser.builder()
                .id(dto.getId())
                .createdAt(dto.getCreatedAt())
                .legacyURL(dto.getLegacyUrl())
                .legacyHtmlUrl(dto.getLegacyHtmlUrl())
                .legacyID(dto.getLegacyID())
                .newEmail(dto.getNewEmail())
                .legacyNodeID(dto.getLegacyNodeID())
                .legacyLogin(dto.getLegacyLogin())
        .build();
    }

    public static final GreatestUserDTO buildFromModel(GreatestUser model) {
        return GreatestUserDTO.builder()
                .id(model.getId())
                .legacyLogin(model.getLegacyLogin())
                .legacyNodeID(model.getLegacyNodeID())
                .legacyHtmlUrl(model.getLegacyHtmlUrl())
                .legacyUrl(model.getLegacyURL())
                .legacyID(model.getLegacyID())
                .createdAt(model.getCreatedAt())
                .newEmail(model.getNewEmail())
        .build();
    }
}
