package com.demo.example.controller.vo.feeset;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class MyCurrentFeesetVO {

    private FeesetVO feeset;

    private int currPageView;

    private int maxPageView;

    @Data
    @ApiModel
    public static class FeesetVO {

        private Long id;

        private Integer code;

        private String title;

        private String description;

        private Long maxPages;

        private Long maxPageView;
    }

}
