package br.com.greatest_company.multithread_test_java.app.domain.dtos;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GreatestUserDTO implements Serializable{

    @Getter
    @Setter
    private String id;             

    @Getter
    @Setter	
    private String legacyLogin;    

    @Getter
    @Setter	
    private int legacyID;       

    @Getter
    @Setter	
    private String legacyNodeID;  

    @Getter
    @Setter	
    private String legacyUrl;

    @Getter
    @Setter	
    private String legacyHtmlUrl;

    @Getter
    @Setter	
    private String newEmail;       

    @Getter
    @Setter	
    private Date createdAt;
    
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
