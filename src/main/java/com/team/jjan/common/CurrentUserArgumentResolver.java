package com.team.jjan.common;

import com.team.jjan.common.dto.LogIn;
import com.team.jjan.common.dto.CurrentUser;
import com.team.jjan.jwt.support.JwtProvider;
import com.team.jjan.user.exception.NoSuchEmailException;
import com.team.jjan.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(LogIn.class);
        boolean hasUserType = CurrentUser.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasUserType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String email = jwtProvider.getUserEmail(request);

        return CurrentUser.of(userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchEmailException("사용자 정보를 찾을 수 없습니다.")));
    }
}
