package com.techacademy.controller;

import static org.assertj.core.api.Assertions.allOf;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import com.techacademy.entity.User;
import com.techacademy.service.UserService;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    @BeforeEach
    void beforeEach1() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    private final WebApplicationContext webApplicationContext;

    UserControllerTest(WebApplicationContext context) {
        this.webApplicationContext = context;
    }

    @BeforeEach
    void beforeEach() {
        // Spring Securityを有効にする
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @DisplayName("User更新画面")
    @WithMockUser
    void testGetUser(User user, Object User) throws Exception {
        // HTTPリクエストに対するレスポンスの検証
        MvcResult result = mockMvc.perform(get("/user/update/1/")) // URLにアクセス
            .andExpect(status().isOk()) // ステータスを確認
            .andExpect(model().attributeExists("user")) // Modelの内容を確認
            .andExpect(model().hasNoErrors()) // Modelのエラー有無の確認
            .andExpect(view().name("user/update")) // viewの確認
            .andReturn(); // 内容の取得
        Object user1 = null;
        Object user2 = null;
        Object user3 = null;
        List<Object> userList = Arrays.asList(user1, user2, user3);
        
     // モックの設定
        List<User> userList1 = 
        (List<com.techacademy.entity.User>) when(userService.getUserList());

        // HTTPリクエストに対するレスポンスの検証
        MvcResult result1 = mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userlist"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("userlist", userList))
                .andExpect(model().attribute("userlist", hasSize(3)))
                .andExpect(model().attribute("userlist", hasItem(user1)))
                .andExpect(model().attribute("userlist", hasItem(user2)))
                .andExpect(model().attribute("userlist", hasItem(user3)))
                .andExpect(view().name("user/list"))
                .andReturn();
                             
                

        // userの検証
        // Modelからuserを取り出す
        
    }

    private Object hasProperty(String string, Matcher<String> matcher) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    private Object hasSize(int i) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }
}