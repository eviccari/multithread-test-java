package br.com.greatest_company.multithread_test_java.app.domain.dtos;

import java.io.Serializable;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GithubUserDTO implements Serializable{

    @Getter
    @Setter
    private String login;   

    @Getter
    @Setter	
    private int id;      
	
    @Getter
    @Setter
    private String node_id;
	
    @Getter
    @Setter
	private String url;     
	
    @Getter
    @Setter
	private String html_url;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
    
}
