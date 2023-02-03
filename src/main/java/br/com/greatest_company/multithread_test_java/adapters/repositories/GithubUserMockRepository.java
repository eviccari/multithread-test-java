package br.com.greatest_company.multithread_test_java.adapters.repositories;

import br.com.greatest_company.multithread_test_java.app.domain.dtos.GithubUserDTO;
import br.com.greatest_company.multithread_test_java.app.domain.exceptions.InternalServerErrorException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class GithubUserMockRepository implements GithubUserRepository{
    @Override
    public List<GithubUserDTO> get(int pageSize, int since) throws InternalServerErrorException {
        var nextID = ++since;
        var gusers = new ArrayList<GithubUserDTO>();

        for(int i = 0; i < pageSize; i++) {
            var githubLogin = String.format("GithubLogin_%d", nextID);
            var guser = GithubUserDTO.builder()
                    .id(nextID)
                    .node_id(String.format("NodeID_%d", nextID))
                    .login(githubLogin)
                    .url(String.format("https://api.github.com/%s/api", githubLogin))
                    .html_url(String.format("https://github.com/%s", githubLogin))
            .build();
            gusers.add(guser);
            nextID++;
        }

        return gusers;
    }
}
