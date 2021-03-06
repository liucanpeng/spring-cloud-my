package com.company.mybatis.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author bin.li
 * @date 2020/10/8
 */
@Getter
@Setter
@Builder
public class LoginResponse {

    private String name;
    private List<String> roles;
    private String token;
}
