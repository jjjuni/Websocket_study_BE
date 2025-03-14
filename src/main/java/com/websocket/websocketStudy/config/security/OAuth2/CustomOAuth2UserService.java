package com.websocket.websocketStudy.config.security.OAuth2;

import com.websocket.websocketStudy.domain.user.data.User;
import com.websocket.websocketStudy.domain.user.data.enums.Role;
import com.websocket.websocketStudy.domain.user.data.enums.SocialType;
import com.websocket.websocketStudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final OAuth2AuthorizedClientService authorizedClientService;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 로그인 진행 시 키가 되는 필드 값 (PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 서비스 구분 코드 ex) naver, kakao
        String providerCode = userRequest.getClientRegistration().getRegistrationId();

        // 소셜 쪽에서 받은 값들을 Map 형태로 받음
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = null;
        String nickname = null;
        SocialType socialType  = null;

        System.out.println(providerCode);
        if ("kakao".equals(providerCode)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

            email = (String) kakaoAccount.get("email");
            nickname = (String) kakaoProfile.get("nickname");
            socialType = SocialType.KAKAO;
        } else if ("google".equals(providerCode)) {
            email = (String) attributes.get("email");
            nickname = (String) attributes.get("name");
            socialType = SocialType.GOOGLE;
        } else if ("naver".equals(providerCode)) {
            Map<String, Object> naverAccount = (Map<String, Object>) attributes.get("response");

            email = (String) naverAccount.get("email");
            nickname = (String) naverAccount.get("name");
            socialType = SocialType.NAVER;
        }

        User user = saveOrUpdateUser(email, nickname, socialType);

        Map<String, Object> modifiedAttributes = new HashMap<>(attributes);
        modifiedAttributes.put("email", email);

        return new UserPrincipal(user, attributes, userNameAttributeName);
    }

    private User saveOrUpdateUser(String email, String nickname, SocialType socialType){
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    System.out.println("신규 유저. 회원가입 진행");

                    return User.builder()
                            .email(email)
                            .name(nickname)
                            .role(Role.UNKNOWN)
                            .socialType(socialType)
                            .build();
                });

        if (user.getId() != null) {
            System.out.println("기존 유저. 로그인 진행");
        }

        return userRepository.save(user);
    }
}