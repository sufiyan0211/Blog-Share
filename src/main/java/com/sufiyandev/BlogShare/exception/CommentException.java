package com.sufiyandev.BlogShare.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentException {
    private String message;
}
