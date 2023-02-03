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
import java.util.List;

public class GithubUserHTTPRepository implements GithubUserRepository{
    @Override
    public List<GithubUserDTO> get(int pageSize, int since) throws InternalServerErrorException {
        try {
            var client = HttpClient.newBuilder().build();

            var githubURL = "https://api.github.com/users";
            var uri = new URI(String.format("%s?per_page=%d&since=%d", githubURL, pageSize, since));
            var req = HttpRequest.newBuilder(uri).GET().build();

            var res = client.send(req, HttpResponse.BodyHandlers.ofString(Charset.defaultCharset()));

            if(res.statusCode() != 200) {
                throw new InternalServerErrorException(res.body());
            } else {
                return new Gson().fromJson(res.body(), new TypeToken<List<GithubUserDTO>>() {}.getType());
            }
        }catch (URISyntaxException | IOException | InterruptedException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
