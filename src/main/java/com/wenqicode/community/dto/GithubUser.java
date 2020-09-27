package com.wenqicode.community.dto;

import java.io.Serializable;

/**
 * @author Wenqi Liang
 * @date 2020/9/27
 */
public class GithubUser implements Serializable {

    private static final long serialVersionUID = 1;

    private String name;
    private Long id;
    private String bio;

    @Override
    public String toString() {
        return "GithubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
