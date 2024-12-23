package com.example.vipa.validation;

import com.example.vipa.dto.order.OrderDetailsInputDto;
import com.example.vipa.dto.post.PostPreviewDto;
import com.example.vipa.model.Order;
import com.example.vipa.model.PaymentAccount;
import com.example.vipa.model.Post;
import com.example.vipa.service.PaymentAccountService;
import com.example.vipa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class OrderValidator implements Validator {

    private static final String CARD_NOT_FOUND_MESSAGE = "Карта с указанным номером не найдена.";
    private static final String NOT_ENOUGN_MONEY_MESSAGE = "На карте недостаточно средств.";
    private static final String EMPTY_POST_LIST_MESSAGE = "Вы не выбрали ни одного объявления для заказа.";

    private final PostService postService;
    private final PaymentAccountService paymentAccountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDetailsInputDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderDetailsInputDto order = (OrderDetailsInputDto) target;
        int orderPrice = order.getPostsInOrder().stream()
                .map(postService::getPostEntity)
                .mapToInt(Post::getPrice)
                .sum();
        PaymentAccount paymentAccount = paymentAccountService.getPaymentAccountByCardNumber(order.getCardNumber());
        if (order.getPostsInOrder().isEmpty()) {
            errors.rejectValue("postsInOrder", HttpStatus.BAD_REQUEST.name(), EMPTY_POST_LIST_MESSAGE);
        } else if (paymentAccount == null) {
            errors.rejectValue("cardNumber", HttpStatus.NOT_FOUND.name(), CARD_NOT_FOUND_MESSAGE);
        } else if (paymentAccount.getCurrentSum() < orderPrice) {
            errors.reject(HttpStatus.BAD_GATEWAY.name(), NOT_ENOUGN_MONEY_MESSAGE);
        }
    }
}
