package br.com.greatest_company.multithread_test_java.app.domain.models;

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
public class GithubUser implements Serializable{

    @Getter
    @Setter
    private String login;   

    @Getter
    @Setter	
    private int id;      
	
    @Getter
    @Setter
    private String nodeID;  
	
    @Getter
    @Setter
	private String url;     
	
    @Getter
    @Setter
	private String htmlurl; 

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
    
}
